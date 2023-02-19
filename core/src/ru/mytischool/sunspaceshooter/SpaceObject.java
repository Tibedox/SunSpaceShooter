package ru.mytischool.sunspaceshooter;

import static ru.mytischool.sunspaceshooter.MyGG.SCR_HEIGHT;
import static ru.mytischool.sunspaceshooter.MyGG.SCR_WIDTH;

public class SpaceObject {
    float x, y;
    float width, height;
    float vx, vy;
    boolean alive = true;

    public SpaceObject(float x, float y, float width, float height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    void move(){
        x+=vx;
        y+=vy;
    }

    float getX(){
        return x-width/2;
    }

    float getY() {
        return y-height/2;
    }

    boolean outOfScreen(){
        return x<0-width/2 || x>SCR_WIDTH+width/2 || y<0-height/2 || y>SCR_HEIGHT+height/2;
    }
}
