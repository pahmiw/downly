package id.downly.extension

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.pdf.PdfRenderer
import android.media.MediaMetadataRetriever
import android.os.ParcelFileDescriptor
import java.io.File

/**
 * @Author Ahmad Pahmi Created on July 2024
 */


fun createThumbnailFromImage(filePath: String, thumbnailSize: Int): Bitmap? {
    val file = File(filePath)
    if (!file.exists()) {
        return null
    }

    val options = BitmapFactory.Options()
    options.inJustDecodeBounds = true
    BitmapFactory.decodeFile(filePath, options)

    options.inSampleSize = calculateInSampleSize(options, thumbnailSize, thumbnailSize)
    options.inJustDecodeBounds = false
    return BitmapFactory.decodeFile(filePath, options)
}

fun calculateInSampleSize(options: BitmapFactory.Options, reqWidth: Int, reqHeight: Int): Int {
    val height = options.outHeight
    val width = options.outWidth
    var inSampleSize = 1

    if (height > reqHeight || width > reqWidth) {
        val halfHeight = height / 2
        val halfWidth = width / 2

        while ((halfHeight / inSampleSize) >= reqHeight && (halfWidth / inSampleSize) >= reqWidth) {
            inSampleSize *= 2
        }
    }
    return inSampleSize
}

fun createThumbnailFromPdf(filePath: String, thumbnailSize: Int): Bitmap? {
    val file = File(filePath)
    if (!file.exists()) {
        return null
    }

    val fileDescriptor = ParcelFileDescriptor.open(file, ParcelFileDescriptor.MODE_READ_ONLY)
    val pdfRenderer = PdfRenderer(fileDescriptor)
    val page = pdfRenderer.openPage(0)

    val bitmap = Bitmap.createBitmap(thumbnailSize, thumbnailSize, Bitmap.Config.ARGB_8888)
    page.render(bitmap, null, null, PdfRenderer.Page.RENDER_MODE_FOR_DISPLAY)

    page.close()
    pdfRenderer.close()
    fileDescriptor.close()

    return bitmap
}

fun createThumbnailFromVideo(filePath: String): Bitmap? {
    return try {
        val retriever = MediaMetadataRetriever()
        retriever.setDataSource(filePath)
        val bitmap = retriever.getFrameAtTime(1, MediaMetadataRetriever.OPTION_CLOSEST_SYNC)
        retriever.release()
        bitmap
    } catch (e: Exception) {
        null
    }
}

fun createUnsupportedFileTypeImage(width: Int, height: Int, text: String): Bitmap {
    val bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
    val canvas = Canvas(bitmap)
    val paint = Paint()

    canvas.drawColor(Color.LTGRAY)

    paint.color = Color.BLACK
    paint.textSize = 40f
    paint.textAlign = Paint.Align.CENTER

    val xPos = canvas.width / 2
    val yPos = (canvas.height / 2 - (paint.descent() + paint.ascent()) / 2).toInt()

    canvas.drawText(text, xPos.toFloat(), yPos.toFloat(), paint)

    return bitmap
}