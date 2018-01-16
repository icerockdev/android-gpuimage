package jp.co.cyberagent.android.gpuimage;

import android.content.Context;
import android.graphics.Bitmap;

public class IRGPUImagePhoenixBlendFilter extends IRGPUImageOpacityBlendFilter {

    private static final String BLEND_SHADER_CODE = "color = (min(base, blend) - max(base, blend) + vec3(1.0));";

    public IRGPUImagePhoenixBlendFilter(int textureResourceId, Context context) {
        super(BLEND_SHADER_CODE, textureResourceId, context);
    }

    public IRGPUImagePhoenixBlendFilter(float opacity, int textureResourceId, Context context) {
        super(BLEND_SHADER_CODE, opacity, textureResourceId, context);
    }

    public IRGPUImagePhoenixBlendFilter(Bitmap secondTexture) {
        super(BLEND_SHADER_CODE, secondTexture);
    }

    public IRGPUImagePhoenixBlendFilter() {
        super(BLEND_SHADER_CODE);
    }
}
