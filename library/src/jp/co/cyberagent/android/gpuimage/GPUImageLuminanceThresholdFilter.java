package jp.co.cyberagent.android.gpuimage;

import android.opengl.GLES20;

public class GPUImageLuminanceThresholdFilter extends GPUImageFilter {

    private static final String FRAGMENT_SHADER =
            " varying highp vec2 textureCoordinate;\n" +
                    " \n" +
                    " uniform sampler2D inputImageTexture;\n" +
                    " uniform highp float threshold;\n" +
                    " \n" +
                    " const highp vec3 W = vec3(0.2125, 0.7154, 0.0721);\n" +
                    "\n" +
                    " void main()\n" +
                    " {\n" +
                    "     highp vec4 textureColor = texture2D(inputImageTexture, textureCoordinate);\n" +
                    "     highp float luminance = dot(textureColor.rgb, W);\n" +
                    "     highp float thresholdResult = step(threshold, luminance);\n" +
                    "     \n" +
                    "     gl_FragColor = vec4(vec3(thresholdResult), textureColor.w);\n" +
                    " }";


    private float mThreshold;
    private int mThresholdLocation;

    public GPUImageLuminanceThresholdFilter() {
            this(0.5f);
    }

    public GPUImageLuminanceThresholdFilter(float threshold) {
        super(NO_FILTER_VERTEX_SHADER, FRAGMENT_SHADER);
        mThreshold = threshold;
    }

    @Override
    public void onInit() {
        super.onInit();
        mThresholdLocation = GLES20.glGetUniformLocation(getProgram(), "threshold");
    }

    @Override
    public void onInitialized() {
        super.onInitialized();
        setFloat(mThresholdLocation, mThreshold);
    }

    public void setThreshold(final float threshold) {
        mThreshold = threshold;
        setFloat(mThresholdLocation, mThreshold);
    }
}
