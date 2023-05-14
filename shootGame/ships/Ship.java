package ships;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;

import effects.Laser;

public abstract class Ship {
	public int shield;
	
	//position & dimension
	public float xPosition, yPosition ;//lower left = (0,0)
	public float width, height;

	public Rectangle shipBoundingBox;
	
	//laser information
	float laserWidth, laserHeight;
	float laserMovementSpeed;
	public float timeBetweenShots;
	float timeSinceLastShot = 0;
	
	
	//graphics
	protected TextureRegion shipTexureRegion, shieldTextureRegion, laserTextureRegion;
	
	//ship characteristic
	public float movementSpeed;

	public boolean destroyed = false;
	
	public Ship(float movementSpeed, int shield, float xCenter, float yCenter,
			float width, float height,
			float laserWidth, float laserHeight, float laserMovementSpeed,
			float timeBetweenShots,
			TextureRegion shipTexureRegion, TextureRegion shieldTextureRegion,
			TextureRegion laserTextureRegion) {
		super();
		this.movementSpeed = movementSpeed;
		this.shield = shield;
		this.xPosition = xCenter - width/2;
		this.yPosition = yCenter - height/2;
		this.width = width;
		this.height = height;
		this.shipBoundingBox = new Rectangle(xPosition, yPosition, width, height);
		this.laserWidth = laserWidth;
		this.laserHeight = laserHeight;
		this.laserMovementSpeed = laserMovementSpeed;
		this.timeBetweenShots = timeBetweenShots;
		this.shipTexureRegion = shipTexureRegion;
		this.shieldTextureRegion = shieldTextureRegion;
		this.laserTextureRegion = laserTextureRegion;
	}
	
	public void move(float xChange, float yChange) {
		xPosition += xChange;
		yPosition += yChange;
	}
	public void update(float deltaTime) {
		timeSinceLastShot += deltaTime;
		this.shipBoundingBox.set(xPosition, yPosition, width, height);
	}
	
	public boolean canFireLaser() {
		return (timeSinceLastShot >= timeBetweenShots);
	}
	
	public abstract Laser[] fireLasers();//depends on enemy or player
	
	public boolean intersects(Rectangle laserBoundingBox) {
		shipBoundingBox.set(xPosition, yPosition, width, height);
		return shipBoundingBox.overlaps(laserBoundingBox);
	}
	
	public void hit(Laser laser , Batch batch) {
		if(shield>0) {
			shield--;
		}
		else {
			destroyed = true;
		}
	}
	
	public void draw(Batch batch) {
		batch.draw(shipTexureRegion, xPosition, yPosition, width, height);
		if(shield > 0) {
			batch.draw(shieldTextureRegion, xPosition, yPosition, width, height);
		}
	}
	
}
