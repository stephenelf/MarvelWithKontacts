package com.stephenelf.marvelwithkontacts.data.net.response

class CharacterContainer( offset:Int, limit:Int,  total:Int,  count:Int,
                          val results: List<com.stephenelf.marvelwithkontacts.data.database.Character>) :Container(offset,limit,total,count){
}