package com.incrowdsports.task.ui

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.commitNow
import com.incrowdsports.task.databinding.ActivityMainBinding
import com.incrowdsports.task.ui.list.FixtureListFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val binding: ActivityMainBinding by lazy { ActivityMainBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        if (savedInstanceState == null) {
            supportFragmentManager.commitNow {
                replace(binding.fragmentContainerView.id, FixtureListFragment.newInstance())
            }
        }
    }

    override fun onBackPressed() {
        if (supportFragmentManager.backStackEntryCount > 0) supportFragmentManager.popBackStack()
        else super.onBackPressed()
    }
}