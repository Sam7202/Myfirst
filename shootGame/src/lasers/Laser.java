package lasers;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;

public class Laser {
	

	//position & dimensions
	public float xPosition, yPosition; //bottom centre of the laser
	public float width;
	public float height;
	public Rectangle laserBoungingBox;
	public int attact = 1;
	//laser physical characteristic
	public float movementspeed;//world units per deltaTime
	
	//graphics
	TextureRegion textureRegion;
	
	//audio
	public Sound laserSound;

	
	
	public Laser(float xPosition, float yPosition, float width, float height,
			float movementspeed,TextureRegion textureRegion, Sound laserSound) {
		this.xPosition = xPosition;
		this.yPosition = yPosition;
		this.width = width;
		this.height = height;
		this.laserBoungingBox = new Rectangle(xPosition, yPosition, width, height);
		this.movementspeed = movementspeed;
		this.textureRegion = textureRegion;
		this.laserSound = laserSound;
		
		//audio
		setAudio();
		
	}
	public void draw(Batch batch) {
		batch.draw(textureRegion, xPosition- width/2, yPosition, width, height);
	}
	public Rectangle getBoundingBox() {
		laserBoungingBox.set(xPosition, yPosition, width, height);
		return laserBoungingBox;
	}
	public void update( float deltaTime) {
		yPosition += movementspeed * deltaTime;
	}
	public void setAudio() {
		laserSound.play(0.3f);
	}

}
