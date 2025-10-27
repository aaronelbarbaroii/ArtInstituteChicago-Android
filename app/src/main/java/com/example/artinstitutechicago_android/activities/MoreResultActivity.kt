package com.example.artinstitutechicago_android.activities

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.artinstitutechicago_android.R
import com.example.artinstitutechicago_android.databinding.ActivityMoreResultBinding
import com.example.artinstitutechicago_android.utils.SessionManager

class MoreResultActivity : AppCompatActivity() {

    lateinit var binding: ActivityMoreResultBinding
    lateinit var session: SessionManager


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMoreResultBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        session = SessionManager(this)

        var page = session.getPage()
        var limit = session.getLimit()


        supportActionBar?.title = getString(R.string.more_result_title)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)


        binding.numberTextView.text = page.toString()
        binding.resultTextView.text = limit.toString()

        binding.pageMinusButton.setOnClickListener {
            if(page > 1) {
                page--
            }
            binding.numberTextView.text = "${page}"
        }

        binding.pagePlusButton.setOnClickListener {
            if(page < 12) {
                page++
            }
            binding.numberTextView.text = "${page}"
        }

        binding.resultSlider.addOnChangeListener { _, value, fromUser ->
            limit = value.toInt()
            binding.resultTextView.text = "${limit}"
        }

        binding.saveButton.setOnClickListener {
            session.setPage(page)
            session.setLimit(limit)
            finish()
        }

    }
}