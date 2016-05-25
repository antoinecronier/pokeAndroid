package com.antoinecronier.pokebattle.entity;

import java.util.ArrayList;

public class PokeTypePokemon {
	private int id;
	private String nom;
	private int attaque;
	private int attaque_spe;
	private int defence;
	private int defence_spe;
	private int vitesse;
	private int pv;
	private int pokedex;
	private PokeTypePokemon evolue;
	private ArrayList<PokeType> types;
	private ArrayList<PokeZone> zones;
}
