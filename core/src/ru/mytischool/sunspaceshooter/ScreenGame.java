package ru.mytischool.sunspaceshooter;

import static ru.mytischool.sunspaceshooter.MyGG.SCR_HEIGHT;
import static ru.mytischool.sunspaceshooter.MyGG.SCR_WIDTH;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;

public class ScreenGame implements Screen {
    MyGG gg;
    Texture imgStars;

    Stars[] stars = new Stars[2];

    public ScreenGame(MyGG myGG) {
        gg = myGG;
        imgStars = new Texture("stars.png");
        stars[0] = new Stars(SCR_WIDTH/2, SCR_HEIGHT/2);
        stars[1] = new Stars(SCR_WIDTH/2, SCR_HEIGHT*3/2);
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
// обработка касаний экрана
        if(Gdx.input.justTouched()) {
            gg.touch.set(Gdx.input.getX(), Gdx.input.getY(), 0);
            gg.camera.unproject(gg.touch);

        }

        // события
        for (Stars s: stars) s.move();

        // отрисовка всей графики
        gg.camera.update();
        gg.batch.setProjectionMatrix(gg.camera.combined);
        gg.batch.begin();
        for (Stars s: stars) gg.batch.draw(imgStars, s.getX(), s.getY(), s.width, s.height);
        gg.batch.end();
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
        imgStars.dispose();
    }
}
