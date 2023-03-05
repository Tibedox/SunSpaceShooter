package ru.mytischool.sunspaceshooter;

import static ru.mytischool.sunspaceshooter.MyGG.*;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
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
    Texture imgAtlasFragment;
    TextureRegion[][] imgFragment = new TextureRegion[2][4];
    Sound sndShot, sndExplosion;

    Stars[] stars = new Stars[2];
    Ship ship;
    ArrayList<Enemy> enemies = new ArrayList<>();
    ArrayList<Shot> shots = new ArrayList<>();
    ArrayList<Fragment> fragments = new ArrayList<>();
    static final int TYPE_ENEMY = 0, TYPE_SHIP = 1;
    Player[] players = new Player[11];

    TextButton btnPlay, btnExit;

    long timeEnemyLastSpawn, timeEnemySpawnInterval = 1000;
    long timeShotLastSpawn, timeShotSpawnInterval = 500;

    int kills;
    boolean isGameOver;

    public ScreenGame(MyGG myGG) {
        gg = myGG;
        isGyroscopeAvailable = Gdx.input.isPeripheralAvailable(Input.Peripheral.Gyroscope);
        isAccelerometerAvailable = Gdx.input.isPeripheralAvailable(Input.Peripheral.Accelerometer);

        btnPlay = new TextButton(gg.fontLarge, "ИГРАТЬ", 300);
        btnExit = new TextButton(gg.fontLarge, "ВЫХОД", 200);

        imgStars = new Texture("stars.png");
        imgShip = new Texture("ship.png");
        imgEnemy = new Texture("enemy2.png");
        imgShot = new Texture("shot.png");
        imgAtlasFragment = new Texture("fragments.png");
        for (int i = 0; i < 4; i++) {
            imgFragment[0][i] = new TextureRegion(imgAtlasFragment, 200*i, 0, 200, 200);
            imgFragment[1][i] = new TextureRegion(imgAtlasFragment, 200*i, 200, 200, 200);
        }

        sndShot = Gdx.audio.newSound(Gdx.files.internal("blaster.wav"));
        sndExplosion = Gdx.audio.newSound(Gdx.files.internal("explosion.wav"));

        // создание игроков для таблицы рекордов
        for (int i = 0; i < players.length; i++) {
            players[i] = new Player("Nobody", 0);
        }
        Player.loadTableOfRecords(players);

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
        if(isGameOver){
            if(btnPlay.hit(gg.touch.x, gg.touch.y)) {
                restart();
            }
            if(btnExit.hit(gg.touch.x, gg.touch.y)) {
                gg.setScreen(gg.screenIntro);
                restart();
            }
        }

        // события
        for (Stars s: stars) s.move();
        if(!isGameOver) ship.move();
        spawnEnemy();
        for (int i = enemies.size()-1; i >= 0; i--) {
            enemies.get(i).move();
            if(enemies.get(i).outOfScreen()){
                enemies.remove(i);
                if(!ship.isInvisible) killShip();
            }
        }
        if(!ship.isInvisible) spawnShot();
        for (int i = shots.size()-1; i >= 0; i--) {
            shots.get(i).move();
            if(shots.get(i).outOfScreen()){
                shots.remove(i);
                break;
            }
            for (int j = 0; j < enemies.size(); j++) {
                if(shots.get(i).overlap(enemies.get(j))){
                    spawnFragments(enemies.get(j).x, enemies.get(j).y, TYPE_ENEMY);
                    shots.remove(i);
                    enemies.remove(j);
                    if(gg.soundOn) sndExplosion.play();
                    kills++;
                    break;
                }
            }
        }
        for (int i = fragments.size()-1; i >= 0; i--) {
            fragments.get(i).move();
            if(fragments.get(i).outOfScreen()){
                fragments.remove(i);
            }
        }

        // отрисовка всей графики
        gg.camera.update();
        gg.batch.setProjectionMatrix(gg.camera.combined);
        gg.batch.begin();
        for(Stars s: stars) gg.batch.draw(imgStars, s.getX(), s.getY(), s.width, s.height);
        for(Fragment frag: fragments) gg.batch.draw(imgFragment[frag.typeShip][frag.typeFragment],
                frag.getX(), frag.getY(), frag.width/2, frag.height/2, frag.width, frag.height,
                1, 1, frag.angle);
        for(Shot shot: shots) gg.batch.draw(imgShot, shot.getX(), shot.getY(), shot.width, shot.height);
        for(Enemy enemy: enemies) gg.batch.draw(imgEnemy, enemy.getX(), enemy.getY(), enemy.width, enemy.height);
        if(!ship.isInvisible) gg.batch.draw(imgShip, ship.getX(), ship.getY(), ship.width, ship.height);
        gg.font.draw(gg.batch, "Kills: "+kills, 10, SCR_HEIGHT-10);
        for (int i = 1; i <= ship.lives; i++) {
            gg.batch.draw(imgShip, SCR_WIDTH-60*i, SCR_HEIGHT-60, 50, 50);
        }
        if(isGameOver){
            gg.font.draw(gg.batch, Player.tableOfRecordsToString(players), 200, SCR_HEIGHT-200);
            btnPlay.font.draw(gg.batch, btnPlay.text, btnPlay.x, btnPlay.y);
            btnExit.font.draw(gg.batch, btnExit.text, btnExit.x, btnExit.y);
        }
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
        imgAtlasFragment.dispose();
        sndShot.dispose();
        sndExplosion.dispose();
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

    void spawnFragments(float x, float y, int typeShip){
        for (int i = 0; i < 50; i++) {
            fragments.add(new Fragment(x, y, typeShip));
        }
    }

    void killShip(){
        spawnFragments(ship.x, ship.y, TYPE_SHIP);
        ship.kill();
        if(gg.soundOn) sndExplosion.play();
        if(ship.lives == 0){
            gameOver();
        }
    }

    void gameOver(){
        isGameOver = true;
        players[players.length-1].name = gg.playerName;
        players[players.length-1].kills = kills;
        Player.saveTableOfRecords(players);
    }

    void restart(){
        fragments.clear();
        enemies.clear();
        shots.clear();
        kills = 0;
        isGameOver = false;
        ship = new Ship();
        timeShotLastSpawn = TimeUtils.millis(); // костыль
    }
}
