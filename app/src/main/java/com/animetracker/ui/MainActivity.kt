package com.animetracker.ui

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.animetracker.R
import com.animetracker.databinding.ActivityMainBinding
import com.google.android.material.bottomappbar.BottomAppBar

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var bottomAppBar: BottomAppBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        bottomAppBar = binding.bottomAppBar

        binding.bottomAppBar.apply {
            setNavigationOnClickListener {
                Toast.makeText(
                    context,
                    "Navigation Clicked",
                    Toast.LENGTH_SHORT
                ).show()
            }
            setOnMenuItemClickListener(
                Toolbar.OnMenuItemClickListener {
                    when (it.itemId) {
                        R.id.search ->
                            Toast.makeText(context, "Search Clicked", Toast.LENGTH_SHORT).show()
                        R.id.more ->
                            Toast.makeText(context, "More Clicked", Toast.LENGTH_SHORT).show()
                    }
                    return@OnMenuItemClickListener true
                }
            )
        }

        binding.fab.apply {
            setOnClickListener {
                Toast.makeText(context, "FAB clicked", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
