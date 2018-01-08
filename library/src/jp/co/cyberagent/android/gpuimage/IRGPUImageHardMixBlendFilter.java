package jp.co.cyberagent.android.gpuimage;

import android.content.Context;
import android.graphics.Bitmap;

public class IRGPUImageHardMixBlendFilter extends IRGPUImageOpacityBlendFilter {

    private static final String BLEND_SHADER_CODE =
            "   color = vec3(hardMixF(base.r, blend.r),\n" +
                    "                hardMixF(base.g, blend.g),\n" +
                    "                hardMixF(base.b, blend.b));";


    public IRGPUImageHardMixBlendFilter(int textureResourceId, Context context) {
        super(BlendHelperFunctions.hardMixF(), BLEND_SHADER_CODE, textureResourceId, context);
    }

    public IRGPUImageHardMixBlendFilter(float opacity, int textureResourceId, Context context) {
        super(BlendHelperFunctions.hardMixF(), BLEND_SHADER_CODE, opacity, textureResourceId, context);
    }

    public IRGPUImageHardMixBlendFilter(Bitmap secondTexture) {
        super(BlendHelperFunctions.hardMixF(), BLEND_SHADER_CODE, secondTexture);
    }

    public IRGPUImageHardMixBlendFilter() {
        super(BlendHelperFunctions.hardMixF(), BLEND_SHADER_CODE);
    }
}
