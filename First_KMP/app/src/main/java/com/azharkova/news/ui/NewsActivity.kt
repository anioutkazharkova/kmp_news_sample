package com.azharkova.news.ui

import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.azharkova.news.R
import com.azharkova.news.adapter.NewsAdapter
import com.azharkova.news.data.NewsItem
import com.azharkova.news.presentation.NewsPresenter
import com.azharkova.news.view.INewsListView

class NewsActivity : AppCompatActivity(), INewsListView {
    private var listView: RecyclerView? = null

    private var adapter: NewsAdapter? = null
    private var _presenter: NewsPresenter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        _presenter = NewsPresenter()
        _presenter?.attachView(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_news)

        listView = findViewById<RecyclerView>(R.id.news_list)
        listView?.layoutManager = LinearLayoutManager(this)
    }

    override fun onResume() {
        super.onResume()
        _presenter?.loadData()
    }

    override fun setupNews(list: List<NewsItem>) {
        if (adapter == null){
            adapter = NewsAdapter()
        }
        listView?.adapter = adapter
        adapter?.update(list)
    }

}
