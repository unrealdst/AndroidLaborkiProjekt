package com.mygdx.game;

import Screens.MainManu;
import Screens.PlayScreen;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

public class BestGameEvaa extends Game {

	public PlayScreen play;
	public MainManu mainMenu;
	public Skin skin;
	
	@Override
	public void create () {
		skin = new Skin(Gdx.files.internal("style/uiskin.json"));
		play = new PlayScreen(this);
		mainMenu = new MainManu(this);
		setScreen(mainMenu);
	}

	@Override
	public void render () {
		super.render();
	}
}
