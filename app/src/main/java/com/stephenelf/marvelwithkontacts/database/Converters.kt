package com.stephenelf.marvelwithkontacts.database

import androidx.room.TypeConverter
import com.stephenelf.marvelwithkontacts.net.response.ThumbnailResponse

class Converters {
    @TypeConverter
    fun stringToObject(string: String): ThumbnailResponse {
        val parts = string.split(",".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
        return ThumbnailResponse(parts[0], parts[1])
    }

    @TypeConverter
    fun objectToString(res: ThumbnailResponse): String {
        val buffer = StringBuffer()
        buffer.append(res.path).append(",").append(res.extension)
        return buffer.toString()
    }
}