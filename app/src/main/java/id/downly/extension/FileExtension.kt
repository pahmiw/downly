package id.downly.extension

/**
 * @Author Ahmad Pahmi Created on July 2024
 */

import android.webkit.MimeTypeMap
import java.net.HttpURLConnection
import java.net.URL
import java.io.File

// Function to get the MIME type from the URL
fun getMimeTypeFromUrl(url: String): String? {
    return try {
        val connection = URL(url).openConnection() as HttpURLConnection
        connection.connect()
        val mimeType = connection.contentType
        connection.disconnect()
        mimeType
    } catch (e: Exception) {
        null
    }
}

fun getFileMimeType(file: File): String? {
    val extension = MimeTypeMap.getFileExtensionFromUrl(file.toString())
    return MimeTypeMap.getSingleton().getMimeTypeFromExtension(extension)
}

// Function to get file extension from MIME type
fun getFileExtensionFromMimeType(mimeType: String?): String? {
    return mimeType?.let {
        MimeTypeMap.getSingleton().getExtensionFromMimeType(it)
    }
}