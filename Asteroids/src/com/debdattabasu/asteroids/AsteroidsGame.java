package com.debdattabasu.asteroids;


import com.badlogic.gdx.Game;


public class AsteroidsGame extends Game {
	
	private AsteroidsGameOverScreen mGameOverScreen;
	private AsteroidsGameIntroScreen mIntroScreen;
	private AsteroidsGameScreen mGameScreen;
	private ResourceManager mResources;
	
	private static AsteroidsGame mInstance;
	
	public static AsteroidsGame getInstance() {
		return mInstance;
	}

	@Override
	public void create() {	
		
		mInstance = this;
		
		mResources = new ResourceManager();
		mResources.loadResources();
		
		mIntroScreen = new AsteroidsGameIntroScreen();
		mGameScreen = new AsteroidsGameScreen();
		mGameOverScreen = new AsteroidsGameOverScreen();
		
		
		setScreen(mIntroScreen);
	}

	@Override
	public void dispose() {
		super.dispose();
		mGameScreen.dispose();
		mResources.dispose();
		mGameOverScreen.dispose();
	
	}
	
}
