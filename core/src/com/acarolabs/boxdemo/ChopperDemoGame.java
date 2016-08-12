package com.acarolabs.boxdemo;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;

/**
 * {PROJECT_NAME}
 * Created by andrey on 2/08/16 04:55 PM.
 */
public class ChopperDemoGame extends ApplicationAdapter{

    private World world;
    private Box2DDebugRenderer debugRenderer;
    private OrthographicCamera camera;
    private Chopper chopper;

    @Override
    public void create() {
        world = new World(new Vector2(0, -10f),true);
        debugRenderer = new Box2DDebugRenderer();

        chopper = new Chopper(0,0, world);

        new FloorBody(world, new float[]{
                -50, -5,
                50, -5
        });

        camera = new OrthographicCamera(Gdx.graphics.getWidth() / 10,Gdx.graphics.getHeight() / 10);


    }

    @Override
    public void render() {
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        update();

        debugRenderer.render(world, camera.combined);
    }

    private void update() {
//        world.step(1f/60f, 6, 2);
        world.step(1/24f, 6, 2);

        chopper.update();

        if(Gdx.input.isTouched()){
            chopper.up();
        }

        if (Gdx.input.isKeyJustPressed(Input.Keys.R)) {
            chopper.reset();
        }
    }

}
