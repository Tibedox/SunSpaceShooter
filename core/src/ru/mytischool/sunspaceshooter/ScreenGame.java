package ru.mytischool.sunspaceshooter;

import static ru.mytischool.sunspaceshooter.MyGG.*;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Sound;
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
    Texture imgShot;
    Sound sndShot;

    Stars[] stars = new Stars[2];
    Ship ship;
    ArrayList<Enemy> enemies = new ArrayList<>();
    ArrayList<Shot> shots = new ArrayList<>();

    long timeEnemyLastSpawn, timeEnemySpawnInterval = 1200;
    long timeShotLastSpawn, timeShotSpawnInterval = 400;

    public ScreenGame(MyGG myGG) {
        gg = myGG;
        isGyroscopeAvailable = Gdx.input.isPeripheralAvailable(Input.Peripheral.Gyroscope);
        isAccelerometerAvailable = Gdx.input.isPeripheralAvailable(Input.Peripheral.Accelerometer);

        imgStars = new Texture("stars.png");
        imgShip = new Texture("ship.png");
        imgEnemy = new Texture("enemy2.png");
        imgShot = new Texture("shot.png");
        sndShot = Gdx.audio.newSound(Gdx.files.internal("blaster.wav"));

        stars[0] = new Stars(SCR_WIDTH/2, SCR_HEIGHT/2);
        stars[1] = new Stars(SCR_WIDTH/2, SCR_HEIGHT*3/2);
        ship = new Ship();
    }

    @Override
    public void show() {
        Gdx.input.setCatchKey(Input.Keys.BACK, true);
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
        if(Gdx.input.isKeyPressed(Input.Keys.BACK)) {
            gg.setScreen(gg.screenIntro);
        }

        // события
        for (Stars s: stars) s.move();
        ship.move();
        spawnEnemy();
        for (int i = enemies.size()-1; i >= 0; i--) {
            enemies.get(i).move();
            if(enemies.get(i).outOfScreen()){
                enemies.remove(i);
            }
        }
        spawnShot();
        for (int i = shots.size()-1; i >= 0; i--) {
            shots.get(i).move();
            if(shots.get(i).outOfScreen()){
                shots.remove(i);
            }
        }

        // отрисовка всей графики
        gg.camera.update();
        gg.batch.setProjectionMatrix(gg.camera.combined);
        gg.batch.begin();
        for(Stars s: stars) gg.batch.draw(imgStars, s.getX(), s.getY(), s.width, s.height);
        for(Shot shot: shots) gg.batch.draw(imgShot, shot.getX(), shot.getY(), shot.width, shot.height);
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
        Gdx.input.setCatchKey(Input.Keys.BACK, false);
    }

    @Override
    public void dispose() {
        imgStars.dispose();
        imgShip.dispose();
        imgEnemy.dispose();
        imgShot.dispose();
    }

    void spawnEnemy(){
        if(TimeUtils.millis() > timeEnemyLastSpawn+timeEnemySpawnInterval){
            enemies.add(new Enemy());
            timeEnemyLastSpawn = TimeUtils.millis();
        }
    }

    void spawnShot(){
        if(TimeUtils.millis() > timeShotLastSpawn+timeShotSpawnInterval){
            shots.add(new Shot(ship.x, ship.y));
            timeShotLastSpawn = TimeUtils.millis();
            if(gg.soundOn) sndShot.play();
        }
    }
}
