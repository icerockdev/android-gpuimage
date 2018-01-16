package jp.co.cyberagent.android.gpuimage;

import android.content.Context;
import android.graphics.Bitmap;

public class IRGPUImageLinearLightBlendFilter extends IRGPUImageOpacityBlendFilter {

    private static final String BLEND_SHADER_CODE =
            "   color = vec3(linearLightF(base.r, blend.r),\n" +
                    "                linearLightF(base.g, blend.g),\n" +
                    "                linearLightF(base.b, blend.b));";

    public IRGPUImageLinearLightBlendFilter(int textureResourceId, Context context) {
        super(BlendHelperFunctions.linearLightF(), BLEND_SHADER_CODE, textureResourceId, context);
    }

    public IRGPUImageLinearLightBlendFilter(float opacity, int textureResourceId, Context context) {
        super(BlendHelperFunctions.linearLightF(), BLEND_SHADER_CODE, opacity, textureResourceId, context);
    }

    public IRGPUImageLinearLightBlendFilter(Bitmap secondTexture) {
        super(BlendHelperFunctions.linearLightF(), BLEND_SHADER_CODE, secondTexture);
    }

    public IRGPUImageLinearLightBlendFilter() {
        super(BlendHelperFunctions.linearLightF(), BLEND_SHADER_CODE);
    }
}
