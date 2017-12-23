package jp.co.cyberagent.android.gpuimage;

public class IRGPUImageAdaptiveTresholdFilter extends GPUImageFilterGroup {

    private GPUImageBoxBlurFilter mBoxBlurFilter;

    public IRGPUImageAdaptiveTresholdFilter() {
        this(4.0f);
    }

    public IRGPUImageAdaptiveTresholdFilter(float blurRadius) {

        GPUImageGrayscaleFilter grayscaleFilter = new GPUImageGrayscaleFilter();
        addFilter(grayscaleFilter);

        mBoxBlurFilter = new GPUImageBoxBlurFilter(blurRadius);
        addFilter(mBoxBlurFilter);

        GPUImageAdaptiveThesholdFilter adaptiveThesholdFilter = new GPUImageAdaptiveThesholdFilter();
        addFilter(adaptiveThesholdFilter);

        adaptiveThesholdFilter.setSecondInputNumberOfItemsBack(mBoxBlurFilter.getMergedFilters().size() + 1);
    }

    public void setBlurRadius(final float radius) {
        mBoxBlurFilter.setBlurSize(radius);
    }
}
