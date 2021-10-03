package com.azharkova.newsapp.redux

import com.azharkova.news.data.NewsItem
import com.azharkova.newsapp.DI
import com.azharkova.newsapp.service.ErrorResponse
import com.azharkova.newsapp.service.NewsService
import com.azharkova.newsapp.threads.defaultDispatcher
import com.azharkova.newsapp.threads.uiDispatcher
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

sealed class NewsSideEffect : Effect {
    data class Error(val error: ErrorResponse) : NewsSideEffect()
}

class NewsListStore : Store<NewsStoreState, NewsAction, NewsSideEffect>, NewsActionType {
    private val scope: CoroutineScope
    private val newsService: NewsService by lazy {
        DI.instance.newsService
    }

    init {
        val job = SupervisorJob()
        scope = CoroutineScope(uiDispatcher + job)
    }

    private val state = MutableStateFlow(NewsStoreState(false, emptyList()))
    private val sideEffect = MutableSharedFlow<NewsSideEffect>()

    override fun observeState(): StateFlow<NewsStoreState> {
        return state
    }

    override fun observeSideEffect(): Flow<NewsSideEffect> {
        return sideEffect
    }

    override fun dispatch(action: NewsAction) {
        val oldState = state.value
        val newState = when (action) {
            is NewsAction.Refresh -> {
                if (oldState.progress) {
                    scope.launch { sideEffect.emit(NewsSideEffect.Error(ErrorResponse("In progress"))) }
                    oldState
                } else {
                    scope.launch(defaultDispatcher) {
                        loadNews(action.forceLoad) }
                    oldState.copy(progress = true)
                }
            }
            is NewsAction.Data -> {
                if (oldState.progress) {
                    val selected = oldState.selectedItem?.let {
                        if (action.feeds.contains(it)) it else null
                    }
                    NewsStoreState(false, action.feeds, selected)
                } else {
                    scope.launch { sideEffect.emit(NewsSideEffect.Error(ErrorResponse("Unexpected action"))) }
                    oldState
                }
            }
            is NewsAction.SelectItem -> {
                if (action.feed == null || oldState.news.contains(action.feed)) {
                    oldState.copy(selectedItem = action.feed)
                } else {
                    scope.launch { sideEffect.emit(NewsSideEffect.Error(ErrorResponse("Unknown feed"))) }
                    oldState
                }
            }
            is NewsAction.Error -> {
                if (oldState.progress) {
                    scope.launch { sideEffect.emit(NewsSideEffect.Error(action.error)) }
                    NewsStoreState(false, oldState.news)
                } else {
                    scope.launch { sideEffect.emit(NewsSideEffect.Error(ErrorResponse("Unexpected action"))) }
                    oldState
                }
            }
        }
        if (newState != oldState) {
            state.value = newState
        }
    }

    override fun refresh(forceLoad: Boolean) {
        dispatch(NewsAction.Refresh(forceLoad))
    }

    override fun data(feeds: List<NewsItem>) {
        dispatch(NewsAction.Data(feeds))
    }

    override fun selectItem(feed: NewsItem?) {
        dispatch(NewsAction.SelectItem(feed))
    }

    override fun error(error: ErrorResponse) {
        dispatch(NewsAction.Error(error))
    }

    private suspend fun loadNews(forceLoad: Boolean) {
        val news = newsService.getNewsList()
        news.content?.let {
            withContext(uiDispatcher) {
                dispatch(NewsAction.Data(it.articles))
            }
        }
        news.errorResponse?.let {
            withContext(uiDispatcher) {
                dispatch(NewsAction.Error(it))
            }
        }
    }
}
