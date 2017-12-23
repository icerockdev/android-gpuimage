package jp.co.cyberagent.android.gpuimage;

import android.content.Context;

public class GPUImageAdaptiveThesholdFilter extends GPUImageTwoInputFilter {

    private static final String FRAGMENT_SHADER_STRING =
            " varying highp vec2 textureCoordinate;\n" +
                    " varying highp vec2 textureCoordinate2;\n" +
                    " \n" +
                    " uniform sampler2D inputImageTexture;\n" +
                    " uniform sampler2D inputImageTexture2; \n" +
                    " \n" +
                    " void main()\n" +
                    " {\n" +
                    "     highp float blurredInput = texture2D(inputImageTexture, textureCoordinate).r;\n" +
                    "     highp float localLuminance = texture2D(inputImageTexture2, textureCoordinate2).r;\n" +
                    "     highp float thresholdResult = step(blurredInput - 0.05, localLuminance);\n" +
                    "     \n" +
                    "     gl_FragColor = vec4(vec3(thresholdResult), 1.0);\n" +
                    " }";

    public GPUImageAdaptiveThesholdFilter() {
        super(FRAGMENT_SHADER_STRING);

    }
}
