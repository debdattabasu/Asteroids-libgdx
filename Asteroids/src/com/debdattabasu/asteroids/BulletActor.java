package com.debdattabasu.asteroids;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class BulletActor extends BaseActor {
	
	private float mAngle = 0;
	private float mElapsedTime = 0;
	

	public void setAngle(float angle) {
		mAngle = angle;
	}
	
	public float getAngle() {
		return mAngle;
	}
	
	public BulletActor() {
		
		float size = Gdx.graphics.getWidth()/250;
		setSize(size, size);
	}
	
	@Override
	public void act(float deltaTime) {
		super.act(deltaTime);
		
		float x = getX(); 
		float y = getY();
		
		x -= deltaTime * Gdx.graphics.getWidth()  * Math.sin(mAngle);
		y += deltaTime * Gdx.graphics.getWidth()  * Math.cos(mAngle);
		
		setPosition(x, y);
		
		if(mElapsedTime > 0.35f) {
			kill();
		}
		
		mElapsedTime += deltaTime;
		
		
	}
	
	
	@Override
	public void draw(SpriteBatch batch, float parentAlpha) {
		super.draw(batch, parentAlpha);
		
		batch.setColor(getColor().r, getColor().g, getColor().b,
				getColor().a * parentAlpha);
		
		Texture dotTexture = ResourceManager.getInstance().blank16Texture;
		float angleDegrees = mAngle * 360 / 2 / 3.14f;
		
		batch.draw(dotTexture, getX(), getY(), getWidth()/2, getHeight()/2,
				getWidth(), getHeight(), 1, 1, angleDegrees, 0, 0, 
				dotTexture.getWidth(), dotTexture.getHeight(), false, false);
	}
	
}
