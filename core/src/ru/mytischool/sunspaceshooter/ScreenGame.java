package ru.mytischool.sunspaceshooter;

import static ru.mytischool.sunspaceshooter.MyGG.*;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.TimeUtils;

import java.util.ArrayList;

public class ScreenGame implements Screen {
    MyGG gg;

    boolean isGyroscopeAvailable;
    boolean isAccelerometerAvailable;

    Texture imgStars;
    Texture imgShip;
    Texture imgEnemy;

    Stars[] stars = new Stars[2];
    Ship ship;
    ArrayList<Enemy> enemies = new ArrayList<>();

    long timeEnemyLastSpawn, timeEnemySpawnInterval = 1000;

    public ScreenGame(MyGG myGG) {
        gg = myGG;
        isGyroscopeAvailable = Gdx.input.isPeripheralAvailable(Input.Peripheral.Gyroscope);
        isAccelerometerAvailable = Gdx.input.isPeripheralAvailable(Input.Peripheral.Accelerometer);

        imgStars = new Texture("stars.png");
        imgShip = new Texture("ship.png");
        imgEnemy = new Texture("enemy2.png");

        stars[0] = new Stars(SCR_WIDTH/2, SCR_HEIGHT/2);
        stars[1] = new Stars(SCR_WIDTH/2, SCR_HEIGHT*3/2);
    }

    @Override
    public void show() {
        ship = new Ship();
    }

    @Override
    public void render(float delta) {
        // обработка касаний экрана
        if(Gdx.input.isTouched()) {
            gg.touch.set(Gdx.input.getX(), Gdx.input.getY(), 0);
            gg.camera.unproject(gg.touch);

            ship.vx = (gg.touch.x-ship.x)/20;
        } else if(isAccelerometerAvailable) {
            ship.vx = -Gdx.input.getAccelerometerX()*10;
        } else if(isGyroscopeAvailable) {
            ship.vx = Gdx.input.getGyroscopeY()*10;
        }

        // события
        for (Stars s: stars) s.move();
        ship.move();
        spawnEnemy();
        for (int i = 0; i < enemies.size(); i++) {
            enemies.get(i).move();
        }


        // отрисовка всей графики
        gg.camera.update();
        gg.batch.setProjectionMatrix(gg.camera.combined);
        gg.batch.begin();
        for (Stars s: stars) gg.batch.draw(imgStars, s.getX(), s.getY(), s.width, s.height);
        for(Enemy enemy: enemies) gg.batch.draw(imgEnemy, enemy.getX(), enemy.getY(), enemy.width, enemy.height);
        gg.batch.draw(imgShip, ship.getX(), ship.getY(), ship.width, ship.height);
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
        imgEnemy.dispose();
    }

    void spawnEnemy(){
        if(TimeUtils.millis() > timeEnemyLastSpawn+timeEnemySpawnInterval){
            enemies.add(new Enemy());
            timeEnemyLastSpawn = TimeUtils.millis();
        }
    }
}
