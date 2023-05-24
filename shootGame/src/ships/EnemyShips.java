package ships;

import java.util.Random;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import lasers.Laser;
import screens.GameScreen;

public class EnemyShips extends Ship{
	float xdirection = 0;
	float ydirection = 0;
	float timeBetweenMove = 1f;
	float timeSineceLastMove = 0;
	public EnemyShips(int life, float movementSpeed, int shield,
			float xCenter, float yCenter,
			float width, float height,
			float laserWidth, float laserHeight, float laserMovementSpeed,
			float timeBetweenShots,
			TextureRegion shipTexureRegion, TextureRegion shieldTextureRegion,
			TextureRegion laserTextureRegion,Sound laserSound) {
		super(life, movementSpeed, shield,
				xCenter, yCenter,
				width, height,
				laserWidth, laserHeight, laserMovementSpeed,
				timeBetweenShots,
				shipTexureRegion, shieldTextureRegion,
				laserTextureRegion, laserSound);
	}

	@Override
	public Laser[] fireLasers() {
		Laser[] laser = new Laser[2];
		laser[0] = new Laser(xPosition+ 0.3f*width,yPosition - laserHeight,
				laserWidth, laserHeight, 
				laserMovementSpeed, laserTextureRegion, laserSound);
		laser[1] = new Laser(xPosition+ 0.6f*width,yPosition - laserHeight,
				laserWidth, laserHeight, 
				laserMovementSpeed, laserTextureRegion, laserSound);

		timeSinceLastShot = 0;

		return laser;
	}
	@Override
	public void update(float deltaTime) {
		super.update(deltaTime);
		
		Random r = new Random();
		float xChange = 0;
		float yChange = 0;
		double direciton = 0;
		
		if(canChangeDirection()) {
			//random the direction
			direciton = r.nextDouble()*2*Math.PI;
			xdirection = (float)Math.cos(direciton);
			ydirection = (float)Math.sin(direciton);;
			timeSineceLastMove = 0;
		}
		if(xdirection <0 && xPosition > 0.5f 
				|| xdirection >= 0 && xPosition + width < GameScreen.WORLD_WIDTH - 0.5f)
			xChange = xdirection * movementSpeed * deltaTime;
		
		if(ydirection >= 0 &&  yPosition + height < GameScreen.WORLD_HEIGHT - 0.5f ||
				ydirection < 0 && yPosition > GameScreen.WORLD_HEIGHT/2)
			yChange = ydirection * movementSpeed * deltaTime;

		move(xChange, yChange);

		//count time since last move
		timeSineceLastMove+=deltaTime;
		
	}
	public void enemyExplosion(Texture explosionTexture) {
	}
	public boolean canChangeDirection() {
		return(timeSineceLastMove >= timeBetweenMove);
	}

	

}
