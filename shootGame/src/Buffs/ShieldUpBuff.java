package Buffs;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;

import ships.PlayerShip;

public class ShieldUpBuff extends Buffs {
	public static Sound SHIELDUP_SOUND ;

	public ShieldUpBuff(float dropspeed, float xPosition, float yPosition,
			float buffWidth, float buffHeight, Texture buffTexture) {
		super(dropspeed, xPosition, yPosition, buffWidth, buffHeight, buffTexture);
		SHIELDUP_SOUND =
				Gdx.audio.newSound( Gdx.files.internal("buffs/shieldUp.ogg"));
	}

	@Override
	public void buffUp(PlayerShip playerShip) {
		//play audio
		SHIELDUP_SOUND.play(0.8f);

		playerShip.shield = 3;
	}

}
