package Buffs;

import com.badlogic.gdx.graphics.Texture;

import ships.PlayerShip;

public class LiveUpBuff extends Buffs{

	public LiveUpBuff(float dropspeed, float xPosition, float yPosition, Texture buffTexture) {
		
		super(dropspeed, xPosition, yPosition, buffTexture);
	}

	@Override
	public void buffUp(PlayerShip playerShip) {
		playerShip.lives = 3;
	}

}
