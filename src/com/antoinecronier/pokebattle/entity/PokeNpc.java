package com.antoinecronier.pokebattle.entity;

import java.util.ArrayList;

public class PokeNpc {
	private int id;
	private String nom;
	private PokeProfession profession;
	private String description;
	private ArrayList<PokeObjet> objets;
	private ArrayList<PokeBadge> badge;
	private ArrayList<PokePokemon> pokemons;
	private ArrayList<PokePokemon> team;
	private PokePosition position;
	private PokeZone zone;
}
