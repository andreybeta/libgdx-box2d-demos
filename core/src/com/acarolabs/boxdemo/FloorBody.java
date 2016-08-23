package com.acarolabs.boxdemo;

import com.badlogic.gdx.physics.box2d.*;

/**
 * {PROJECT_NAME}
 * Created by andrey on 2/08/16 05:01 PM.
 */
public class FloorBody {

    public FloorBody(World world, float[] points) {
        BodyDef floorDef = new BodyDef();
        floorDef.type = BodyDef.BodyType.StaticBody;
        floorDef.position.set(0, 0f);

        Body floorBody = world.createBody(floorDef);

        ChainShape floorShape = new ChainShape();
        floorShape.createChain(points);

        FixtureDef floorFixtureDef = new FixtureDef();
        floorFixtureDef.shape = floorShape;
        floorFixtureDef.density = 4f;
        floorFixtureDef.friction = 0.3f;
        floorFixtureDef.restitution= 0.3f;

        floorBody.createFixture(floorFixtureDef);

        floorShape.dispose();
    }
}
