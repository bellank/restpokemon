package com.one.kumaran.restpokemon.ui.main

import com.one.kumaran.restpokemon.repository.network.ApiRepository
import com.one.kumaran.restpokemon.repository.state.MainState
import com.one.kumaran.restpokemon.ui.Presenter
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import timber.log.Timber

class MainPresenter : MainPresenterContract {
    lateinit var mainState: MainState
    lateinit var mainView: MainView
    val compositeDisposable = CompositeDisposable()

    override fun attach(view: MainView) {
        mainView = view
        mainState = MainState(20, 0, true)
        mainView.showLoading(mainState)
        retrieveData(mainState.apply { isLoading = true })
    }

    override fun detach() {
        compositeDisposable.dispose()
    }

    override fun loadMore() {
        retrieveData(mainState.apply { isLoading = true; offset += 20 })
    }

    private fun retrieveData(mainState: MainState) {
        compositeDisposable.add(ApiRepository().getAllPokemonByOffset(mainState.limit, mainState.offset)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        { pokemon -> mainView.populateData(mainState, pokemon) },
                        { e -> Timber.e(e)
                            mainState.isLoading = false
                            mainView.showError(mainState) },
                        { mainState.isLoading = false
                            mainView.showError(mainState) }))
    }
}

interface MainPresenterContract: Presenter<MainView> {
    fun loadMore()
}