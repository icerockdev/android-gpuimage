package jp.co.cyberagent.android.gpuimage;

import android.content.Context;

public class IRGPUImageGlowBlendFilter extends IRGPUImageOpacityBlendFilter {

    private static final String BLEND_SHADER_CODE =
            "   color = vec3(reflectF(blend.r, base.r),\n" +
                    "                reflectF(blend.g, base.g),\n" +
                    "                reflectF(blend.b, base.b));";

    public IRGPUImageGlowBlendFilter(int textureResourceId, Context context) {
        super(BlendHelperFunctions.reflectF(), BLEND_SHADER_CODE, textureResourceId, context);
    }

    public IRGPUImageGlowBlendFilter(float opacity, int textureResourceId, Context context) {
        super(BlendHelperFunctions.reflectF(), BLEND_SHADER_CODE, opacity, textureResourceId, context);
    }
}
