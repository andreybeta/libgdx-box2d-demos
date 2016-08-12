attribute vec4 a_position;
attribute vec2 a_texCoord0;
uniform mat4 u_projTrans;
varying vec2 v_texCoords;

void main() {

 v_texCoords = a_texCoord0;
 gl_Position =  u_projTrans * a_position;
}


/*
attribute vec4 a_position;
attribute vec2 a_texCoord0;
uniform mat4 u_projTrans;
varying vec2 v_texCoords;

void main() {
 v_texCoords = a_texCoord0;
 gl_Position =  u_projTrans * a_position;
}
*/
/*
attribute vec4 a_position;
attribute vec4 a_color;
attribute vec2 a_texCoord0;

uniform mat4 u_projTrans;
uniform mat4 u_projView;

varying vec4 v_color;
varying vec2 v_texCoords;

void main() {
    v_color = a_color;
    v_texCoords = a_texCoord0;
    //gl_Position = u_projTrans * a_position;
    gl_Position = u_projView * vec4(Position, 0.0, 1.0);
}

*/
/*


attribute vec3 a_position;
attribute vec4 a_color;

uniform mat4 u_worldTrans;
uniform mat4 u_projViewTrans;

varying vec4 v_color;

void main() {
 v_color = a_color;
 gl_Position = u_projViewTrans * u_worldTrans * vec4(a_position, 1.0);
 vec3 ndc = gl_Position.xyz / gl_Position.w ; // perspective divide.

 float zDist = 1.0 - ndc.z; // 1 is close to camera, 0 is far
 gl_PointSize = 500.0 * zDist; // between 0 and 50 now.
}
*/