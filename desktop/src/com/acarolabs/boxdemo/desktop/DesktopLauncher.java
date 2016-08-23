package com.acarolabs.boxdemo.desktop;

import com.acarolabs.boxdemo.*;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.width = 800;
		config.height = 600;

//		new LwjglApplication(new BoxDemoGame(), config);
//		new LwjglApplication(new ChopperDemoGame(), config);
//		new LwjglApplication(new MeshTextureDemo(), config);
//		new LwjglApplication(new PolyBatchDemo(), config);
//		new LwjglApplication(new Box2dLightTest(), config);
//		new LwjglApplication(new SimpleBox2dLights(), config);
		new LwjglApplication(new ExplosionDemo(), config);
	}
}
