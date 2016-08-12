package com.acarolabs.boxdemo;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.physics.box2d.joints.WheelJoint;
import com.badlogic.gdx.physics.box2d.joints.WheelJointDef;

/**
 * {PROJECT_NAME}
 * Created by andrey on 1/08/16 12:04 PM.
 */
public class Car {

    public static final float CHASSIS_DENSITY = 10f;
    public static final float WHEEL_DENSITY = 20f;
    public static final float FRICTION = 100f;
    public static final int MOTOR_SPEED = -30;
    public static final int MAX_MOTOR_TORQUE = 500;
    public final Body chassis;
    private final Body leftWheelBody;
    private final Body rightWheelBody;
    private final WheelJoint leftAxis;
    private final WheelJoint rightAxis;

    Vector2 startPosition;
    private boolean isRunning;

    public Car(final float x, final float y, World world) {
        BodyDef bodyDef = new BodyDef();
        startPosition = new Vector2(x, y);
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(startPosition);

        bodyDef.angularDamping = 5 ;

        this.chassis = world.createBody(bodyDef);

        // Chassis
        PolygonShape chassisShape = new PolygonShape();
        chassisShape.setAsBox(3, 1);

        FixtureDef chassisFixtureDef = new FixtureDef();
        chassisFixtureDef.shape = chassisShape;
        chassisFixtureDef.density = CHASSIS_DENSITY;
        chassisFixtureDef.friction = FRICTION;
//        fixtureDef.restitution = 0.6f;

        chassis.createFixture(chassisFixtureDef);

        // Wheels

        CircleShape wheelShape = new CircleShape();
        wheelShape.setRadius(0.5f);

        FixtureDef wheelFixtureDef = new FixtureDef();
        wheelFixtureDef.shape = wheelShape;
        wheelFixtureDef.density = WHEEL_DENSITY;
        wheelFixtureDef.restitution = 0.1f;
        wheelFixtureDef.friction = FRICTION;

        this.leftWheelBody = world.createBody(bodyDef);
        leftWheelBody.createFixture(wheelFixtureDef );

        this.rightWheelBody = world.createBody(bodyDef);
//        rightWheelBody.createFixture(wheelShape, 5);
        rightWheelBody.createFixture(wheelFixtureDef);


        WheelJointDef wheelJointDef = new WheelJointDef();
        wheelJointDef.bodyA = chassis;
        wheelJointDef.bodyB = leftWheelBody;
        wheelJointDef.localAnchorA.set(-2f, -1.6f);
        wheelJointDef.localAxisA.set(Vector2.Y);
        //wheelJointDef.enableMotor = true;
        wheelJointDef.motorSpeed = MOTOR_SPEED;
        wheelJointDef.frequencyHz = 5;
        wheelJointDef.maxMotorTorque = MAX_MOTOR_TORQUE;
       // wheelJointDef.dampingRatio = 5;


        leftAxis = (WheelJoint) world.createJoint(wheelJointDef);
//        leftAxis.setSpringFrequencyHz(1);

        wheelJointDef.bodyB = rightWheelBody;
//        wheelJointDef.enableMotor = false;
        wheelJointDef.localAnchorA.set(2f, -1.6f);
        rightAxis = (WheelJoint) world.createJoint(wheelJointDef);



        chassisShape.dispose();
        wheelShape.dispose();
    }

    public void stop(){
        //rightAxis.enableMotor(false);
        isRunning = false;
        leftAxis.enableMotor(false);
    }

    public void run(){
        //rightAxis.enableMotor(true);
        isRunning = true;
        leftAxis.enableMotor(true);
    }

    public void reset(){
        chassis.setTransform(startPosition, 0);
        chassis.setLinearVelocity(0, 0);
        chassis.setAngularVelocity(0);
        chassis.resetMassData();


        leftWheelBody.setTransform(startPosition.x, startPosition.y - 1.6f, 0);
        leftWheelBody.setLinearVelocity(0, 0);
        leftWheelBody.setAngularVelocity(0);
        leftWheelBody.resetMassData();

//        leftAxis.enableMotor(false);
//        rightAxis.enableMotor(false);

//        leftAxis.setMotorSpeed(0);
//        rightAxis.setMotorSpeed(0);


        rightWheelBody.setTransform(startPosition.x, startPosition.y - 1.6f, 0);
        rightWheelBody.setLinearVelocity(0, 0);
        rightWheelBody.setAngularVelocity(0);
        rightWheelBody.resetMassData();
    }

    public boolean isRunning() {
        return isRunning;
    }

    public void toForward() {
        leftAxis.setMotorSpeed(-MOTOR_SPEED);
    }

    public void reverse() {
        leftAxis.setMotorSpeed(MOTOR_SPEED);
    }
}
