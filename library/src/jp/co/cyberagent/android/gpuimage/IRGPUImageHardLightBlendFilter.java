package jp.co.cyberagent.android.gpuimage;

import android.content.Context;
import android.graphics.Bitmap;

public class IRGPUImageHardLightBlendFilter extends IRGPUImageOpacityBlendFilter {

    private static final String BLEND_SHADER_CODE =
            "   color = vec3(overlayF(blend.r, base.r),\n" +
            "                overlayF(blend.g, base.g),\n" +
            "                overlayF(blend.b, base.b));";

    public IRGPUImageHardLightBlendFilter(int textureResourceId, Context context) {
        super(BlendHelperFunctions.overlayF(), BLEND_SHADER_CODE, textureResourceId, context);
    }

    public IRGPUImageHardLightBlendFilter(float opacity, int textureResourceId, Context context) {
        super(BlendHelperFunctions.overlayF(), BLEND_SHADER_CODE, opacity, textureResourceId, context);
    }

    public IRGPUImageHardLightBlendFilter(Bitmap secondTexture) {
        super(BlendHelperFunctions.overlayF(), BLEND_SHADER_CODE, secondTexture);
    }

    public IRGPUImageHardLightBlendFilter() {
        super(BlendHelperFunctions.overlayF(), BLEND_SHADER_CODE);
    }
}
