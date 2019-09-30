package com.stephenelf.marvelwithkontacts

import android.Manifest
import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.util.DisplayMetrics
import android.util.Log
import android.view.VelocityTracker
import android.view.View
import android.view.ViewGroup
import android.view.animation.AccelerateInterpolator
import android.view.animation.DecelerateInterpolator
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.dynamicanimation.animation.DynamicAnimation
import androidx.dynamicanimation.animation.SpringAnimation
import androidx.dynamicanimation.animation.SpringForce
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.bumptech.glide.request.RequestOptions
import com.orhanobut.logger.Logger
import com.stephenelf.marvelwithkontacts.repositories.Repository
import com.stephenelf.marvelwithkontacts.util.GlideApp
import com.stephenelf.marvelwithkontacts.util.People
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*
import javax.inject.Inject

class MainActivity : AppCompatActivity() {

    private lateinit var peopleAdapter: PeopleAdapter;

    companion object {
        internal var PERMISSIONS_REQUEST_READ_CONTACTS = 1

    }

    @Inject
    lateinit var repository: Repository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        MyApplication.INSTANCE.coolComponent.inject(this)
        checkPermissions()
        setupView()
        startAnimation()
    }

    private fun checkPermissions() {
        if (checkSelfPermission(Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(
                arrayOf(Manifest.permission.READ_CONTACTS),
                PERMISSIONS_REQUEST_READ_CONTACTS
            )
        } else
            fillData()
    }


    private fun setupView() {
        people_view.setLayoutManager(LinearLayoutManager(this, RecyclerView.VERTICAL, false))
        peopleAdapter = PeopleAdapter()
        people_view.setAdapter(peopleAdapter)
    }

    private fun fillData() {
        repository.getPeople().subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : DisposableSingleObserver<List<People>>() {
                override fun onSuccess(people: List<People>) {
                    peopleAdapter.setPeopleList(people)
                }

                override fun onError(e: Throwable) {
                    Log.e("MainActivity", "", e)
                }
            })
    }

    override fun onRequestPermissionsResult(
        requestCode: Int, permissions: Array<String>,
        grantResults: IntArray
    ) {
        if (requestCode == PERMISSIONS_REQUEST_READ_CONTACTS) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission is granted
                fillData()
            } else {
                Toast.makeText(this, R.string.permission_required, Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun startAnimation() {
        val displayMetrics = DisplayMetrics()
        windowManager.defaultDisplay.getMetrics(displayMetrics)

        people_view.setY(displayMetrics.heightPixels.toFloat())
        val peopleAnim = SpringAnimation(
            people_view, DynamicAnimation.TRANSLATION_Y,
            (logo.height + sub_title.height).toFloat()
        )
        val vt = VelocityTracker.obtain()
        // Compute velocity in the unit pixel/second
        vt.computeCurrentVelocity(5)
        peopleAnim.setStartVelocity(vt.yVelocity)

        peopleAnim.spring.stiffness = SpringForce.STIFFNESS_VERY_LOW

        logo.setAlpha(0f)
        sub_title.setAlpha(0f)
        logo.animate().alpha(1f).setInterpolator(DecelerateInterpolator()).setDuration(2000)
            .start()

        sub_title.animate().alpha(1f).setInterpolator(AccelerateInterpolator())
            .setDuration(1000).setStartDelay(2000)
            .setListener(object : AnimatorListenerAdapter() {
                override fun onAnimationEnd(animation: Animator) {
                    super.onAnimationEnd(animation)
                    peopleAnim.start()
                }
            })
            .start()
    }


    private inner class PeopleAdapter : RecyclerView.Adapter<PeopleViewHolder>() {

        private var peopleList: List<People> = ArrayList<People>()
        private val inflater = layoutInflater


        fun setPeopleList(peopleList: List<People>) {
            this.peopleList = peopleList
            notifyDataSetChanged()
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PeopleViewHolder =
            PeopleViewHolder(inflater.inflate(R.layout.recycler_view_item, parent, false))


        override fun onBindViewHolder(holder: PeopleViewHolder, position: Int) {
            holder.onBind(
                peopleList[position].thumbnail, peopleList[position].name,
                peopleList[position].isChecked,
                peopleList[position].phone
            )
            holder.itemView.setOnClickListener {
                if (!peopleList[position].isChecked) {
                    peopleList[position].isChecked = true
                    holder.phoneVisibility(View.VISIBLE)
                } else {
                    peopleList[position].isChecked = false
                    holder.phoneVisibility(View.GONE)
                }
            }
        }

        override fun getItemCount(): Int {
            return peopleList.size
        }
    }

    inner class PeopleViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private var people_name: TextView? = null
        private var people_phone: TextView? = null
        private var people_icon: ImageView? = null
        private var people_checkbox: ImageView? = null

        init {
            people_name = itemView.findViewById(R.id.people_name)
            people_phone = itemView.findViewById(R.id.people_phone)
            people_icon = itemView.findViewById(R.id.people_icon)
            people_checkbox = itemView.findViewById(R.id.people_checkbox)
        }

        fun phoneVisibility(visibility: Int) {
            people_phone?.visibility = visibility
        }


        fun onBind(thumbnail: Uri, text: String, isCheched: Boolean, phone: String?) {
            people_name?.text = text

            Logger.d("url="+thumbnail.toString())
            people_icon?.let {
                GlideApp.with(itemView.context)
                    .load(thumbnail)
                    .placeholder(R.drawable.ic_person_outline_24dp)
                    .centerCrop()
                    .apply(RequestOptions().transform(CircleCrop()))
                    .into(it)
            }

            if (isCheched)
                people_checkbox?.visibility = View.VISIBLE
            else
                people_checkbox?.visibility = View.GONE

            if (phone != null) {
                people_phone?.text = phone
                people_phone?.visibility = View.VISIBLE
            } else
                people_phone?.visibility = View.GONE
        }
    }
}
