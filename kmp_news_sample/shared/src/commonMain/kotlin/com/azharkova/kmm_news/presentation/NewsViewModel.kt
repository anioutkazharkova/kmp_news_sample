package com.azharkova.kmm_news.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras
import com.azharkova.kmm_news.DI
import com.azharkova.kmm_news.data.NewsItem
import com.azharkova.kmm_news.threads.ioD
import com.azharkova.kmm_news.threads.ioDispatcher
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.reflect.KClass

class NewsViewModel : BaseViewModel() {
    private val newsService = DI.newsService
    val newsItems = MutableSharedFlow<List<NewsItem>>(replay = 1, onBufferOverflow = BufferOverflow.DROP_OLDEST)

    fun loadNews() {
        scope.launch {
            val result = withContext(ioD) {
                newsService.loadNews()
            }
            newsItems.tryEmit(result.getOrNull()?.articles.orEmpty())
        }
    }
}
