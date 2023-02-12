package ru.mytischool.sunspaceshooter;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.math.Vector3;

import java.util.Map;
import java.util.TreeMap;

public class MyGG extends Game {
	public static final float SCR_WIDTH = 720, SCR_HEIGHT = 1280;
	SpriteBatch batch;
	OrthographicCamera camera;
	Vector3 touch;
	BitmapFont font, fontLarge;

	Map<String, String[]> text = new TreeMap<>();
	public static final int EN = 0, RU = 1;
	int lang = RU;

	ScreenGame screenGame;
	ScreenIntro screenIntro;
	ScreenSettings screenSettings;
	ScreenAbout screenAbout;

	@Override
	public void create () {
		// создание системных объектов
		batch = new SpriteBatch();
		camera = new OrthographicCamera();
		camera.setToOrtho(false, SCR_WIDTH, SCR_HEIGHT);
		touch = new Vector3();
		generateFont();
		setAllText();

		screenIntro = new ScreenIntro(this);
		screenGame = new ScreenGame(this);
		screenSettings = new ScreenSettings(this);
		screenAbout = new ScreenAbout(this);
		setScreen(screenIntro);
	}

	@Override
	public void dispose () {
		batch.dispose();
		font.dispose();
		fontLarge.dispose();
	}

	void generateFont(){
		FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("crystal.ttf"));
		FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
		parameter.color = new Color(1, 0.8f, 0.4f, 1);
		parameter.size = 45;
		parameter.borderColor = Color.BLACK;
		parameter.borderWidth = 2;
		parameter.borderStraight = true;
		parameter.shadowColor = new Color(0.1f, 0.1f, 0.1f, 0.8f);
		parameter.shadowOffsetX = parameter.shadowOffsetY = 3;
		parameter.characters = "абвгдеёжзийклмнопрстуфхцчшщъыьэюяabcdefghijklmnopqrstuvwxyzАБВГДЕЁЖЗИЙКЛМНОПРСТУФХЦЧШЩЪЫЬЭЮЯABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789][_!$%#@|\\/?-+=()*&.;:,{}\"´`'<>";
		font = generator.generateFont(parameter);
		parameter.size = 60;
		fontLarge = generator.generateFont(parameter);
		generator.dispose();
	}

	void setAllText(){
		text.put("Exit", new String[]{"Exit", "Выход"});
		text.put("EXIT", new String[]{"EXIT", "ВЫХОД"});
		text.put("KILLS", new String[]{"KILLS: ", "СБИТО: "});
		text.put("PLAY", new String[]{"PLAY", "ИГРАТЬ"});
		text.put("SETTINGS", new String[]{"SETTINGS", "НАСТРОЙКИ"});
		text.put("ABOUT", new String[]{"ABOUT", "ОБ ИГРЕ"});
		text.put("SOUND ON", new String[]{"SOUND ON", "ЗВУК ВКЛ"});
		text.put("SOUND OFF", new String[]{"SOUND OFF", "ЗВУК ВЫКЛ"});
		text.put("MUSIC ON", new String[]{"MUSIC ON", "МУЗЫКА ВКЛ"});
		text.put("MUSIC OFF", new String[]{"MUSIC OFF", "МУЗЫКА ВЫКЛ"});
		text.put("LANGUAGE", new String[]{"LANGUAGE EN", "ЯЗЫК РУС"});
	}
}
