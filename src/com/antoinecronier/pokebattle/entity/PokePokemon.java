package com.antoinecronier.pokebattle.entity;

import org.joda.time.DateTime;

import com.tactfactory.harmony.annotation.Column;
import com.tactfactory.harmony.annotation.Entity;
import com.tactfactory.harmony.annotation.GeneratedValue;
import com.tactfactory.harmony.annotation.Id;
import com.tactfactory.harmony.annotation.Column.Type;
import com.tactfactory.harmony.annotation.GeneratedValue.Strategy;
import com.tactfactory.harmony.annotation.ManyToOne;
import com.tactfactory.harmony.bundles.rest.annotation.Rest;

@Entity
@Rest
public class PokePokemon {
	@Id
    @Column(type = Type.INTEGER, hidden = true)
    @GeneratedValue(strategy = Strategy.MODE_IDENTITY)
	private int id;
	
	@Column(type = Type.STRING)
	private String surnom;
	
	@Column(type = Type.INTEGER)
	private int niveau;
	
	@Column(type = Type.DATETIME)
	private DateTime capture;
	
	@ManyToOne
	private PokeTypePokemon type;
	
	@ManyToOne
	private PokeAttaque attaque1;
	
	@ManyToOne
	private PokeAttaque attaque2;
	
	@ManyToOne
	private PokeAttaque attaque3;
	
	@ManyToOne
	private PokeAttaque attaque4;
}
