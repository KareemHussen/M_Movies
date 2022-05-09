package com.example.moviesland.Fragments

import EpisodeAdapter
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.moviesland.Models.SeriesModel.SeriesResult
import com.example.moviesland.R
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.android.synthetic.main.bottom_sheet_season.view.*
import kotlinx.android.synthetic.main.episode_layout.view.*

class EpisodeBottomSheet(var list : List<Int> , var series : SeriesResult , var season : Int) : BottomSheetDialogFragment() {

    var episodeAdapter = EpisodeAdapter()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var view = inflater.inflate(R.layout.episode_layout, container, false)


        view.rvEpisode.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        view.rvEpisode.adapter = episodeAdapter

        episodeAdapter.submitList(list)



        return view
    }
}