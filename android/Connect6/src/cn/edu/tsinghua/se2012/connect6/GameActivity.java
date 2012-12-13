package cn.edu.tsinghua.se2012.connect6;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.ZoomControls;
import cn.edu.tsinghua.se2012.connect6.ChessBoardView;;

public class GameActivity extends Activity{
	//���̱���ͼƬ��ԭʼBitmap����
	private Bitmap originalChessBoard;
	//���̱���ͼƬ�ĵ�����Ĳ�����ʵ����ʾ��Bitmap����
	private Bitmap resizeChessBoard;
	
	//����Ϊ���е���Ϸ״̬����������
	private static boolean soundOpen = true;		//�����Ƿ���
	private static boolean vibrateOpen = true;		//���Ƿ���
    private boolean computer;
    //0 = ���˻���ս
    //1 = �˻���ս
    private int mode;
    //0 = ��ϰģʽ
    //1 = ʵսģʽ
    private int scaleSize = 3;							//��ǰ�����ڷŴ�ı�������Ϊ1-5��Ĭ��Ϊ3����С��Ϊ1,2,�Ŵ��Ϊ4,5
    private int[] scaleArray = new int[5]; 				//�洢����ͼƬ��5�ִ�С�ĳߴ�
    
	final int CODE = 0x717;				//������Ϸ���ý���������
	
	ChessBoardView chessboard;
	private Button newGameBtn;
	private Button undoGameBtn;
	private Button gameSettingBtn;
	private Button returnmenuBtn;
	private ZoomControls zoomControls;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.game);
		
		chessboard = (ChessBoardView)findViewById(R.id.chessborad);
		newGameBtn = (Button)findViewById(R.id.newgame);
		undoGameBtn = (Button)findViewById(R.id.undogame);
		gameSettingBtn = (Button)findViewById(R.id.gamesetting);
		returnmenuBtn = (Button)findViewById(R.id.returnmenu);
		zoomControls = (ZoomControls)findViewById(R.id.zoomControls);
		
		//��ȡ��Ļ�ֱ���
		DisplayMetrics dm = new DisplayMetrics();   
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        int screenWidth = dm.widthPixels;
        int screenHeight = dm.heightPixels;
        
        //�õ�����ͼƬ��5�ִ�С�ĳߴ�
        for (int i = 0; i < 5; i++){
        	scaleArray[i] = (int)((screenWidth-10) * Math.pow(1.25,i-2));
        }
        
        //���ú����̱���ͼƬ
        originalChessBoard = BitmapFactory.decodeResource(getResources(), R.drawable.chessboard); 
		resizeChessBoard = Bitmap.createScaledBitmap(originalChessBoard, scaleArray[2], scaleArray[2], true);
		chessboard.setImageBitmap(resizeChessBoard);
        
		//����������
        chessboard.setScreenWidth(screenWidth);
        chessboard.setScreenHeight(screenHeight);        
        chessboard.invalidate();     				//���»�������
        
        //�����˵���ȡ�Ƿ��˻���ս��Ϊ��ϰģʽ����ʵսģʽ
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
		
		//���̷Ŵ�   
		zoomControls.setOnZoomInClickListener(new View.OnClickListener()   
        {   
            public void onClick(View v)   
            {    
            	scaleSize = scaleSize + 1;
            	resizeChessBoard = Bitmap.createScaledBitmap(originalChessBoard, scaleArray[scaleSize-1], scaleArray[scaleSize-1], true); 
                chessboard.setImageBitmap(resizeChessBoard);
                               
                zoomControls.setIsZoomOutEnabled(true);
                
                if (scaleSize == 5){
                	zoomControls.setIsZoomInEnabled(false);
                }
            }   
        });
		
        //���̼�С   
		zoomControls.setOnZoomOutClickListener(new View.OnClickListener()   
        {   
            public void onClick(View v) {   
            	scaleSize = scaleSize - 1;
            	resizeChessBoard = Bitmap.createScaledBitmap(originalChessBoard, scaleArray[scaleSize-1], scaleArray[scaleSize-1], true); 
                chessboard.setImageBitmap(resizeChessBoard);
                                
                zoomControls.setIsZoomInEnabled(true);
                
                if (scaleSize == 1){
                	zoomControls.setIsZoomOutEnabled(false);
                } 
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
