package com.antoinecronier.pokebattle.entity;

import java.util.ArrayList;

import com.tactfactory.harmony.annotation.Column;
import com.tactfactory.harmony.annotation.Entity;
import com.tactfactory.harmony.annotation.GeneratedValue;
import com.tactfactory.harmony.annotation.Id;
import com.tactfactory.harmony.annotation.Column.Type;
import com.tactfactory.harmony.annotation.GeneratedValue.Strategy;
import com.tactfactory.harmony.annotation.OneToMany;
import com.tactfactory.harmony.bundles.rest.annotation.Rest;

@Entity
@Rest
public class PokeType {
	@Id
    @Column(type = Type.INTEGER, hidden = true)
    @GeneratedValue(strategy = Strategy.MODE_IDENTITY)
	private int id;
	
	@Column(type = Type.STRING)
	private String nom;
	
	@Column(type = Type.INTEGER)
	private int modificateur;
	
	@OneToMany
	private ArrayList<PokeType> typeFort;
	
	@OneToMany
	private ArrayList<PokeType> typeFaible;
}
