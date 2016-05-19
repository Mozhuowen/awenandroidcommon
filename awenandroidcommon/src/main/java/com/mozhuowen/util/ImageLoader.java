package com.mozhuowen.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.widget.ImageView;

import com.nostra13.universalimageloader.cache.disc.impl.UnlimitedDiskCache;
import com.nostra13.universalimageloader.cache.disc.naming.HashCodeFileNameGenerator;
import com.nostra13.universalimageloader.cache.memory.impl.LruMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.core.decode.BaseImageDecoder;
import com.nostra13.universalimageloader.core.download.BaseImageDownloader;
import com.nostra13.universalimageloader.utils.StorageUtils;

import java.io.File;

public class ImageLoader
{
    private static ImageLoader instance;
    private static com.nostra13.universalimageloader.core.ImageLoader imageLoader;
    private static DisplayImageOptions options;

    private ImageLoader(Context context) {
        //other commponent image option
        DisplayImageOptions imageOptions = new DisplayImageOptions.Builder()
                .showImageOnLoading(android.R.color.darker_gray)
                .showImageOnFail(android.R.color.darker_gray)
                .cacheInMemory(true).cacheOnDisk(true)
                .resetViewBeforeLoading(true).considerExifParams(false)
                .bitmapConfig(Bitmap.Config.RGB_565) //RGB_565较小  ARGB_8888
                .imageScaleType(ImageScaleType.EXACTLY)
                .build();


        int maxMemory = (int) Runtime.getRuntime().maxMemory();
        int memCacheSize = maxMemory / 8;
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(context)
                .threadPoolSize(3)
                // default Thread.NORM_PRIORITY - 1
                .threadPriority(Thread.NORM_PRIORITY)
                // default FIFO
                .tasksProcessingOrder(QueueProcessingType.LIFO)
                // default
                .denyCacheImageMultipleSizesInMemory()
                .memoryCache(new LruMemoryCache(memCacheSize))
//                .memoryCache(new WeakMemoryCache())
                .memoryCacheSize(memCacheSize)
                .diskCacheSize(50 * 1024 * 1024)
                .diskCache(
                        new UnlimitedDiskCache(StorageUtils.getCacheDirectory(context, true)))
                .diskCacheFileNameGenerator(new HashCodeFileNameGenerator())
                        // default
                .imageDownloader(new BaseImageDownloader(context))
                        // default
                .imageDecoder(new BaseImageDecoder(false))
                        // default
                .defaultDisplayImageOptions(DisplayImageOptions.createSimple())
                        // default
                .defaultDisplayImageOptions(imageOptions)
                .build();

        imageLoader = com.nostra13.universalimageloader.core.ImageLoader.getInstance();
        imageLoader.init(config);

        //ComeOn image option
//        options = new DisplayImageOptions.Builder()
//                .cacheInMemory(true)
//                .cacheOnDisk(true)
//                .showImageOnLoading(R.color.light_gray)
//                .build();
    }

    public static ImageLoader getInstance(Context context) {
        if (instance == null ) {
            synchronized (ImageLoader.class) {
                instance = new ImageLoader(context);
            }
        }
        return instance;
    }

    public void loadImage(ImageView imageview,String imageuri) {
        imageLoader.displayImage(imageuri,imageview);
    }

    public File getCacheFile(String imageuri) {
        return imageLoader.getDiskCache().get(imageuri);
    }

    public com.nostra13.universalimageloader.core.ImageLoader getLoader() {
//        imageLoader.loadImage("", null, new ImageLoadingListener() {
//            @Override
//            public void onLoadingStarted(String s, View view) {
//
//            }
//
//            @Override
//            public void onLoadingFailed(String s, View view, FailReason failReason) {
//
//            }
//
//            @Override
//            public void onLoadingComplete(String s, View view, Bitmap bitmap) {
//
//            }
//
//            @Override
//            public void onLoadingCancelled(String s, View view) {
//
//            }
//        });

        return imageLoader;
    }

}