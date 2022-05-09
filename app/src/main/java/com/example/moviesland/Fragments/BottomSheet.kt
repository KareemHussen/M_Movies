package com.example.moviesland.Fragments

import EpisodeAdapter
import SeriesAdapter
import android.os.Bundle
import android.util.Log
import android.view.ContextMenu
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.moviesland.R
import com.example.moviesland.Models.SeriesModel.Details.Season
import com.example.moviesland.Models.SeriesModel.Details.SeriesDetails
import com.example.moviesland.Models.SeriesModel.SeriesResult
import com.example.moviesland.uii.ViewModel
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.android.synthetic.main.bottom_sheet_season.*
import kotlinx.android.synthetic.main.bottom_sheet_season.view.*
import kotlinx.android.synthetic.main.episode_layout.*
import kotlinx.android.synthetic.main.episode_layout.view.*
import kotlinx.android.synthetic.main.season_item.view.*
import java.util.zip.Inflater

class BottomSheet(var list: List<Season> , var series : SeriesResult) : BottomSheetDialogFragment() {


    var seriesAdapter = SeriesAdapter()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var view = inflater.inflate(R.layout.bottom_sheet_season , container , false)



            view.rvSeasons.layoutManager = LinearLayoutManager(requireContext() , LinearLayoutManager.VERTICAL , false)
            view.rvSeasons.adapter = seriesAdapter

            seriesAdapter.submitList(list)

            seriesAdapter.setOnItemClickListner {

                val arrayList : ArrayList<Int> = ArrayList()

                for ( i in 1 until  it.episode_count + 1){

                    arrayList.add(i)
                    Log.d("yyyy" , i.toString())
                }

                val episodeBottomSheet = EpisodeBottomSheet(arrayList , series, it.season_number)
                this@BottomSheet.dismiss()
                episodeBottomSheet.show(parentFragmentManager , "Gamed")


            }



        return view





    }
}