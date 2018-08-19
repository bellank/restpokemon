package com.one.kumaran.restpokemon.repository.state

data class MainState(var limit: Int, var offset: Int, var isLoading: Boolean) {}

//TODO if we decide to go for MVI
sealed class Input {
    object LoadData: Input()
    object LoadMoreData: Input()
}

sealed class Output {
    object LoadingData: Output()
    object LoadingCompleted: Output()
    data class LoadingError(val throwable: Throwable): Output()
}
