package io.github.kabirnayeem99.ilbo.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import io.github.kabirnayeem99.ilbo.R
import io.github.kabirnayeem99.ilbo.db.ArticleDatabase
import io.github.kabirnayeem99.ilbo.repositories.NewsRepository
import io.github.kabirnayeem99.ilbo.ui.fragments.BreakingNewsFragment
import io.github.kabirnayeem99.ilbo.ui.fragments.SavedNewsFragment
import io.github.kabirnayeem99.ilbo.ui.fragments.SearchNewsFragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    lateinit var newsViewModel: NewsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)

        setUpBottomNavBar()

        setUpViewModel()
    }

    private fun setUpViewModel() {
        val newsRepository = NewsRepository(ArticleDatabase(this))
        val newsViewModelProviderFactory = NewsViewModelProviderFactory(newsRepository)

        newsViewModel =
            ViewModelProvider(this, newsViewModelProviderFactory).get(NewsViewModel::class.java)


    }

    private fun makeCurrentFragment(fragment: Fragment) =
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.flFragment, fragment)
            commit()
        }

    private fun setUpBottomNavBar() {

        val breakingNewsFragment = BreakingNewsFragment()
        val savedNewsFragment = SavedNewsFragment()
        val searchNewsFragment = SearchNewsFragment()

        makeCurrentFragment(breakingNewsFragment)

        bottomNavigationView.setOnNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.icBreakingNews -> makeCurrentFragment(breakingNewsFragment)
                R.id.icFavorite -> makeCurrentFragment(savedNewsFragment)
                R.id.icSearchNews -> makeCurrentFragment(searchNewsFragment)
            }
            true
        }
    }

}