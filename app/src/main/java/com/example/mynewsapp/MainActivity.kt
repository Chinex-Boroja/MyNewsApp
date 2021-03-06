package com.example.mynewsapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.doOnPreDraw
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.example.mynewsapp.databinding.ActivityMainBinding
import com.example.mynewsapp.repository.NewsRepository
import com.example.mynewsapp.repository.db.ArticleModelDatabase
import com.example.mynewsapp.viewmodels.NewsViewModel
import com.example.mynewsapp.viewmodels.NewsViewModelFactory
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    lateinit var viewModel: NewsViewModel

    private lateinit var binding: ActivityMainBinding
    private val navController: NavController
        get() = Navigation.findNavController(this, R.id.newsFragment)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_main)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val newsRepository = NewsRepository(ArticleModelDatabase(this))
        val viewModelProvider = NewsViewModelFactory(newsRepository)
        viewModel = ViewModelProvider(this, viewModelProvider).get(NewsViewModel::class.java)

        binding.root.doOnPreDraw {
            binding.bottomNavigationView.setupWithNavController(navController)
        }

//        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottomNavigationView)
//        val navHostFragment = supportFragmentManager
//            .findFragmentById(R.id.news_host) as? NavHostFragment
//        val navController = navHostFragment?.navController
//
//        if (navController != null) {
//            bottomNavigationView.setupWithNavController(navController)
//        }

    }
}