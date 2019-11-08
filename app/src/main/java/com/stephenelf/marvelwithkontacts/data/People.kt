package com.stephenelf.marvelwithkontacts.data

import android.net.Uri

data class People(val name:String, val thumbnail: Uri, val phone: String?){

    var isChecked: Boolean=false;

}