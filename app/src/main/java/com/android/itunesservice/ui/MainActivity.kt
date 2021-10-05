package com.android.itunesservice.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.android.itunesservice.databinding.ActivityMainBinding
import com.android.itunesservice.utils.DEFAULT_CATEGORY
import com.android.itunesservice.utils.DEFAULT_QUERY
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    var QUERY_STATE = DEFAULT_QUERY
    var CATEGORY_STATE = DEFAULT_CATEGORY

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

    }
}