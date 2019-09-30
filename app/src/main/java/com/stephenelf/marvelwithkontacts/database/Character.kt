package com.stephenelf.marvelwithkontacts.database


import androidx.room.Entity
import androidx.room.PrimaryKey
import com.stephenelf.marvelwithkontacts.net.response.ThumbnailResponse


@Entity

data class Character(@PrimaryKey val id:Long, val name:String, val thumbnail:ThumbnailResponse)

