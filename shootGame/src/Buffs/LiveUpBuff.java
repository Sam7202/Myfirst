package Buffs;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;

import ships.PlayerShip;

public class LiveUpBuff extends Buffs{
	public static Sound LIVEUP_SOUND;

	public LiveUpBuff(float dropspeed, float xPosition, float yPosition,
			float buffWidth,float buffHeight,Texture buffTexture) {
		super(dropspeed, xPosition, yPosition,buffWidth, buffHeight, buffTexture);
		LIVEUP_SOUND =
				Gdx.audio.newSound( Gdx.files.internal("buffs/liveUp.mp3"));	}

	@Override
	public void buffUp(PlayerShip playerShip) {
		LIVEUP_SOUND.play(0.7f);
		playerShip.life = 3;
	}

}
