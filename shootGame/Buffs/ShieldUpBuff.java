package Buffs;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;

import ships.PlayerShip;

public class ShieldUpBuff extends Buffs {
	public static Sound SHIELDUP_SOUND =
			Gdx.audio.newSound( Gdx.files.internal("buffs/shieldUp.ogg"));;
	
	public ShieldUpBuff(float dropspeed, float xPosition, float yPosition, 
			Texture shieldUpTexture) {
		super(dropspeed, xPosition, yPosition, shieldUpTexture);
		
		}

	@Override
	public void buffUp(PlayerShip playerShip) {
		//play audio
		SHIELDUP_SOUND.play(0.8f);
		
		playerShip.shield = 3;
	}

}
