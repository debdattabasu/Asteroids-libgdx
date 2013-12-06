package com.debdattabasu.asteroids;

import java.util.HashMap;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.BitmapFont.TextBounds;
import com.badlogic.gdx.utils.Disposable;

public class ResourceManager implements Disposable {
	
	public static ResourceManager getInstance() {
		return mInstance;
	}
	
	private static ResourceManager mInstance;
	
	ResourceManager() {
		mInstance = this;
	}
	
	public Texture playerShipTexture;
	public Texture playerShipJetTexture;
	public Texture blank16Texture;
	public Texture saucerTexture;
	public final Texture[] asteroidTextureArray = new Texture[4];
	
	
	private final HashMap<Float, BitmapFont> mFontMap = new HashMap<Float, BitmapFont>(); 
	
	public BitmapFont getFontForHeight(float height) {
		BitmapFont ret;
		
		if(mFontMap.containsKey(height)) {
			ret = mFontMap.get(height);
		} else {
		
			ret = new BitmapFont(Gdx.files.internal("data/bebas_neue.fnt"), false);
			TextBounds bounds = ret.getBounds("K");
			float scaleFactor = height/bounds.height;
			ret.setScale(scaleFactor);
			mFontMap.put(height, ret);
		}
		
		return ret;
	}

	public void loadResources() {
		
		playerShipTexture = new Texture(Gdx.files.internal("data/player.png"));
		playerShipJetTexture = new Texture(Gdx.files.internal("data/player_jet.png"));
		blank16Texture = new Texture(Gdx.files.internal("data/blank16.png"));
		saucerTexture = new Texture(Gdx.files.internal("data/saucer.png"));
		
		for(int i = 0; i < 4; i++) {
			asteroidTextureArray[i] = new Texture(Gdx.files.internal("data/asteroid" + i + ".png"));
		}
		
	}

	@Override
	public void dispose() {
		playerShipJetTexture.dispose();
		playerShipTexture.dispose();
		blank16Texture.dispose();
		saucerTexture.dispose();
		for(Texture texture : asteroidTextureArray) {
			texture.dispose();
		}
		
		for(BitmapFont font : mFontMap.values()) {
			font.dispose();
		}

	}
	
}
