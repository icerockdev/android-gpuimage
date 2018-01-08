package jp.co.cyberagent.android.gpuimage;

import android.content.Context;
import android.graphics.Bitmap;

public class IRGPUImageDivideBlendFilter extends IRGPUImageOpacityBlendFilter {

    private static final String BLEND_SHADER_CODE =
            "   color = vec3(divideF(base.r, blend.r),\n" +
                    "                divideF(base.g, blend.g),\n" +
                    "                divideF(base.b, blend.b));";

    public IRGPUImageDivideBlendFilter(int textureResourceId, Context context) {
        super(BlendHelperFunctions.divideF(), BLEND_SHADER_CODE, textureResourceId, context);
    }

    public IRGPUImageDivideBlendFilter(float opacity, int textureResourceId, Context context) {
        super(BlendHelperFunctions.divideF(), BLEND_SHADER_CODE, opacity, textureResourceId, context);
    }

    public IRGPUImageDivideBlendFilter(Bitmap secondTexture) {
        super(BlendHelperFunctions.divideF(), BLEND_SHADER_CODE, secondTexture);
    }

    public IRGPUImageDivideBlendFilter() {
        super(BlendHelperFunctions.divideF(), BLEND_SHADER_CODE);
    }
}
