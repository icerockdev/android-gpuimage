package jp.co.cyberagent.android.gpuimage;

import android.content.Context;

public class IRGPUImageDarkenBlendFilter extends IRGPUImageOpacityBlendFilter {

    private static final String BLEND_SHADER_CODE = "color = min(base,blend);";

    public IRGPUImageDarkenBlendFilter(int textureResourceId, Context context) {
        super(BLEND_SHADER_CODE, textureResourceId, context);
    }

    public IRGPUImageDarkenBlendFilter(float opacity, int textureResourceId, Context context) {
        super(BLEND_SHADER_CODE, opacity, textureResourceId, context);
    }
}
