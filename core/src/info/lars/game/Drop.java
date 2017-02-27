package info.lars.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * Created by Lars on 11.02.2017.
 */

public class Drop extends Game {

    SpriteBatch batch;//Для отображения текстур на экране
    BitmapFont font;//Для отображения текста на экране

    @Override
    public void create(){
        batch = new SpriteBatch();
        font = new BitmapFont();
        this.setScreen(new MainMenu(this));
    }

    @Override
    public  void render(){
        super.render();
    }

    @Override
    public void dispose(){
        super.dispose();
        batch.dispose();
        font.dispose();
    }
}
