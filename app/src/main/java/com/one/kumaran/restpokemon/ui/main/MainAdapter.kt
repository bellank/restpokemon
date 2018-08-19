package com.one.kumaran.restpokemon.ui.main

import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.one.kumaran.restpokemon.R
import com.one.kumaran.restpokemon.repository.model.Pokemon
import kotlinx.android.synthetic.main.item_main.view.*

class MainAdapter(val itemClickListener: ItemClickListener, val loadMoreListener: LoadMoreListener,
                  val recyclerView: RecyclerView):
        RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    val dataSet = ArrayList<Pokemon>()
    var isLoading = false

    init {
        setUpLoadMore()
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(viewGroup.context)
                .inflate(R.layout.item_main, viewGroup, false) as View
        val viewHolder = NormalViewHolder(view)
        view.setOnClickListener { itemClickListener.itemClicked(dataSet.get(viewHolder.adapterPosition)) }
        return viewHolder
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is NormalViewHolder) {
            Glide.with(holder.view.context)
                    .load(dataSet.get(position).url)
                    .into(holder.image)
            holder.name.setText(dataSet.get(position).name)
            holder.types.setText(dataSet.get(position).types)
        }
    }

    override fun getItemCount(): Int {
        return dataSet.size
    }

    fun addItem(pokemon: Pokemon) {
        dataSet.add(pokemon)
    }

    private fun setUpLoadMore() {
        val linearLayoutManager = recyclerView.layoutManager as LinearLayoutManager
        recyclerView.addOnScrollListener(object: RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val totalItems = linearLayoutManager.itemCount
                val lastVisibleItem = linearLayoutManager.findLastVisibleItemPosition()
                val visibleThreshold = 5
                if (!isLoading && (totalItems <= (lastVisibleItem + visibleThreshold))) {
                    loadMoreListener.loadMore()
                }
            }
        })
    }

    interface ItemClickListener {
        fun itemClicked(pokemon: Pokemon)
    }

    interface LoadMoreListener {
        fun loadMore()
    }

    class NormalViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        val name = view.name
        val image = view.image
        val types = view.types
    }
}
