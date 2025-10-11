package com.example.leetpeek

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.ViewCompat
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationView

class MainActivity : AppCompatActivity() {

    lateinit var appBarConfiguration: AppBarConfiguration
    lateinit var drawerLayout: DrawerLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, true)
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.nav_host)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host) as NavHostFragment
        val navController = navHostFragment.navController

        val bottomNav = findViewById<BottomNavigationView>(R.id.nav)
        val navView = findViewById<NavigationView>(R.id.nav_view)
        drawerLayout = findViewById(R.id.drawer)
        val toolbar = findViewById<Toolbar>(R.id.toolbar)

        setSupportActionBar(toolbar)

        val prefs = getSharedPreferences("leetpeek_prefs", Context.MODE_PRIVATE)
        val username = prefs.getString("username", null)


        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.landingPage,
                R.id.profilePage,
                R.id.SubmissionPage,
                R.id.BadgesPage,
                R.id.SolvedPage
            ),
            drawerLayout
        )

        setupActionBarWithNavController(navController, appBarConfiguration)
        bottomNav.setupWithNavController(navController)
        navController.addOnDestinationChangedListener { _, _, _ ->
            supportActionBar?.title = "LeetPeek"
        }

        bottomNav.setOnItemSelectedListener { item ->
            val currentUsername = prefs.getString("username", null)


            when {
                (currentUsername.isNullOrEmpty() && navController.currentDestination?.id == R.id.landingPage) -> {
                    Toast.makeText(this, "Please enter a username first.", Toast.LENGTH_SHORT).show()
                     false
                }
                navController.currentDestination?.id == R.id.landingPage -> {
                    Toast.makeText(this, "Please click 'View Profile' to continue.", Toast.LENGTH_SHORT).show()
                     false
                }
                else -> {
                    NavigationUI.onNavDestinationSelected(item, navController)
                    true
                }
            }
        }

        navView.setNavigationItemSelectedListener { menuItem ->
            val currentUsername = prefs.getString("username", null)

            when {
                (currentUsername.isNullOrEmpty() &&  navController.currentDestination?.id == R.id.landingPage) -> {
                    Toast.makeText(this, "Please enter a username first.", Toast.LENGTH_SHORT).show()
                    drawerLayout.closeDrawers()
                    false
                }
                navController.currentDestination?.id == R.id.landingPage -> {
                    Toast.makeText(this, "Please click 'View Profile' to continue.", Toast.LENGTH_SHORT).show()
                    drawerLayout.closeDrawers()
                    false
                }
                else -> {
                    NavigationUI.onNavDestinationSelected(menuItem, navController)
                    drawerLayout.closeDrawers()
                    true
                }
            }
        }

    }

    override fun onSupportNavigateUp(): Boolean {
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host) as NavHostFragment
        val navController = navHostFragment.navController
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }
}
