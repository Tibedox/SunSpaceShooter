package ru.mytischool.sunspaceshooter;

import static ru.mytischool.sunspaceshooter.MyGG.*;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;

public class ScreenGame implements Screen {
    MyGG gg;

    boolean isGyroscopeAvailable;

    Texture imgStars;
    Texture imgShip;

    Stars[] stars = new Stars[2];
    Ship ship;

    public ScreenGame(MyGG myGG) {
        gg = myGG;
        isGyroscopeAvailable = Gdx.input.isPeripheralAvailable(Input.Peripheral.Gyroscope);

        imgStars = new Texture("stars.png");
        imgShip = new Texture("ship.png");

        stars[0] = new Stars(SCR_WIDTH/2, SCR_HEIGHT/2);
        stars[1] = new Stars(SCR_WIDTH/2, SCR_HEIGHT*3/2);
        ship = new Ship();
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        String x="", y, z;
        // обработка касаний экрана
        if(Gdx.input.isTouched()) {
            gg.touch.set(Gdx.input.getX(), Gdx.input.getY(), 0);
            gg.camera.unproject(gg.touch);

            ship.vx = (gg.touch.x-ship.x)/20;
        } else if(isGyroscopeAvailable) {
            x = ""+Gdx.input.getGyroscopeX()*100;
        }

        // события
        for (Stars s: stars) s.move();
        ship.move();

        // отрисовка всей графики
        gg.camera.update();
        gg.batch.setProjectionMatrix(gg.camera.combined);
        gg.batch.begin();
        for (Stars s: stars) gg.batch.draw(imgStars, s.getX(), s.getY(), s.width, s.height);
        gg.batch.draw(imgShip, ship.getX(), ship.getY(), ship.width, ship.height);
        gg.font.draw(gg.batch, x, 10, 800);
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
        imgShip.dispose();
    }
}
