package jp.co.cyberagent.android.gpuimage;

import android.content.Context;
import android.graphics.Bitmap;

public class IRGPUImageLightenBlendFilter extends IRGPUImageOpacityBlendFilter {

    private static final String BLEND_SHADER_CODE = "color = max(base,blend);";

    public IRGPUImageLightenBlendFilter(int textureResourceId, Context context) {
        super(BLEND_SHADER_CODE, textureResourceId, context);
    }

    public IRGPUImageLightenBlendFilter(float opacity, int textureResourceId, Context context) {
        super(BLEND_SHADER_CODE, opacity, textureResourceId, context);
    }

    public IRGPUImageLightenBlendFilter(Bitmap secondTexture) {
        super(BLEND_SHADER_CODE, secondTexture);
    }

    public IRGPUImageLightenBlendFilter() {
        super(BLEND_SHADER_CODE);
    }
}
