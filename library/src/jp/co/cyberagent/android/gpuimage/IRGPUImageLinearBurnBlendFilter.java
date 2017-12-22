package jp.co.cyberagent.android.gpuimage;

import android.content.Context;

public class IRGPUImageLinearBurnBlendFilter extends IRGPUImageOpacityBlendFilter {

    private static final String BLEND_SHADER_CODE = "color = max(base + blend - 1.0, 0.0);";

    public IRGPUImageLinearBurnBlendFilter(int textureResourceId, Context context) {
        super(BLEND_SHADER_CODE, textureResourceId, context);
    }

    public IRGPUImageLinearBurnBlendFilter(float opacity, int textureResourceId, Context context) {
        super(BLEND_SHADER_CODE, opacity, textureResourceId, context);
    }
}
