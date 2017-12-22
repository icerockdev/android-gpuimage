package jp.co.cyberagent.android.gpuimage;

import android.content.Context;

public class IRGPUImageColorBlendFilter extends IRGPUImageOpacityBlendFilter {

    private static final String BLEND_SHADER_CODE =
            "mediump vec3 blendHSL = RGBToHSL(blend);\n" +
            "   color = HSLToRGB(vec3(blendHSL.r, blendHSL.g, RGBToHSL(base).b));";

    public IRGPUImageColorBlendFilter(int textureResourceId, Context context) {
        super(BlendHelperFunctions.hslColorSpaceHelperFunctions(), BLEND_SHADER_CODE, textureResourceId, context);
    }

    public IRGPUImageColorBlendFilter(float opacity, int textureResourceId, Context context) {
        super(BlendHelperFunctions.hslColorSpaceHelperFunctions(), BLEND_SHADER_CODE, opacity, textureResourceId, context);
    }
}
