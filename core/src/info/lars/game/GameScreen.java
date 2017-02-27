package info.lars.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;

import java.util.Iterator;

public class GameScreen implements Screen {
	OrthographicCamera camera;
	SpriteBatch batch;
	Texture  dropImage, bucketImage, Flag;
	Sound dropSound;
	Music rain;
	Rectangle bucket;
	Vector3 touchPos;
	Array<Rectangle> rainDrops;
	long lastDropTime;
	final Drop game;
	int Caught;


	public  GameScreen(final Drop gam) {
		this.game = gam;

		camera   = new OrthographicCamera();
		camera.setToOrtho(false,800,480);
		batch = new SpriteBatch();
		touchPos = new Vector3();

		dropImage = new Texture("preview.jpg");
		bucketImage = new Texture("preview1.jpg");
		Flag = new Texture("image.png");

		dropSound = Gdx.audio.newSound(Gdx.files.internal("Zieg_-_Heil (mp3cut.ru).mp3"));
		rain = Gdx.audio.newMusic(Gdx.files.internal("V_jskovij_Ukra_nskij_Marsh_-_YA_nasral_sob_v_sharovari_(iPlayer.fm).mp3"));

		rain.setLooping(true);
		rain.play();

		bucket = new Rectangle();
		bucket.x = 800/2 - 64/2;
		bucket.y = 20;
		bucket.width = 64;
		bucket.height = 64;
		rainDrops = new Array<Rectangle>();
		spawn();
	}
	private void spawn(){
		Rectangle rainDrop = new Rectangle();
		rainDrop.x = MathUtils.random(0, 800-64);
		rainDrop.y = 480-64;
		rainDrop.width = 64;
		rainDrop.height = 64;
		rainDrops.add(rainDrop);
		lastDropTime = TimeUtils.nanoTime();
	}

	@Override
	public void render (float delta) {
		Gdx.gl.glClearColor(0, 0, 0.2f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		camera.update();
		game.batch.setProjectionMatrix(camera.combined);
		game.batch.begin();
		game.batch.draw(Flag,0,0);
		game.font.draw(game.batch, "Armenians Caught: "+ Caught,0,480);
		game.batch.draw(bucketImage, bucket.x, bucket.y);
		for (Rectangle raindrop: rainDrops){
			game.batch.draw(dropImage, raindrop.x, raindrop.y);
		}
		game.batch.end();

		if (Gdx.input.isTouched()){

			touchPos.set(Gdx.input.getX(),Gdx.input.getY(), 0);
			camera.unproject(touchPos);
			bucket.x = touchPos.x - 64/2;
		}

		if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) bucket.x -= 200*Gdx.graphics.getDeltaTime();
		if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) bucket.x += 200*Gdx.graphics.getDeltaTime();

		if (bucket.x < 0) bucket.x = 0;
		if (bucket.x > 800) bucket.x = 800-64;

		if (TimeUtils.nanoTime() - lastDropTime >1000000000) spawn();

		Iterator<Rectangle> iter = rainDrops.iterator();
		while(iter.hasNext()){
			Rectangle raindrop = iter.next();
			raindrop.y -=200*Gdx.graphics.getDeltaTime();
			if (raindrop.y + 64 <0) iter.remove();
			if (raindrop.overlaps(bucket)){
				Caught++;
				dropSound.play();
				iter.remove();
				if (Caught == 100){
					game.setScreen(new WinScreen(game));
					rain.dispose();
					dispose();
				}
			}
		}
	}

	@Override
	public void resize(int width, int height) {

	}

	@Override
	public void pause() {

	}

	@Override
	public void resume() {

	}

	@Override
	public void hide() {

	}

	@Override
	public void dispose () {
		dropSound.dispose();
		bucketImage.dispose();
		dropImage.dispose();
		rain.dispose();
		batch.dispose();
	}

	@Override
	public void show(){
		rain.play();
	}
}
