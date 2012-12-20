package cn.edu.tsinghua.se2012.connect6;

import java.util.Vector;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import cn.edu.tsinghua.se2012.connect6.ChessBoardView;

@SuppressWarnings({"rawtypes", "unchecked"})
public class GameActivity extends Activity {	
	private static Vector data = new Vector();

	private ChessBoardView chessboard;
	static public Button newGameBtn;
	static public Button undoGameBtn;
	private Button gameSettingBtn;
	static public Button saveGameBtn;
	private Button zoomOutBtn;
	private Button zoomInBtn;
	private SoundPool soundpool;

	// ����Ϊ���е���Ϸ״̬����������
	static public boolean undoEnable = true;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.game);

		chessboard = (ChessBoardView) findViewById(R.id.chessborad);
		newGameBtn = (Button) findViewById(R.id.newgame);
		undoGameBtn = (Button) findViewById(R.id.undogame);
		gameSettingBtn = (Button) findViewById(R.id.gamesetting);
		saveGameBtn = (Button) findViewById(R.id.save);
		zoomOutBtn = (Button) findViewById(R.id.zoomout);
		zoomInBtn = (Button) findViewById(R.id.zoomin);
		soundpool = new SoundPool(1, AudioManager.STREAM_SYSTEM, 0);
		zoomOutBtn.setEnabled(false);

		// ��ȡ��Ļ�ֱ���
		DisplayMetrics dm = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(dm);
		int screenWidth = dm.widthPixels;
		int screenHeight = dm.heightPixels;
		int barHeight = (int) (40.0f * dm.density + 0.5f);

		chessboard.SetArea(0, screenWidth, 0, screenHeight - barHeight * 2);

		// ����������
		chessboard.ZoomOut();
		chessboard.invalidate(); // ���»�������

		// Vector data = new Vector();
		chessboard.init(data, StartActivity.isPVE);
		if (StartActivity.isPVE && (!StartActivity.isFirst)) {
			chessboard.Last();
		} else {
			chessboard.First();
		}
		CheckUndo();

		// ����
		undoGameBtn.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				playSound();
				chessboard.Back();
				chessboard.invalidate();
				CheckUndo();
			}
		});

		// ��Ϸ����
		gameSettingBtn.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				playSound();
				Intent intent = new Intent(GameActivity.this,
						GameSettingActivity.class);
				startActivity(intent);
			}
		});

		// ������С
		zoomOutBtn.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				playSound();
				chessboard.ZoomOut();
				chessboard.invalidate();

				if (0 == chessboard.getCurrentSizeLevel())
					zoomOutBtn.setEnabled(false);
				if (3 == chessboard.getCurrentSizeLevel())
					zoomInBtn.setEnabled(true);
			}
		});

		// ���̷Ŵ�
		zoomInBtn.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				playSound();
				chessboard.ZoomIn();
				chessboard.invalidate();

				if (4 == chessboard.getCurrentSizeLevel())
					zoomInBtn.setEnabled(false);
				if (1 == chessboard.getCurrentSizeLevel())
					zoomOutBtn.setEnabled(true);
			}

		});
	}

	// ��ʼ����Ϸ
	public void NewgameClick(View v) {
		playSound();
		Dialog alertDialog = new AlertDialog.Builder(this).setTitle("�Ƿ񱣴����ף�")
				.setMessage("�Ƿ񱣴����ף����������������ǰ���е���Ϸ����ʧ���������������֮ǰ��������׽������ǣ�")
				.setPositiveButton("����", new DialogInterface.OnClickListener() {

					public void onClick(DialogInterface dialog, int which) {
						SaveChess();
						if (StartActivity.isPVE && (!StartActivity.isFirst)) {
							chessboard.Last();
						} else {
							chessboard.First();
						}
						chessboard.invalidate();
						CheckUndo();
					}
				})
				.setNeutralButton("������", new DialogInterface.OnClickListener() {

					public void onClick(DialogInterface dialog, int which) {
						if (StartActivity.isPVE && (!StartActivity.isFirst)) {
							chessboard.Last();
						} else {
							chessboard.First();
						}
						chessboard.invalidate();
						CheckUndo();
					}
				})
				.setNegativeButton("ȡ��", new DialogInterface.OnClickListener() {

					public void onClick(DialogInterface dialog, int which) {

					}
				}).setCancelable(false).create();
		alertDialog.show();
	}

	// ��������
	public void SaveClick(View v) {
		playSound();
		Dialog alertDialog = new AlertDialog.Builder(this).setTitle("ȷ���������ף�")
				.setMessage("ȷ�����������𣿣�֮ǰ��������׽������ǣ�")
				.setPositiveButton("ȷ��", new DialogInterface.OnClickListener() {

					public void onClick(DialogInterface dialog, int which) {
						SaveChess();						
					}
				})
				.setNegativeButton("ȡ��", new DialogInterface.OnClickListener() {

					public void onClick(DialogInterface dialog, int which) {

					}
				}).setCancelable(false).create();
		alertDialog.show();
	}

	// ��������
	
	public void LoadClick(View v) {
		playSound();
		if (StartActivity.hasChessManual == false){
			Toast.makeText(GameActivity.this, "��ǰû������", Toast.LENGTH_SHORT).show();
		}else{
			if (!undoEnable) {
				SharedPreferences preferences = getSharedPreferences("Data",
						MODE_PRIVATE);
				int Size = preferences.getInt("Size", 0);
				data.clear();
				for (int i = 0; i < Size; i++) {
					mypoint p = new mypoint(preferences.getInt("x" + i, 0),
							preferences.getInt("y" + i, 0), preferences.getInt(
									"color" + i, 0));
					data.add(p);
				}
				chessboard.init(data, StartActivity.isPVE);
				chessboard.Open();
				chessboard.invalidate();
				CheckUndo();
				return;
			}
			Dialog alertDialog = new AlertDialog.Builder(this)
					.setTitle("ȷ���������ף�")
					.setMessage("ȷ�����������𣿣���ǰ���е���Ϸ����ʧ��")
					.setPositiveButton("ȷ��", new DialogInterface.OnClickListener() {

						public void onClick(DialogInterface dialog, int which) {
							SharedPreferences preferences = getSharedPreferences(
									"Data", MODE_PRIVATE);
							int Size = preferences.getInt("Size", 0);
							data.clear();
							for (int i = 0; i < Size; i++) {
								mypoint p = new mypoint(preferences.getInt("x" + i,
										0), preferences.getInt("y" + i, 0),
										preferences.getInt("color" + i, 0));
								data.add(p);
							}
							chessboard.init(data, StartActivity.isPVE);
							chessboard.Open();
							chessboard.invalidate();
							CheckUndo();
						}
					})
					.setNegativeButton("ȡ��", new DialogInterface.OnClickListener() {

						public void onClick(DialogInterface dialog, int which) {

						}
					}).setCancelable(false).create();
			alertDialog.show();
		}		
	}

	// �������˵�
	public void ReturnClick(View v) {
		playSound();
		if (!undoEnable) {
			data.clear();
			finish();
			return;
		}
		Dialog alertDialog = new AlertDialog.Builder(this).setTitle("�Ƿ񱣴����ף�")
				.setMessage("�Ƿ񱣴����ף����������������ǰ���е���Ϸ����ʧ���������������֮ǰ��������׽������ǣ�")
				.setPositiveButton("����", new DialogInterface.OnClickListener() {

					public void onClick(DialogInterface dialog, int which) {
						SaveChess();
						data.clear();
						finish();
					}
				})
				.setNeutralButton("������", new DialogInterface.OnClickListener() {

					public void onClick(DialogInterface dialog, int which) {
						data.clear();
						finish();
					}
				})
				.setNegativeButton("ȡ��", new DialogInterface.OnClickListener() {

					public void onClick(DialogInterface dialog, int which) {

					}
				}).setCancelable(false).create();
		alertDialog.show();
	}

	public void SaveChess() {
		Vector tempdata = chessboard.getData();
		mypoint p;
		SharedPreferences preferences = getSharedPreferences("Data",
				MODE_PRIVATE);
		SharedPreferences.Editor editor = preferences.edit();
		int Size = tempdata.size();
		editor.putInt("Size", Size);
		for (int i = 0; i < Size; i++) {
			p = (mypoint) tempdata.elementAt(i);
			editor.putInt("x" + i, p.getx());
			editor.putInt("y" + i, p.gety());
			editor.putInt("color" + i, p.getcolor());
		}
		boolean saveSuccess = editor.commit();
		if (saveSuccess){
			StartActivity.hasChessManual = true;
			Toast.makeText(this, "�������׳ɹ�", Toast.LENGTH_SHORT).show();
		}else{
			Toast.makeText(this, "��������ʧ��", Toast.LENGTH_SHORT).show();
		}
	}

	public void CheckUndo() {
		int Size = chessboard.getData().size();
		if ((0 == Size)
				|| ((1 == Size) && StartActivity.isPVE && (!StartActivity.isFirst))) {
			undoGameBtn.setEnabled(false);
			newGameBtn.setEnabled(false);
			saveGameBtn.setEnabled(false);
			undoEnable = false;
		} else {
			undoGameBtn.setEnabled(true);
			newGameBtn.setEnabled(true);
			saveGameBtn.setEnabled(true);
			undoEnable = true;
		}
		if (!StartActivity.isPractice)
			undoGameBtn.setEnabled(false);
	}

	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (!undoEnable) {
			data.clear();
			finish();
			return true;
		}
		Dialog alertDialog = new AlertDialog.Builder(this).setTitle("�Ƿ񱣴����ף�")
				.setMessage("�Ƿ񱣴����ף����������������ǰ���е���Ϸ����ʧ���������������֮ǰ��������׽������ǣ�")
				.setPositiveButton("����", new DialogInterface.OnClickListener() {

					public void onClick(DialogInterface dialog, int which) {
						SaveChess();
						data.clear();
						finish();
					}
				})
				.setNeutralButton("������", new DialogInterface.OnClickListener() {

					public void onClick(DialogInterface dialog, int which) {
						data.clear();
						finish();
					}
				})
				.setNegativeButton("ȡ��", new DialogInterface.OnClickListener() {

					public void onClick(DialogInterface dialog, int which) {

					}
				}).setCancelable(false).create();
		alertDialog.show();
		return true;
	}
	
	public void playSound(){
		if (StartActivity.soundOpen) {
			final int sourceId = soundpool.load(GameActivity.this,
					R.raw.boardbutton, 1);
			soundpool.setOnLoadCompleteListener(new SoundPool.OnLoadCompleteListener() {
						public void onLoadComplete(SoundPool soundPool,
								int sampleId, int status) {
							soundPool.play(sourceId, 1, 1, 0,
									0, 1);
						}
					});
		}
	}

}
