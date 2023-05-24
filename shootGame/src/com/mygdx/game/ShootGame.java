package com.mygdx.game;


import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import screens.MenuScreen;

public class ShootGame extends Game {
	MenuScreen menuScreen;
	
	public SpriteBatch batch;
	
	@Override
	public void create () {
		batch= new SpriteBatch();
		this.setScreen(new MenuScreen(this));
	}
	@Override
	public void render () {
		super.render();

	}
	@Override
	public void resize(int width, int height) {
		this.screen.resize(width, height);
	}
	@Override
	public void dispose () {
		batch.dispose();
		super.dispose();
	}
}
