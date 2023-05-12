package com.mygdx.game;


import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import screens.GameOverScreen;
import screens.GameScreen;
import screens.MenuScreen;

public class ShootGame extends Game {
	MenuScreen menuScreen;
	
	public SpriteBatch batch;
	public void setGameOverScreen(int score){
		this.setScreen(new GameOverScreen(this,score));
	}
	public void setMenuScreen(){
		this.setScreen(new MenuScreen());
	}
	public void setGameScreen(){
		this.setScreen(new GameScreen(this));
	}
	@Override
	public void create () {
		batch= new SpriteBatch();
		menuScreen = new MenuScreen();
//		this.setMenuScreen();
		this.setScreen(new GameOverScreen(this , 100));
	}
	@Override
	public void render () {
		//		if(Gdx.input.isKeyPressed(Keys.A))
		//		if(Gdx.input.isKeyPressed(Keys.ESCAPE))
		//		gameScreen.pause();
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
