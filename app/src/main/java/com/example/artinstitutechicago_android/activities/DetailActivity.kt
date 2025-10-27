package com.example.artinstitutechicago_android.activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.artinstitutechicago_android.R
import com.example.artinstitutechicago_android.data.Picture
import com.example.artinstitutechicago_android.data.PictureService
import com.example.artinstitutechicago_android.databinding.ActivityDetailBinding
import com.example.artinstitutechicago_android.utils.SessionManager
import com.squareup.picasso.Picasso
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DetailActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_PICTURE_ID = "PICTURE_ID"
    }
    lateinit var binding: ActivityDetailBinding
    lateinit var picture: Picture
    lateinit var session: SessionManager
    lateinit var favoriteItem: MenuItem

    var isFavorite = false


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        session = SessionManager(this)

        val id = intent.getIntExtra(EXTRA_PICTURE_ID, -1)

        isFavorite = session.isFavorite(id.toString())

        getPicture(id)

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {

        menuInflater.inflate(R.menu.activity_detail_menu, menu)

        favoriteItem = menu.findItem(R.id.action_favorite)
        setFavoriteMenu()

        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId) {
            R.id.action_favorite ->{
                isFavorite = !isFavorite
                if(isFavorite){
                    session.setFavorite(picture.id.toString(), picture.id.toString())
                }
                else {
                    session.setFavorite(picture.id.toString(), "")
                }
                setFavoriteMenu()
                true
            }

            android.R.id.home -> {
                finish()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }



    fun loadData(){

        supportActionBar?.title = picture.title
        supportActionBar?.subtitle = picture.artisTitle
        supportActionBar?.setDisplayHomeAsUpEnabled(true)


        // Image
        Picasso.get().load(picture.getImageUrl()).into(binding.thumbnailImageView)
        binding.thumbnailImageView.setOnClickListener {
            val intent = Intent(this, PhotoPaintingActivity::class.java)
            intent.putExtra(EXTRA_PICTURE_ID, picture.id)
            startActivity(intent)
        }


        // Description
        val description: String? = picture.description
        if(description == null) {
            binding.descriptionTextView.setText(R.string.detail_text_description)
        } else {
            binding.descriptionTextView.text = picture.description
        }

        binding.publicationHistoryTextView.text = picture.publication

        val publication = picture.publication
        if(publication == null) {
            binding.publicationHistoryTextView.setText(R.string.detail_text_publication)
        } else {
            binding.descriptionTextView.text = picture.publication
        }

        binding.exhibitionHistoryTextView.text = picture.exhibition

        val exhibition = picture.exhibition
        if(exhibition == null) {
            binding.exhibitionHistoryTextView.setText(R.string.detail_text_exhibition)
        } else {
            binding.exhibitionHistoryTextView.text = picture.exhibition
        }
    }


    fun setFavoriteMenu() {
        if (isFavorite) {
            favoriteItem.setIcon(R.drawable.ic_menu_favorite_selected)
        } else {
            favoriteItem.setIcon(R.drawable.ic_menu_favorite)
        }
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