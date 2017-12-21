package jp.co.cyberagent.android.gpuimage;

import android.content.Context;

public class IRGPUImageExclusionBlendFilter extends IRGPUImageOpacityBlendFilter {

    private static final String BLEND_SHADER_CODE = "color = (base + blend - 2.0 * base * blend);";

    public IRGPUImageExclusionBlendFilter(int textureResourceId, Context context) {
        super(BLEND_SHADER_CODE, textureResourceId, context);
    }

    public IRGPUImageExclusionBlendFilter(float opacity, int textureResourceId, Context context) {
        super(BLEND_SHADER_CODE, opacity, textureResourceId, context);
    }
}
