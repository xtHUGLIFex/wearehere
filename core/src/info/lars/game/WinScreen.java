package info.lars.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;

import java.awt.Font;

/**
 * Created by Lars on 13.02.2017.
 */

public class WinScreen implements Screen {


    OrthographicCamera camera;
    Texture back;
    Music win;
    final Drop game;
    Font wining;
    public WinScreen(final Drop gam) {
        game = gam;
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 800, 480);
        back = new Texture("win.png");
        win = Gdx.audio.newMusic(Gdx.files.internal("Гимн_-_России.mp3"));
        win.play();
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0.2f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        camera.update();
        game.batch.setProjectionMatrix(camera.combined);
        game.batch.begin();
        game.batch.draw(back,0,0,800,480);
        game.font.draw(game.batch, "Теперь ты один из нас",400,400);
        game.batch.end();
        if (Gdx.input.isTouched()){
            game.setScreen(new MainMenu(game));
            win.dispose();
            dispose();
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
    public void dispose() {
        win.dispose();
        back.dispose();

    }
}
