package jp.co.cyberagent.android.gpuimage;

import android.content.Context;
import android.graphics.Bitmap;

public class IRGPUImageOverlayBlendFilter extends IRGPUImageOpacityBlendFilter {

    private static final String BLEND_SHADER_CODE =
            "   color = vec3(overlayF(base.r, blend.r),\n" +
                    "                overlayF(base.g, blend.g),\n" +
                    "                overlayF(base.b, blend.b));";

    public IRGPUImageOverlayBlendFilter(int textureResourceId, Context context) {
        super(BlendHelperFunctions.overlayF(), BLEND_SHADER_CODE, textureResourceId, context);
    }

    public IRGPUImageOverlayBlendFilter(float opacity, int textureResourceId, Context context) {
        super(BlendHelperFunctions.overlayF(), BLEND_SHADER_CODE, opacity, textureResourceId, context);
    }

    public IRGPUImageOverlayBlendFilter(Bitmap secondTexture) {
        super(BlendHelperFunctions.overlayF(), BLEND_SHADER_CODE, secondTexture);
    }

    public IRGPUImageOverlayBlendFilter() {
        super(BlendHelperFunctions.overlayF(), BLEND_SHADER_CODE);
    }
}
