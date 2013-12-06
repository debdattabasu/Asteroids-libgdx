package com.debdattabasu.asteroids;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;

public class AsteroidsGameScreen implements Screen {
	
	private Stage mStage;
	
	private PlayerShipActor mPlayer;
	
	private final ArrayList<BaseActor> mActorList = 
			new ArrayList<BaseActor>();
	
	private final ArrayList<DamageableActor> mDamageableActorList = 
			new ArrayList<DamageableActor>();
	
	private final ArrayList<BulletActor> mBulletActorList = 
			new ArrayList<BulletActor>();
	
	private static AsteroidsGameScreen mInstance;
	
	
	public static AsteroidsGameScreen getInstance() {
		return mInstance;
	}
	
	public SparkEffectActor addSparkEffect() {
		SparkEffectActor spark = new SparkEffectActor();
		mActorList.add(spark);
		return spark;
	}
	
	
	public PlayerShipActor addPlayer() {
		PlayerShipActor player = new PlayerShipActor();
		mActorList.add(player);
		return player;
	}
	
	public BulletActor addBullet() {
		BulletActor bullet = new BulletActor();
		mActorList.add(bullet);
		return bullet;
		
	}
	
	public AsteroidActor addAsteroid() {
		AsteroidActor asteroid = new AsteroidActor();
		mActorList.add(asteroid);
		return asteroid;
	}
	
	public FlyingSaucerActor addFlyingSaucer() {
		FlyingSaucerActor saucer = new FlyingSaucerActor();
		mActorList.add(saucer);
		return saucer;
	}
	
	private int mScore;
	private int mNumLives;
	
	private Stage mHudStage;
	private TextActor mHudScoreActor;
	private final PlayerShipActor[] mHudLivesArray = new PlayerShipActor[3];
	
	private void doGameOver() {
		AsteroidsGameOverScreen.getInstance().setScore(mScore);
		AsteroidsGame.getInstance().setScreen(AsteroidsGameOverScreen.getInstance());
	}
	
	public void queueGameOver() {
		
		mStage.addAction(Actions.sequence(Actions.delay(1f), Actions.run(new Runnable() {
			
			@Override
			public void run() {
				doGameOver();
			}
		})));
	}
	
	public void killPlayer() {
		
		mNumLives -= 1;
		
		for(PlayerShipActor life : mHudLivesArray) {
			life.setVisible(false);
		}
		
		for(int i = 0; i < mNumLives; i ++) {
			PlayerShipActor life = mHudLivesArray[i];
			life.setVisible(true);
		}
			
		if(mNumLives != 0) {
			queuePlayerSpawn();
			
		} else {
			queueGameOver();
		}
	}
	
	public void incrementScore() {
		mScore += 25; 
		mHudScoreActor.setText("SCORE : " + mScore);
	}
	
	public AsteroidsGameScreen() {
		mInstance = this;
		mStage = new Stage();
		mHudStage = new Stage();

		float padding = Gdx.graphics.getHeight() / 50;
		float fontHeight = Gdx.graphics.getHeight() / 20;
		float x = padding; 
		float y = Gdx.graphics.getHeight() - padding - fontHeight;
		
		mHudScoreActor = new TextActor();
		mHudScoreActor.setText("SCORE : 0");
		mHudScoreActor.setFontHeight(fontHeight);
		mHudScoreActor.setPosition(x, y);
		
		mHudStage.addActor(mHudScoreActor);
		
		float size =  Gdx.graphics.getHeight() / 20;
		
		y -= size + padding;
		x -= size / 3;
		
		for(int i = 0; i < 3; i ++) {
			PlayerShipActor life = new PlayerShipActor();
			life.setPosition(x, y); 
			
			
			life.setSize(size, size);
			
			x += 0.25 * padding + life.getWidth(); 
			
			mHudLivesArray[i] = life;
			mHudStage.addActor(life);
		}
		
		Gdx.input.setInputProcessor(mStage);
	}
	
	public void initializeGame() {
		
		mActorList.clear();
		
		addPlayer();
		for(int i = 0; i < 10; i ++) {
			AsteroidActor asteroid = addAsteroid();
			asteroid.calcStartParams();
		}
		
		for(int i = 0; i < 2; i ++) {
			addFlyingSaucer();
		}
		
		mScore = 0;
		mNumLives = 3;
		
	}
	

	@Override
	public void dispose() {
		mStage.dispose();
		
	}
	
	private void handleKillableActors() {
		
		mStage.getActors().clear();
		mBulletActorList.clear();
		mDamageableActorList.clear();

		for(BaseActor actor : mActorList) {
			 
			if(!actor.isDead()) {
				 mStage.addActor(actor);
				 
				 if(PlayerShipActor.class.isInstance(actor)) {
					 mPlayer = (PlayerShipActor) actor;
				 }
				 
				 if(DamageableActor.class.isInstance(actor)) {
					 mDamageableActorList.add((DamageableActor) actor);
				 }
				 
				 if(BulletActor.class.isInstance(actor)) {
					 mBulletActorList.add((BulletActor) actor);
				 }
				 
			}
		}
		
		mActorList.clear();
		
		for(Actor actor : mStage.getActors()) {
			
			mActorList.add((BaseActor) actor);
		}
	}
	
	private void handleBullets() {
		
		for(BulletActor bullet : mBulletActorList) {
			for(DamageableActor actor : mDamageableActorList) {
				
				if(bullet.intersects(actor)) {
					bullet.kill();
					actor.damage(bullet.getAngle());
					
					SparkEffectActor spark = addSparkEffect();
					spark.setPosition(bullet.getX(), bullet.getY());
				}
			}
		}
	}
	
	
	
	public void queuePlayerSpawn() {
		mStage.addAction(Actions.sequence(Actions.delay(1f), Actions.run(new Runnable() {
			
			@Override
			public void run() {
				addPlayer();
				
			}
		})));
	}
	
	public void queueFlyingSaucerSpawn() {
		mStage.addAction(Actions.sequence(Actions.delay(2f), Actions.run(new Runnable() {
			
			@Override
			public void run() {
				addFlyingSaucer();
				
			}
		})));
	}
	
	
	private void handleCollision() {

		for(DamageableActor actor: mDamageableActorList) {
			
			
			if(mPlayer != null && actor!= mPlayer && actor.intersects(mPlayer) ) {
				mPlayer.damage(actor.getAngle());
				actor.damage(mPlayer.getAngle());
				Vector2 sparkPos = mPlayer.getTipPosition();
				SparkEffectActor spark = addSparkEffect();
				spark.setPosition(sparkPos.x, sparkPos.y);
				mPlayer = null;
				
			}
		}
		
	}
	

	@Override
	public void resize(int width, int height) {
		mStage.setViewport(width, height);

	}



	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		
		handleKillableActors();
		handleCollision();
		handleBullets();
		
		mStage.act(delta);
		mStage.draw();
		mHudStage.draw();
		
	}


	@Override
	public void show() {
		Gdx.input.setInputProcessor(mStage);
		
	}

	@Override
	public void hide() {
		Gdx.input.setInputProcessor(null);
		
	}

	@Override
	public void pause() {
		
	}

	@Override
	public void resume() {
		
	}

}
