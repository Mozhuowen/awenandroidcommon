package com.mozhuowen.camera.util;

import android.graphics.Bitmap;
import android.os.Environment;

import com.orhanobut.logger.Logger;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class FileUtil {
	private static final  String TAG = "FileUtil";
	private static final File parentPath = Environment.getExternalStorageDirectory();
	private static   String storagePath = "";
	private static final String DST_FOLDER_NAME = "PlayCamera";


	private static String initPath(){
		if(storagePath.equals("")){
			storagePath = parentPath.getAbsolutePath()+"/" + DST_FOLDER_NAME;
			File f = new File(storagePath);
			if(!f.exists()){
				f.mkdir();
			}
		}
		return storagePath;
	}

	public static String saveBitmap(Bitmap b){

		String path = initPath();
		long dataTake = System.currentTimeMillis();
		String jpegName = path + "/" + dataTake +".jpg";
		Logger.d(TAG, "saveBitmap:jpegName = " + jpegName);
		try {
			long filesize = 0;
			FileOutputStream fout = new FileOutputStream(jpegName);
			BufferedOutputStream bos = new BufferedOutputStream(fout);
			b.compress(Bitmap.CompressFormat.JPEG, 30, bos);
			bos.flush();
			bos.close();
			filesize = new File(jpegName).length();
			Logger.d("saveBitmap filesize->"+filesize);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return jpegName;
	}


}
