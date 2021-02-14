package io.github.kabirnayeem99.ilbo.ui.fragments

import android.os.Bundle
import android.text.Editable
import android.util.Log
import android.view.View
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import io.github.kabirnayeem99.ilbo.R
import io.github.kabirnayeem99.ilbo.adapter.NewsAdapter
import io.github.kabirnayeem99.ilbo.ui.MainActivity
import io.github.kabirnayeem99.ilbo.ui.NewsViewModel
import io.github.kabirnayeem99.ilbo.utils.Constants.Companion.SEARCH_TIME_DELAY
import io.github.kabirnayeem99.ilbo.utils.Resource
import kotlinx.android.synthetic.main.fragment_search_news.*
import kotlinx.coroutines.Job
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SearchNewsFragment : Fragment(R.layout.fragment_search_news) {
    private lateinit var newsViewModel: NewsViewModel
    private lateinit var newsAdapter: NewsAdapter

    companion object {
        private const val TAG = "SearchNewsFragment"
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        newsViewModel = (activity as MainActivity).newsViewModel

        setUpRecyclerView()

        var job: Job? = null

        etSearch.addTextChangedListener { editable: Editable? ->
            job = MainScope().launch {
                delay(SEARCH_TIME_DELAY)
                editable?.let { editable ->
                    when {
                        editable.toString().isNotEmpty() -> {
                            newsViewModel.searchForNews(editable.toString())
                        }
                    }
                }
            }
        }

        newsViewModel.searchNews.observe(viewLifecycleOwner, { resourceResponse ->
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
        rvSearchNews.apply {
            Log.d(TAG, "setUpRecyclerView: recycler view has been started")
            adapter = newsAdapter
            layoutManager = LinearLayoutManager(activity)
        }


    }
}