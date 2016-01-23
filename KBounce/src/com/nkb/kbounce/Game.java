package com.nkb.kbounce;

import java.util.Random;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

public class Game extends Activity {
	private static final String TAG = "KBounce" ;
	public static final String KEY_DIFFICULTY="com.nkb.kbounce.difficulty" ;
	public static final int DIFFICULTY_EASY = 0 ;
	public static final int DIFFICULTY_MEDIUM = 1 ;
	public static final int DIFFICULTY_HARD = 2 ;
	protected static final int DIFFICULTY_CONTINUE = -1;
	private static final String PREF_ARCADE = "arcade" ;
	int level,life,points;
	int board[][] = new int[30][30];
	float totalVel;
	//Ball balls[];
	int x[],y[],xmin[],ymin[],xmax[],ymax[];
	float velX[],velY[];
	int initFlag;
	Random rn;
	private ArcadeView arcadeView;
	private String boardString;
	Handler mHandler;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Log.d(TAG, "onCreate" );
		int diff = getIntent().getIntExtra(KEY_DIFFICULTY,DIFFICULTY_EASY);
		initializeGame(diff);
		//calculateUsedTiles();
		arcadeView = new ArcadeView(this);
		setContentView(arcadeView);
		//arcadeView.requestFocus();
		getIntent().putExtra(KEY_DIFFICULTY, DIFFICULTY_CONTINUE);
		//mView = new AnimateView(this);
		//setContentView(mView);
		mHandler = new GameLoopHandler(arcadeView);//(200, arcadeView);
	}
	void initializeGame(int diff) {
		for(int i=0;i<30;i++)
			for(int j=0;j<30;j++) {
				if(i==0 || j==0 || i==29 || j==29)
					board[i][j]=1;
				else
					board[i][j]=0;
			}
		level=1;
		life=level+1;
		points=0;
		initFlag=0;
		x=new int[level+1];
		y=new int[level+1];
		velX=new float[level+1];
		velY=new float[level+1];
		xmin=new int[level+1];
		ymin=new int[level+1];
		xmax=new int[level+1];
		ymax=new int[level+1];
		//initFlag=0;
		/*x[0]=2;
		x[1]=3;
		y[0]=4;
		y[1]=5;
		velX[0]=5; velY[0]=5;
		velY[1]=4; velX[1]=4;
		*/
		
		/*for(int i=0;i<=level;i++)
			balls[i]=new Ball();*/
		//balls=new Ball[level+1];
		//for(int i=0;i<=level;i++)
			//balls[i].initBall(30,30);
		boardString=toArcadeString(board);
		switch (diff) {
			case DIFFICULTY_CONTINUE:
				String arc;
				arc = getPreferences(MODE_PRIVATE).getString(PREF_ARCADE,boardString);
				board=fromArcadeString(arc);
				break;
			case DIFFICULTY_EASY: totalVel=20; break;
			case DIFFICULTY_MEDIUM: totalVel=30; break;
			case DIFFICULTY_HARD: totalVel=40; break;
				
				
		}
	}
	@Override
	public void onResume() {
		super.onResume();
		Music.play(this, R.raw.second);
		mHandler.sendEmptyMessage(99);
		Log.d(TAG, "onResume");
	}
	@Override
	public void onDestroy() {
		super.onDestroy();
		Log.d(TAG, "onDestroy");
	}
	@Override
	protected void onPause() {
		super.onPause();
		Log.d(TAG, "onPause" );
		Music.stop(this);
		getPreferences(MODE_PRIVATE).edit().putString(PREF_ARCADE,toArcadeString(board)).commit();
	}
	static private String toArcadeString(int[][] board) {
		StringBuilder buf = new StringBuilder();
		for(int i=0;i<30;i++)
			for(int j=0;j<30;j++)
				buf.append(board[i][j]);
		return buf.toString();
	}
	static protected int[][] fromArcadeString(String string) {
		int[][] board = new int[30][30];
		int k=0;
		for(int i=0;i<30;i++)
			for(int j=0;j<30;j++)
				board[i][j]=string.charAt(k++)-'0';
		return board;
	}
}
