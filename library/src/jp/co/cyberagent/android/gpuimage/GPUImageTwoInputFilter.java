/*
 * Copyright (C) 2012 CyberAgent
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package jp.co.cyberagent.android.gpuimage;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import jp.co.cyberagent.android.gpuimage.util.TextureRotationUtil;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.opengl.GLES20;

/**
 * Класс которому можно задать на вход 2 текстуры
 *
 * По умолчанию, на первый вход принимает предыдущую текстуру, на второй изображение из конструктора или метода
 * @see #setBitmap(Bitmap)
 * На первый вход задать bipmap нельзя.
 *
 * Если нужно указать на входы не предыдущую текстуру и не bitmap, то стоит использовать эти параметры:
 * mFirstInputNumberOfItemsBack количество которое нужно вычесть из позиции текущего фильтра для получения первой текстуры
 * mSecondInputNumberOfItemsBack количество которое нужно вычесть из позиции текущего фильтра для получения второй текстуры
 *
 * Так как GPUImage все группы фильтров разворачивает в линейный список, и хранит у себя массив полученных
 * на каждом шаге текстур, то используя индексы можно получить необходимые текстуры.
 *
 * Пример использования можно посмотреть в классе IRGPUImageUnsharpMaskFilter
 * @see IRGPUImageUnsharpMaskFilter
 *
 * Пример:
 * Имеются оригинальное изображение - O, и 2 фильтра - F1, F2, причем F2 наследуется от GPUImageTwoInputFilter.
 * Необходимо передать O на вход F1, полученный результат передать на второй вход F2.
 * А на первый вход F2 нужно передать O.
 *
 * Создаем в группу в которой выстраиваем цепочку:
 * первым складываем F1 в группу, за ним добавляем F2 в группу.
 * Если F1 групповой фильтр то для указания  mFirstInputNumberOfItemsBack или  mSecondInputNumberOfItemsBack
 * нужно взять количество фильтров которое содержит F1, F1.getMergedFilters.
 * Если F1 не групповой фильтр, то указываем 1.
 * Так же к количеству нужно добавить 1, если нужно взять не последнюю текстуру.
 * F2.setFirstInputNumberOfItemsBack(F1.getMergedFilters() + 1) таким образом возьмем текстуру которая подавалась на вход F1
 *
 * А для указания на вход последней текстуры можно использовать PREVIOUS_INPUT
 *
 * F2.setSecondInputNumberOfItemsBack(GPUImageTwoinputFilter.PREVIUOS_INPUT)
 *
 * Обработка получения текстур находится в GPUImageFilterGroup#onDraw
 * @see GPUImageFilterGroup#onDraw(int, FloatBuffer, FloatBuffer)
 */
public class GPUImageTwoInputFilter extends GPUImageFilter {
    private static final String VERTEX_SHADER = "attribute vec4 position;\n" +
            "attribute vec4 inputTextureCoordinate;\n" +
            "attribute vec4 inputTextureCoordinate2;\n" +
            " \n" +
            "varying vec2 textureCoordinate;\n" +
            "varying vec2 textureCoordinate2;\n" +
            " \n" +
            "void main()\n" +
            "{\n" +
            "    gl_Position = position;\n" +
            "    textureCoordinate = inputTextureCoordinate.xy;\n" +
            "    textureCoordinate2 = inputTextureCoordinate2.xy;\n" +
            "}";

    public int mFilterSecondTextureCoordinateAttribute;
    public int mFilterInputTextureUniform2;
    public int mFilterSourceTexture2 = OpenGlUtils.NO_TEXTURE;
    private ByteBuffer mTexture2CoordinatesBuffer;
    private Bitmap mBitmap;

    public static final int BITMAP_INPUT = -100;
    public static final int PREVIOUS_INPUT = 1;

    private int mFirstInputNumberOfItemsBack = 1;
    private int mSecondInputNumberOfItemsBack = BITMAP_INPUT;

    public GPUImageTwoInputFilter(String fragmentShader) {
        this(VERTEX_SHADER, fragmentShader);
    }

    public GPUImageTwoInputFilter(String fragmentShader, int textureResourceId, Context context) {
        this(VERTEX_SHADER, fragmentShader);
        Bitmap inputTexture = BitmapFactory.decodeResource(context.getResources(), textureResourceId);
        setBitmap(inputTexture);
    }

    public GPUImageTwoInputFilter(String vertexShader, String fragmentShader) {
        super(vertexShader, fragmentShader);
        setRotation(Rotation.NORMAL, false, false);
    }

    @Override
    public void onInit() {
        super.onInit();

        mFilterSecondTextureCoordinateAttribute = GLES20.glGetAttribLocation(getProgram(), "inputTextureCoordinate2");
        mFilterInputTextureUniform2 = GLES20.glGetUniformLocation(getProgram(), "inputImageTexture2"); // This does assume a name of "inputImageTexture2" for second input texture in the fragment shader
        GLES20.glEnableVertexAttribArray(mFilterSecondTextureCoordinateAttribute);

        if (mBitmap != null&&!mBitmap.isRecycled()) {
            setBitmap(mBitmap);
        }
    }
    
    public void setBitmap(final Bitmap bitmap) {
        if (bitmap != null && bitmap.isRecycled()) {
            return;
        }
        mBitmap = bitmap;
        if (mBitmap == null) {
            return;
        }
        runOnDraw(new Runnable() {
            public void run() {
                if (mFilterSourceTexture2 == OpenGlUtils.NO_TEXTURE) {
                    if (bitmap == null || bitmap.isRecycled()) {
                        return;
                    }
                    GLES20.glActiveTexture(GLES20.GL_TEXTURE3);
                    mFilterSourceTexture2 = OpenGlUtils.loadTexture(bitmap, OpenGlUtils.NO_TEXTURE, false);
                }
            }
        });
    }

    public void setFilterSourceTexture2(final int filterSourceTexture2) {
//        runOnDraw(new Runnable() {
//            public void run() {
//                if (mFilterSourceTexture2 == OpenGlUtils.NO_TEXTURE) {
//
//                    GLES20.glActiveTexture(GLES20.GL_TEXTURE3);
                    mFilterSourceTexture2 = filterSourceTexture2;
//                }
//            }
//        });
    }

    public Bitmap getBitmap() {
        return mBitmap;
    }

    public void recycleBitmap() {
        if (mBitmap != null && !mBitmap.isRecycled()) {
            mBitmap.recycle();
            mBitmap = null;
        }
    }

    public void onDestroy() {
        super.onDestroy();
        GLES20.glDeleteTextures(1, new int[]{
                mFilterSourceTexture2
        }, 0);
        mFilterSourceTexture2 = OpenGlUtils.NO_TEXTURE;
    }

    @Override
    protected void onDrawArraysPre() {
        GLES20.glEnableVertexAttribArray(mFilterSecondTextureCoordinateAttribute);
        GLES20.glActiveTexture(GLES20.GL_TEXTURE3);
        GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, mFilterSourceTexture2);
        GLES20.glUniform1i(mFilterInputTextureUniform2, 3);

        mTexture2CoordinatesBuffer.position(0);
        GLES20.glVertexAttribPointer(mFilterSecondTextureCoordinateAttribute, 2, GLES20.GL_FLOAT, false, 0, mTexture2CoordinatesBuffer);
    }

    public void setRotation(final Rotation rotation, final boolean flipHorizontal, final boolean flipVertical) {
        float[] buffer = TextureRotationUtil.getRotation(rotation, flipHorizontal, flipVertical);

        ByteBuffer bBuffer = ByteBuffer.allocateDirect(32).order(ByteOrder.nativeOrder());
        FloatBuffer fBuffer = bBuffer.asFloatBuffer();
        fBuffer.put(buffer);
        fBuffer.flip();

        mTexture2CoordinatesBuffer = bBuffer;
    }

    public int getFirstInputNumberOfItemsBack() {
        return mFirstInputNumberOfItemsBack;
    }

    public void setFirstInputNumberOfItemsBack(int firstInputNumberOfItemsBack) {
        mFirstInputNumberOfItemsBack = firstInputNumberOfItemsBack;
    }

    public int getSecondInputNumberOfItemsBack() {
        return mSecondInputNumberOfItemsBack;
    }

    public void setSecondInputNumberOfItemsBack(int secondInputNumberOfItemsBack) {
        mSecondInputNumberOfItemsBack = secondInputNumberOfItemsBack;
    }
}
