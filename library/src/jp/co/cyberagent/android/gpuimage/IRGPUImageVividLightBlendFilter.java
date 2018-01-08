package jp.co.cyberagent.android.gpuimage;

import android.content.Context;
import android.graphics.Bitmap;

public class IRGPUImageVividLightBlendFilter extends IRGPUImageOpacityBlendFilter {

    private static final String BLEND_SHADER_CODE =
            "   color = vec3(vividLightF(base.r, blend.r),\n" +
                    "                vividLightF(base.g, blend.g),\n" +
                    "                vividLightF(base.b, blend.b));";

    public IRGPUImageVividLightBlendFilter(int textureResourceId, Context context) {
        super(BlendHelperFunctions.vividLightF(), BLEND_SHADER_CODE, textureResourceId, context);
    }

    public IRGPUImageVividLightBlendFilter(float opacity, int textureResourceId, Context context) {
        super(BlendHelperFunctions.vividLightF(), BLEND_SHADER_CODE, opacity, textureResourceId, context);
    }

    public IRGPUImageVividLightBlendFilter(Bitmap secondTexture) {
        super(BlendHelperFunctions.vividLightF(), BLEND_SHADER_CODE, secondTexture);
    }

    public IRGPUImageVividLightBlendFilter() {
        super(BlendHelperFunctions.vividLightF(), BLEND_SHADER_CODE);
    }
}
