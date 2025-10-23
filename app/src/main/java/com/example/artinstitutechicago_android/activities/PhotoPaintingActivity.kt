package com.example.artinstitutechicago_android.activities

import android.os.Bundle
import android.renderscript.ScriptIntrinsicResize
import android.util.Log
import android.view.LayoutInflater
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
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

    fun loadData(){
        // image
        Picasso.get().load(picture.getImageUrl()).into(binding.paintingImageView)

        // texto
        binding.artistTextView.text = picture.artisTitle

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