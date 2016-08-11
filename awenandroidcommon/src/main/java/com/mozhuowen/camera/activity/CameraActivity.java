package com.mozhuowen.camera.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Point;
import android.hardware.Camera;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.SurfaceHolder;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;

import com.mozhuowen.R;
import com.mozhuowen.camera.camera.CameraInterface;
import com.mozhuowen.camera.camera.preview.CameraSurfaceView;
import com.mozhuowen.camera.util.DisplayUtil;
import com.mozhuowen.camera.util.FileUtil;
import com.orhanobut.logger.Logger;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;


public class CameraActivity extends Activity implements CameraInterface.CamOpenOverCallback,Camera.PictureCallback {
	private static final String TAG = "yanzi";
	CameraSurfaceView surfaceView = null;
	ImageButton shutterBtn;
	float previewRate = -1f;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Thread openThread = new Thread(){
			@Override
			public void run() {
				// TODO Auto-generated method stub
				CameraInterface.getInstance().doOpenCamera(CameraActivity.this,CameraActivity.this);
			}
		};
		openThread.start();
		setContentView(R.layout.activity_camera);
		initUI();
		initViewParams();
		
		shutterBtn.setOnClickListener(new BtnListeners());
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.camera, menu);
		return true;
	}

	private void initUI(){
		View view = this.findViewById(android.R.id.content);
		view.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
			Window window = getWindow();
			window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
			window.setStatusBarColor(Color.TRANSPARENT);
		} else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT)
			// 透明状态栏
			getWindow().addFlags(
					WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

		surfaceView = (CameraSurfaceView)findViewById(R.id.camera_surfaceview);
		shutterBtn = (ImageButton)findViewById(R.id.btn_shutter);
	}
	private void initViewParams(){
		LayoutParams params = surfaceView.getLayoutParams();
		Point p = DisplayUtil.getScreenMetrics(this);
		params.width = p.x;
		params.height = p.y;
		previewRate = DisplayUtil.getScreenRate(this); //Ĭ��ȫ���ı���Ԥ��
		surfaceView.setLayoutParams(params);

		//�ֶ���������ImageButton�Ĵ�СΪ120dip��120dip,ԭͼƬ��С��64��64
		LayoutParams p2 = shutterBtn.getLayoutParams();
		p2.width = DisplayUtil.dip2px(this, 80);
		p2.height = DisplayUtil.dip2px(this, 80);
		shutterBtn.setLayoutParams(p2);	

	}

	@Override
	public void cameraHasOpened() {
		// TODO Auto-generated method stub
		SurfaceHolder holder = surfaceView.getSurfaceHolder();
		CameraInterface.getInstance().doStartPreview(holder, previewRate);
	}

	@Override
	public void onPictureTaken(final byte[] data, final Camera camera) {
		Observable.create(new Observable.OnSubscribe<String>() {
			@Override
			public void call(Subscriber<? super String> subscriber) {
				String filename = savePicture(data, camera);

				subscriber.onNext(filename);
				subscriber.onCompleted();
			}
		})
				.subscribeOn(Schedulers.io())
				.observeOn(AndroidSchedulers.mainThread())
				.subscribe(new Action1<String>() {
					@Override
					public void call(String s) {
						Intent data = new Intent();
						data.putExtra("filepath", s);
						setResult(RESULT_OK, data);
						finish();
					}
				}, new Action1<Throwable>() {
					@Override
					public void call(Throwable throwable) {
						throwable.printStackTrace();
						Logger.e("save pic fail!");
					}
				});
	}

	private class BtnListeners implements OnClickListener{

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			int i = v.getId();
			if (i == R.id.btn_shutter) {
				CameraInterface.getInstance().doTakePicture();
			} else {
			}
		}

	}

	private String  savePicture(byte[] data, Camera camera) {
		Bitmap b = null;
		if(null != data){
			b = BitmapFactory.decodeByteArray(data, 0, data.length);//data���ֽ����ݣ����������λͼ
			CameraInterface.getInstance().doStopCamera();
		}
		if(null != b)
		{
//			Bitmap rotaBitmap = ImageUtil.getRotateBitmap(b, 90.0f);
			return FileUtil.saveBitmap(b);
		}
		return null;
	}

}
