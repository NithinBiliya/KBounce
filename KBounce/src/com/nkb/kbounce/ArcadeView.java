package com.nkb.kbounce;

import java.util.Random;

import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AnimationUtils;

public class ArcadeView extends View {
	private static final String TAG = "KBounce" ;
	private final Game game;
	private float width,height,heightStart;
	private String ttext="hello";
	private Paint background,darkLine,lightLine,lightTile,darkTile,text,circlePaint,lineDirPaint;
	private final float timeSlice=(float) 0.5;
	private int lineDirection; // -1 : horizontal , 1 : vertical
	private int touchi,touchj,linex1,linex2,liney1,liney2;
	private int lineArrayX[]=new int[30],lineArrayY[]=new int[30],xIndex,yIndex,totalBlocks;
	boolean touch,winFlag,loseFlag;
	//private int x[],y[];
	//private float velX[],velY[];
	//Random rn;

	public ArcadeView(Context context) {
		super(context);
		this.game = (Game) context;
		setFocusable(true);
		setFocusableInTouchMode(true);
		background = new Paint();
		//background.setColor(getResources().getColor(R.color.arcade_background));
		background.setColor(Color.BLACK);
		darkLine = new Paint();
		//darkLine.setColor(getResources().getColor(R.color.arcade_dark));
		darkLine.setColor(Color.argb(255,255,255,255));
		darkLine.setStrokeWidth(1);
		lightLine = new Paint();
		//lightLine.setColor(getResources().getColor(R.color.arcade_dark));
		lightLine.setColor(Color.argb(100,255,255,255));
		lightLine.setStrokeWidth(1);
		lightTile = new Paint();
		//lightTile.setColor(getResources().getColor(R.color.arcade_background));
		lightTile.setColor(Color.argb(100,200,150,170));
		lightTile.setStrokeWidth(0);
		darkTile = new Paint();
		//darkTile.setColor(getResources().getColor(R.color.arcade_foreground));
		darkTile.setColor(Color.argb(127,200,150,170));
		darkTile.setStrokeWidth(0);
		heightStart=50;
		text = new Paint(Paint.ANTI_ALIAS_FLAG);
		text.setColor(Color.LTGRAY);
		text.setStyle(Style.FILL);
		text.setTextSize(20);
		text.setTextScaleX(1);
		text.setTextAlign(Paint.Align.LEFT);
		circlePaint = new Paint();
		circlePaint.setAntiAlias(true);
		circlePaint.setDither(true);
		circlePaint.setColor(Color.argb(255,0,0,0));
		lineDirection=-1;
		lineDirPaint=new Paint();
		lineDirPaint.setColor(Color.argb(255, 0, 0, 255));
		touch=false;
		xIndex=-1; yIndex=-1;
		winFlag=false;  loseFlag=false;
		/*if(this.game.initFlag==0) {
			/*for(int i=0;i<=this.game.level;i++) {
				this.game.x[i]=(int) (this.game.x[i]*width+width/2);
				this.game.y[i]=(int) (this.game.y[i]*height+height/2+heightStart);
			}*/
			/*this.game.initFlag=1;
			this.game.x=new int[this.game.level+1];
			this.game.y=new int[this.game.level+1];
			this.game.velX=new float[this.game.level+1];
			this.game.velY=new float[this.game.level+1];
			Random rn=new Random();
			for(int i=0;i<=this.game.level;i++) {
				this.game.x[i]=rn.nextInt((int)(width*28));
				this.game.x[i]+=width;
				this.game.y[i]=rn.nextInt((int)(height*28));
				this.game.y[i]+=height+heightStart;
				//x[i]++;
				//y[i]++;
				float dir=rn.nextInt(360);
				dir=(float) (dir*3.142857143/180.0);
				this.game.velX[i]=(float) (10.0*Math.cos(dir));
				this.game.velY[i]=(float) (10.0*Math.sin(dir));
				//Log.d(TAG,"i,x,y  "+String.valueOf(i)+"  "+String.valueOf(x[i])+"  "+String.valueOf(y[i]));
			}
		}*/
	}
	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh) {
		width = w / 30f;
		height = h / 30f;
		height=width;
		//getRect(selX, selY, selRect);
		Log.d(TAG, "onSizeChanged: width " + width + ", height " + height);
		
		if(this.game.initFlag==0) {
		/*for(int i=0;i<=this.game.level;i++) {
			this.game.x[i]=(int) (this.game.x[i]*width+width/2);
			this.game.y[i]=(int) (this.game.y[i]*height+height/2+heightStart);
		}*/
		this.game.initFlag=1;
		//this.game.x=new int[this.game.level+1];
		//this.game.y=new int[this.game.level+1];
		//this.game.velX=new float[this.game.level+1];
		//this.game.velY=new float[this.game.level+1];
		Random rn=new Random();
		for(int i=0;i<=this.game.level;i++) {
			this.game.xmin[i]=(int)width;
			this.game.xmax[i]=(int)(width*29);
			this.game.ymin[i]=(int)(height+heightStart);
			this.game.ymax[i]=(int)(height*29+heightStart);
			this.game.x[i]=rn.nextInt((int)(width*28));
			this.game.x[i]+=width;
			this.game.y[i]=rn.nextInt((int)(height*28));
			this.game.y[i]+=height+heightStart;
			//x[i]++;
			//y[i]++;
			float dir=rn.nextInt(360);
			dir=(float) (dir*3.142857143/180.0);
			this.game.velX[i]=(float) (this.game.totalVel*Math.cos(dir));
			this.game.velY[i]=(float) (this.game.totalVel*Math.sin(dir));
			//Log.d(TAG,"i,x,y  "+String.valueOf(i)+"  "+String.valueOf(x[i])+"  "+String.valueOf(y[i]));
		}
	}
		
		super.onSizeChanged(w, h, oldw, oldh);
	}
	@Override
	protected void onDraw(Canvas canvas) {
		canvas.drawRect(0, 0, getWidth(), getHeight(), background);
		if(lineDirection<0)
			//canvas.drawLine(10*width, heightStart/2, 20*width, heightStart/2, lineDirPaint);
			canvas.drawRect(10*width, heightStart/2+width/2, 20*width, heightStart/2-width/2, lineDirPaint);
		else
			//canvas.drawLine(15*width, heightStart/2-10, 15*width, heightStart/2+10, lineDirPaint);
			canvas.drawRect(15*width, heightStart/2-10, 16*width, heightStart/2+10, lineDirPaint);
		for(int i=0;i<30;i++)
			for(int j=0;j<30;j++) {
				if(this.game.board[i][j]==1) {
					canvas.drawRect(i*width, heightStart+j*height, (i+1)*width, heightStart+(j+1)*height, darkLine);
					canvas.drawRect(i*width+1, heightStart+j*height+1, (i+1)*width-1, heightStart+(j+1)*height-1, lightTile);
				}
				else {
					canvas.drawRect(i*width, heightStart+j*height, (i+1)*width, heightStart+(j+1)*height, lightLine);
					canvas.drawRect(i*width+1, heightStart+j*height+1, (i+1)*width-1, heightStart+(j+1)*height-1, darkTile);
				}
			}
		this.game.points=calculatePoints();
		canvas.drawText("Level : "+String.valueOf(this.game.level), 20, heightStart+height*30+20, text);
		canvas.drawText("Life : "+String.valueOf(this.game.life), 20, heightStart+height*30+55, text);
		canvas.drawText("Points : "+String.valueOf(this.game.points), 20, heightStart+height*30+90, text);
		//canvas.drawText(ttext, 20, heightStart+height*30+80, text);
		for(int i=0;i<=this.game.level;i++) {
			canvas.drawCircle(this.game.x[i], this.game.y[i], width/2, circlePaint);
			this.game.x[i]+=this.game.velX[i]*timeSlice;
			this.game.y[i]+=this.game.velY[i]*timeSlice;
			if(this.game.x[i]>this.game.xmax[i]-width/2 || this.game.x[i]<this.game.xmin[i]+width/2) {
				if(!(winFlag||loseFlag))
					Music.playOnce(this.game, R.raw.hit);
				this.game.velX[i]*=-1;
				this.game.x[i]+=this.game.velX[i]*timeSlice;
			}
			if(this.game.y[i]>this.game.ymax[i]-width/2 || this.game.y[i]<this.game.ymin[i]+width/2) {
				if(!(winFlag||loseFlag))
					Music.playOnce(this.game, R.raw.hit);
				this.game.velY[i]*=-1;
				this.game.y[i]+=this.game.velY[i]*timeSlice;
			}
			/*for(int j=0;j<30;j++)
				for(int k=0;k<30;k++) {
					if(this.game.board[j][k]==1) {
						if((float)(this.game.y[i])>=(float)(height*k-heightStart) && (float)(this.game.y[i])<=(float)(height*(k+1)-heightStart)) {
							if(Math.sqrt(((float)(this.game.x[i]-width*j))*((float)(this.game.x[i]-width*j)))<=(float)(width/2) || 
									Math.sqrt(((float)(this.game.x[i]-width*(j+1)))*((float)(this.game.x[i]-width*(j+1))))<=(float)(width/2)) {
								this.game.velX[i]*=-1;
								this.game.velY[i]*=-1;
								this.game.x[i]+=this.game.velX[i]*timeSlice;
								this.game.y[i]+=this.game.velY[i]*timeSlice;
								this.game.velY[i]*=-1;
								this.game.x[i]+=this.game.velX[i]*timeSlice;
								this.game.y[i]+=this.game.velY[i]*timeSlice;
							}
						}
						if((float)(this.game.x[i])>=(float)(width*j) && (float)(this.game.x[i])<=(float)(width*(j+1))) {
							if(Math.sqrt(((float)(this.game.y[i]-height*k-heightStart))*((float)(this.game.y[i]-height*k-heightStart)))<=(float)(width/2) || 
									Math.sqrt(((float)(this.game.y[i]-height*(k+1)-heightStart))*((float)(this.game.y[i]-height*(k+1)-heightStart)))<=(float)(width/2)) {
								this.game.velY[i]*=-1;
								this.game.velX[i]*=-1;
								this.game.y[i]+=this.game.velY[i]*timeSlice;
								this.game.x[i]+=this.game.velX[i]*timeSlice;
								this.game.velX[i]*=-1;
								this.game.y[i]+=this.game.velY[i]*timeSlice;
								this.game.x[i]+=this.game.velX[i]*timeSlice;
							}
						}
					}
				}*/
		}
		if(touch) {
			if(lineDirection<0) {
				if(this.game.board[linex1][liney1]==1 && this.game.board[linex2][liney2]==1) {
					linex1++; linex2--;
					int z;
					for(z=liney1-2;this.game.board[linex1][z]==0;z--);
					z++;
					int ballFlag=0;
					for(int p=0;p<=this.game.level;p++)
						if(this.game.x[p]>linex1*width && this.game.x[p]<(linex2+1)*width && 
								this.game.y[p]>z*height+heightStart && this.game.y[p]<liney1*height+heightStart ) {
							this.game.ymax[p]=(int) (liney1*height+heightStart);
							ballFlag=1;
						}
					if(ballFlag==0) {
						for(z=liney1-1;this.game.board[linex1][z]==0;z--)
							for(int q=linex1;q<=linex2;q++)
								this.game.board[q][z]=1;
					}
					ballFlag=0;
					for(z=liney1+1;this.game.board[linex1][z]==0;z++);
					z--;
					for(int p=0;p<=this.game.level;p++)
						if(this.game.x[p]>linex1*width && this.game.x[p]<(linex2+1)*width && 
								this.game.y[p]>(liney1+1)*height+heightStart && this.game.y[p]<(z+1)*height+heightStart) {
							this.game.ymin[p]=(int) ((liney1+1)*height+heightStart);
							ballFlag=1;
						}
					if(ballFlag==0) {
						for(z=liney1+1;this.game.board[linex1][z]==0;z++)
							for(int q=linex1;q<=linex2;q++)
								this.game.board[q][z]=1;
					}
					touch=false;
					xIndex=-1; yIndex=-1;
				}
				else {
					if(this.game.board[linex1][liney1]==0) {
						this.game.board[linex1][liney1]=1;
						lineArrayX[++xIndex]=linex1;
						lineArrayY[++yIndex]=liney1;
						linex1--;
					}
					if(this.game.board[linex2][liney2]==0) {
						this.game.board[linex2][liney2]=1;
						lineArrayX[++xIndex]=linex2;
						lineArrayY[++yIndex]=liney2;
						linex2++;
					}
					if(linex1==linex2-1)
						linex2++;
				}
			}
			else {
				if(this.game.board[linex1][liney1]==1 && this.game.board[linex2][liney2]==1) {
					liney1++; liney2--;
					int z;
					for(z=linex1-2;this.game.board[z][liney1]==0;z--);
					z++;
					
					int ballFlag=0;
					for(int p=0;p<=this.game.level;p++)
						if(this.game.x[p]>z*width && this.game.x[p]<linex1*width && 
								this.game.y[p]>liney1*height+heightStart && this.game.y[p]<(liney2+1)*height+heightStart ) {
							this.game.xmax[p]=(int) (linex1*width);
							ballFlag=1;
						}
					if(ballFlag==0) {
						for(z=linex1-1;this.game.board[z][liney1]==0;z--)
							for(int q=liney1;q<=liney2;q++)
								this.game.board[z][q]=1;
					}
					ballFlag=0;
					for(z=linex1+1;this.game.board[z][liney1]==0;z++);
					z--;
					for(int p=0;p<=this.game.level;p++)
						if(this.game.x[p]>(linex1+1)*width && this.game.x[p]<z*width && 
								this.game.y[p]>liney1*height+heightStart && this.game.y[p]<(liney2+1)*height+heightStart) {
							this.game.xmin[p]=(int) ((linex1+1)*width);
							ballFlag=1;
						}
					if(ballFlag==0) {
						for(z=linex1+1;this.game.board[z][liney1]==0;z++)
							for(int q=liney1;q<=liney2;q++)
								this.game.board[z][q]=1;
					}
					touch=false;
					xIndex=-1; yIndex=-1;
				}
				else {
					if(this.game.board[linex1][liney1]==0) {
						this.game.board[linex1][liney1]=1;
						lineArrayX[++xIndex]=linex1;
						lineArrayY[++yIndex]=liney1;
						liney1--;
					}
					if(this.game.board[linex2][liney2]==0) {
						this.game.board[linex2][liney2]=1;
						lineArrayX[++xIndex]=linex2;
						lineArrayY[++yIndex]=liney2;
						liney2++;
					}
					if(liney1==liney2-1)
						liney2++;
				}
			}
			int j,k;
			for(int i=0;i<=this.game.level;i++) {
				for(int m=0;m<=xIndex;m++) {
					j=lineArrayX[m]; k=lineArrayY[m];
					if((float)(this.game.y[i])>=(float)(height*k-heightStart) && (float)(this.game.y[i])<=(float)(height*(k+1)-heightStart)) {
						if(Math.sqrt(((float)(this.game.x[i]-width*j))*((float)(this.game.x[i]-width*j)))<=(float)(width/2) || 
								Math.sqrt(((float)(this.game.x[i]-width*(j+1)))*((float)(this.game.x[i]-width*(j+1))))<=(float)(width/2)) {
							this.game.velX[i]*=-1;
							this.game.velY[i]*=-1;
							this.game.x[i]+=this.game.velX[i]*timeSlice;
							this.game.y[i]+=this.game.velY[i]*timeSlice;
							this.game.velY[i]*=-1;
							this.game.x[i]+=this.game.velX[i]*timeSlice;
							this.game.y[i]+=this.game.velY[i]*timeSlice;
							for(int z=0;z<=xIndex;z++)
								this.game.board[lineArrayX[z]][lineArrayY[z]]=0;
							touch=false;
							xIndex=yIndex=-1;
							this.game.life--;
							startAnimation(AnimationUtils.loadAnimation(game,R.anim.shake));
							if(this.game.life==0) {
								loseFlag=true;
							}
						}
					}
					if((float)(this.game.x[i])>=(float)(width*j) && (float)(this.game.x[i])<=(float)(width*(j+1))) {
						if(Math.sqrt(((float)(this.game.y[i]-height*k-heightStart))*((float)(this.game.y[i]-height*k-heightStart)))<=(float)(width/2) || 
								Math.sqrt(((float)(this.game.y[i]-height*(k+1)-heightStart))*((float)(this.game.y[i]-height*(k+1)-heightStart)))<=(float)(width/2)) {
							this.game.velY[i]*=-1;
							this.game.velX[i]*=-1;
							this.game.y[i]+=this.game.velY[i]*timeSlice;
							this.game.x[i]+=this.game.velX[i]*timeSlice;
							this.game.velX[i]*=-1;
							this.game.y[i]+=this.game.velY[i]*timeSlice;
							this.game.x[i]+=this.game.velX[i]*timeSlice;
							for(int z=0;z<=xIndex;z++)
								this.game.board[lineArrayX[z]][lineArrayY[z]]=0;
							touch=false;
							xIndex=yIndex=-1;
							this.game.life--;
							startAnimation(AnimationUtils.loadAnimation(game,R.anim.shake));
							if(this.game.life==0) {
								loseFlag=true;
							}
						}
					}
				}
			}	
		}
		totalBlocks=0;
		for(int i=0;i<30;i++)
			for(int j=0;j<30;j++)
				if(this.game.board[i][j]==1)
					totalBlocks++;
		if(totalBlocks>=0.75*30*30) {
			winFlag=true;
			/*this.game.points=calculatePoints();
			Paint gameOver = new Paint(Paint.ANTI_ALIAS_FLAG);
			gameOver.setColor(Color.LTGRAY);
			gameOver.setStyle(Style.FILL);
			gameOver.setTextSize(20);
			gameOver.setTextScaleX(1);
			gameOver.setTextAlign(Paint.Align.LEFT);
			canvas.drawRect(0, 0, getWidth(), getHeight(), background);
			canvas.drawText("GAMEOVER", (float) (0.1*getWidth()), 150, gameOver);
			canvas.drawText("YOU WIN THE GAME!", (float) (0.1*getWidth()), 200, gameOver);
			canvas.drawText("Points : "+String.valueOf(this.game.points), (float) (0.1*getWidth()), 250, gameOver);*/
		}
		if(winFlag||loseFlag) {
			this.game.points=calculatePoints();
			Paint gameOver = new Paint(Paint.ANTI_ALIAS_FLAG);
			gameOver.setColor(Color.LTGRAY);
			gameOver.setStyle(Style.FILL);
			gameOver.setTextSize(20);
			gameOver.setTextScaleX(1);
			gameOver.setTextAlign(Paint.Align.LEFT);
			canvas.drawRect(0, 0, getWidth(), getHeight(), background);
			canvas.drawText("GAMEOVER", (float) (0.1*getWidth()), 150, gameOver);
			if(winFlag)
				canvas.drawText("YOU WIN THE GAME!", (float) (0.1*getWidth()), 200, gameOver);
			else
				canvas.drawText("YOU LOSE THE GAME!", (float) (0.1*getWidth()), 200, gameOver);
			canvas.drawText("Points : "+String.valueOf(this.game.points), (float) (0.1*getWidth()), 250, gameOver);
		}
		//touch=false;
	}
	int calculatePoints() {
		int p=0;
		totalBlocks=0;
		for(int i=0;i<30;i++)
			for(int j=0;j<30;j++)
				if(this.game.board[i][j]==1)
					totalBlocks++;
		p+=totalBlocks*10;
		p+=this.game.life*100;
		return p;
	}
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		if(!(winFlag || loseFlag)) {
		if (event.getAction() != MotionEvent.ACTION_DOWN)
			return super.onTouchEvent(event);
		if(event.getY()>=0 && event.getY()<heightStart)
			lineDirection*=-1;
		else if(event.getX()>0 && event.getX()<width*30)
		if(event.getY()>heightStart && event.getY()<heightStart+height*30) {
			touchi=(int) (event.getX()/width);
			touchj=(int) ((event.getY()-heightStart)/height);
			linex1=linex2=touchi;
			liney1=liney2=touchj;
			if(this.game.board[touchi][touchj]==0)
				touch=true;
			//this.game.board[i][j]=1;
		}
		else {
			startAnimation(AnimationUtils.loadAnimation(game,R.anim.shake));
		}
		ttext=String.valueOf(touchi);
		ttext+="  "+String.valueOf(touchj);
		//invalidate();
		//Log.d(TAG, "onTouchEvent: x " + i + ", y " + j);
		}
		return true;
	}
}
