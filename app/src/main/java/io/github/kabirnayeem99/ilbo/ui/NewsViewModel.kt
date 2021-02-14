package io.github.kabirnayeem99.ilbo.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.github.kabirnayeem99.ilbo.models.NewsResponse
import io.github.kabirnayeem99.ilbo.repositories.NewsRepository
import io.github.kabirnayeem99.ilbo.utils.Resource
import kotlinx.coroutines.launch
import retrofit2.Response

class NewsViewModel(
    private val newsRepository: NewsRepository
) : ViewModel() {

    val breakingNews: MutableLiveData<Resource<NewsResponse>> = MutableLiveData()
    private var breakingNewsPage = 1
    val searchNews: MutableLiveData<Resource<NewsResponse>> = MutableLiveData()
    private var searchNewsPage = 1

    init {
        getBreakingNews("us")
    }

    private fun getBreakingNews(countryCode: String) = viewModelScope.launch {

        // Creates a Mutable Breaking News LiveData with no value assigned to it.
        // Or in other words, creates a loading state
        breakingNews.postValue(Resource.Loading()) // Loading state
        val response = newsRepository.getBreakingNews(countryCode, breakingNewsPage)

        breakingNews.postValue(handleBreakingNewsResponse(response)) // Success or error state

    }

    fun searchForNews(searchQuery: String) = viewModelScope.launch {

        searchNews.postValue(Resource.Loading())

        val response = newsRepository.searchForNews(searchQuery, searchNewsPage)

        searchNews.postValue(handleSearchForNewsResponse(response))
    }

    /**
     * If the result is successful, it return the Resource.Success state with the response data
     * If it is failed it returns Resource.Error state with error message
     */
    private fun handleBreakingNewsResponse(response: Response<NewsResponse>): Resource<NewsResponse> {
        if (response.isSuccessful) {
            response.body()?.let { resultResponse ->
                return Resource.Success(resultResponse)
            }
        }

        return Resource.Error(response.message())
    }

    private fun handleSearchForNewsResponse(response: Response<NewsResponse>): Resource<NewsResponse> {
        if (response.isSuccessful) {
            response.body()?.let { resultResponse ->
                return Resource.Success(resultResponse)
            }
        }

        return Resource.Error(response.message())
    }
}