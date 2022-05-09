package com.example.moviesland.Adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.moviesland.Fragments.*

class FragmentAdapter(fragmentManger : FragmentManager , lifecycle : Lifecycle) : FragmentStateAdapter(fragmentManger , lifecycle)  {
    override fun getItemCount(): Int {
        return 5
    }

    override fun createFragment(position: Int): Fragment {
        return when(position){
            0 -> HomeFragment()

            1 -> MoviesFragment()

            2 -> SeriesFragment()

            3 -> KidsFragment()

            else -> {
                FavouriteFragment()
            }
        }
    }


}