package com.stephenelf.marvelwithkontacts.net.response

data class CharacterResponse (val code:Int, val status:String,val etag:String,val copyright:String,
                              val attributionText:String, val attributionHTML:String, val data: CharacterContainer) {
}