package com.one.kumaran.restpokemon.ui.detail

import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.one.kumaran.restpokemon.R
import com.one.kumaran.restpokemon.repository.model.Pokemon
import com.one.kumaran.restpokemon.ui.detail.DetailActivity.Companion.POKEMON

class DetailActivity : AppCompatActivity() {

    companion object {
        val POKEMON = "pokemon"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
        render()
    }

    fun render() {
        val name = findViewById<TextView>(R.id.name)
        val attack = findViewById<TextView>(R.id.attack)
        val speed = findViewById<TextView>(R.id.speed)
        val defense = findViewById<TextView>(R.id.defense)
        val abilities = findViewById<TextView>(R.id.abilities)
        val icon = findViewById<ImageView>(R.id.icon)
        val pokemon = intent.getParcelableExtra<Pokemon>(POKEMON)
        name.setText(pokemon.name)
        attack.setText(pokemon.attack)
        speed.setText(pokemon.speed)
        defense.setText(pokemon.defense)
        abilities.setText(pokemon.abilities)
        Glide.with(this)
                .load(pokemon.url)
                .into(icon)
    }
}

fun Context.DetailActivity(pokemon: Pokemon): Intent {
    return Intent(this, DetailActivity()::class.java).apply {
        putExtra(POKEMON, pokemon)
    }
}