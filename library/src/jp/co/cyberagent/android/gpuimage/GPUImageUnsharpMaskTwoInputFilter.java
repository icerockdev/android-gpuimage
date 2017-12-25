package jp.co.cyberagent.android.gpuimage;

import android.opengl.GLES20;

public class GPUImageUnsharpMaskTwoInputFilter extends GPUImageTwoInputFilter {
    private int mIntensityLocation;
    private float mIntensity;

    private static final String UNSHARP_MASK_FRAGMENT_SHADER =
            " varying highp vec2 textureCoordinate;\n" +
                    " varying highp vec2 textureCoordinate2;\n" +
                    " \n" +
                    " uniform sampler2D inputImageTexture;\n" +
                    " uniform sampler2D inputImageTexture2; \n" +
                    " \n" +
                    " uniform highp float intensity;\n" +
                    " \n" +
                    " void main()\n" +
                    " {\n" +
                    "     lowp vec4 sharpImageColor = texture2D(inputImageTexture, textureCoordinate);\n" +
                    "     lowp vec3 blurredImageColor = texture2D(inputImageTexture2, textureCoordinate2).rgb;\n" +
                    "     \n" +
                    "     gl_FragColor = vec4(sharpImageColor.rgb * intensity + blurredImageColor * (1.0 - intensity), sharpImageColor.a);\n" +
                    "//     gl_FragColor = mix(blurredImageColor, sharpImageColor, intensity);\n" +
                    "//     gl_FragColor = vec4(sharpImageColor.rgb - (blurredImageColor.rgb * intensity), 1.0);\n" +
                    " }";

    public GPUImageUnsharpMaskTwoInputFilter() {
        this(4.0f);
    }
    public GPUImageUnsharpMaskTwoInputFilter(float intensity) {
        super(UNSHARP_MASK_FRAGMENT_SHADER);
        mIntensity = intensity;
    }

    @Override
    public void onInit() {
        super.onInit();
        mIntensityLocation = GLES20.glGetUniformLocation(getProgram(), "intensity");
    }

    @Override
    public void onInitialized() {
        super.onInitialized();
        setIntensity(mIntensity);
    }

    public void setIntensity(final float intensity) {
        mIntensity = intensity;
        setFloat(mIntensityLocation, mIntensity);
    }
}
