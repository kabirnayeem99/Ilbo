package io.github.kabirnayeem99.ilbo.ui.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import io.github.kabirnayeem99.ilbo.R
import io.github.kabirnayeem99.ilbo.ui.MainActivity
import io.github.kabirnayeem99.ilbo.ui.NewsViewModel

class BreakingNewsFragment : Fragment(R.layout.fragment_breaking_news) {

    private lateinit var newsViewModel: NewsViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        newsViewModel = (activity as MainActivity).newsViewModel
    }
}