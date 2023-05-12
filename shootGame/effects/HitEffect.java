package effects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;

public class HitEffect {
	float xPosition;
	float yPosition;
	float hitEffectWidth =5;
	float hitEffectHeight =5;
	Texture hittexture;
	//audio 
	public static Sound HIT_SOUND;
	public HitEffect(float xPosition, float yPosition, float hitEffectWidth, float hitEffectHeight,
			Texture hittexture) {
		super();
		this.xPosition = xPosition;
		this.yPosition = yPosition;
		this.hitEffectWidth = hitEffectWidth;
		this.hitEffectHeight = hitEffectHeight;
		this.hittexture = hittexture;
		
		HIT_SOUND = Gdx.audio.newSound(Gdx.files.internal("hit sound.wav"));
		HIT_SOUND.play(0.2f);
	}
	public void draw(Batch batch) {
		batch.draw(hittexture, xPosition- hitEffectWidth/2 , yPosition, hitEffectWidth, hitEffectHeight);
	}
	
}
