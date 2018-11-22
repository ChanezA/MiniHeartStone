package com.miniheartstone.servlet;

abstract class Hero {
		protected String name;
		protected int vie;
		protected int mana;
		//liste de cartes
		protected int manaMax;
		abstract void power();
}
