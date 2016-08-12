package com.acarolabs.boxdemo.desktop;

import com.acarolabs.boxdemo.ChopperDemoGame;
import com.acarolabs.boxdemo.MeshTextureDemo;
import com.acarolabs.boxdemo.PolyBatchDemo;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.acarolabs.boxdemo.BoxDemoGame;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.width = 800;
		config.height = 600;

//		new LwjglApplication(new ChopperDemoGame(), config);
//		new LwjglApplication(new MeshTextureDemo(), config);
		new LwjglApplication(new BoxDemoGame(), config);
//		new LwjglApplication(new PolyBatchDemo(), config);
	}
}
