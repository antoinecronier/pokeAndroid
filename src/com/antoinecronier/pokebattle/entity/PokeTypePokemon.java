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
public class PokeTypePokemon {
	@Id
    @Column(type = Type.INTEGER, hidden = true)
    @GeneratedValue(strategy = Strategy.MODE_IDENTITY)
	private int id;
	
	@Column(type = Type.STRING)
	private String nom;
	
	@Column(type = Type.INTEGER)
	private int attaque;
	
	@Column(type = Type.INTEGER)
	private int attaque_spe;
	
	@Column(type = Type.INTEGER)
	private int defence;
	
	@Column(type = Type.INTEGER)
	private int defence_spe;
	
	@Column(type = Type.INTEGER)
	private int vitesse;
	
	@Column(type = Type.INTEGER)
	private int pv;
	
	@Column(type = Type.INTEGER, unique = true)
	private int pokedex;
	
	@ManyToOne()
	@Column(nullable = true)
	private PokeTypePokemon evolue;
	
	@OneToMany()
	private ArrayList<PokeType> types;
	
	@OneToMany()
	@Column(nullable = true)
	private ArrayList<PokeZone> zones;
}
