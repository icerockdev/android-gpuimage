package jp.co.cyberagent.android.gpuimage;

public class IRGPUImageAdaptiveThresholdFilter extends GPUImageFilterGroup {

    private GPUImageBoxBlurFilter mBoxBlurFilter;

    public IRGPUImageAdaptiveThresholdFilter() {
        this(4.0f);
    }

    public IRGPUImageAdaptiveThresholdFilter(float blurRadius) {

        GPUImageGrayscaleFilter grayscaleFilter = new GPUImageGrayscaleFilter();
        addFilter(grayscaleFilter);

        mBoxBlurFilter = new GPUImageBoxBlurFilter(blurRadius);
        addFilter(mBoxBlurFilter);

        GPUImageAdaptiveThresholdFilter adaptiveThresholdFilter = new GPUImageAdaptiveThresholdFilter();
        addFilter(adaptiveThresholdFilter);

        adaptiveThresholdFilter.setSecondInputNumberOfItemsBack(mBoxBlurFilter.getMergedFilters().size() + 1);
    }

    public void setBlurRadius(final float radius) {
        mBoxBlurFilter.setBlurSize(radius);
    }
}
