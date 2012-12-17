package cn.edu.tsinghua.se2012.connect6;

import android.graphics.PointF;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;

public class ChessBoardOnTouchListener implements OnTouchListener {
	private PointF startPoint = new PointF();

	public boolean onTouch(View v, MotionEvent event) {
		switch (event.getAction() & MotionEvent.ACTION_MASK) {
		case MotionEvent.ACTION_DOWN:
			startPoint.set(event.getX(), event.getY());// ��ʼ��
			break;

		case MotionEvent.ACTION_MOVE:// �ƶ��¼�
			// ͼƬ�϶��¼�
			float dx = event.getX() - startPoint.x;// x���ƶ�����
			float dy = event.getY() - startPoint.y;
			break;
		case MotionEvent.ACTION_UP:
			break;
		// ����ָ�뿪��Ļ������Ļ���д���(��ָ)
		case MotionEvent.ACTION_POINTER_UP:
			break;
		// ����Ļ���Ѿ��д��㣨��ָ��,����һ����ָѹ����Ļ
		case MotionEvent.ACTION_POINTER_DOWN:
			break;
		}
		return true;
	}

}
