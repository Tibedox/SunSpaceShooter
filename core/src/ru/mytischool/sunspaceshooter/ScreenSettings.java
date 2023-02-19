package ru.mytischool.sunspaceshooter;

import static ru.mytischool.sunspaceshooter.MyGG.SCR_HEIGHT;
import static ru.mytischool.sunspaceshooter.MyGG.SCR_WIDTH;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;

public class ScreenSettings implements Screen {
    MyGG gg;
    Texture imgBG;
    TextButton btnName, btnSound, btnMusic, btnClearRec, btnBack;

    public ScreenSettings(MyGG myGG) {
        gg = myGG;
        imgBG = new Texture("bg/cosmos06.jpg");

        btnName = new TextButton(gg.fontLarge, "ИМЯ: "+gg.playerName, 1100);
        btnSound = new TextButton(gg.fontLarge, "ЗВУК ВКЛ", 1000);
        btnMusic = new TextButton(gg.fontLarge, "МУЗЫКА ВКЛ", 900);
        btnClearRec = new TextButton(gg.fontLarge, "ОЧИСТКА РЕКОРДОВ", 800);
        btnBack = new TextButton(gg.fontLarge, "НАЗАД", 700);
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
            if(btnName.hit(gg.touch.x, gg.touch.y)) {

            }
            if(btnSound.hit(gg.touch.x, gg.touch.y)) {

            }
            if(btnMusic.hit(gg.touch.x, gg.touch.y)) {

            }
            if(btnClearRec.hit(gg.touch.x, gg.touch.y)) {

            }
            if(btnBack.hit(gg.touch.x, gg.touch.y)) {
                gg.setScreen(gg.screenIntro);
            }
        }

        // события

        // отрисовка всей графики
        gg.camera.update();
        gg.batch.setProjectionMatrix(gg.camera.combined);
        gg.batch.begin();
        gg.batch.draw(imgBG, 0, 0, SCR_WIDTH, SCR_HEIGHT);
        btnName.font.draw(gg.batch, btnName.text, btnName.x, btnName.y);
        btnSound.font.draw(gg.batch, btnSound.text, btnSound.x, btnSound.y);
        btnMusic.font.draw(gg.batch, btnMusic.text, btnMusic.x, btnMusic.y);
        btnClearRec.font.draw(gg.batch, btnClearRec.text, btnClearRec.x, btnClearRec.y);
        btnBack.font.draw(gg.batch, btnBack.text, btnBack.x, btnBack.y);
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
