package com.one.kumaran.restpokemon.ui.main

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.LinearLayout
import com.one.kumaran.restpokemon.R
import com.one.kumaran.restpokemon.repository.model.Pokemon
import com.one.kumaran.restpokemon.repository.state.MainState
import com.one.kumaran.restpokemon.ui.detail.DetailActivity

import kotlinx.android.synthetic.main.activity_main.*
import timber.log.Timber

class MainActivity : AppCompatActivity(), MainView {

    private lateinit var progressLayout: LinearLayout
    private lateinit var mainAdapter: MainAdapter
    lateinit var presenter: MainPresenterContract

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)
        setUpUi()
        presenter = MainPresenter()
        presenter.attach(this)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onDestroy() {
        presenter.detach()
        super.onDestroy()
    }

    override fun showLoading(mainState: MainState) {
        updateProgress(mainState)
    }

    override fun populateData(mainState: MainState, pokemon: Pokemon) {
        updateProgress(mainState)
        mainAdapter.addItem(pokemon)
        mainAdapter.notifyDataSetChanged()
    }

    override fun dataLoadCompleted(mainState: MainState) {
        updateProgress(mainState)
    }

    override fun errorOccured (error: Throwable, mainState: MainState) {
        Timber.e(error)
        updateProgress(mainState)
        //TODO: Error handling
    }

    private fun updateProgress(mainState: MainState) {
        mainAdapter.isLoading = mainState.isLoading
        if (mainState.isLoading) progressLayout.visibility = View.VISIBLE
        else progressLayout.visibility = View.GONE
    }

    private fun setUpUi() {
        val recyclerView: RecyclerView = findViewById(R.id.recylerPokeList)
        progressLayout = findViewById(R.id.loadMoreLayout)
        recyclerView.layoutManager = LinearLayoutManager(this)
        mainAdapter = MainAdapter(clickListener(), loadMoreListener(), recyclerView)
        recyclerView.adapter = mainAdapter
        recyclerView.addItemDecoration(DividerItemDecoration(this,
                LinearLayoutManager.VERTICAL))
    }

    private fun clickListener() = object: MainAdapter.ItemClickListener {
        override fun itemClicked(pokemon: Pokemon) {
            startActivity(DetailActivity(pokemon))
        }
    }

    private fun loadMoreListener() = object: MainAdapter.LoadMoreListener {
        override fun loadMore() {
            presenter.loadMore()
        }
    }
}
