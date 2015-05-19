package com.mygdx.game;

import Screens.PlayScreen;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class BestGameEvaa extends Game {
	SpriteBatch batch;
	Texture img;
	public PlayScreen play;
	
	
	@Override
	public void create () {
		batch = new SpriteBatch();
		img = new Texture("badlogic.jpg");
		play = new PlayScreen(this);
		
		setScreen(play);		
	}

	@Override
	public void render () {
		super.render();
	}
}
