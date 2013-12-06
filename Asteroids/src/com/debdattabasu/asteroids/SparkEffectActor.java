package com.debdattabasu.asteroids;

import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class SparkEffectActor extends BaseActor {
	
	private static final Random mRandomSparkDir = new Random(196);
	float[] mSparkDirections = new float[15];
	private Texture mTexture;
	
	private float mParticleRadius;
	private float mDieTime;
	private float mSparkMaxRadius;
	private float mSparkRadius = 0;
	private float mSparkTransparency = 1;
	private float mCurrentTime = 0;

	
	public SparkEffectActor() {


		mTexture =  ResourceManager.getInstance().blank16Texture;
		
		for(int i = 0; i < 15; i++) {
	        this.mSparkDirections[i] = ((float)mRandomSparkDir.nextFloat() * 3.14f * 2);
	    }
		
		float worldWidth = Gdx.graphics.getWidth();
		
		this.mDieTime = 0.5f;
		
		this.mParticleRadius = worldWidth / 750;
		this.mSparkMaxRadius = worldWidth / 20;

	}
	
	@Override public void draw (SpriteBatch batch, float parentAlpha) {
		super.draw(batch, parentAlpha);
		
		
		batch.setColor(getColor().r, getColor().g, getColor().b, 
				getColor().a * parentAlpha *  mSparkTransparency);
		
		float x = getX();
		float y = getY();
		for(int i = 0; i < 15; i ++) {
			
			float myX = (float) (x + Math.cos(mSparkDirections[i]) * mSparkRadius);
			float myY = (float) (y + Math.sin(mSparkDirections[i]) * mSparkRadius);
			
			batch.draw(mTexture, myX - mParticleRadius, myY - mParticleRadius,
					mParticleRadius * 2, mParticleRadius * 2);
			
		}
		
	}
	
	@Override
	public void act(float deltaTime) {
		super.act(deltaTime);
		
		float sparkSpeed =  this.mSparkMaxRadius / this.mDieTime;
        float transSpeed = 1f / this.mDieTime;
        this.mSparkRadius =  this.mCurrentTime * sparkSpeed;
        this.mSparkTransparency = 1f - this.mCurrentTime * transSpeed;
        this.mCurrentTime += deltaTime;

        if (this.mCurrentTime >= this.mDieTime){
            this.kill();
        }
	}

}
