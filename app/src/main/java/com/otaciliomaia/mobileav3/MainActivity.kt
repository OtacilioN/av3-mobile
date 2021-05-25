package com.otaciliomaia.mobileav3

import android.Manifest
import android.content.ContentUris
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {
    companion object {
        const val REQUEST_CODE = 42
    }

    var imageList: MutableList<ImageItem> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val recyclerView = findViewById<View>(R.id.recycler_view) as RecyclerView

        recyclerView.adapter = ImagesAdapter(imageList)
        recyclerView.layoutManager = GridLayoutManager(this@MainActivity, 2)
        recyclerView.setHasFixedSize(true)
        ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE), REQUEST_CODE)
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        if (requestCode == REQUEST_CODE && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            loadImageItems()
        } else if(requestCode == REQUEST_CODE && grantResults[0] == PackageManager.PERMISSION_DENIED) {
            Toast.makeText(this, "Permission denied", Toast.LENGTH_LONG).show()
        }
    }

    fun loadImageItems() {
        val projection = arrayOf(MediaStore.Images.Media._ID, MediaStore.Images.Media.DISPLAY_NAME)
        val sortOrder = "${MediaStore.Images.Media.DATE_ADDED} DESC"
        contentResolver.query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, projection, null, null, sortOrder)?.use { cursor ->
            val idColumns = cursor.getColumnIndexOrThrow(MediaStore.Images.Media._ID)
            val nameColumns = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DISPLAY_NAME)
            while (cursor.moveToNext()) {
                val id = cursor.getLong(idColumns)
                val name = cursor.getString(nameColumns)
                val contentUris = ContentUris
                        .withAppendedId(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, id)
                imageList.add(ImageItem(contentUris, name))
            }
            val recyclerView = findViewById<View>(R.id.recycler_view) as RecyclerView
            recyclerView.adapter?.notifyDataSetChanged()
        }
    }
}