package jp.co.cyberagent.android.gpuimage;

import android.content.Context;

public class IRGPUImageScreenBlendFilter extends IRGPUImageOpacityBlendFilter {

    private static final String BLEND_SHADER_CODE = "color = (1.0 - ((1.0 - base) * (1.0 - blend)));";

    public IRGPUImageScreenBlendFilter(int textureResourceId, Context context) {
        super(BLEND_SHADER_CODE, textureResourceId, context);
    }

    public IRGPUImageScreenBlendFilter(float opacity, int textureResourceId, Context context) {
        super(BLEND_SHADER_CODE, opacity, textureResourceId, context);
    }
}
