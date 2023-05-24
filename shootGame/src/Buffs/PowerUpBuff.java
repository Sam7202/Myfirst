package Buffs;

import com.badlogic.gdx.graphics.Texture;

import ships.PlayerShip;

public class PowerUpBuff extends Buffs {

	public PowerUpBuff(float dropspeed, float xPosition, float yPosition,
			float buffWidth, float buffHeight, Texture buffTexture) {
		super(dropspeed, xPosition, yPosition, buffWidth, buffHeight, buffTexture);
	}


	@Override
	public
	void buffUp(PlayerShip playerShip) {
		
	}

}
