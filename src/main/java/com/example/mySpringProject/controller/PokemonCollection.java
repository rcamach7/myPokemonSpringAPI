package com.example.mySpringProject.controller;

import java.util.HashMap;

public class PokemonCollection {
	HashMap<String, String> pokeDex;
	
	public PokemonCollection() {
		pokeDex = new HashMap<String, String>();
		pokeDex.put("pickachu", "electric");
		pokeDex.put("charmander", "fire");
		pokeDex.put("squirtle", "water");
	}
	
	public void addPokemon(String name, String type) {
		name = name.toLowerCase();
		type = type.toLowerCase();
		pokeDex.put(name, type);
	}
	
	public boolean contains(String name) {
		name = name.toLowerCase();
		if (pokeDex.containsKey(name)) {
			return true;
		} else {
			return false;
		}
	}
	
	public boolean deletePokemon(String name) {
		name = name.toLowerCase();
		if (contains(name)) {
			pokeDex.remove(name);
			return true;
		} else {
			return false;
		}
	}
	
	public void changeType(String name, String typeChange) {
		name = name.toLowerCase();
		pokeDex.put(name, typeChange);
	}
	
	public String printAll() {
		String allPokemon = "";
		for (String name: pokeDex.keySet()) { 
			allPokemon = allPokemon + " | " + name +":" + pokeDex.get(name);
		}
		return allPokemon;
	}
}
