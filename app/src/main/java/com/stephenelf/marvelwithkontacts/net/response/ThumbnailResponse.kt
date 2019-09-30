package com.stephenelf.marvelwithkontacts.net.response

data class ThumbnailResponse(val path:String, val extension:String) {

    override fun toString(): String {
        val buffer = StringBuffer()
        buffer.append(path).append(".").append(extension)
        return buffer.toString()
    }
}