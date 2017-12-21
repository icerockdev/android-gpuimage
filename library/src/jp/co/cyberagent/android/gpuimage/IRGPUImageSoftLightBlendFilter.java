package jp.co.cyberagent.android.gpuimage;

import android.content.Context;

public class IRGPUImageSoftLightBlendFilter extends IRGPUImageOpacityBlendFilter {

    private static final String BLEND_SHADER_CODE =
            "   color = vec3(softLightF(base.r, blend.r),\n" +
                    "                softLightF(base.g, blend.g),\n" +
                    "                softLightF(base.b, blend.b));";

    public IRGPUImageSoftLightBlendFilter(int textureResourceId, Context context) {
        super(BlendHelperFunctions.softLightF(), BLEND_SHADER_CODE, textureResourceId, context);
    }

    public IRGPUImageSoftLightBlendFilter(float opacity, int textureResourceId, Context context) {
        super(BlendHelperFunctions.softLightF(), BLEND_SHADER_CODE, opacity, textureResourceId, context);
    }
}
