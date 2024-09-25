package owner.yacer.mynewsapp.fragments

import android.app.ProgressDialog
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.core.view.GravityCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.SnapHelper
import kotlinx.android.synthetic.main.activity_home_page.*
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import owner.yacer.mynewsapp.*
import owner.yacer.mynewsapp.activities.HomeActivity
import owner.yacer.mynewsapp.adapters.FavoriteNewsAdapter
import owner.yacer.mynewsapp.adapters.HeadlinesAdapter
import owner.yacer.mynewsapp.models.Item
import owner.yacer.mynewsapp.adapters.itemAdapter
import owner.yacer.mynewsapp.api.ApiClient
import owner.yacer.mynewsapp.api.NewsService
import java.lang.RuntimeException
import java.util.*


class HomeFragment : Fragment(R.layout.fragment_home) {
    companion object {
        lateinit var headlinesAdapter: HeadlinesAdapter
        lateinit var newsAdapter: itemAdapter
    }

    private val headlines = listOf<Item>()
    private var currentPage = 2;
    private val newsService = ApiClient.getInstance().create(NewsService::class.java)
    private lateinit var progressBox: ProgressDialog

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val drawer = (activity as HomeActivity).mDrawerLayout

        // Init the progress box to be used later
        progressBox = ProgressDialog(context)
        with(progressBox) {
            setCancelable(false)
            setMessage("يرجى الإنتظار")
        }

        // Setup headline news
        progressBox.show()
        lifecycleScope.launch(Dispatchers.IO) {
            val headlinesDeferred = async { getNews(1) }
            val newsDeferred = async { getNews(2) }
            try {
                val headlines = headlinesDeferred.await()
                val news = newsDeferred.await()
                withContext(Dispatchers.Main) {
                    Log.e(TAG, "onViewCreated: We are here in main context", )
                    setupHeadlinesRecyclerView(headlines)
                    setupNewsRecyclerView(news)
                }
            } catch (e: Exception) {
                e.printStackTrace()
            } finally {
                progressBox.dismiss()
            }
        }

        more_btn_home.setOnClickListener {
            drawer.openDrawer(GravityCompat.START)
        }

    }

    private suspend fun getNews(page: Int): List<Item> {
        val response = newsService.getNews("aljazeera.net", page)
        if (!(response.isSuccessful))
            throw RuntimeException(response.message())
        val news = response.body()!!.articles
        // Check if any headline has been liked before
        val items = LinkedList<Item>()
        for (article in news) {
            var liked = false
            for (favorite_article in FavoriteNewsAdapter.articles)
                if (favorite_article.url == article.url) liked = true
            items.add(Item(article, liked))
        }
        return items
    }

    private fun setupHeadlinesRecyclerView(list: List<Item>) {
        headlinesAdapter = HeadlinesAdapter()
        headlinesAdapter.headlines = list
        val mFirstLayoutManager =
            LinearLayoutManager(this.context, LinearLayoutManager.HORIZONTAL, false)
        First_rv.adapter = headlinesAdapter
        First_rv.layoutManager = mFirstLayoutManager
        val snapHelper: SnapHelper = PagerSnapHelper()
        snapHelper.attachToRecyclerView(First_rv)
    }

    private fun setupNewsRecyclerView(list: List<Item>) {
        newsAdapter = itemAdapter()
        newsAdapter.articles = list
        val newsLayoutManager = LinearLayoutManager(this.context)
        Second_rv.adapter = newsAdapter
        Second_rv.layoutManager = newsLayoutManager
    }
}

