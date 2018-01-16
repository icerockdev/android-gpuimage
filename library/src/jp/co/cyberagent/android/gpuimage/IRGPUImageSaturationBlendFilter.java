package jp.co.cyberagent.android.gpuimage;

import android.content.Context;
import android.graphics.Bitmap;

public class IRGPUImageSaturationBlendFilter extends IRGPUImageOpacityBlendFilter {

    private static final String BLEND_SHADER_CODE =
            "   mediump vec3 baseHSL = RGBToHSL(base);\n" +
                    "   color = HSLToRGB(vec3(baseHSL.r, RGBToHSL(blend).g, baseHSL.b));";

    public IRGPUImageSaturationBlendFilter(int textureResourceId, Context context) {
        super(BlendHelperFunctions.hslColorSpaceHelperFunctions(), BLEND_SHADER_CODE, textureResourceId, context);
    }

    public IRGPUImageSaturationBlendFilter(float opacity, int textureResourceId, Context context) {
        super(BlendHelperFunctions.hslColorSpaceHelperFunctions(), BLEND_SHADER_CODE, opacity, textureResourceId, context);
    }

    public IRGPUImageSaturationBlendFilter(Bitmap secondTexture) {
        super(BlendHelperFunctions.hslColorSpaceHelperFunctions(), BLEND_SHADER_CODE, secondTexture);
    }

    public IRGPUImageSaturationBlendFilter() {
        super(BlendHelperFunctions.hslColorSpaceHelperFunctions(), BLEND_SHADER_CODE);
    }
}
