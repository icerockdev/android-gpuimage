package jp.co.cyberagent.android.gpuimage;

import android.content.Context;
import android.graphics.Bitmap;

public class IRGPUImageDifferenceBlendFilter extends IRGPUImageOpacityBlendFilter {

    private static final String BLEND_SHADER_CODE = "color = abs(base-blend);";

    public IRGPUImageDifferenceBlendFilter(int textureResourceId, Context context) {
        super(BLEND_SHADER_CODE, textureResourceId, context);
    }

    public IRGPUImageDifferenceBlendFilter(float opacity, int textureResourceId, Context context) {
        super(BLEND_SHADER_CODE, opacity, textureResourceId, context);
    }

    public IRGPUImageDifferenceBlendFilter(Bitmap secondTexture) {
        super(BLEND_SHADER_CODE, secondTexture);
    }

    public IRGPUImageDifferenceBlendFilter() {
        super(BLEND_SHADER_CODE);
    }
}
