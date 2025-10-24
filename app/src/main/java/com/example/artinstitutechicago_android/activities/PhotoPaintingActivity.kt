package com.example.artinstitutechicago_android.activities

import android.content.Intent
import android.os.Bundle
import android.renderscript.ScriptIntrinsicResize
import android.util.Log
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.net.toUri
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.artinstitutechicago_android.R
import com.example.artinstitutechicago_android.data.Picture
import com.example.artinstitutechicago_android.data.PictureService
import com.example.artinstitutechicago_android.databinding.ActivityPhotoPaintingBinding
import com.squareup.picasso.Picasso
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class PhotoPaintingActivity : AppCompatActivity() {


    lateinit var binding: ActivityPhotoPaintingBinding
    lateinit var picture: Picture
    lateinit var sharedItem: MenuItem

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityPhotoPaintingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val id = intent.getIntExtra(DetailActivity.EXTRA_PICTURE_ID, -1)

        getPicture(id)

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {

        menuInflater.inflate(R.menu.activity_photo_painiting_menu, menu)

        sharedItem = menu.findItem(R.id.action_share)


        return true
    }

    fun loadData(){
        supportActionBar?.hide()

        // image
        Picasso.get().load(picture.getImageUrl()).into(binding.paintingImageView)
        binding.paintingImageView.setOnClickListener {
            android.R.id.home
            finish()
        }

        // texto
        binding.titleTextView.text = picture.title
        binding.artistTextView.text = picture.artisTitle

        // Button
        binding.platformButton.setOnClickListener {
            val url = picture.getImageUrl()

            val dialog = AlertDialog.Builder(this)
                .setTitle(R.string.photo_painting_dialog_title)
                .setMessage(getString(R.string.photo_painting_dialog_message) + "${picture.title}?")
                .setPositiveButton(R.string.photo_painting_dialog_positive_button) { dialog, which ->
                    setSare(url)
                }
                .setNegativeButton(R.string.photo_painting_dialog_negative_button, null)
                .create()

            dialog.show()
        }
    }

    fun setSare(url: String): Boolean {
        val shareIntent: Intent = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_STREAM, url.toUri())
            type = "image/jpg"
        }
        startActivity(Intent.createChooser(shareIntent, null))
        return true
    }

    fun getPicture(id: Int) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val service = PictureService.getInstace()
                picture = service.getPictureId(id).result
                CoroutineScope(Dispatchers.Main).launch {
                    loadData()
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}