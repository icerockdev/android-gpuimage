package jp.co.cyberagent.android.gpuimage;

import android.content.Context;
import android.graphics.Bitmap;

public class IRGPUImageColorBurnBlendFilter extends IRGPUImageOpacityBlendFilter {

    private static final String BLEND_SHADER_CODE =
            "   color = vec3(colorBurnF(base.r, blend.r),\n" +
                    "                colorBurnF(base.g, blend.g),\n" +
                    "                colorBurnF(base.b, blend.b));";

    public IRGPUImageColorBurnBlendFilter(int textureResourceId, Context context) {
        super(BlendHelperFunctions.colorBurnF(), BLEND_SHADER_CODE, textureResourceId, context);
    }

    public IRGPUImageColorBurnBlendFilter(float opacity, int textureResourceId, Context context) {
        super(BlendHelperFunctions.colorBurnF(), BLEND_SHADER_CODE, opacity, textureResourceId, context);
    }

    public IRGPUImageColorBurnBlendFilter(Bitmap secondTexture) {
        super(BlendHelperFunctions.colorBurnF(), BLEND_SHADER_CODE, secondTexture);
    }

    public IRGPUImageColorBurnBlendFilter() {
        super(BlendHelperFunctions.colorBurnF(), BLEND_SHADER_CODE);
    }
}
