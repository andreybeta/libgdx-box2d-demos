package com.acarolabs.boxdemo;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.PolygonRegion;
import com.badlogic.gdx.graphics.g2d.PolygonSprite;
import com.badlogic.gdx.graphics.g2d.PolygonSpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.math.EarClippingTriangulator;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.ShortArray;

public class PolyBatchDemo extends ApplicationAdapter implements GestureDetector.GestureListener {

    Texture textureSolid;
    PolygonSprite polySprite;
    PolygonSpriteBatch polyBatch;
    OrthographicCamera camera;
    Vector2 touch;

    @Override
    public void create() {
        super.create();

        camera = new OrthographicCamera(Gdx.graphics.getWidth(),Gdx.graphics.getHeight());
        camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        touch = new Vector2();
        Gdx.input.setInputProcessor(new GestureDetector(this));

        polyBatch = new PolygonSpriteBatch(); // To assign at the beginning

        polyBatch.setProjectionMatrix(camera.combined);

        // Creating the color filling (but textures would work the same way)
        Pixmap pix = new Pixmap(1, 1, Pixmap.Format.RGBA8888);
        pix.setColor(0xFF33691E); // DE is red, AD is green and BE is blue.
        pix.fill();
        textureSolid = new Texture(pix);


        Texture texture = new Texture(Gdx.files.internal("ground.jpg"));

        texture.setWrap(Texture.TextureWrap.Repeat, Texture.TextureWrap.Repeat);

        TextureRegion textureRegion = new TextureRegion(texture);

        float[] vertices = new float[] {
                00, 0,
                10, 50,
                100, 60,
                200, 200,
                250, 250,
                400, 100,
                500, 50,
                600, 70,
                700, 80,
                900, 00
        };

        EarClippingTriangulator triangulator = new EarClippingTriangulator();
        ShortArray triangleIndices = triangulator.computeTriangles(vertices);

        PolygonRegion polyReg = new PolygonRegion(textureRegion, vertices, triangleIndices.toArray());

        polySprite = new PolygonSprite(polyReg);
    }

    @Override
    public void resize(int width, int height) {
        super.resize(width, height);
    }

    @Override
    public void render() {

        Gdx.gl.glClearColor(0.2f, 0.2f, 0.6f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        polyBatch.begin();
        polySprite.draw(polyBatch);
        polyBatch.end();

    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

    @Override
    public void dispose() {
        polyBatch.dispose();
    }

    @Override
    public boolean touchDown(float x, float y, int pointer, int button) {
        return false;
    }

    @Override
    public boolean tap(float x, float y, int count, int button) {
        return false;
    }

    @Override
    public boolean longPress(float x, float y) {
        return false;
    }

    @Override
    public boolean fling(float velocityX, float velocityY, int button) {
        return false;
    }

    @Override
    public boolean pan(float x, float y, float deltaX, float deltaY) {
        camera.translate(-deltaX, deltaY);
        camera.update();
        return true;
    }

    @Override
    public boolean panStop(float x, float y, int pointer, int button) {
        return false;
    }

    @Override
    public boolean zoom(float initialDistance, float distance) {
        return false;
    }

    @Override
    public boolean pinch(Vector2 initialPointer1, Vector2 initialPointer2, Vector2 pointer1, Vector2 pointer2) {
        return false;
    }

    @Override
    public void pinchStop() {

    }

}