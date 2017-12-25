package jp.co.cyberagent.android.gpuimage;

public class GPUImageUnsharpMaskFilter extends GPUImageFilterGroup {

    private GPUImageGaussianBlurFilter mBlurFilter;
    private GPUImageUnsharpMaskTwoInputFilter mUnsharpMaskFilter;

    public GPUImageUnsharpMaskFilter() {
        this(4.0f, 4.0f);
    }

    public GPUImageUnsharpMaskFilter(float intensity, float blurRadius) {
        mBlurFilter = new GPUImageGaussianBlurFilter();
        addFilter(mBlurFilter);

        mUnsharpMaskFilter = new GPUImageUnsharpMaskTwoInputFilter();
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
