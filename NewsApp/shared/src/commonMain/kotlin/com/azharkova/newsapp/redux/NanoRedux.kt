package com.azharkova.newsapp.redux

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow

interface StoreState
interface Action
interface Effect

interface Store<S : StoreState, A : Action, E : Effect> {
    fun observeState(): StateFlow<S>
    fun observeSideEffect(): Flow<E>
    fun dispatch(action: A)
}