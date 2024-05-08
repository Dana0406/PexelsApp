package com.example.pexelsapp.data

import android.content.ContentValues
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.os.Handler
import android.os.Looper
import android.provider.MediaStore
import android.widget.Toast
import com.example.pexelsapp.data.utils.Constants
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.io.OutputStream
import java.net.HttpURLConnection
import java.net.URL
import javax.inject.Inject

class ImageDownloader @Inject constructor(){

    suspend fun downloadImage(imageUrl: String, context: Context) {
        withContext(Dispatchers.IO) {
            try {
                val url = URL(imageUrl)
                val connection: HttpURLConnection = url.openConnection() as HttpURLConnection
                connection.doInput = true
                connection.connect()
                val input = connection.inputStream
                val bitmap = BitmapFactory.decodeStream(input)

                val savedImageUri = saveImageToGallery(bitmap, context)

                if (savedImageUri != null) {
                    showSuccessToast(context)
                } else {
                }
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
    }

    private fun saveImageToGallery(bitmap: Bitmap, context: Context): Uri? {
        val savedImageUri: Uri?
        val filename = "${System.currentTimeMillis()}.jpg"
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            val resolver = context.contentResolver
            val contentValues = ContentValues().apply {
                put(MediaStore.MediaColumns.DISPLAY_NAME, filename)
                put(MediaStore.MediaColumns.MIME_TYPE, Constants.IMAGE_FORMAT)
                put(MediaStore.MediaColumns.RELATIVE_PATH, Environment.DIRECTORY_PICTURES)
            }
            val imageUri = resolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues)
            savedImageUri = imageUri
            imageUri?.let { uri ->
                resolver.openOutputStream(uri)?.use { outputStream ->
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream)
                }
            }
        } else {
            val imagesDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)
            val image = File(imagesDir, filename)
            val stream: OutputStream = FileOutputStream(image)
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream)
            stream.flush()
            stream.close()
            savedImageUri = Uri.fromFile(image)
        }
        return savedImageUri
    }

    private fun showSuccessToast(context: Context) {
        val message = Constants.DOWNLOAD_MESSAGE
        val coroutineScope = CoroutineScope(Dispatchers.Main)

        coroutineScope.launch {
            Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
        }
    }
}