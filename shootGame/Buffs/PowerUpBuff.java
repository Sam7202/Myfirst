package Buffs;

import com.badlogic.gdx.graphics.Texture;

import ships.PlayerShip;

public class PowerUpBuff extends Buffs {

	public PowerUpBuff(float dropspeed, float xPosition, float yPosition, Texture buffTexture) {
		super(dropspeed, xPosition, yPosition, buffTexture);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void buffUp(PlayerShip playerShip) {
		// TODO Auto-generated method stub
		playerShip.timeBetweenShots-=0.1f;

	}

}
