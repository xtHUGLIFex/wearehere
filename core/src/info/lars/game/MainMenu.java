package info.lars.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.sun.org.apache.xerces.internal.util.TeeXMLDocumentFilterImpl;

/**
 * Created by Lars on 11.02.2017.
 */

public class MainMenu  implements Screen {

    final Drop game;
    OrthographicCamera camera;
    Texture back;
    Music oh;

    public MainMenu(final Drop gam){
        game = gam;

        camera = new OrthographicCamera();
        camera.setToOrtho(false, 800, 480);
        back = new Texture("image(1).png");
        oh = Gdx.audio.newMusic(Gdx.files.internal("Hans Zimmer's Inception in Concert in Vienna - Time.mp3"));
        oh.play();
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
        game.batch.draw(back,0,0);
        game.font.draw(game.batch,"Tobi Pizda!!!", 100, 150);
        game.font.draw(game.batch,"Touch or Click to begin!", 100, 50);
        game.batch.end();

        if (Gdx.input.isTouched()){
            game.setScreen(new GameScreen(game));
            oh.dispose();
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

    }
}
