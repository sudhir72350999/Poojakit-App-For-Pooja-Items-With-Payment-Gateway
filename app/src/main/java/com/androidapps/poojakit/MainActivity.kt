package com.androidapps.poojakit

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.PopupMenu
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.fragment.findNavController
import com.androidapps.poojakit.activities.LoginActivity
import com.androidapps.poojakit.databinding.ActivityMainBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.ktx.Firebase

import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import com.google.android.material.navigation.NavigationView


class MainActivity : AppCompatActivity() {
    private lateinit var binding : ActivityMainBinding


    var i =0;
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)



        if(FirebaseAuth.getInstance().currentUser==null){
            startActivity(Intent(this,LoginActivity::class.java))
            finish()


        }
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.fragment_container)
        val navController = navHostFragment!!.findNavController()
        val popupMenu= PopupMenu(this,null)
        popupMenu.inflate(R.menu.bottom_nav)
        binding.bottomBar.setupWithNavController(popupMenu.menu,navController)

        binding.bottomBar.onItemSelected={
            when(it){
                0->{
                    i=0;
                    navController.navigate(R.id.homeFragment)
                }
                1->i=1
                2->i=2
            }
        }
//        navController.addOnDestinationChangedListener(object :NavController.OnDestinationChangedListener{
//            override fun onDestinationChanged(
//                controller: NavController,
//                destination: NavDestination,
//                arguments: Bundle?
//            ) {
//                title= when (destination.id){
//                    R.id.cartFragment -> "My Cart"
//                    R.id.moreFragment ->"My Dashboard"
//                    else ->"Poojakit"
//                }
//            }
//
//        })


    }

    override fun onBackPressed() {
        super.onBackPressed()
        if(i==0){
            finish()
        }
    }
}