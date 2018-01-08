package jp.co.cyberagent.android.gpuimage;

import android.content.Context;
import android.graphics.Bitmap;

public class IRGPUImagePinLightBlendFilter extends IRGPUImageOpacityBlendFilter {

    private static final String BLEND_SHADER_CODE =
            "   color = vec3(pinLightF(base.r, blend.r),\n" +
                    "                pinLightF(base.g, blend.g),\n" +
                    "                pinLightF(base.b, blend.b));";

    public IRGPUImagePinLightBlendFilter(int textureResourceId, Context context) {
        super(BlendHelperFunctions.pinLightF(), BLEND_SHADER_CODE, textureResourceId, context);
    }

    public IRGPUImagePinLightBlendFilter(float opacity, int textureResourceId, Context context) {
        super(BlendHelperFunctions.pinLightF(), BLEND_SHADER_CODE, opacity, textureResourceId, context);
    }

    public IRGPUImagePinLightBlendFilter(Bitmap secondTexture) {
        super(BlendHelperFunctions.pinLightF(), BLEND_SHADER_CODE, secondTexture);
    }

    public IRGPUImagePinLightBlendFilter() {
        super(BlendHelperFunctions.pinLightF(), BLEND_SHADER_CODE);
    }
}
