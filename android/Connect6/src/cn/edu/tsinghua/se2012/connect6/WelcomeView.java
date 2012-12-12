package cn.edu.tsinghua.se2012.connect6;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

//��ʼ��ӭ����
public class WelcomeView extends SurfaceView implements SurfaceHolder.Callback {// ʵ���������ڻص��ӿ�
	StartActivity activity;
	Paint paint;
	int currentAlpha = 0; // ��ǰ��͸����
	int screenWidth = (int) StartActivity.screenWidth;
	int screenHeight = (int) StartActivity.screenHeight;
	int sleepSpan = 50;
	int pic;
	Bitmap logo; // ��ǰlogoͼƬ����

	public WelcomeView(StartActivity activity) {
		super(activity);
		this.activity = activity;
		this.getHolder().addCallback(this);// �����������ڻص��ӿڵ�ʵ����
		paint = new Paint(); // ��������
		paint.setAntiAlias(true); // �򿪿����
		pic = R.drawable.welcome;
		Bitmap temp = BitmapFactory.decodeResource(activity.getResources(), pic);
		logo = Bitmap.createScaledBitmap(temp, screenWidth, screenHeight, true);
	}

	@Override
	public void onDraw(Canvas canvas) {
		try {
			// ���ƺ�ɫ�������屳��
			paint.setColor(Color.BLACK);
			paint.setAlpha(255);
			canvas.drawRect(0, 0, screenWidth, screenHeight, paint);

			// ����ƽ����ͼ
			if (logo == null)
				return;
			paint.setAlpha(currentAlpha);
			canvas.drawBitmap(logo, 0, 0, paint);
			if (logo.isRecycled()){
				logo.recycle();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void surfaceChanged(SurfaceHolder arg0, int arg1, int arg2, int arg3) {
	}

	public void surfaceCreated(SurfaceHolder arg0) { // ����ʱ������
		new Thread() {
			public void run() {				
				for (int i = 255; i > -10; i = i - 10) // ��̬����ͼƬ��͸����ֵ�������ػ�
				{
					currentAlpha = i;
					if (currentAlpha < 0) {
						currentAlpha = 0;
					}
					SurfaceHolder myholder = WelcomeView.this.getHolder();
					Canvas canvas = myholder.lockCanvas();// ��ȡ����
					try {
						synchronized (myholder) {
							onDraw(canvas); // ����
						}
					} catch (Exception e) {
						e.printStackTrace();
					} finally {
						if (canvas != null) {
							myholder.unlockCanvasAndPost(canvas);
						}
					}

					try {
						if (i == 255) {
							Thread.sleep(1000);
						}
						Thread.sleep(sleepSpan);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
				activity.hd.sendEmptyMessage(0);
			}
		}.start();
	}

	public void surfaceDestroyed(SurfaceHolder arg0) { // ����ʱ������
	}
}
