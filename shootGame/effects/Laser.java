package effects;

import com.badlogic.gdx.Gdx;
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
	//laser physical characteristic
	public float movementspeed;//world units per deltaTime
	
	//graphics
	TextureRegion textureRegion;
	
	//audio
	public static Sound LASER_SOUND;
	
	
	public Laser(float xPosition, float yPosition, float width, float height,
			float movementspeed,TextureRegion textureRegion) {
		super();
		this.xPosition = xPosition;
		this.yPosition = yPosition;
		this.width = width;
		this.height = height;
		this.laserBoungingBox = new Rectangle(xPosition, yPosition, width, height);
		this.movementspeed = movementspeed;
		this.textureRegion = textureRegion;
		
		//audio
		LASER_SOUND = Gdx.audio.newSound(Gdx.files.internal("playerLaserSound.ogg"));
		long id = LASER_SOUND.play(0.2f);
//		laserSound.setPitch(id, 1.5f);
		LASER_SOUND.setLooping(id, false);

	}
	public void draw(Batch batch) {
		batch.draw(textureRegion, xPosition- width/2, yPosition, width, height);
	}
	public Rectangle getBoundingBox() {
		laserBoungingBox.set(xPosition, yPosition, width, height);
		return laserBoungingBox;
	}
//	public void update(Batch batch, float deltaTime) {
//		yPosition += movementspeed * deltaTime;
//	}

}
