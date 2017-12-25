package jp.co.cyberagent.android.gpuimage;

public class GPUImageAdaptiveThresholdFilter extends GPUImageFilterGroup {

    private GPUImageBoxBlurFilter mBoxBlurFilter;

    public GPUImageAdaptiveThresholdFilter() {
        this(4.0f);
    }

    public GPUImageAdaptiveThresholdFilter(float blurRadius) {

        GPUImageGrayscaleFilter grayscaleFilter = new GPUImageGrayscaleFilter();
        addFilter(grayscaleFilter);

        mBoxBlurFilter = new GPUImageBoxBlurFilter(blurRadius);
        addFilter(mBoxBlurFilter);

        GPUImageAdaptiveThresholdTwoInputFilter adaptiveThresholdFilter = new GPUImageAdaptiveThresholdTwoInputFilter();
        addFilter(adaptiveThresholdFilter);

        adaptiveThresholdFilter.setSecondInputNumberOfItemsBack(mBoxBlurFilter.getMergedFilters().size() + 1);
    }

    public void setBlurRadius(final float radius) {
        mBoxBlurFilter.setBlurSize(radius);
    }
}
