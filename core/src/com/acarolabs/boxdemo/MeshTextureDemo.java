/*******************************************************************************
 * Copyright 2011 See AUTHORS file.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 ******************************************************************************/
/*
 * Copyright 2010 Mario Zechner (contact@badlogicgames.com), Nathan Sweet (admin@esotericsoftware.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the
 * License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS"
 * BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language
 * governing permissions and limitations under the License.
 */

package com.acarolabs.boxdemo;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.VertexAttributes.Usage;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.FrameBuffer;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.sun.prism.ps.ShaderFactory;

import java.util.Random;

/**
 * Draws a triangle and a trapezoid. The trapezoid is intersection between two triangles, one stencil
 * and the triangle shown on left.
 */
public class MeshTextureDemo extends ApplicationAdapter {
    private static final float FACTOR = 400;
    static final int LENGTH = 100;

    FrameBuffer stencilFrameBuffer;
    FrameBuffer frameBuffer;
    Mesh mesh;

    Mesh stencilMesh;
    ShaderProgram meshShader;
    Texture texture;
    SpriteBatch spriteBatch;
    private Mesh groundMesh;
    private Mesh surfaceMesh;
    private Texture groundTexture;
    private Texture surfaceTexture;

    @Override
    public void render () {
//        Gdx.gl20.glViewport(0, 0, frameBuffer.getWidth(), frameBuffer.getHeight());
//        Gdx.gl20.glViewport(0, 0, 800, 600);
        Gdx.gl20.glClearColor(0.4f, 0.7f, 0.9f, 1);
        Gdx.gl20.glClear(GL20.GL_COLOR_BUFFER_BIT);
        Gdx.gl20.glEnable(GL20.GL_BLEND);
        Gdx.gl20.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
//        Gdx.gl.glEnable(GL20.GL_TEXTURE_2D);

//        Gdx.gl20.glEnable(GL20.GL_TEXTURE_2D);
//        Gdx.gl20.glColorMask(false, false, false, false);
//        Gdx.gl20.glDepthMask(false);

//        frameBuffer.begin();
//        texture.bind();
//        meshShader.begin();
//        meshShader.setUniformi("u_texture", 0);
//        mesh.render(meshShader, GL20.GL_TRIANGLES);
//        meshShader.end();
//        frameBuffer.end();
//
//        stencilFrameBuffer.begin();
//        Gdx.gl20.glViewport(0, 0, frameBuffer.getWidth(), frameBuffer.getHeight());
//        Gdx.gl20.glClearColor(1f, 1f, 0f, 1);
//        Gdx.gl20.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_STENCIL_BUFFER_BIT);
//        Gdx.gl20.glEnable(GL20.GL_TEXTURE_2D);
//
//        Gdx.gl20.glEnable(GL20.GL_STENCIL_TEST);
//
//        Gdx.gl20.glColorMask(false, false, false, false);
//        Gdx.gl20.glDepthMask(false);
//        Gdx.gl20.glStencilFunc(GL20.GL_NEVER, 1, 0xFF);
//        Gdx.gl20.glStencilOp(GL20.GL_REPLACE, GL20.GL_KEEP, GL20.GL_KEEP);
//
//        Gdx.gl20.glStencilMask(0xFF);
//        Gdx.gl20.glClear(GL20.GL_STENCIL_BUFFER_BIT);
//
//        meshShader.begin();
//        stencilMesh.render(meshShader, GL20.GL_TRIANGLES);
//        meshShader.end();
//
//        Gdx.gl20.glColorMask(true, true, true, true);
//        Gdx.gl20.glDepthMask(true);
//        Gdx.gl20.glStencilMask(0x00);
//        Gdx.gl20.glStencilFunc(GL20.GL_EQUAL, 1, 0xFF);
//
//        meshShader.begin();
//        mesh.render(meshShader, GL20.GL_TRIANGLES);
//        meshShader.end();
//
//
//        Gdx.gl20.glDisable(GL20.GL_STENCIL_TEST);
//        stencilFrameBuffer.end();




        draw(spriteBatch, meshShader);


        texture.bind();
        meshShader.begin();
//        meshShader.setUniformMatrix("u_worldView", matrix);
        meshShader.setUniformMatrix("u_projTrans", spriteBatch.getProjectionMatrix());

        meshShader.setUniformi("u_texture", 0);
        mesh.render(meshShader, GL20.GL_TRIANGLES);
        mesh.render(meshShader, GL20.GL_LINE_STRIP);
        meshShader.end();

//        Gdx.gl20.glViewport(0, 0, Gdx.graphics.getBackBufferWidth(), Gdx.graphics.getBackBufferHeight());
//        Gdx.gl20.glClearColor(0.2f, 0.2f, 0.2f, 1);
//        Gdx.gl20.glClear(GL20.GL_COLOR_BUFFER_BIT);
//
//        spriteBatch.begin();
//        spriteBatch.draw(frameBuffer.getColorBufferTexture(), 0, 0, 256, 256, 0, 0, frameBuffer.getColorBufferTexture().getWidth(),
//                frameBuffer.getColorBufferTexture().getHeight(), false, true);
//
//        spriteBatch.draw(stencilFrameBuffer.getColorBufferTexture(), 256, 256, 256, 256, 0, 0, frameBuffer.getColorBufferTexture()
//                .getWidth(), frameBuffer.getColorBufferTexture().getHeight(), false, true);
//        spriteBatch.end();
    }

    @Override
    public void create () {


        mesh = new Mesh(true, 8, 9,
                new VertexAttribute(Usage.Position, 3, ShaderProgram.POSITION_ATTRIBUTE),
//                new VertexAttribute(Usage.ColorPacked, 4, ShaderProgram.COLOR_ATTRIBUTE),
                new VertexAttribute(Usage.TextureCoordinates, 2, ShaderProgram.TEXCOORD_ATTRIBUTE+"0")
        );

        //float col = Color.toFloatBits(255, 255, 255, 255);


//        mesh.setVertices(new float[] {
//            -0.5f, -0.5f, 0, col,
//                0.5f, -0.5f, 0, c2,
//                0, 0.5f, 0, c3,
//        });
//
        mesh.setIndices(new short[] {
            0, 1, 2,
                0, 2, 3,
                4, 5, 6
        });

        mesh.setVertices(new float[] {
                000, 200, 0, 0, 0,
                200, 200, 0, 1, 0,
                200, 000, 0, 1, 1,
                000, 000, 0, 0, 1,

                200, 200, 0, 0, 0,
                400, 400, 0, 1, 0,
                200, 000, 0, 0, 1,
        });
//        mesh.setVertices(new float[] {
//                50, 100, 0, col, 0, 1,
//                500, 150, 0, col, 1, 1,
//                600, 150, 0, col, 0, 1,
//                700, 100, 0, col, 1, 1,
//
//                700, 50, 0, col, 1, 0,
//                600, 100, 0, col, 0, 0,
//                500, 100, 0, col, 1, 0,
//                50, 50, 0, col, 0, 0,
//
//
//
//        });

//        mesh.setVertices(new float[] { -0.5f, -0.5f, 0, 0, 0, 0.5f, -0.5f, 0, 1, 0, 0, 0.5f, 0, 0.5f, 1});

//        stencilMesh = new Mesh(true, 3, 0,
//                new VertexAttribute(Usage.Position, 3, "a_Position"),
//                new VertexAttribute(Usage.ColorPacked, 4, "a_Color"),
//                new VertexAttribute(Usage.TextureCoordinates, 2, "a_texCoords")
//        );
//
//        stencilMesh.setVertices(new float[] {-0.5f, 0.5f, 0, col, 0, 0, 0.5f, 0.5f, 0, c2, 1, 0, 0, -0.5f, 0, c3, 0.5f, 1});

        texture = new Texture(Gdx.files.internal("uvmap.jpg"));
        texture.setWrap(Texture.TextureWrap.Repeat, Texture.TextureWrap.Repeat);
        texture.setFilter(Texture.TextureFilter.Linear,
                Texture.TextureFilter.Linear);

        spriteBatch = new SpriteBatch();
//        frameBuffer = new FrameBuffer(Format.RGB565, 128, 128, false);
//        stencilFrameBuffer = new FrameBuffer(Format.RGB565, 128, 128, false, true);
        createShader();


        initializeTextures();
        createMeshes(100);
//        ShaderProgram sp = ShaderPrograml.
    }

    private void initializeTextures() {
        groundTexture = new Texture(Gdx.files.internal("ground3.png"));
        groundTexture.setWrap(Texture.TextureWrap.Repeat, Texture.TextureWrap.Repeat);
        groundTexture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);

        surfaceTexture = new Texture(Gdx.files.internal("grass2.png"));
        surfaceTexture.setWrap(Texture.TextureWrap.Repeat, Texture.TextureWrap.ClampToEdge);
        surfaceTexture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);

    }

    public void draw(SpriteBatch batch, ShaderProgram shader){


        batch.setShader(shader);
//        shapeRenderer.setProjectionMatrix(GameScreen.getCamera().combined);
        shader.begin();
        //shader.setUniformMatrix("u_projTrans", GameScreen.getCamera().combined);
        shader.setUniformMatrix("u_projTrans", spriteBatch.getProjectionMatrix());

        groundTexture.bind();
        shader.setUniformi("u_texture", 0);
        groundMesh.render(shader, GL20.GL_TRIANGLES);
        groundMesh.render(shader, GL20.GL_LINES);


        surfaceTexture.bind();
        shader.setUniformi("u_texture", 0);
//        shader.setUniformMatrix("u_projTrans", GameScreen.getCamera().combined);
        shader.setUniformMatrix("u_projTrans", spriteBatch.getProjectionMatrix());

        surfaceMesh.render(shader, GL20.GL_TRIANGLES);
        surfaceMesh.render(shader, GL20.GL_LINES);

        shader.end();

    }

    public void createMeshes(int res) {
        Random r = new Random();
        //res (resolution) is the number of height-points
        //minimum is 2, which will result in a box (under each height-point there is another vertex)
        if (res < 2)
            res = 2;

        groundMesh = new Mesh(Mesh.VertexDataType.VertexArray, true, 2 * res, (2*res-1)*6,
                new VertexAttribute(VertexAttributes.Usage.Position, 2, "a_position"),
                new VertexAttribute(VertexAttributes.Usage.TextureCoordinates, 2, "a_texCoord0"));


        surfaceMesh = new Mesh(Mesh.VertexDataType.VertexArray, true, 2 * res, (2*res-1)*6,
                new VertexAttribute(VertexAttributes.Usage.Position, 2, "a_position"),
                new VertexAttribute(VertexAttributes.Usage.TextureCoordinates, 2, "a_texCoord0"));

        float x = 0f;     //current position to put vertices
        float med = 0.3f * FACTOR; //starting y
        float y = med;

        float slopeWidth = (float) (LENGTH / ((float) (res - 1)))/25; //horizontal distance between 2 heightpoints
        slopeWidth *= FACTOR;

        // VERTICES
        float[] tempVer = new float[2*2*2*res]; //hold vertices before setting them to the mesh
        float[] tempVerSurface = new float[2*2*2*res]; //hold vertices before setting them to the mesh
        int offset = 0; //offset to put it in tempVer
        int offset2 = 0;
        float[] tempVer2 = new float[2*res];
        int flahUp = 0;
        for (int i = 0; i<res; i++) {

            tempVer[offset+0] = x;
            tempVer[offset+1] = 0; // below height
            tempVer[offset+2] = (x/FACTOR) * 4;
            tempVer[offset+3] = 0; // below height

            tempVer[offset+4] = x;
            tempVer[offset+5] = y;  // height
            tempVer[offset+6] = (x/FACTOR) * 4;
            tempVer[offset+7] = (y/FACTOR) * 4;  // height

            Gdx.app.log("MeshTextureDemo", "x: " + String.valueOf(tempVer[offset + 6]));
            Gdx.app.log("MeshTextureDemo", "y: " + String.valueOf(tempVer[offset + 7]));

            tempVerSurface[offset + 0] = x;
            tempVerSurface[offset + 1] = y - .02f * FACTOR;
            tempVerSurface[offset + 2] = x / FACTOR;
            tempVerSurface[offset + 3] = 1f; // where i have changed

            tempVerSurface[offset+4] = x;
            tempVerSurface[offset+5] = y + 0.05f * FACTOR;
            tempVerSurface[offset+6] = x / FACTOR;
            tempVerSurface[offset+7] = 0;  // where i have changed.

            //tempVer2[offset2+0] = x;      tempVer2[offset2+1] = 0f; // below height
            tempVer2[offset2+0] = x;      tempVer2[offset2+1] = y; // below height
            //next position:
            x += slopeWidth;
            if(flahUp < 20) {
                y += ((r.nextFloat()*10)/200) * FACTOR;
            }else {
                y -= ((r.nextFloat()*6)/200) * FACTOR ;
            }
            flahUp++;
            if(flahUp > 35) flahUp = 0;

            offset +=8;
            offset2 +=2;
        }

//        groundShape.createChain(tempVer2);
        groundMesh.setVertices(tempVer);
        surfaceMesh.setVertices(tempVerSurface);

        // INDICES
        short[] tempIn = new short[(2*res-1)*6];
        offset = 0;
        for (int i = 0; i<2*res-2; i+=2) {

            tempIn[offset + 0] = (short) (i);       // below height
            tempIn[offset + 1] = (short) (i + 2);   // below next height
            tempIn[offset + 2] = (short) (i + 1);   // height

            tempIn[offset + 3] = (short) (i + 1);   // height
            tempIn[offset + 4] = (short) (i + 2);   // below next height
            tempIn[offset + 5] = (short) (i + 3);   // next height

            offset+=6;
        }

        groundMesh.setIndices(tempIn);
        surfaceMesh.setIndices(tempIn);

        return;
    }

    private void createShader () {
//        String vertexShader = "attribute vec4 a_position;    \n" + "attribute vec4 a_color;\n" + "attribute vec2 a_texCoord;\n"
//                + "varying vec4 v_Color;" + "varying vec2 v_texCoords; \n" +
//
//                "void main()                  \n" + "{                            \n" + "   v_Color = a_color;"
//                + "   v_texCoords = a_texCoord;\n" + "   gl_Position =   a_position;  \n" + "}                            \n";
//        String fragmentShader = "#ifdef GL_ES\n" + "precision mediump float;\n" + "#endif\n" + "varying vec4 v_Color;\n"
//                + "varying vec2 v_texCoords; \n" + "uniform sampler2D u_texture;\n" +
//
//                "void main()                                  \n" + "{                                            \n"
//                + "  gl_FragColor = v_Color * texture2D(u_texture, v_texCoords);\n" + "}";

//        meshShader = new ShaderProgram(vertexShader, fragmentShader);
//        if (meshShader.isCompiled() == false) throw new IllegalStateException(meshShader.getLog());
        meshShader = new ShaderProgram(
                Gdx.files.internal("shaders/vertexShader.glsl"),
                Gdx.files.internal("shaders/fragmentShader.glsl")
        );
    }

    @Override
    public void dispose () {
        mesh.dispose();
        texture.dispose();
//        frameBuffer.dispose();
//        stencilFrameBuffer.dispose();
//        stencilMesh.dispose();
        spriteBatch.dispose();
        meshShader.dispose();
    }

}