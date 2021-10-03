package com.azharkova.news.ui

import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.azharkova.news.adapter.NewsAdapter
import com.azharkova.news.data.NewsItem
import com.azharkova.newsapp.android.R
import com.azharkova.newsapp.redux.NewsListStore
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class NewsActivity : AppCompatActivity(){
    private var listView: RecyclerView? = null
    private val store: NewsListStore by lazy {
        NewsListStore()
    }

    private var adapter: NewsAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_news)

        listView = findViewById<RecyclerView>(R.id.news_list)
        listView?.layoutManager = LinearLayoutManager(this)
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                store.observeState().collect {
                   setupNews(it.news)
                }
            }

        }
    }

    override fun onResume() {
        super.onResume()
        store.refresh(true)
    }

    fun setupNews(list: List<NewsItem>) {
        if (adapter == null){
            adapter = NewsAdapter()
        }
        listView?.adapter = adapter
        adapter?.update(list)
    }

}
