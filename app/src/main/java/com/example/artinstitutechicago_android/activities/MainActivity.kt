package com.example.artinstitutechicago_android.activities

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.artinstitutechicago_android.R
import com.example.artinstitutechicago_android.adapters.PictureAdapter
import com.example.artinstitutechicago_android.data.Picture
import com.example.artinstitutechicago_android.data.PictureService
import com.example.artinstitutechicago_android.databinding.ActivityMainBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding
    lateinit var adapter: PictureAdapter

    var originalPictureList: List<Picture> = emptyList()
    var filteredPictureList: List<Picture> = emptyList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        adapter = PictureAdapter(originalPictureList) { position ->
            val picture = originalPictureList[position]
            val intent = Intent(this, DetailActivity::class.java)
            intent.putExtra(DetailActivity.EXTRA_PICTURE_ID, picture.id)
            startActivity(intent)
        }

        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager = LinearLayoutManager(this)

        pictureList()

    }

    override fun onResume() {
        super.onResume()

        adapter.notifyDataSetChanged()
    }

    fun pictureList() {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val service = PictureService.getInstace()
                originalPictureList = service.getAllPictures().result
                filteredPictureList = originalPictureList
                CoroutineScope(Dispatchers.Main).launch {
                    adapter.updateItems(filteredPictureList)
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }


}