package com.acarolabs.boxdemo;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;

public class BoxDemoGame extends ApplicationAdapter implements InputProcessor {
    private static final float MAX_VELOCITY = 100;
    SpriteBatch batch;
	Sprite sprite;
	Texture img;
	World world;
	Body body;
	Box2DDebugRenderer debugRenderer;
	Matrix4 debugMatrix;
	OrthographicCamera camera;


	float torque = 0.0f;
	boolean drawSprite = true;

	final float PIXELS_TO_METERS = 50f;
	private Car car;
    private BitmapFont defaultFont;
    private Texture background;
    private Chopper chop;
    private Mesh groundMesh;
    private ShaderProgram meshShader;
    private Texture groundTexture;
    private Mesh surfaceMesh;
    private Texture surfaceTexture;

    @Override
	public void create() {

		batch = new SpriteBatch();
		img = new Texture("badlogic.jpg");
        background = new Texture("blue_desert.png");

		sprite = new Sprite(img);
		sprite.setPosition(-sprite.getWidth()/2,-sprite.getHeight()/2);

		world = new World(new Vector2(0, -10f),true);


        Gdx.input.setInputProcessor(this);

        // Create a Box2DDebugRenderer, this allows us to see the physics simulation controlling the scene
        debugRenderer = new Box2DDebugRenderer();

        camera = new OrthographicCamera(Gdx.graphics.getWidth(),Gdx.graphics.
				getHeight());

        createBox(0, 0, 0.5f);
        //createBox(6, -2, 1.2f);
        //createBox(6, 0, 2);
        //createBox(5, 0, 0.5f);

        createFloor();

        createFont();

		car = new Car(4, 4, world);

        chop = new Chopper(0, 5, world);



        meshShader = new ShaderProgram(
                Gdx.files.internal("shaders/vertexShader.glsl"),
                Gdx.files.internal("shaders/fragmentShader.glsl")
        );

        initializeTextures();

	}

    private void createBox(float x, float y, float size) {
//        float x = (sprite.getX() + sprite.getWidth()/2) / PIXELS_TO_METERS;
//        float y = (sprite.getY() + sprite.getHeight()/2) / PIXELS_TO_METERS;

        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(x-5f, y+6);

        body = world.createBody(bodyDef);

        PolygonShape shape = new PolygonShape();
        shape.setAsBox(size, size);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.density = 0.5f;
        fixtureDef.friction = 0.3f;
        fixtureDef.restitution = 0.1f;

        body.createFixture(fixtureDef);
        shape.dispose();
    }

    private void createFloor() {
		BodyDef floorDef = new BodyDef();
		floorDef.type = BodyDef.BodyType.StaticBody;
		floorDef.position.set(0, 0f);

		Body floorBody = world.createBody(floorDef);

        ChainShape floorShape = new ChainShape();
        float[] floorPoints = new float[]{
            0, 2,
                10, 2,
                15, 0,
                20, 3,
                30, 3,
                40, 1,
                50, 0,
                80, 2,
                90, 4,
                100, 1,
                200, 5,
                300, 5,
                400, 0,
                500, 5,
                550, 0,
                550, 5
        };

        floorShape.createChain(floorPoints);

		FixtureDef floorFixtureDef = new FixtureDef();
		floorFixtureDef.shape = floorShape;
		floorFixtureDef.density = 40f;
        floorFixtureDef.friction = 30f;
		floorFixtureDef.restitution= 0.3f;

		floorBody.createFixture(floorFixtureDef);

		floorShape.dispose();

        createMeshFloor(floorPoints);
        createMeshFloorSurface(floorPoints);
	}


    private void initializeTextures() {
        groundTexture = new Texture(Gdx.files.internal("ground2.jpg"));
        groundTexture.setWrap(Texture.TextureWrap.Repeat, Texture.TextureWrap.Repeat);
        groundTexture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);

        surfaceTexture = new Texture(Gdx.files.internal("grass-2.png"));
        surfaceTexture.setWrap(Texture.TextureWrap.Repeat, Texture.TextureWrap.ClampToEdge);
        surfaceTexture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
    }

    private void createMeshFloor(float[] topVertices) {
        int points = topVertices.length / 2;
        float[] vertex = new float[points * 8];
        int offset = 0;

        for (int i = 0; i < points * 2; i = i+2) {
            float x = topVertices[i];
            float y = topVertices[i + 1];

            vertex[offset + 0] = x;
            vertex[offset + 1] = -10;
            vertex[offset + 2] = x / 2;
            vertex[offset + 3] = 0;

            vertex[offset + 4] = x;
            vertex[offset + 5] = y;
            vertex[offset + 6] = x / 2;
            vertex[offset + 7] = (y + 10) / 2;

            Gdx.app.log("MeshTextureDemo", "x: " + String.valueOf(vertex[offset + 6]));
            //Gdx.app.log("MeshTextureDemo", "y: " + String.valueOf(vertex[offset + 7]));

            offset += 8;
        }

        groundMesh = new Mesh(Mesh.VertexDataType.VertexArray, true, 2 * points, (2 * points - 1) * 6,
                new VertexAttribute(VertexAttributes.Usage.Position, 2, "a_position"),
                new VertexAttribute(VertexAttributes.Usage.TextureCoordinates, 2, "a_texCoord0"));

        groundMesh.setVertices(vertex);


        // INDICES
        short[] tempIn = new short[( 2 * points-1)*6];
        offset = 0;
        for (int i = 0; i < 2 * points - 2; i+=2) {

            tempIn[offset + 0] = (short) (i);       // below height
            tempIn[offset + 1] = (short) (i + 2);   // below next height
            tempIn[offset + 2] = (short) (i + 1);   // height

            tempIn[offset + 3] = (short) (i + 1);   // height
            tempIn[offset + 4] = (short) (i + 2);   // below next height
            tempIn[offset + 5] = (short) (i + 3);   // next height

            offset+=6;
        }
//        short[] v = new short[]{0, 1, 2};

        groundMesh.setIndices(tempIn);
//        groundMesh.setIndices(v);

    }

    private void createMeshFloorSurface(float[] topVertices) {
        int points = topVertices.length / 2;
        float[] vertex = new float[points * 8];
        int offset = 0;

        for (int i = 0; i < points * 2; i = i+2) {
            float x = topVertices[i];
            float y = topVertices[i + 1];

            vertex[offset + 0] = x;
            vertex[offset + 1] = y - 0.5f;
            vertex[offset + 2] = x / 2;
            vertex[offset + 3] = 1;

            vertex[offset + 4] = x;
            vertex[offset + 5] = y;
            vertex[offset + 6] = x / 2;
            vertex[offset + 7] = 0;

            //Gdx.app.log("MeshTextureDemo", "x: " + String.valueOf(vertex[offset + 6]));
            //Gdx.app.log("MeshTextureDemo", "y: " + String.valueOf(vertex[offset + 7]));

            offset += 8;
        }

        surfaceMesh = new Mesh(Mesh.VertexDataType.VertexArray, true, 2 * points, (2* points - 1) * 6,
                new VertexAttribute(VertexAttributes.Usage.Position, 2, "a_position"),
                new VertexAttribute(VertexAttributes.Usage.TextureCoordinates, 2, "a_texCoord0"));

        surfaceMesh.setVertices(vertex);

        // INDICES
        short[] tempIn = new short[( 2 * points-1)*6];
        offset = 0;
        for (int i = 0; i < 2 * points - 2; i+=2) {

            tempIn[offset + 0] = (short) (i);       // below height
            tempIn[offset + 1] = (short) (i + 2);   // below next height
            tempIn[offset + 2] = (short) (i + 1);   // height

            tempIn[offset + 3] = (short) (i + 1);   // height
            tempIn[offset + 4] = (short) (i + 2);   // below next height
            tempIn[offset + 5] = (short) (i + 3);   // next height

            offset+=6;
        }
//        short[] v = new short[]{0, 1, 2};

        surfaceMesh.setIndices(tempIn);
//        groundMesh.setIndices(v);

    }

    private void createFont() {
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("font.ttf"));

        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 18;
        parameter.characters = FreeTypeFontGenerator.DEFAULT_CHARS;


        defaultFont = generator.generateFont(parameter);
        defaultFont.setColor(Color.BLACK);
        generator.dispose();
    }

	@Override
	public void render() {
//        Gdx.gl.glClearColor(0.4f, 0.7f, 0.9f, 1);
//        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
////        Gdx.gl20.glEnable(GL20.GL_BLEND);
////        Gdx.gl20.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
//
//        Gdx.gl.glEnable(GL20.GL_BLEND);
//        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);

        Gdx.gl20.glClearColor(0, 0, 0, 1);
        Gdx.gl20.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);

        Gdx.gl20.glEnable(GL20.GL_BLEND);
        Gdx.gl20.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);


        update();

        batch.setProjectionMatrix(camera.combined);

		// Scale down the sprite batches projection matrix to box2D size
		debugMatrix = batch.getProjectionMatrix().cpy().scale(PIXELS_TO_METERS,
				PIXELS_TO_METERS, 0);


        batch.setShader(meshShader);
		batch.begin();

        batch.draw(background, camera.position.x - background.getWidth()/2, camera.position.y - background.getHeight()/2);
//		if(drawSprite) {
//            batch.draw(sprite, sprite.getX(), sprite.getY(), sprite.getOriginX(),
//                    sprite.getOriginY(),
//                    sprite.getWidth(), sprite.getHeight(), sprite.getScaleX(), sprite.
//                            getScaleY(), sprite.getRotation());
//        }


        defaultFont.draw(batch, "Camera X " + camera.position.x, camera.position.x - 390, camera.position.y + 300);
        defaultFont.draw(batch, "Camera X " + camera.position.y, camera.position.x - 390, camera.position.y + 290);


        defaultFont.draw(batch, "Car X " + car.chassis.getPosition().x, camera.position.x - 390, camera.position.y + 270);
        defaultFont.draw(batch, "Car Y " + car.chassis.getPosition().y, camera.position.x - 390, camera.position.y + 260);
        defaultFont.draw(batch, "Car Motor " + (car.isRunning() ? "ON" : "OFF"), camera.position.x - 390, camera.position.y + 250);







//        shapeRenderer.setProjectionMatrix(GameScreen.getCamera().combined);
        meshShader.begin();
        //shader.setUniformMatrix("u_projTrans", GameScreen.getCamera().combined);
        meshShader.setUniformMatrix("u_projTrans", debugMatrix);

        groundTexture.bind(0);
        meshShader.setUniformi("u_texture", 0);
        groundMesh.render(meshShader, GL20.GL_TRIANGLES);
        //groundMesh.render(meshShader, GL20.GL_LINES);

        surfaceTexture.bind(0);
        meshShader.setUniformi("u_texture", 0);
        surfaceMesh.render(meshShader, GL20.GL_TRIANGLES);
//        surfaceMesh.render(meshShader, GL20.GL_LINES);

        meshShader.end();

        batch.end();


        // Now render the physics world using our scaled down matrix
        // Note, this is strictly optional and is, as the name suggests, just for debugging purposes
        debugRenderer.render(world, debugMatrix);

	}

    private void update() {
        // Step the physics simulation forward at a rate of 60hz
        world.step(1f/60f, 6, 3);

//		camera.translate(
//				car.chassis.getPosition().x ,
//				car.chassis.getPosition().y
//		);
        this.camera.position.set(
                this.car.chassis.getPosition().x * PIXELS_TO_METERS,
                this.car.chassis.getPosition().y * PIXELS_TO_METERS,
                0
        );
        camera.update();

        chop.update();
        // Apply torque to the physics chassis.  At start this is 0 and will do nothing.  Controlled with [] keys
        // Torque is applied per frame instead of just once
        //chassis.applyTorque(torque,true);

        // Set the sprite's position from the updated physics chassis location
        sprite.setPosition(
        		(body.getPosition().x * PIXELS_TO_METERS) - sprite.getWidth()/2 ,
				(body.getPosition().y * PIXELS_TO_METERS) -sprite.getHeight()/2
		);

//        camera.translate(sprite.getX(), sprite.getY());

        // Ditto for rotation
        sprite.setRotation((float)Math.toDegrees(body.getAngle()));

        //chassis.applyForceToCenter(0, 20, true);
    }

    @Override
	public void dispose() {
		img.dispose();
		world.dispose();
	}



	@Override
	public boolean keyUp(int keycode) {
		return false;
	}

	@Override
	public boolean keyDown(int keycode) {

        Vector2 vel = this.body.getLinearVelocity();
        Vector2 pos = this.body.getPosition();
        Gdx.app.log("KEY", "Key code " + keycode);

// apply left impulse, but only if max velocity is not reached yet
        if (Gdx.input.isKeyJustPressed(Input.Keys.LEFT) && vel.x > -MAX_VELOCITY) {
            //this.chassis.applyLinearImpulse(-2f, 0, pos.x, pos.y, true);
            this.body.applyLinearImpulse(-2f, 0, pos.x, pos.y, true);
//            this.camera.translate(-0.5f * PIXELS_TO_METERS, 0);



        }

// apply right impulse, but only if max velocity is not reached yet
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT) && vel.x < MAX_VELOCITY) {
//            this.chassis.applyLinearImpulse(2f, 0, pos.x, pos.y, true);
            this.body.applyLinearImpulse(2f, 0, pos.x, pos.y, true);
        }

        if (Gdx.input.isKeyJustPressed(Input.Keys.R)) {
            this.car.reset();
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
            if (this.car.isRunning()) {
                this.car.stop();
            } else {
                this.car.run();
            }
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.D)) {
            this.car.toForward();

        }

        if (Gdx.input.isKeyJustPressed(Input.Keys.A)) {
            this.car.reverse();
        }

//		// On right or left arrow set the velocity at a fixed rate in that direction
//		if(keycode == Input.Keys.RIGHT)
//			chassis.applyLinearImpulse(1f, 0f, 0, 0, true);
//		if(keycode == Input.Keys.LEFT) {
//            chassis.applyLinearImpulse(-1f, 0f, 0, 0, true);
////            chassis.applyLinearImpulse(-1f,0f);
//        }
//
        if (keycode == Input.Keys.UP) {
            chop.up();
        }

        if (keycode == Input.Keys.LEFT) {
            chop.up();
        }

//		if(keycode == Input.Keys.UP) {
////			chassis.applyForceToCenter(0f,11f,true);
//            //chassis.applyForceToCenter(0, 400f, true);
//            body.applyLinearImpulse(0, 1, pos.x, pos.y, true);
//
////			chassis.applyForce(0,1)
//        }

//        if(keycode == Input.Keys.UP) {
//			chassis.applyForceToCenter(0f,11f,true);


//			chassis.applyForce(0,1)
//        }

//		if(keycode == Input.Keys.DOWN)
//			chassis.applyForceToCenter(0f, -10f, true);
//
//		// On brackets ( [ ] ) apply torque, either clock or counterclockwise
//		if(keycode == Input.Keys.RIGHT_BRACKET)
//			torque += 0.1f;
//		if(keycode == Input.Keys.LEFT_BRACKET)
//			torque -= 0.1f;
//
//		// Remove the torque using backslash /
//		if(keycode == Input.Keys.BACKSLASH)
//			torque = 0.0f;
//
//		// If user hits spacebar, reset everything back to normal
//		if(keycode == Input.Keys.SPACE) {
//			chassis.setLinearVelocity(0f, 0f);
//			chassis.setAngularVelocity(0f);
//			torque = 0f;
//			sprite.setPosition(0f,0f);
//			chassis.setTransform(0f,0f,0f);
//		}
//
//		// The ESC key toggles the visibility of the sprite allow user to see physics debug info
//		if(keycode == Input.Keys.ESCAPE)
//			drawSprite = !drawSprite;
//
		return true;
	}

	@Override
	public boolean keyTyped(char character) {
        Gdx.app.log("KEY", "Key code " + character);

        return true;
	}


    // On touch we apply force from the direction of the users touch.
    // This could result in the object "spinning"
    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {

//        //body.applyForce(1f, 1f, screenX, screenY, true);
//        float x = (camera.position.x + (screenX - (Gdx.graphics.getWidth() / 2))) / PIXELS_TO_METERS;
//        float y = (camera.position.y - (screenY - (Gdx.graphics.getHeight() / 2))) / PIXELS_TO_METERS;
//        createBox(x, y, 0.1f);
//
//        Gdx.app.log("DOWN", "Screen X: " + x + " Screen Y: " + y);
        //chassis.applyTorque(0.4f,true);
        if (this.car.isRunning()) {
            this.car.stop();
        } else {
            this.car.run();
        }

        return true;
    }

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		return false;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		return false;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		return false;
	}

	@Override
	public boolean scrolled(int amount) {
		return false;
	}
}

