package jp.co.cyberagent.android.gpuimage;

import android.content.Context;

public class IRGPUImageHueBlendFilter extends IRGPUImageOpacityBlendFilter {

    private static final String BLEND_SHADER_CODE =
            "   mediump vec3 baseHSL = RGBToHSL(base);\n" +
                    "   color = HSLToRGB(vec3(RGBToHSL(blend).r, baseHSL.g, baseHSL.b));";

    public IRGPUImageHueBlendFilter(int textureResourceId, Context context) {
        super(BlendHelperFunctions.hslColorSpaceHelperFunctions(), BLEND_SHADER_CODE, textureResourceId, context);
    }

    public IRGPUImageHueBlendFilter(float opacity, int textureResourceId, Context context) {
        super(BlendHelperFunctions.hslColorSpaceHelperFunctions(), BLEND_SHADER_CODE, opacity, textureResourceId, context);
    }
}
