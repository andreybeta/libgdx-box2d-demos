package com.acarolabs.boxdemo;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.physics.box2d.*;

import static com.acarolabs.boxdemo.ExplosionDemo.PIXELS_TO_METERS;

/**
 * {PROJECT_NAME}
 * Created by andrey on 13/08/16 03:09 PM.
 */
public class Coin extends Sprite{
    private static final float SIZE = 0.5f * PIXELS_TO_METERS; // 50cms
    //private final Sprite coinSprite;
    private final Body coinBody;

    public Coin(final float x, final float y, World world) {

        super(new Texture(Gdx.files.internal("coin.png")));
//        setTexture(texture);
        setSize(SIZE, SIZE);
        setPosition(x - SIZE / 2, y - SIZE / 2);
        setOriginCenter();


        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(getX() + SIZE / 2, getY() + SIZE / 2);

        coinBody = world.createBody(bodyDef);

        FixtureDef fd1 = new FixtureDef();

        CircleShape cTest = new CircleShape();
        cTest.setRadius((SIZE / 2) / PIXELS_TO_METERS);

        fd1.shape = cTest;
        //fd1.isSensor = true;
        fd1.density = 3f;
        fd1.friction = 0.4f;
        fd1.restitution = 0.5f;

        coinBody.createFixture(fd1).setUserData("coin");

    }

    public void update(){
        setRotation(MathUtils.radiansToDegrees * coinBody.getAngle());
        setPosition(
                (coinBody.getPosition().x * PIXELS_TO_METERS) - SIZE/2,
                (coinBody.getPosition().y * PIXELS_TO_METERS) - SIZE/2
        );

    }

    public void render(SpriteBatch batch) {
        batch.draw(this,
                getX(), getY(),
                getOriginX(), getOriginY(),
                getWidth(), getHeight(),
                getScaleX(), getScaleY(),
                getRotation()
        );
    }
}
