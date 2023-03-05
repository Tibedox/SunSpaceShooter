package ru.mytischool.sunspaceshooter;

import com.badlogic.gdx.math.MathUtils;

public class Fragment extends SpaceObject{
    int typeShip;
    int typeFragment;
    float angle, speedRotation;

    public Fragment(float x, float y, int typeShip) {
        super(x, y, MathUtils.random(20f, 40), MathUtils.random(20f, 40));
        float v = MathUtils.random(1f, 8);
        float a = MathUtils.random(0f, 360);
        vx = (float) (Math.sin(a)*v);
        vy = (float) (Math.cos(a)*v);
        typeFragment = MathUtils.random(0, 3);
        this.typeShip = typeShip;
        speedRotation = MathUtils.random(-5f, 5);
    }

    @Override
    void move() {
        super.move();
        angle += speedRotation;
    }
}
