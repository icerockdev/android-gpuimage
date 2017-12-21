package jp.co.cyberagent.android.gpuimage;

import android.content.Context;

public class IRGPUImageColorDodgeBlendFilter extends IRGPUImageOpacityBlendFilter {

    private static final String BLEND_SHADER_CODE =
            "   color = vec3(colorDodgeF(base.r, blend.r),\n" +
                    "                colorDodgeF(base.g, blend.g),\n" +
                    "                colorDodgeF(base.b, blend.b));";

    public IRGPUImageColorDodgeBlendFilter(int textureResourceId, Context context) {
        super(BlendHelperFunctions.colorDodgeF(), BLEND_SHADER_CODE, textureResourceId, context);
    }

    public IRGPUImageColorDodgeBlendFilter(float opacity, int textureResourceId, Context context) {
        super(BlendHelperFunctions.colorDodgeF(), BLEND_SHADER_CODE, opacity, textureResourceId, context);
    }
}
