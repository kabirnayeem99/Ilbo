package io.github.kabirnayeem99.ilbo.ui.fragments

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import io.github.kabirnayeem99.ilbo.R
import io.github.kabirnayeem99.ilbo.adapter.NewsAdapter
import io.github.kabirnayeem99.ilbo.ui.MainActivity
import io.github.kabirnayeem99.ilbo.ui.NewsViewModel
import io.github.kabirnayeem99.ilbo.utils.Resource
import kotlinx.android.synthetic.main.fragment_breaking_news.*

class BreakingNewsFragment : Fragment(R.layout.fragment_breaking_news) {

    companion object {
        private const val TAG = "BreakingNewsFragment"
    }

    private lateinit var newsViewModel: NewsViewModel
    private lateinit var newsAdapter: NewsAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        newsViewModel = (activity as MainActivity).newsViewModel

        setUpRecyclerView()

        newsViewModel.breakingNews.observe(viewLifecycleOwner, { resourceResponse ->
            when (resourceResponse) {
                is Resource.Success -> {
                    hideProgressBar()
                    resourceResponse.data?.let { newsResponse ->
                        newsAdapter.differ.submitList(newsResponse.articles)
                    }
                }

                is Resource.Error -> {
                    hideProgressBar()
                    resourceResponse.message?.let { errorMessage ->
                        Log.e(TAG, errorMessage)
                    }
                }

                is Resource.Loading -> {
                    showProgressBar()
                }
            }
        })
    }

    private fun hideProgressBar() {
        paginationProgressBar.visibility = View.INVISIBLE
    }

    private fun showProgressBar() {
        paginationProgressBar.visibility = View.VISIBLE
    }

    private fun setUpRecyclerView() {
        newsAdapter = NewsAdapter()

        rvBreakingNews.apply {
            Log.d(TAG, "setUpRecyclerView: recycler view has been started")
            adapter = newsAdapter
            layoutManager = LinearLayoutManager(activity)
        }

    }
}