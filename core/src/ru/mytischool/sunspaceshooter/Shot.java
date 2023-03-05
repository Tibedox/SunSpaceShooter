package ru.mytischool.sunspaceshooter;

import static ru.mytischool.sunspaceshooter.MyGG.SCR_HEIGHT;
import static ru.mytischool.sunspaceshooter.MyGG.SCR_WIDTH;

import com.badlogic.gdx.math.MathUtils;

public class Shot extends SpaceObject{
    public Shot(float x, float y) {
        super(x, y, 100, 100);
        vy = 10;
    }

    boolean overlap(Enemy enemy){
        return Math.abs(x-enemy.x) < width/3 + enemy.width/2 & Math.abs(y-enemy.y) < height/3 + enemy.height/2;
    }
}
