package jp.co.cyberagent.android.gpuimage;

import android.content.Context;

public class IRGPUImageLuminosityBlendFilter extends IRGPUImageOpacityBlendFilter {

    private static final String BLEND_SHADER_CODE =
            "   mediump vec3 baseHSL = RGBToHSL(base);\n" +
                    "   color = HSLToRGB(vec3(baseHSL.r, baseHSL.g, RGBToHSL(blend).b));";

    public IRGPUImageLuminosityBlendFilter(int textureResourceId, Context context) {
        super(BlendHelperFunctions.hslColorSpaceHelperFunctions(), BLEND_SHADER_CODE, textureResourceId, context);
    }

    public IRGPUImageLuminosityBlendFilter(float opacity, int textureResourceId, Context context) {
        super(BlendHelperFunctions.hslColorSpaceHelperFunctions(), BLEND_SHADER_CODE, opacity, textureResourceId, context);
    }
}
