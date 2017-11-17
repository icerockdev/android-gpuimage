package jp.co.cyberagent.android.gpuimage;

import android.opengl.GLES20;

public class IRGPUImageUnsharpMaskFilter extends GPUImageFilterGroup {

    private GPUImageGaussianBlurFilter mBlurFilter;
    private GPUImageTwoInputFilter mUnsharpMaskFilter;
    private int mIntensityLocation;
    private float mIntensity;
    private float mBlurRadius;


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

    public IRGPUImageUnsharpMaskFilter() {
        this(4.0f, 4.0f);
    }

    public IRGPUImageUnsharpMaskFilter(float intensity, float blurRadius) {
        mIntensity = intensity;
        mBlurRadius = blurRadius;

        mBlurFilter = new GPUImageGaussianBlurFilter(mBlurRadius);
        addFilter(mBlurFilter);

        mUnsharpMaskFilter = new GPUImageTwoInputFilter(UNSHARP_MASK_FRAGMENT_SHADER);
        mUnsharpMaskFilter.setFilterSource2(mBlurFilter);
        addFilter(mUnsharpMaskFilter);
    }

    @Override
    public void onInit() {
        super.onInit();
        mIntensityLocation = GLES20.glGetUniformLocation(mUnsharpMaskFilter.getProgram(), "intensity");
    }

    @Override
    public void onInitialized() {
        super.onInitialized();
        setIntensity(mIntensity);
        setBlurRadius(mBlurRadius);
    }

    public void setIntensity(final float intensity) {
        mIntensity = intensity;
        mUnsharpMaskFilter.setFloat(mIntensityLocation, mIntensity);
    }

    public void setBlurRadius(final float radius) {
        mBlurRadius = radius;
        mBlurFilter.setBlurSize(mBlurRadius);
    }
}
