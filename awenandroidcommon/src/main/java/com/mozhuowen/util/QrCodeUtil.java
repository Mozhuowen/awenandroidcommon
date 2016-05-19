package com.mozhuowen.util;

import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.widget.ImageView;

import com.github.yoojia.zxing.qrcode.Encoder;

/**
 * Created by Awen on 16/4/8.
 * Email:mozhuowen@gmail.com
 */
public class QrCodeUtil {

    static Encoder mEncoder;
    static ImageView imageView;

    public static void Encode(ImageView iv,String content) {
        imageView = iv;

        final int dimension = 300;
        mEncoder = new Encoder.Builder()
                .setBackgroundColor(0xFFFFFF) // 指定背景颜色，默认为白色
                .setCodeColor(0xFF000000) // 指定编码块颜色，默认为黑色
                .setOutputBitmapWidth(dimension) // 生成图片宽度
                .setOutputBitmapHeight(dimension) // 生成图片高度
                .setOutputBitmapPadding(0) // 设置为没有白边
                .build();
        EncodeTask encodeTask = new EncodeTask();
        encodeTask.execute(content);
    }


    static class EncodeTask extends AsyncTask<String, Void, Bitmap> {

        @Override
        protected Bitmap doInBackground(String... params) {
            return mEncoder.encode(params[0]);
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            imageView.setImageBitmap(bitmap);
        }
    }
}
