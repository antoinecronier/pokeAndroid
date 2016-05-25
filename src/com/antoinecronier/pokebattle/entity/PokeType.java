package com.antoinecronier.pokebattle.entity;

import java.util.ArrayList;

public class PokeType {
	private int id;
	private String nom;
	private int modificateur;
	private ArrayList<PokeType> typeFort;
	private ArrayList<PokeType> typeFaible;
}
