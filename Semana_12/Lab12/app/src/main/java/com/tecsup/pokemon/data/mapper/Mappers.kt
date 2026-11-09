package com.tecsup.pokemon.data.mapper

import com.tecsup.pokemon.data.remote.dto.ItemDto
import com.tecsup.pokemon.data.remote.dto.PokemonDetalleDto
import com.tecsup.pokemon.domain.model.Item

private const val SPRITES = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/"

/** "charizard-mega-x" -> "Charizard Mega X" */
private fun nombreBonito(nombre: String) =
    nombre.split("-").joinToString(" ") { it.replaceFirstChar(Char::uppercase) }

/** El número sale de la url: https://pokeapi.co/api/v2/pokemon/4/ -> 4 */
private fun ItemDto.numero(): String = url.trimEnd('/').substringAfterLast('/')

/** Pokémon de la lista -> modelo de la UI. El id es el nombre (lo usa el endpoint de detalle). */
fun ItemDto.aModelo(esFavorito: Boolean = false) = Item(
    id = name,
    titulo = nombreBonito(name),
    subtitulo = "N.º ${numero()} · Tipo fuego",
    imagenUrl = "$SPRITES${numero()}.png",
    esFavorito = esFavorito
)

/** Detalle -> modelo de la UI, con altura y peso en metros y kilos. */
fun PokemonDetalleDto.aModelo(esFavorito: Boolean) = Item(
    id = name,
    titulo = nombreBonito(name),
    subtitulo = "N.º $id · Tipos: ${types.joinToString { it.type.name }} · " +
        "Altura ${height / 10.0} m · Peso ${weight / 10.0} kg",
    imagenUrl = sprites.frontDefault,
    esFavorito = esFavorito
)
