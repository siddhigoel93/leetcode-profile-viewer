package com.example.leetpeek

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationView


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.nav_host)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host) as NavHostFragment
        val navController = navHostFragment.navController
        val bottomNav = findViewById<BottomNavigationView>(R.id.nav)
        val drawerLayout = findViewById<DrawerLayout>(R.id.drawer)
        val navView = findViewById<NavigationView>(R.id.nav_view)
        val toolbar = findViewById<Toolbar>(R.id.toolbar)

        bottomNav.setupWithNavController(navController)
        setSupportActionBar(toolbar)

        supportActionBar?.setDisplayHomeAsUpEnabled(false)
        supportActionBar?.setHomeButtonEnabled(false)

        toolbar.setNavigationIcon(R.drawable.hamburger)
        toolbar.setNavigationOnClickListener {
            drawerLayout.openDrawer(navView)
        }
        fun navigateTo(destination: Int) {
            val currentId = navController.currentDestination?.id
            when (currentId) {
                R.id.profilePage -> {
                    when (destination) {
                        R.id.SubmissionPage -> navController.navigate(R.id.action_profilePage_to_SubmissionPage)
                        R.id.BadgesPage -> navController.navigate(R.id.action_profilePage_to_BadgesPage)
                        R.id.SolvedPage -> navController.navigate(R.id.action_profilePage_to_SolvedPage)
                    }
                }
                R.id.SubmissionPage -> {
                    when (destination) {
                        R.id.profilePage -> navController.navigate(R.id.action_SubmissionPage_to_profilePage)
                        R.id.BadgesPage -> navController.navigate(R.id.action_SubmissionPage_to_BadgesPage)
                        R.id.SolvedPage -> navController.navigate(R.id.action_SubmissionPage_to_SolvedPage)
                    }
                }
                R.id.BadgesPage -> {
                    when (destination) {
                        R.id.profilePage -> navController.navigate(R.id.action_BadgesPage_to_profilePage)
                        R.id.SubmissionPage -> navController.navigate(R.id.action_BadgesPage_to_SubmissionPage)
                        R.id.SolvedPage -> navController.navigate(R.id.action_BadgesPage_to_SolvedPage)
                    }
                }
                R.id.SolvedPage -> {
                    when (destination) {
                        R.id.profilePage -> navController.navigate(R.id.action_SolvedPage_to_profilePage)
                        R.id.SubmissionPage -> navController.navigate(R.id.action_SolvedPage_to_SubmissionPage)
                        R.id.BadgesPage -> navController.navigate(R.id.action_SolvedPage_to_BadgesPage)
                    }
                }
            }
            drawerLayout.closeDrawers()
        }
        navView.setNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.profilePage -> navigateTo(R.id.profilePage)
                R.id.SubmissionPage -> navigateTo(R.id.SubmissionPage)
                R.id.BadgesPage -> navigateTo(R.id.BadgesPage)
            }

            drawerLayout.closeDrawers()
            true
        }
    }

}