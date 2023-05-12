package effects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;

import screens.GameScreen;

public class LowLifeEffect {
	Texture lowLifetexture;
	
	//parameters of fading the red border
	float alpha = 0.2f;
	float increasingSpeed = (1f/60f) / 1.5f;
	
	//audio
	public static Sound LOW_LIVES_ALARM ;

	public LowLifeEffect(Texture lowLifetexture) {
		super();
		this.lowLifetexture = lowLifetexture;
		LOW_LIVES_ALARM = Gdx.audio.newSound(Gdx.files.internal("low life alarm.mp3"));
	}
	public void draw(Batch batch) {
		//fading red border
		if(alpha>=1)
			increasingSpeed *=(float)-1;
		else if(alpha<=0)
			increasingSpeed *=(float)-1;
		alpha+=increasingSpeed;
		batch.setColor(1, 1, 1, alpha);
		
		//draw the border
		batch.draw(lowLifetexture, 0, 0, GameScreen.WORLD_WIDTH, GameScreen.WORLD_HEIGHT);
		
		//reset batch
		batch.setColor(1, 1, 1, 1);
	}
	public void stop() {
		LOW_LIVES_ALARM.stop();
	}
	public void play() {
		LOW_LIVES_ALARM.loop(2f);
	}
	
	
	
}
