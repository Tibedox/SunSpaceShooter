package ru.mytischool.sunspaceshooter;

import static ru.mytischool.sunspaceshooter.MyGG.SCR_WIDTH;

public class Ship extends SpaceObject {
    public Ship() {
        super(SCR_WIDTH/2, 150, 100, 100);
    }

    @Override
    void move() {
        super.move();
        outOfScreen();
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
}
