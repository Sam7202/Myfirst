package Buffs;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Rectangle;

import ships.PlayerShip;

public abstract class Buffs {
	float dropspeed;
	public float xPosition, yPosition;
	public float buffWidth = 3f, buffHeight = 4f;
	Rectangle buffBoundingBox;
	Texture buffTexture;

	public Buffs(float dropspeed, float xPosition, float yPosition,
			float buffWidth, float buffHeight,
			Texture buffTexture) {
		this.dropspeed = dropspeed;
		this.xPosition = xPosition;
		this.yPosition = yPosition;
		this.buffWidth = buffWidth;
		this.buffHeight = buffHeight;
		this.buffTexture = buffTexture;

		this.buffBoundingBox = new Rectangle(xPosition, yPosition, buffWidth, buffHeight);
	}
	public boolean contactWithPlayerShip(Rectangle playerShipRectangle) {
		buffBoundingBox.set(xPosition, yPosition, buffWidth, buffHeight);

		if(buffBoundingBox.overlaps(playerShipRectangle)) 
			return true;
		else
			return false;
	}
	abstract void buffUp(PlayerShip playerShip);

	public void drawBuff(Batch batch) {
		batch.draw(buffTexture, xPosition, yPosition, buffWidth, buffHeight);
	}
	public void updateBuff(float deltaTime) {
		yPosition -= dropspeed * deltaTime;
	}
}
