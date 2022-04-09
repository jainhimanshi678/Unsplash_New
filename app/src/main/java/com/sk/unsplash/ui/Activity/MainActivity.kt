package com.sk.unsplash.ui.Activity

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowManager
import com.sk.unsplash.R
import com.sk.unsplash.databinding.ActivityMainBinding
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.sk.unsplash.ui.fragment.HomeFragment
import com.sk.unsplash.interfaces.IMainActivity


class MainActivity : AppCompatActivity(),IMainActivity {

    lateinit var binding: ActivityMainBinding

    /**
     * Holds current fragment.
     */
    private var mCurrentFragment: Fragment? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setBottomNavigationItemSelectedListener()
        setStatusBarColor()
        setCurrentFragment(HomeFragment())
    }

    /**
     * Sets the color of status bar
     */
    private fun setStatusBarColor(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            getWindow().setStatusBarColor(ContextCompat.getColor(this, R.color.white));
        }
    }

    /**
     * Sets bottom navigation item selected listener.
     */
    private fun setBottomNavigationItemSelectedListener() {
        binding.ndMain.setOnNavigationItemReselectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.m_Home -> {
                    switchFragment(HomeFragment())
                }
                /* R.id.actionInsights -> {
                     switchFragment(InsightsFragment())
                     return@setOnItemSelectedListener true
                 }
                 R.id.actionAccount -> {
                     switchFragment(AccountsFragment())
                     return@setOnItemSelectedListener true
                 }*/
            }
            true
        }
    }

    /**
     * Switch the fragment to requested fragment.
     *
     * @param fragment requested fragment
     */
    fun switchFragment(fragment: Fragment) {
        mCurrentFragment = supportFragmentManager.findFragmentById(binding.flMain.id)
        try {
            if (fragment.javaClass.name != mCurrentFragment?.javaClass?.name ?: "") {
                mCurrentFragment = fragment
                supportFragmentManager.beginTransaction()
                    .replace(binding.flMain.id, fragment, fragment::class.java.name)
                    .addToBackStack(fragment::class.java.name).commit()
            }
        } catch (exception: Exception) {

        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        mCurrentFragment = supportFragmentManager.findFragmentById(binding.flMain.id)
        if (mCurrentFragment is HomeFragment
        ) {
            finish()
        }
    }

    private fun setCurrentFragment(fragment:Fragment)=
        supportFragmentManager.beginTransaction().apply {
            replace(binding.flMain.id,fragment)
            commit()
        }

}