package com.bpointer.productsmvvm

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.bpointer.productsmvvm.databinding.ActivityMainBinding
import com.bpointer.productsmvvm.features.Products
import com.bpointer.productsmvvm.features.favorites.FavProducts
import com.bpointer.productsmvvm.util.setStatusBarColor
import com.google.android.material.navigation.NavigationBarView

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    companion object {
        // var navigationNew: BottomNavigationView? = null
        var currentScreen = ""
    }
    private val mOnNavigationItemSelectedListener =
        NavigationBarView.OnItemSelectedListener { item ->

            when (item.itemId) {
                R.id.menuProduct -> {

                    if (currentScreen != "Products") {
                        currentScreen = "Products"
                        replaceFragment(Products(), "Products")
                        return@OnItemSelectedListener true
                    } else
                        return@OnItemSelectedListener false

                }
                R.id.menuFav -> {

                    if (currentScreen != "FavProducts") {
                        currentScreen = "FavProducts"
                        replaceFragment(FavProducts(), "FavProducts")
                        return@OnItemSelectedListener true
                    } else
                        return@OnItemSelectedListener false

                }

                else -> {
                    return@OnItemSelectedListener true
                }
            }
            //false
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_main)
        MyApplication.mMyComponent.inject(this)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        currentScreen = ""
        initView()
//        val fragment = Products()
//        replaceFragment(fragment, "Products")
    }

    fun initView() {
        setStatusBarColor(Color.parseColor("#FFFFFF"))

        binding.navigationView.inflateMenu(R.menu.nav_menu)
        binding.navigationView.setOnItemSelectedListener (mOnNavigationItemSelectedListener)
        binding.navigationView.selectedItemId = R.id.menuProduct;


    }

    fun setTabVisibility(visibility: Int){
        binding.navigationView.visibility = visibility

    }


    private fun replaceFragment(fragment: Fragment, fragmentTag: String) {
        try {
            supportFragmentManager
                .beginTransaction()
                .replace(R.id.container, fragment, fragmentTag)
                .commitAllowingStateLoss()
        } catch (e: Exception) {
            e.printStackTrace()
        } catch (e: Error) {
            e.printStackTrace()
        }
    }

}