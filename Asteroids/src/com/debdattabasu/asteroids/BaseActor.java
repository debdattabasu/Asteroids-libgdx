package com.debdattabasu.asteroids;

import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class BaseActor extends Actor {

	private boolean mIsdead = false;
	private boolean mWrapEdge = false;
	
	protected float mSpeed = 0;
	protected float mAngle = 0;
	
	public void setParams(float angle, float speed) {
		mAngle = angle; mSpeed = speed;
	}
	
	private static final Random mPosRandomizer = new Random(45);
	private static final Random mAngleRandomizer = new Random(87);
	private static final Random mSpeedRandomizer = new Random(124);
	
	public void randomizeAngle() {
		mAngle = (float) (2 * 3.14 * mAngleRandomizer.nextDouble());
	}
	
	public void randomizeSpeed() {
		float minSpeed = Gdx.graphics.getWidth() * 0.02f; 
		float speedFactor = Gdx.graphics.getWidth() * 0.03f;
		mSpeed =  minSpeed + speedFactor * mSpeedRandomizer.nextFloat();
	}
	
	public void randomizePositionOnEdge() {
		
		float x = 0;
		float y = 0;
		
		float floatSize = getWidth() * 0.95f;
		
		switch(mPosRandomizer.nextInt(4)) {
		case 0:
			x = - floatSize;
			y = Gdx.graphics.getHeight() * mPosRandomizer.nextFloat();
			break;
		case 1:
			x = Gdx.graphics.getWidth() * mPosRandomizer.nextFloat();
			y = Gdx.graphics.getHeight() + floatSize;
			break;
		case 2:
			x = Gdx.graphics.getWidth() + floatSize;
			y = Gdx.graphics.getHeight() * mPosRandomizer.nextFloat();
			break;
		case 3:
			x = Gdx.graphics.getWidth() * mPosRandomizer.nextFloat();
			y = - floatSize;
			break;
			
		}
		
		setPosition(x, y);
	}
	
	
	public float getAngle() {
		return mAngle;
	}
	
	public float getSpeed() {
		return mSpeed;
	}
	
	public boolean isDead() {
		return mIsdead;
	}
	
	public void setWrapEdge(boolean value) {
		mWrapEdge = value;
	}
	
	public boolean getWrapEdge() {
		return mWrapEdge;
	}
	
	
	public void kill() {
		mIsdead = true;
	}
	
	public void doWrapEdge() {
		
		float x = getX();
		float y = getY(); 
		boolean isOutside = false;
		
		if(y > Gdx.graphics.getHeight()) {
			if(mWrapEdge) {
				y = y - Gdx.graphics.getHeight() - getHeight();
			}
			isOutside = true;
			
		} else if(y < -getHeight()) {
			if(mWrapEdge) {
				y = Gdx.graphics.getHeight() - y - getHeight();
			}
			isOutside = true;
		}
		
		if(x > Gdx.graphics.getWidth()) {
			if(mWrapEdge) {
				x = x - Gdx.graphics.getWidth() - getWidth();
			}
			isOutside = true;
			
		} else if(x < -getWidth()) {
			if(mWrapEdge) {
				x = Gdx.graphics.getWidth() - x - getWidth();
			}
			isOutside = true;
			
		}
		
		setPosition(x, y);
		
		if(isOutside && !mWrapEdge) {
			kill();
		}
		
	}
	
	private final Rectangle mRect0 = new Rectangle();
	private final Rectangle mRect1 = new Rectangle();
	
	public boolean intersects(BaseActor other) {
		
		mRect0.set(getX(), getY(), getWidth(), getHeight());
		mRect1.set(other.getX(), other.getY(), other.getWidth(), other.getHeight());
		return mRect0.overlaps(mRect1);
	}
	
	@Override
	public void act(float deltaTime) {
		super.act(deltaTime);
		doWrapEdge();

	}
	
	public BaseActor() {
		
	}
}
