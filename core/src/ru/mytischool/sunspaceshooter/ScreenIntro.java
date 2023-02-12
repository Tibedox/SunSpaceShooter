package ru.mytischool.sunspaceshooter;

import static ru.mytischool.sunspaceshooter.MyGG.SCR_HEIGHT;
import static ru.mytischool.sunspaceshooter.MyGG.SCR_WIDTH;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;

public class ScreenIntro implements Screen {
    MyGG gg;

    Texture imgBG;

    TextButton btnPlay, btnSettings, btnAbout, btnExit;

    public ScreenIntro(MyGG myGG) {
        gg = myGG;

        imgBG = new Texture("stars.png");

        btnPlay = new TextButton(gg.fontLarge, "PLAY", 650);
        btnSettings = new TextButton(gg.fontLarge, "SETTINGS", 550);
        btnAbout = new TextButton(gg.fontLarge, "ABOUT", 450);
        btnExit = new TextButton(gg.fontLarge, "EXIT", 350);
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
            if(btnPlay.hit(gg.touch.x, gg.touch.y)) {
                gg.setScreen(gg.screenGame);
            }
            if(btnSettings.hit(gg.touch.x, gg.touch.y)) {
                gg.setScreen(gg.screenSettings);
            }
            if(btnAbout.hit(gg.touch.x, gg.touch.y)) {
                gg.setScreen(gg.screenAbout);
            }
            if(btnExit.hit(gg.touch.x, gg.touch.y)) {
                Gdx.app.exit();
            }
        }

        // события

        // отрисовка всей графики
        gg.camera.update();
        gg.batch.setProjectionMatrix(gg.camera.combined);
        gg.batch.begin();
        gg.batch.draw(imgBG, 0, 0, SCR_WIDTH, SCR_HEIGHT);
        btnPlay.font.draw(gg.batch, gg.text.get(btnPlay.text)[gg.lang], btnPlay.x, btnPlay.y);
        btnSettings.font.draw(gg.batch, gg.text.get(btnSettings.text)[gg.lang], btnSettings.x, btnSettings.y);
        btnAbout.font.draw(gg.batch, gg.text.get(btnAbout.text)[gg.lang], btnAbout.x, btnAbout.y);
        btnExit.font.draw(gg.batch, gg.text.get(btnExit.text)[gg.lang], btnExit.x, btnExit.y);
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
        imgBG.dispose();
    }
}
