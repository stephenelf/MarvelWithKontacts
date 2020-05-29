package com.stephenelf.marvelwithkontacts.domain.repositories

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.orhanobut.logger.Logger
import com.stephenelf.marvelwithkontacts.R
import com.stephenelf.marvelwithkontacts.data.net.MarvelAPI
import com.stephenelf.marvelwithkontacts.data.net.response.CharacterResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException


class RemoteRepository(val context: Context, val marvelAPI: MarvelAPI) {

    fun getCharacters(): LiveData<List<com.stephenelf.marvelwithkontacts.data.database.Character>> {
        val data: MutableLiveData<List<com.stephenelf.marvelwithkontacts.data.database.Character>> =
            MutableLiveData<List<com.stephenelf.marvelwithkontacts.data.database.Character>>()

        val now = System.currentTimeMillis()
        val md5 = calculateHash(
            now.toString(), context.getString(R.string.marvel_private_key),
            context.getString(R.string.marvel_public_key)
        )

        marvelAPI.getCharacters(
            context.getString(R.string.marvel_public_key), now, md5,
            MarvelAPI.LIMIT, 0
        ).enqueue(object : Callback<CharacterResponse> {
            override fun onResponse(
                call: Call<CharacterResponse>,
                response: Response<CharacterResponse>
            ) {
                Logger.e("response="+response.body()?.data?.results)
                data.value=response.body()?.data?.results
            }

            override fun onFailure(call: Call<CharacterResponse>, t: Throwable) {

            }
        })
        return data
    }
}

private fun calculateHash(timeStamp: String, privateKey: String, publicKey: String): String {
    val buffer = StringBuffer()
    buffer.append(timeStamp).append(privateKey).append(publicKey)
    val digest = generateDigest(buffer.toString())
    return toHexString(digest)
}


private fun generateDigest(input: String): ByteArray? {
    try {
        val md = MessageDigest.getInstance("MD5")
        md.update(input.toByteArray())

        return md.digest()
    } catch (nsae: NoSuchAlgorithmException) {
        nsae.printStackTrace()
    }

    return null
}

private fun toHexString(digest: ByteArray?): String {
    if (digest == null) {
        return ""
    }
    val stringBuilder = StringBuilder()
    for (digestByte in digest) {
        stringBuilder.append(
            Integer.toString(
                (digestByte.toInt() and 0xff) + 0x100,
                16
            ).substring(1)
        )
    }
    return stringBuilder.toString()
}
