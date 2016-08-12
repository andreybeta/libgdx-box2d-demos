package com.acarolabs.boxdemo;


import box2dLight.PointLight;
import box2dLight.RayHandler;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;

/**
 * {PROJECT_NAME}
 * Created by andrey on 12/08/16 06:24 PM.
 */
public class SimpleBox2dLights extends ApplicationAdapter {
    /** the camera **/
    OrthographicCamera camera;
    RayHandler rayHandler;
    World world;
    private Texture bg;
    private SpriteBatch batch;

    @Override
    public void create() {

        batch = new SpriteBatch();

        camera = new OrthographicCamera(48, 32);
        camera.update();
        world = new World(new Vector2(0, -10), true);
        rayHandler = new RayHandler(world);
//        new PointLight(rayHandler, 32);

        PointLight pointLight = new PointLight(rayHandler, 32);
        pointLight.setColor(Color.RED);

        PointLight bluePointLight = new PointLight(rayHandler, 32, Color.BLACK.BLUE, 20, 5, 5);
//
//        box2dLight.PointLight light = new box2dLight.PointLight(
//                rayHandler, RAYS_PER_BALL, null, LIGHT_DISTANCE, 0f, 0f);

//        rayHandler.

        bg = new Texture(Gdx.files.internal("bg.png"));
    }

    @Override
    public void render() {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        world.step(Gdx.graphics.getDeltaTime(), 8, 3);


        batch.begin();
        {
            batch.draw(bg, -48 / 2f, -32/2, 48, 32);
        }
        batch.end();

        rayHandler.setCombinedMatrix(camera.combined);
        rayHandler.updateAndRender();

        batch.setProjectionMatrix(camera.combined);
    }
}
