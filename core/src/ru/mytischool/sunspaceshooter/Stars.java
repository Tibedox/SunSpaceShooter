package ru.mytischool.sunspaceshooter;

import static ru.mytischool.sunspaceshooter.MyGG.*;

public class Stars extends SpaceObject {
    public Stars(float x, float y) {
        super(x, y, SCR_WIDTH, SCR_HEIGHT);
        vy = -2;
    }

    @Override
    void move() {
        super.move();
        if(outOfScreen()) {
            y = SCR_HEIGHT*3/2;
        }
    }
}
