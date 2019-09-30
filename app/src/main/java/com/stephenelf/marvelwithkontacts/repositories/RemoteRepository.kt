package com.stephenelf.marvelwithkontacts.repositories

import android.content.Context
import com.stephenelf.marvelwithkontacts.R
import com.stephenelf.marvelwithkontacts.net.MarvelAPI
import com.stephenelf.marvelwithkontacts.net.response.CharacterResponse
import io.reactivex.Single
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException

class RemoteRepository(val context: Context, val marvelAPI: MarvelAPI) {

    fun getCharacters(): Single<CharacterResponse> {
        val now = System.currentTimeMillis()
        val md5 = calculateHash(
            now.toString(), context.getString(R.string.marvel_private_key),
            context.getString(R.string.marvel_public_key)
        )

        return marvelAPI.getCharacters(
            context.getString(R.string.marvel_public_key), now, md5,
            MarvelAPI.LIMIT, 0
        )
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
            stringBuilder.append(Integer.toString((digestByte.toInt() and 0xff) + 0x100, 16).substring(1))
        }
        return stringBuilder.toString()
    }
}