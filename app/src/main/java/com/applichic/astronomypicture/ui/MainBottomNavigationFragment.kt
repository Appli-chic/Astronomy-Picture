package com.applichic.astronomypicture.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.applichic.astronomypicture.R
import com.applichic.astronomypicture.databinding.FragmentMainBottomNavigationBinding


class MainBottomNavigationFragment : Fragment() {
    private lateinit var binding: FragmentMainBottomNavigationBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMainBottomNavigationBinding.inflate(inflater, container, false)

        // Handle the bottom navigation through [main_bottom_navigation_graph]
        val navHostFragment =
            childFragmentManager.findFragmentById(R.id.main_bottom_navigation_host) as NavHostFragment?

        if (navHostFragment != null) {
            NavigationUI.setupWithNavController(
                binding.bottomNavigation,
                navHostFragment.navController
            )
        }

        return binding.root
    }
}