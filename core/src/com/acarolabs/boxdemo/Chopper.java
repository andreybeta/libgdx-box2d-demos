package com.acarolabs.boxdemo;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;

/**
 * {PROJECT_NAME}
 * Created by andrey on 2/08/16 04:35 PM.
 */
public class Chopper {

    private static final float CHASSIS_DENSITY = 1;
    private final Vector2 startPosition;
    private final World world;
    private final Body chassis;

    public Chopper(final float x, final float y, World world) {
        startPosition = new Vector2(x, y);
        this.world = world;

        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(startPosition);


        chassis = world.createBody(bodyDef);


        // Chassis
        PolygonShape chassisShape = new PolygonShape();
        chassisShape.setAsBox(2, 1);

        FixtureDef chassisFixtureDef = new FixtureDef();
        chassisFixtureDef.shape = chassisShape;
        chassisFixtureDef.density = CHASSIS_DENSITY;

        chassis.createFixture(chassisFixtureDef);

    }

    public void up() {
        chassis.applyForceToCenter(0, 110, true);
//        chassis.applyLinearImpulse(0, 50, 0, 0, true);
//        chassis.setLinearVelocity(0, 9);
//        chassis.setLinearVelocity(1, 0);

    }

    public void reset(){
        chassis.setTransform(startPosition, 0);
        chassis.setLinearVelocity(0, 0);
        chassis.setAngularVelocity(0);
        chassis.resetMassData();
    }

    public void update() {
//        chassis.applyForceToCenter(0, 50, true);
        if (chassis.getLinearVelocity().y < 1) {
          chassis.applyLinearImpulse(0, 1.5f, 0, 0, true);
        }


//        chassis.setLinearVelocity(0, 1);
    }
}
