package jp.co.cyberagent.android.gpuimage;

import android.opengl.GLES20;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import static jp.co.cyberagent.android.gpuimage.GPUImageRenderer.CUBE;
import static jp.co.cyberagent.android.gpuimage.util.TextureRotationUtil.TEXTURE_NO_ROTATION;

public class GPUImageLinkedFilter extends GPUImageFilter {

    private GPUImageFilter mFirstFilter;
    private GPUImageTwoInputFilter mSecondFilter;

    private int[] mFrameBuffers;
    private int[] mFrameBufferTextures;

    private final FloatBuffer mGLCubeBuffer;
    private final FloatBuffer mGLTextureBuffer;

    public GPUImageLinkedFilter(GPUImageFilter firstFilter, GPUImageTwoInputFilter secondFilter) {
        mFirstFilter = firstFilter;
        mSecondFilter = secondFilter;

        mGLCubeBuffer = ByteBuffer.allocateDirect(CUBE.length * 4)
                .order(ByteOrder.nativeOrder())
                .asFloatBuffer();
        mGLCubeBuffer.put(CUBE).position(0);

        mGLTextureBuffer = ByteBuffer.allocateDirect(TEXTURE_NO_ROTATION.length * 4)
                .order(ByteOrder.nativeOrder())
                .asFloatBuffer();
        mGLTextureBuffer.put(TEXTURE_NO_ROTATION).position(0);
    }

    @Override
    public void onInit() {
        super.onInit();
        if (mFirstFilter != null)
            mFirstFilter.init();
        if (mSecondFilter != null)
            mSecondFilter.init();
    }

    @Override
    public void onDestroy() {
        destroyFramebuffers();
        if (mFirstFilter != null)
            mFirstFilter.destroy();
        if (mSecondFilter != null)
            mSecondFilter.destroy();
        super.onDestroy();
    }

    private void destroyFramebuffers() {
        if (mFrameBufferTextures != null) {
            GLES20.glDeleteTextures(mFrameBufferTextures.length, mFrameBufferTextures, 0);
            mFrameBufferTextures = null;
        }
        if (mFrameBuffers != null) {
            GLES20.glDeleteFramebuffers(mFrameBuffers.length, mFrameBuffers, 0);
            mFrameBuffers = null;
        }
    }

    @Override
    public void onOutputSizeChanged(final int width, final int height) {
        super.onOutputSizeChanged(width, height);
        if (mFrameBuffers != null) {
            destroyFramebuffers();
        }

        if (mFirstFilter != null)
            mFirstFilter.onOutputSizeChanged(width, height);
        if (mSecondFilter != null)
            mSecondFilter.onOutputSizeChanged(width, height);


        int size = 1;
        mFrameBuffers = new int[1];
        mFrameBufferTextures = new int[1];

        for (int i = 0; i < size; i++) {
            GLES20.glGenFramebuffers(1, mFrameBuffers, i);
            GLES20.glGenTextures(1, mFrameBufferTextures, i);
            GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, mFrameBufferTextures[i]);
            GLES20.glTexImage2D(GLES20.GL_TEXTURE_2D, 0, GLES20.GL_RGBA, width, height, 0,
                    GLES20.GL_RGBA, GLES20.GL_UNSIGNED_BYTE, null);
            GLES20.glTexParameterf(GLES20.GL_TEXTURE_2D,
                    GLES20.GL_TEXTURE_MAG_FILTER, GLES20.GL_LINEAR);
            GLES20.glTexParameterf(GLES20.GL_TEXTURE_2D,
                    GLES20.GL_TEXTURE_MIN_FILTER, GLES20.GL_LINEAR);
            GLES20.glTexParameterf(GLES20.GL_TEXTURE_2D,
                    GLES20.GL_TEXTURE_WRAP_S, GLES20.GL_CLAMP_TO_EDGE);
            GLES20.glTexParameterf(GLES20.GL_TEXTURE_2D,
                    GLES20.GL_TEXTURE_WRAP_T, GLES20.GL_CLAMP_TO_EDGE);

            GLES20.glBindFramebuffer(GLES20.GL_FRAMEBUFFER, mFrameBuffers[i]);
            GLES20.glFramebufferTexture2D(GLES20.GL_FRAMEBUFFER, GLES20.GL_COLOR_ATTACHMENT0,
                    GLES20.GL_TEXTURE_2D, mFrameBufferTextures[i], 0);

            GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, 0);
            GLES20.glBindFramebuffer(GLES20.GL_FRAMEBUFFER, 0);
        }
    }

    @Override
    public void onDraw(int textureId, FloatBuffer cubeBuffer, FloatBuffer textureBuffer) {

        if (mFirstFilter == null || mSecondFilter == null) return;

        runPendingOnDrawTasks();
        if (!isInitialized() || mFrameBuffers == null || mFrameBufferTextures == null) {
            return;
        }

        GLES20.glBindFramebuffer(GLES20.GL_FRAMEBUFFER, mFrameBuffers[0]);
        GLES20.glClearColor(0, 0, 0, 0);

        mFirstFilter.onDraw(textureId, mGLCubeBuffer, mGLTextureBuffer);

        GLES20.glBindFramebuffer(GLES20.GL_FRAMEBUFFER, 0);
        int firstFilterTextureId = mFrameBufferTextures[0];

        if (mSecondFilter.isInputTexturesReversed()) {
            mSecondFilter.setFilterSourceTexture2(textureId);
            mSecondFilter.onDraw(firstFilterTextureId, cubeBuffer, textureBuffer);
        } else {
            mSecondFilter.setFilterSourceTexture2(firstFilterTextureId);
            mSecondFilter.onDraw(textureId, cubeBuffer, textureBuffer);
        }
    }

    public GPUImageFilter getFirstFilter() {
        return mFirstFilter;
    }

    public GPUImageFilter getSecondFilter() {
        return mSecondFilter;
    }
}
