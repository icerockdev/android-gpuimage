package jp.co.cyberagent.android.gpuimage;

import android.content.Context;

public class GPUImageSoftEleganceFilter extends GPUImageFilterGroup {

    public GPUImageSoftEleganceFilter(Context context) {
        GPUImageLookupFilter lookupFilter1 = new GPUImageLookupFilter(R.drawable.lookup_soft_elegance_1, context);
        addFilter(lookupFilter1);

        GPUImageGaussianBlurFilter blurFilter = new GPUImageGaussianBlurFilter(10.0f);
        addFilter(blurFilter);

        GPUImageAlphaBlendFilter alphaBlendFilter = new GPUImageAlphaBlendFilter(0.14f);
        alphaBlendFilter.setFirstInputNumberOfItemsBack(blurFilter.getMergedFilters().size() + 1);
        alphaBlendFilter.setSecondInputNumberOfItemsBack(GPUImageTwoInputFilter.PREVIOUS_INPUT);
        addFilter(alphaBlendFilter);

        GPUImageLookupFilter lookupFilter2 = new GPUImageLookupFilter(R.drawable.lookup_soft_elegance_2, context);

        addFilter(lookupFilter2);

    }
}
