package com.thomas.hikvision;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.PixelFormat;
import android.os.AsyncTask;
import android.os.Handler;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback;
import android.view.SurfaceView;

import android.view.View;
import android.widget.ProgressBar;
import com.hikvision.netsdk.HCNetSDK;
import com.hikvision.netsdk.NET_DVR_PREVIEWINFO;
import com.hikvision.netsdk.RealPlayCallBack;

@SuppressLint("NewApi")
public class PlaySurfaceView extends SurfaceView implements Callback {

    private final String TAG = "PlaySurfaceView";
    private Activity activity;
    private int m_iWidth = 0;
    private ProgressBar progressBar;
    public int getM_iWidth() {
        return m_iWidth;
    }

    public void setM_iWidth(int m_iWidth) {
        this.m_iWidth = m_iWidth;
    }

    public int getM_iHeight() {
        return m_iHeight;
    }

    public void setM_iHeight(int m_iHeight) {
        this.m_iHeight = m_iHeight;
    }

    private int m_iHeight = 0;
    public int m_iPreviewHandle = -1;
    private SurfaceHolder m_hHolder;

    public int m_lUserID = -1;
    public int m_iChan = 0;
    private int index;

    public int getIndex() {
        return index;
    }

    public int getM_iPreviewHandle() {
        return m_iPreviewHandle;
    }

    private PlaySurfaceViewCallBack playSurfaceViewCallBack;


    public void setPlaySurfaceViewCallBack(PlaySurfaceViewCallBack playSurfaceViewCallBack) {
        this.playSurfaceViewCallBack = playSurfaceViewCallBack;
    }

    public PlaySurfaceView(Activity demoActivity, int index) {
        super( demoActivity);
        // TODO Auto-generated constructor stub
        activity= demoActivity;
        m_hHolder = this.getHolder();
        m_hHolder.addCallback(this);
        this.index = index;
    }

    @Override
    public void surfaceChanged(SurfaceHolder arg0, int arg1, int arg2, int arg3) {
        // TODO Auto-generated method stub
//        setZOrderOnTop(true);
//        getHolder().setFormat(PixelFormat.TRANSLUCENT);

    }

    @Override
    public void surfaceCreated(SurfaceHolder arg0) {
        // TODO Auto-generated method stub
        System.out.println("surfaceCreated");
        if (playSurfaceViewCallBack!=null)
            playSurfaceViewCallBack.surfaceCreated(arg0,index);
    }


    @Override
    public void surfaceDestroyed(SurfaceHolder arg0) {
        // TODO Auto-generated method stub
        System.out.println("surfaceDestroyed");
    }

    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.setMeasuredDimension(m_iWidth - 1, m_iHeight - 1);
    }

    public void setParam(int nScreenSize) {
        m_iWidth = nScreenSize / 2;
        m_iHeight = m_iWidth;
    }

    public void startPreview(int iUserID, int iChan) {
        Log.i(TAG, "preview channel:" + iChan);
        if (iUserID < 0) {
            Log.e(TAG, "please login on device first");
            return;
        }
        new MyPlayAsyncTask().execute(iUserID,iChan);
    }

    class MyPlayAsyncTask extends AsyncTask<Integer,Integer,Integer>{

        @Override
        protected Integer doInBackground(Integer... integers) {
            NET_DVR_PREVIEWINFO previewInfo = new NET_DVR_PREVIEWINFO();
            previewInfo.lChannel = integers[1];
            previewInfo.dwStreamType = 0; // substream
            previewInfo.bBlocked = 1;
            previewInfo.hHwnd = m_hHolder;
            // HCNetSDK start preview

            m_iPreviewHandle = HCNetSDK.getInstance().NET_DVR_RealPlay_V40(integers[0],
                    previewInfo, null);
            if (m_iPreviewHandle < 0) {
                Log.e(TAG, "NET_DVR_RealPlay is failed!Err:"
                        + HCNetSDK.getInstance().NET_DVR_GetLastError());

            }
            return null;
        }
    }

    public void stopPreview() {
        new MyStopPreviewTask().execute("");
    }

    class MyStopPreviewTask extends AsyncTask<String,Integer,Integer>{

        @Override
        protected Integer doInBackground(String... strings) {
            HCNetSDK.getInstance().NET_DVR_StopRealPlay(m_iPreviewHandle);
            return null;
        }
    }
}
