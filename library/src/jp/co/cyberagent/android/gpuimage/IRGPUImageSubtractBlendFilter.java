package jp.co.cyberagent.android.gpuimage;

import android.content.Context;

public class IRGPUImageSubtractBlendFilter extends IRGPUImageOpacityBlendFilter {

    private static final String BLEND_SHADER_CODE = "color = max(base - blend, 0.0);";

    public IRGPUImageSubtractBlendFilter(int textureResourceId, Context context) {
        super(BLEND_SHADER_CODE, textureResourceId, context);
    }

    public IRGPUImageSubtractBlendFilter(float opacity, int textureResourceId, Context context) {
        super(BLEND_SHADER_CODE, opacity, textureResourceId, context);
    }
}
