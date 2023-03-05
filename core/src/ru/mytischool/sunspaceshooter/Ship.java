package ru.mytischool.sunspaceshooter;

import static ru.mytischool.sunspaceshooter.MyGG.SCR_WIDTH;

import com.badlogic.gdx.utils.TimeUtils;

public class Ship extends SpaceObject {
    boolean isInvisible;
    int lives = 3;
    long timeSartInvisible, timeInvisibleInterval = 1000;

    public Ship() {
        super(SCR_WIDTH/2, 100, 100, 100);
    }

    @Override
    void move() {
        super.move();
        outOfScreen();
        if(isInvisible){
            if(timeSartInvisible+timeInvisibleInterval< TimeUtils.millis()){
                x = SCR_WIDTH/2;
                isInvisible = false;
            }
        }
    }

    @Override
    boolean outOfScreen() {
        if(x<width/2){
            vx = 0;
            x = width/2;
        }
        if(x>SCR_WIDTH-width/2){
            vx = 0;
            x = SCR_WIDTH-width/2;
        }
        return true;
    }

    void kill(){
        isInvisible = true;
        lives--;
        timeSartInvisible = TimeUtils.millis();
    }
}
