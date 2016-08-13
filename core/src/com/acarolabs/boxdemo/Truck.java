package com.acarolabs.boxdemo;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g3d.utils.shapebuilders.BoxShapeBuilder;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.physics.box2d.joints.PrismaticJoint;
import com.badlogic.gdx.physics.box2d.joints.PrismaticJointDef;
import com.badlogic.gdx.physics.box2d.joints.RevoluteJoint;
import com.badlogic.gdx.physics.box2d.joints.RevoluteJointDef;

/**
 * {PROJECT_NAME}
 * Created by andrey on 13/08/16 12:05 PM.
 */
public class Truck {

    private final Vector2 startPosition;
    public final Body cart;
    private final FixtureDef boxDef;
    private final Body axle1;
    private final PrismaticJointDef prismaticJointDef;
    private final PrismaticJoint spring1;
    private final Body axle2;
    private final PrismaticJoint spring2;
    private final Body wheel1;
    private final Body wheel2;
    private final RevoluteJointDef revoluteJointDef;
    private final RevoluteJoint motor1;
    private final RevoluteJoint motor2;

    public Truck(final float x, final float y, World world) {
        BodyDef circleBodyDef = new BodyDef();
        startPosition = new Vector2(x, y);
        circleBodyDef.type = BodyDef.BodyType.DynamicBody;
        circleBodyDef.position.set(startPosition);
        circleBodyDef.angularDamping = 2;

        cart = world.createBody(circleBodyDef);

        boxDef = new FixtureDef();
        boxDef.density = 2;
        boxDef.friction = 0.5f;
        boxDef.restitution = 0.2f;
        boxDef.filter.groupIndex = -1;

        PolygonShape chassisShape = new PolygonShape();
        chassisShape.setAsBox(1.5f, 0.5f);

        boxDef.shape = chassisShape;
        cart.createFixture(boxDef);

        PolygonShape shape = new PolygonShape();
        shape.setAsBox(0.4f, 0.15f, new Vector2(-1, -0.3f), (float)Math.PI / 3f);
        boxDef.shape = shape;
        cart.createFixture(boxDef);

        shape.setAsBox(0.4f, 0.15f, new Vector2(1, -0.3f), (float)-Math.PI / 3f);
        boxDef.shape = shape;
        cart.createFixture(boxDef);

//        cart.SetMassFromShapes();

        boxDef.density = 1;

        // add the axles //
        axle1 = world.createBody(circleBodyDef);

        shape = new PolygonShape();
        shape.setAsBox(
                0.4f, 0.1f,
                new Vector2(
                        (float) (-1 - 0.6f * Math.cos(Math.PI / 3)),
                        (float) (-0.3f - 0.6f * Math.sin(Math.PI / 3))
                ),
                (float) (Math.PI / 3f)
        );
        boxDef.shape = shape;
        axle1.createFixture(boxDef);

//        axle1.SetMassFromShapes();

        prismaticJointDef = new PrismaticJointDef();
        prismaticJointDef.initialize(
                cart, axle1,
                axle1.getWorldCenter(),
                new Vector2(
                        (float)Math.cos(Math.PI/3),
                        (float)Math.sin(Math.PI/3)
                ));
        prismaticJointDef.lowerTranslation = -0.3f;
        prismaticJointDef.upperTranslation = 0.5f;
        prismaticJointDef.enableLimit = true;
        prismaticJointDef.enableMotor = true;

        spring1 = (PrismaticJoint) world.createJoint(prismaticJointDef);


        axle2 = world.createBody(circleBodyDef);

        shape = new PolygonShape();
        shape.setAsBox(
                0.4f, 0.1f,
                new Vector2(
                        (float) (1 + 0.6f * Math.cos(-Math.PI / 3f)),
                        (float) (-0.3f + 0.6f * Math.sin(-Math.PI / 3))
                ),
                (float) -(Math.PI / 3f)
        );
        boxDef.shape = shape;
        axle2.createFixture(boxDef);

//        axle2.SetMassFromShapes();

        prismaticJointDef.initialize(
                cart, axle2,
                axle2.getWorldCenter(),
                new Vector2(
                        (float)-Math.cos(Math.PI/3),
                        (float) Math.sin(Math.PI/3)
                )
        );

        spring2 = (PrismaticJoint) world.createJoint(prismaticJointDef);

        // add wheels //

        CircleShape circleShape = new CircleShape();
        circleShape.setRadius(0.7f);

        FixtureDef circleDef = new FixtureDef();

        circleDef.density = 1f;
        circleDef.friction = 5f;
        circleDef.restitution = 0.2f;
        circleDef.filter.groupIndex = -1;

        circleDef.shape = circleShape;

        //Wheel 1
        circleBodyDef = new BodyDef();
        circleBodyDef.angularDamping = 2;
        //circleBodyDef.allowSleep = false;
        circleBodyDef.type = BodyDef.BodyType.DynamicBody;


        circleBodyDef.position.set(
                (float) (axle1.getWorldCenter().x - 0.3 * Math.cos(Math.PI / 3)),
                (float) (axle1.getWorldCenter().y - 0.3 * Math.sin(Math.PI / 3))
        );


        wheel1 = world.createBody(circleBodyDef);
        wheel1.createFixture(circleDef);

        //Wheel 2
        circleBodyDef.position.set(
                (float) (axle2.getWorldCenter().x + 0.3*Math.cos(-Math.PI/3)),
                (float) (axle2.getWorldCenter().y + 0.3*Math.sin(-Math.PI/3))
        );

        wheel2 = world.createBody(circleBodyDef);
        wheel2.createFixture(circleDef);

        // add joints //
        revoluteJointDef = new RevoluteJointDef();
        revoluteJointDef.enableMotor = true;

        revoluteJointDef.initialize(axle1, wheel1, wheel1.getWorldCenter());
        motor1 = (RevoluteJoint)world.createJoint(revoluteJointDef);

        revoluteJointDef.initialize(axle2, wheel2, wheel2.getWorldCenter());
        motor2 = (RevoluteJoint) world.createJoint(revoluteJointDef);

//        for (int i = 0; i < 2; i++) {
//
//            circleBodyDef = new BodyDef();
//            if (i == 0) {
//                circleBodyDef.position.set(
//                        (float) (axle1.getWorldCenter().x - 0.3 * Math.cos(Math.PI / 3)),
//                        (float) (axle1.getWorldCenter().y - 0.3 * Math.sin(Math.PI / 3))
//                );
//            } else {
//                circleBodyDef.position.set(
//                        (float) (axle2.getWorldCenter().x + 0.3*Math.cos(-Math.PI/3)),
//                        (float) (axle2.getWorldCenter().y + 0.3*Math.sin(-Math.PI/3))
//                );
//            }
//
//            circleBodyDef.allowSleep = false;
//
//            if (i == 0) {
//                wheel1 = world.createBody(circleBodyDef);
//            } else {
//                wheel2 = world.createBody(circleBodyDef);
//            }
//
//
//            (i == 0 ? wheel1 : wheel2).createShape(circleDef);
//            (i == 0 ? wheel1 : wheel2).SetMassFromShapes();
//
//        }

    }

    public void update() {
       // motor1.setMaxMotorTorque(0);

//        if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
//            motor1.setMotorSpeed(15 * (float)Math.PI);
//
//        } else if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
//            motor1.setMotorSpeed(15 * (float)Math.PI);
//
//        } else {
//            motor1.setMotorSpeed(0);
//        }

        float f = (Gdx.input.isKeyPressed(Input.Keys.UP) ? 1 : Gdx.input.isKeyPressed(Input.Keys.DOWN) ? -1 : 0);

        motor1.setMotorSpeed(15 * (float) Math.PI * f);
        motor1.setMaxMotorTorque(Gdx.input.isKeyPressed(Input.Keys.UP) || Gdx.input.isKeyPressed(Input.Keys.DOWN) ? 17 : 0.5f);

        motor2.setMotorSpeed(15 * (float) Math.PI * f);
        motor2.setMaxMotorTorque(Gdx.input.isKeyPressed(Input.Keys.UP) || Gdx.input.isKeyPressed(Input.Keys.DOWN) ? 12 : 0.5f);

        spring1.setMaxMotorForce(30 + Math.abs(800 * (float)Math.pow(spring1.getJointTranslation(), 2)));
        //spring1.SetMotorSpeed(-4*Math.pow(spring1.GetJointTranslation(), 1));
        spring1.setMotorSpeed((spring1.getMotorSpeed() - 10 * spring1.getJointTranslation()) * 0.4f);

        spring2.setMaxMotorForce( 20 + Math.abs(800 * (float) Math.pow(spring2.getJointTranslation(), 2)));
        spring2.setMotorSpeed(-4 * (float) Math.pow(spring2.getJointTranslation(), 1));

        float torque = 30 * (Gdx.input.isKeyPressed(Input.Keys.LEFT) ? 1f: Gdx.input.isKeyPressed(Input.Keys.RIGHT) ? -1f : 0f);
        cart.applyTorque(torque, false);

    }
}
