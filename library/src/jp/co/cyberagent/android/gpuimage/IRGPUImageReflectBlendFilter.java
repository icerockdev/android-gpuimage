package jp.co.cyberagent.android.gpuimage;

import android.content.Context;

public class IRGPUImageReflectBlendFilter extends IRGPUImageOpacityBlendFilter {

    private static final String BLEND_SHADER_CODE =
            "   color = vec3(reflectF(base.r, blend.r),\n" +
                    "                reflectF(base.g, blend.g),\n" +
                    "                reflectF(base.b, blend.b));";

    public IRGPUImageReflectBlendFilter(int textureResourceId, Context context) {
        super(BlendHelperFunctions.reflectF(), BLEND_SHADER_CODE, textureResourceId, context);
    }

    public IRGPUImageReflectBlendFilter(float opacity, int textureResourceId, Context context) {
        super(BlendHelperFunctions.reflectF(), BLEND_SHADER_CODE, opacity, textureResourceId, context);
    }
}
