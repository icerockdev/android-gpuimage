package jp.co.cyberagent.android.gpuimage;

import android.opengl.GLES20;

public class IRGPUImageUnsharpMaskFilter extends GPUImageFilterGroup {

    private GPUImageGaussianBlurFilter mBlurFilter;
    private GPUImageUnsharpMaskFilter mUnsharpMaskFilter;

    public IRGPUImageUnsharpMaskFilter() {
        this(4.0f, 4.0f);
    }

    public IRGPUImageUnsharpMaskFilter(float intensity, float blurRadius) {
        mBlurFilter = new GPUImageGaussianBlurFilter();
        addFilter(mBlurFilter);

        mUnsharpMaskFilter = new GPUImageUnsharpMaskFilter();
        addFilter(mUnsharpMaskFilter);

        mUnsharpMaskFilter.setFirstInputNumberOfItemsBack(mBlurFilter.getMergedFilters().size() + 1);
        mUnsharpMaskFilter.setSecondInputNumberOfItemsBack(GPUImageTwoInputFilter.PREVIOUS_INPUT);

        mBlurFilter.setBlurSize(blurRadius);
        mUnsharpMaskFilter.setIntensity(intensity);
    }

    public void setIntensity(final float intensity) {
        mUnsharpMaskFilter.setIntensity(intensity);
    }

    public void setBlurRadius(final float radius) {
        mBlurFilter.setBlurSize(radius);
    }
}
