package com.antoinecronier.pokebattle.entity;

import java.util.ArrayList;

import com.tactfactory.harmony.annotation.Column;
import com.tactfactory.harmony.annotation.Entity;
import com.tactfactory.harmony.annotation.GeneratedValue;
import com.tactfactory.harmony.annotation.Id;
import com.tactfactory.harmony.annotation.Column.Type;
import com.tactfactory.harmony.annotation.GeneratedValue.Strategy;
import com.tactfactory.harmony.annotation.ManyToOne;
import com.tactfactory.harmony.annotation.OneToMany;
import com.tactfactory.harmony.bundles.rest.annotation.Rest;

@Entity
@Rest
public class PokeNpc {
	@Id
    @Column(type = Type.INTEGER, hidden = true)
    @GeneratedValue(strategy = Strategy.MODE_IDENTITY)
	private int id;
	
	@Column(type = Type.STRING)
	private String nom;
	
	@Column(type = Type.ENUM)
	private PokeProfession profession;
	
	@Column(type = Type.TEXT)
	private String description;
	
	@OneToMany
	@Column(nullable = true)
	private ArrayList<PokeObjet> objets;
	
	@OneToMany
	@Column(nullable = true)
	private ArrayList<PokeBadge> badge;
	
	@OneToMany
	@Column(nullable = true)
	private ArrayList<PokePokemon> pokemons;
	
	@OneToMany
	@Column(nullable = true)
	private ArrayList<PokePokemon> team;
	
	@ManyToOne
	@Column(nullable = true)
	private PokePosition position;
	
	@ManyToOne
	@Column(nullable = true)
	private PokeZone zone;
}
