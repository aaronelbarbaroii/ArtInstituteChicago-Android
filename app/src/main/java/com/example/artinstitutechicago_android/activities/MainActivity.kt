package com.example.artinstitutechicago_android.activities

import android.content.Intent
import android.os.Bundle
import android.se.omapi.Session
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.window.OnBackInvokedDispatcher
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.get
import androidx.core.view.isInvisible
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.artinstitutechicago_android.R
import com.example.artinstitutechicago_android.adapters.PictureAdapter
import com.example.artinstitutechicago_android.data.Picture
import com.example.artinstitutechicago_android.data.PictureService
import com.example.artinstitutechicago_android.databinding.ActivityMainBinding
import com.example.artinstitutechicago_android.utils.SessionManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding
    lateinit var adapter: PictureAdapter

    lateinit var session: SessionManager

    lateinit var spinner: Spinner

    var originalPictureList: List<Picture> = emptyList()
    var filteredPictureList: List<Picture> = emptyList()

    var page = 1
    var limit = 10
    val spinnerList = listOf(10, 20, 30, 40, 50, 60, 70, 80, 90, 100)

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
            setSession()
            intent.putExtra(DetailActivity.EXTRA_PICTURE_ID, picture.id)
            startActivity(intent)
        }

        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager = LinearLayoutManager(this)

        session = SessionManager(this)


        binding.nextImageView.setOnClickListener {
            if(page < 12) {
                page++
                modifyArrow(page)
            }

        }
        binding.previousImageView.setOnClickListener {
            if(page > 1){
                page--
                modifyArrow(page)
            }
        }

        binding.numPageTextView.text = page.toString()



        // spinner
        spinner = binding.limitSpinner
        // Create an ArrayAdapter using the string array and a default spinner layout.
        val arrayAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, spinnerList)
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.adapter = arrayAdapter

        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                // Se llama cuando un elemento es seleccionado

                // Aquí puedes usar 'elementoSeleccionado' para hacer lo que necesites
                limit = spinnerList[position]
                pictureList()

                // Por ejemplo, mostrarlo en un Toast:
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                // Se llama cuando no hay nada seleccionado (por ejemplo, al principio)
                // Generalmente no se necesita en este caso, pero es necesario implementarla
            }
        }


        getSession()

        showArrow(page)
        hideArrow(page)

        pictureList()

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.activity_main_menu, menu)

        val searchView = menu.findItem(R.id.action_search).actionView as SearchView

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String): Boolean {
                filteredPictureList = originalPictureList.filter { it.title.contains(newText, true) }
                adapter.updateItems(filteredPictureList)
                return true
            }
        })

        return true
    }

    override fun onResume() {
        super.onResume()


        adapter.notifyDataSetChanged()
    }


    override fun onPause() {
        super.onPause()
        setSession()
    }

    override fun onStop() {
        super.onStop()
        setSession()
    }

    override fun onDestroy() {
        super.onDestroy()
        setSession()
    }


    fun showArrow(page: Int){
        if(page > 1) {
            binding.previousImageView.visibility = View.VISIBLE
        }
        if(page < 12) {
            binding.nextImageView.visibility = View.VISIBLE
        }
    }

    fun hideArrow(page: Int){
        if(page == 1) {
            binding.previousImageView.visibility = View.INVISIBLE
        }
        if(page == 12) {
            binding.nextImageView.visibility = View.INVISIBLE
        }
    }

    fun modifyArrow(page: Int){
        binding.numPageTextView.text = page.toString()
        showArrow(page)
        hideArrow(page)
        pictureList()
    }

    fun getSession(){
        page = session.getPage()
        limit = session.getLimit()
        binding.numPageTextView.text = page.toString()
       val position = when(limit){
           10 -> 0
           20 -> 1
           30 -> 2
           40 -> 3
           50 -> 4
           60 -> 5
           70 -> 6
           80 -> 7
           90 -> 8
           100 -> 9
           else -> 0
        }
        spinner.setSelection(position)
    }

    fun setSession(){
        session.setPage(page)
        session.setLimit(limit)
    }

    fun pictureList() {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val service = PictureService.getInstace()

                originalPictureList = service.getPageAllPictures(page, limit).result
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