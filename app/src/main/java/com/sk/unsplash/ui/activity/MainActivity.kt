package com.sk.unsplash.ui.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowManager
import com.sk.unsplash.R
import com.sk.unsplash.databinding.ActivityMainBinding
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.sk.unsplash.interfaces.IMainActivity
import com.sk.unsplash.models.photo.PhotoResponseItem
import com.sk.unsplash.ui.fragment.*

class MainActivity : AppCompatActivity(), IMainActivity {

    private lateinit var binding: ActivityMainBinding

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
    private fun setStatusBarColor() {
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.statusBarColor = ContextCompat.getColor(this, R.color.teal_700)
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
                 R.id.m_Category -> {
                     switchFragment(CategoryFragment())
                 }
                 R.id.m_Setting -> {
                     switchFragment((SettingFragment()))
                 }
            }
        }
    }

    /**
     * Switch the fragment to requested fragment.
     *
     * @param fragment requested fragment
     */
     override fun switchFragment(fragment: Fragment) {
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

    private fun setCurrentFragment(fragment: Fragment) =
        supportFragmentManager.beginTransaction().apply {
            replace(binding.flMain.id, fragment)
            commit()
        }

    override fun setPhotoLongPressListener(photo: PhotoResponseItem) {
        val photoDialogFragment = PhotoDialogFragment().newInstance(photo)
        photoDialogFragment.show(supportFragmentManager, "filter")
        //switchFragment(PhotoDialogFragment().newInstance(photo))
    }

    override fun setPhotoOnClickListener(photo: PhotoResponseItem) {
        switchFragment(ExploreFragment().newInstance(photo))
    }

    override fun sendLink(photo: String) {
        val sendIntent: Intent = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_TEXT, photo)
            type = "text/plain"
        }
        val shareIntent = Intent.createChooser(sendIntent, null)
        startActivity(shareIntent)
    }

}