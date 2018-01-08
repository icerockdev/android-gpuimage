package jp.co.cyberagent.android.gpuimage;

import android.content.Context;
import android.graphics.Bitmap;
import android.opengl.GLES20;

public class IRGPUImageOpacityBlendFilter extends GPUImageTwoInputFilter {

    public static final String OPACITY_FRAGMENT_SHADER_TEMPLATE =
            " varying highp vec2 textureCoordinate;\n" +
                    " varying highp vec2 textureCoordinate2;\n" +
                    " \n" +
                    " uniform sampler2D inputImageTexture;\n" +
                    " uniform sampler2D inputImageTexture2;\n" +
                    " \n" +
                    " uniform lowp float opacity;\n" +
                    " \n" +
//                   Place for additional helper functions
                    " %s\n" +
                    " \n" +
                    " void main()\n" +
                    " {\n" +
                    "    lowp vec4 baseFull = texture2D(inputImageTexture, textureCoordinate);\n" +
                    "    lowp vec4 blendFull = texture2D(inputImageTexture2, textureCoordinate2);\n" +
                    "    lowp vec3 base = baseFull.rgb;\n" +
                    "    lowp vec3 blend = blendFull.rgb;\n" +
                    "    lowp vec3 color; // pixel color with opacity NOT taken into account\n" +
                    "\n" +
//                  Particular blend shader implementation should initialize `color` variable
                    "    %s\n" +
                    "\n" +
                    "    lowp float totalOpacity = opacity * blendFull.a;\n" +
                    "    lowp vec3 resultColor = (color * totalOpacity + base * (1.0 - totalOpacity));\n" +
                    "    lowp float resultAlpha = baseFull.a + (1.0 - baseFull.a) * blendFull.a;\n" +
                    "    gl_FragColor = vec4(resultColor, resultAlpha);\n" +
                    " }";

    private static final String DEFAULT_BLEND_SHADER_HELPER_FUNCTION = "";
    private static final String DEFAULT_BLEND_SHADER_CODE = "color = base;";
    private static final float DEFAULT_OPACITY = 0.5f;

    private float mOpacity;
    private int mOpacityLocation;


    public IRGPUImageOpacityBlendFilter() {
        this(DEFAULT_BLEND_SHADER_HELPER_FUNCTION, DEFAULT_BLEND_SHADER_CODE);
    }

    public IRGPUImageOpacityBlendFilter(String blendShaderCode, float opacity) {
        this(DEFAULT_BLEND_SHADER_HELPER_FUNCTION, blendShaderCode, opacity);
    }

    public IRGPUImageOpacityBlendFilter(String blendShaderCode) {
        this(DEFAULT_BLEND_SHADER_HELPER_FUNCTION, blendShaderCode);
    }

    public IRGPUImageOpacityBlendFilter(String blendShaderHelperFunctionsCode, String blendShaderCode, float opacity) {
        super(String.format(OPACITY_FRAGMENT_SHADER_TEMPLATE, blendShaderHelperFunctionsCode, blendShaderCode));
        mOpacity = opacity;
    }

    public IRGPUImageOpacityBlendFilter(String blendShaderHelperFunctionsCode, String blendShaderCode) {
        super(String.format(OPACITY_FRAGMENT_SHADER_TEMPLATE, blendShaderHelperFunctionsCode, blendShaderCode));
    }

    public IRGPUImageOpacityBlendFilter(String blendShaderCode,
                                        int textureResourceId, Context context) {
        this(DEFAULT_BLEND_SHADER_HELPER_FUNCTION, blendShaderCode, DEFAULT_OPACITY, textureResourceId, context);
    }

    public IRGPUImageOpacityBlendFilter(String blendShaderCode, float opacity, int textureResourceId,
                                        Context context) {
        this(DEFAULT_BLEND_SHADER_HELPER_FUNCTION, blendShaderCode, opacity, textureResourceId, context);
    }

    public IRGPUImageOpacityBlendFilter(String blendShaderHelperFunctionsCode, String blendShaderCode,
                                        int textureResourceId, Context context) {
        super(String.format(OPACITY_FRAGMENT_SHADER_TEMPLATE, blendShaderHelperFunctionsCode, blendShaderCode),
                textureResourceId, context);
    }

    public IRGPUImageOpacityBlendFilter(String blendShaderHelperFunctionsCode, String blendShaderCode, float opacity,
                                        int textureResourceId, Context context) {
        super(String.format(OPACITY_FRAGMENT_SHADER_TEMPLATE, blendShaderHelperFunctionsCode, blendShaderCode),
                textureResourceId, context);
        mOpacity = opacity;
    }

    public IRGPUImageOpacityBlendFilter(String blendShaderCode, Bitmap secondTexture) {
        this(DEFAULT_BLEND_SHADER_HELPER_FUNCTION, blendShaderCode, secondTexture);
    }

    public IRGPUImageOpacityBlendFilter(String blendShaderHelperFunctionsCode, String blendShaderCode, Bitmap secondTexture) {
        super(String.format(OPACITY_FRAGMENT_SHADER_TEMPLATE, blendShaderHelperFunctionsCode, blendShaderCode),
                secondTexture);
        mOpacity = DEFAULT_OPACITY;
    }

    @Override
    public void onInit() {
        super.onInit();
        mOpacityLocation = GLES20.glGetUniformLocation(getProgram(), "opacity");
    }

    @Override
    public void onInitialized() {
        super.onInitialized();
        setOpacity(mOpacity);
    }

    public void setOpacity(final float opacity) {
        mOpacity = opacity;
        setFloat(mOpacityLocation, mOpacity);
    }
}