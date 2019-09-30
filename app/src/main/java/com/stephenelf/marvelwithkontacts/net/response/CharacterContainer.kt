package com.stephenelf.marvelwithkontacts.net.response

class CharacterContainer( offset:Int, limit:Int,  total:Int,  count:Int,
                          val results: List<com.stephenelf.marvelwithkontacts.database.Character>) :Container(offset,limit,total,count){
}