package io.github.kabirnayeem99.ilbo.ui

import androidx.lifecycle.ViewModel
import io.github.kabirnayeem99.ilbo.repositories.NewsRepository

class NewsViewModel(
    val newsRepository: NewsRepository
) : ViewModel() {
}