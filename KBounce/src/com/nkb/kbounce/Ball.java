package com.nkb.kbounce;

import java.util.Random;

public class Ball {
	int x,y,radius,initFlag;
	float velX,velY;
	Random rn; 
	Ball() {
		rn=new Random();
		x=rn.nextInt(30-2);
		y=rn.nextInt(30-2);
		x++;
		y++;
		float dir=rn.nextInt(360);
		dir=(float) (dir*3.142857143/180.0);
		velX=(float) (10.0*Math.cos(dir));
		velY=(float) (10.0*Math.sin(dir));
		initFlag=0;
	}
	/*void initBall(int m, int n) {
		rn=new Random();
		x=rn.nextInt(m-2);
		y=rn.nextInt(n-2);
		x++;
		y++;
		float dir=rn.nextInt(360);
		dir=(float) (dir*3.142857143/180.0);
		velX=(float) (10.0*Math.cos(dir));
		velY=(float) (10.0*Math.sin(dir));
		initFlag=0;
	}*/
}
