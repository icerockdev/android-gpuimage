package jp.co.cyberagent.android.gpuimage;

import android.content.Context;

public class IRGPUImageNormalBlendFilter extends IRGPUImageOpacityBlendFilter {

    private static final String BLEND_SHADER_CODE = "color = blend;";

    public IRGPUImageNormalBlendFilter(int textureResourceId, Context context) {
        super(BLEND_SHADER_CODE, textureResourceId, context);
    }

    public IRGPUImageNormalBlendFilter(float opacity, int textureResourceId, Context context) {
        super(BLEND_SHADER_CODE, opacity, textureResourceId, context);
    }
}
