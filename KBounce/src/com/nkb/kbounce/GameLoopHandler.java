package com.nkb.kbounce;

import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;

public class GameLoopHandler extends Handler {
	private static String TAG = "KBounce";
//	private boolean mShouldStop;
	//private int mIterationsRequired;
	//private int mCurIteration;
	private View mView;

	public GameLoopHandler(View view) { //int count, View view) {
		//mIterationsRequired = count;
		//mCurIteration = 0;
		mView = view;
	}
	@Override
	public void handleMessage(Message msg) {
		super.handleMessage(msg);
		//Log.d(TAG, "message received");
		//if (mCurIteration < mIterationsRequired) {
			//++mCurIteration;
			mView.invalidate();
			this.sendEmptyMessage(0);
		//}
	}
}
