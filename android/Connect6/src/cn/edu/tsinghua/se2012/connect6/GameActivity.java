package cn.edu.tsinghua.se2012.connect6;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import cn.edu.tsinghua.se2012.connect6.ChessBoardView;;

public class GameActivity extends Activity{
	private static boolean soundOpen = true;
	private static boolean vibrateOpen = true;
    private boolean computer;
    //0 = ���˻���ս
    //1 = �˻���ս
    private int mode;
    //0 = ��ϰģʽ
    //1 = ʵսģʽ
	
	final int CODE = 0x717;				//������Ϸ���ý���������
	
	ChessBoardView chessboard;
	private Button newGameBtn;
	private Button undoGameBtn;
	private Button gameSettingBtn;
	private Button returnmenuBtn;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.game);
		
		chessboard = (ChessBoardView)findViewById(R.id.chessborad);
		newGameBtn = (Button)findViewById(R.id.newgame);
		undoGameBtn = (Button)findViewById(R.id.undogame);
		gameSettingBtn = (Button)findViewById(R.id.gamesetting);
		returnmenuBtn = (Button)findViewById(R.id.returnmenu);
		
		//��ȡ��Ļ�ֱ���
		DisplayMetrics dm = new DisplayMetrics();   
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        int screenWidth = dm.widthPixels;
        int screenHeight = dm.heightPixels;
        
        chessboard.setScreenWidth(screenWidth);
        chessboard.setScreenHeight(screenHeight);
        chessboard.invalidate();     
        
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        computer = bundle.getBoolean("isPVE");
        mode = bundle.getBoolean("isPractice")?0:1;      
		
		//��ʼ����Ϸ
		newGameBtn.setOnClickListener(new View.OnClickListener() {
		    public void onClick(View v) {
		    	//to be added
		    }
		});
		
		//����
		undoGameBtn.setOnClickListener(new View.OnClickListener() {
		    public void onClick(View v) {
		    	//to be added
		    }
		});
		
		//��Ϸ����
		gameSettingBtn.setOnClickListener(new View.OnClickListener() {
		    public void onClick(View v) {
		    	Intent intent = new Intent(GameActivity.this,
						GameSettingActivity.class);
				startActivityForResult(intent, CODE);
		    }
		});
		  
		//�������˵�
		returnmenuBtn.setOnClickListener(new View.OnClickListener() {
		    public void onClick(View v) {
		    	Intent intent = new Intent(GameActivity.this,
						StartActivity.class);
				startActivity(intent);
				finish();
		    }
		});
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data){
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode == CODE && resultCode == CODE){
			Bundle bundle = data.getExtras();
			soundOpen = bundle.getBoolean("soundOpen");
			vibrateOpen = bundle.getBoolean("vibrateOpen");
		}
	}
}
