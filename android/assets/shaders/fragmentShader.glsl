#ifdef GL_ES
#define LOWP lowp
precision mediump float;
#else
#define LOWP
#endif

varying vec2 v_texCoords;
uniform sampler2D u_texture;

void main() {
  gl_FragColor = texture2D(u_texture, v_texCoords);
}

/*
#ifdef GL_ES
#define LOWP lowp
precision mediump float;
#else
#define LOWP
#endif

varying vec2 v_texCoords;
uniform sampler2D u_texture;

void main() {
  gl_FragColor = texture2D(u_texture, v_texCoords);
}
*/
 /*
#ifdef GL_ES
    precision mediump float;
#endif

varying vec4 v_color;
varying vec2 v_texCoords;
uniform sampler2D u_texture;
uniform mat4 u_projTrans;

void main() {

        //vec3 color = texture2D(u_texture, v_texCoords).rgb;
        //float gray = (color.r + color.g + color.b) / 3.0;
        //vec3 grayscale = vec3(gray);

        //gl_FragColor = vec4(grayscale, 1.0);


        gl_FragColor = v_color * texture2D(u_texture, v_texCoords);
}
*/
/*
#ifdef GL_FRAGMENT_PRECISION_HIGH
precision highp float;
#else
precision mediump float;
#endif

varying vec4 v_color;

void main() {
    gl_FragColor = vec4(v_color);
}


#ifdef GL_ES
    precision mediump float;
#endif

varying vec4 v_Color;
varying vec2 v_texCoord0;
uniform sampler2D u_texture;

void main()
{
    gl_FragColor = v_Color * texture2D(u_texture, v_texCoords);
}
*/
