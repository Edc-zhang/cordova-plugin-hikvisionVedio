package com.thomas.hikvision;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.GradientDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.SurfaceHolder;
import android.view.View;
import android.view.Window;
import android.widget.*;

import com.hikvision.netsdk.ExceptionCallBack;
import com.hikvision.netsdk.HCNetSDK;
import com.hikvision.netsdk.NET_DVR_DEVICEINFO_V30;
import com.hikvision.netsdk.NET_DVR_PICCFG_V30;
import com.hikvision.netsdk.NET_DVR_VIDEOEFFECT;

import org.MediaPlayer.PlayM4.Player;

import java.io.File;
import java.io.FileOutputStream;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;

/**
 * Created by Thomas.Wang on 2017/5/23.
 */

public class HikvisionSingleActivity extends Activity implements View.OnClickListener {
    private Button back;
    private TextView title;
    private Button more;
    private FrameLayout singleFL;
    private PlaySurfaceView playSurfaceView ;
    private Button m_oPreviewBtn;

    private NET_DVR_DEVICEINFO_V30 m_oNetDvrDeviceInfoV30 = null;

    private int m_iLogID = -1; // return by NET_DVR_Login_v30
    private int m_iPlayID = -1; // return by NET_DVR_RealPlay_V30

    private int m_iPort = -1; // play port
    private int m_iStartChan = 0; // start channel no

    private String ip ;
    private String port ;
    private String userName ;
    private String password ;
    private int index = 0;
    private String channelName;


    private final static String TAG = "HikvisionSingleActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);//去掉标题栏 第一种方法
        setContentView(getIdTypeLayout("activity_hikvision_singleshow"));
        getParams();
        getChannelName();
        findViews();
        canvasView();
        initPopupWindow();
//        setListeners();
    }


    private void getChannelName(){
        new MyChannelNameTask().execute("");
    }

    private void findViews() {
        title = (TextView) findViewById(getIdTypeId("single_tx_title"));
        title.setText("");
        singleFL = (FrameLayout) findViewById(getIdTypeId("single_sur_player"));
        findViewById(getIdTypeId("single_btn_back")).setOnClickListener(this);
        more = (Button) findViewById(getIdTypeId("single_btn_more"));
        more.setOnClickListener(this);
    }

    private void setListeners() {
        if (m_iLogID < 0) {
            Log.e(TAG, "please login on device first");
            login();
        }
    }

    private void getParams(){
        Intent intent = getIntent();
        m_iLogID = intent.getIntExtra("m_iLogID",-1);
        ip = intent.getStringExtra("ip");
        port = intent.getStringExtra("port");
        userName = intent.getStringExtra("userName");
        password = intent.getStringExtra("password");
        m_iStartChan= intent.getIntExtra("m_iStartChan",0);
        index = intent.getIntExtra("index",0);
    }

    private void canvasView() {
        DisplayMetrics metric = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metric);

        playSurfaceView = new PlaySurfaceView(this,0);
        playSurfaceView.setParam(metric.widthPixels * 2);
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.WRAP_CONTENT,
                FrameLayout.LayoutParams.WRAP_CONTENT);

        singleFL.addView(playSurfaceView,params);

//        final FrameLayout loadingFL = new FrameLayout(this);
//        FrameLayout.LayoutParams loadingFLParames = new FrameLayout.LayoutParams(metric.widthPixels,metric.widthPixels);
//
//
//        LinearLayout loadingLL = new LinearLayout(this);
//        FrameLayout.LayoutParams loadingLLParames = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.WRAP_CONTENT,
//                FrameLayout.LayoutParams.WRAP_CONTENT);
//        loadingLLParames.gravity = Gravity.CENTER;
//        loadingLL.setOrientation(LinearLayout.VERTICAL);
//
//        ProgressBar progressBar = new ProgressBar(this);//进度圈圈
//        LinearLayout.LayoutParams progressBararams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
//                LinearLayout.LayoutParams.WRAP_CONTENT);
//        progressBararams.gravity = Gravity.CENTER_HORIZONTAL;
//        loadingLL.addView(progressBar,progressBararams);
//
//        TextView textView = new TextView(this);//进度说明
//        LinearLayout.LayoutParams textViewParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
//                LinearLayout.LayoutParams.WRAP_CONTENT);
//        textViewParams.gravity = Gravity.CENTER_HORIZONTAL;
//        textView.setText("Loading~~~");
//        textView.setTextColor(Color.parseColor("#FFFFFF"));
//        loadingLL.addView(textView,textViewParams);

//        loadingFL.addView(loadingLL,loadingLLParames);


//        loadingFL.setVisibility(View.VISIBLE);
//        singleFL.addView(loadingFL,loadingFLParames);
        playSurfaceView.setPlaySurfaceViewCallBack(new PlaySurfaceViewCallBack() {
            @Override
            public void surfaceCreated(SurfaceHolder surfaceHolder, int i) {
                playSurfaceView.startPreview(m_iLogID,m_iStartChan+index);
//                new MyChannelNameTask().execute("");
            }
        });

    }

    class MyChannelNameTask extends AsyncTask<String,Integer,String>{

        @Override
        protected String  doInBackground(String... strings) {
            String channelName = "";
            try {
                NET_DVR_PICCFG_V30 netDvrPiccfgV30 = new NET_DVR_PICCFG_V30();
                HCNetSDK.getInstance().NET_DVR_GetDVRConfig(m_iLogID,HCNetSDK.NET_DVR_GET_PICCFG_V30,m_iStartChan+index,netDvrPiccfgV30);
                channelName = new String(netDvrPiccfgV30.sChanName,"GB2312").trim();
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            return channelName;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            channelName = s;
            title.setText(s);
        }
    }
    private void login(){
        try {
            if (m_iLogID < 0) {
                // login on the device
                m_iLogID = loginNormalDevice();
                if (m_iLogID < 0) {
                    Log.e(TAG, "This device logins failed!");
                    return;
                } else {
                    System.out.println("m_iLogID=" + m_iLogID);
                }
                // get instance of exception callback and set
                ExceptionCallBack oexceptionCbf = getExceptiongCbf();
                if (oexceptionCbf == null) {
                    Log.e(TAG, "ExceptionCallBack object is failed!");
                    return;
                }

                if (!HCNetSDK.getInstance().NET_DVR_SetExceptionCallBack(oexceptionCbf)) {
                    Log.e(TAG, "NET_DVR_SetExceptionCallBack is failed!");
                    return;
                }
                Log.i(TAG, "Login sucess ****************************1***************************");
            } else {
                // whether we have logout
                if (!HCNetSDK.getInstance().NET_DVR_Logout_V30(m_iLogID)) {
                    Log.e(TAG, " NET_DVR_Logout is failed!");
                    return;
                }
                m_iLogID = -1;
            }
        } catch (Exception err) {
            Log.e(TAG, "error: " + err.toString());
        }
    }
    private int loginNormalDevice() {
        // get instance
        m_oNetDvrDeviceInfoV30 = new NET_DVR_DEVICEINFO_V30();
        if (null == m_oNetDvrDeviceInfoV30) {
            Log.e(TAG, "HKNetDvrDeviceInfoV30 new is failed!");
            return -1;
        }
        String strIP = ip;
        int nPort = Integer.parseInt(port);
        String strUser = userName;
        String strPsd = password;
        // call NET_DVR_Login_v30 to login on, port 8000 as default
        int iLogID = HCNetSDK.getInstance().NET_DVR_Login_V30(strIP, nPort, strUser, strPsd, m_oNetDvrDeviceInfoV30);

        if (iLogID < 0) {
            Log.e(TAG, "NET_DVR_Login is failed!Err:" + HCNetSDK.getInstance().NET_DVR_GetLastError());
            return -1;
        }
        m_iStartChan = m_oNetDvrDeviceInfoV30.byStartChan +index;
        Log.i(TAG, "NET_DVR_Login is Successful!");

        return iLogID;
    }

    private void captureCamera(){
        new Thread(new Runnable() {
            @Override
            public void run() {

                try {
                    if (playSurfaceView.getM_iPreviewHandle()<0){
                        Log.e(TAG, "please start preview first");
                        return;
                    }
                    m_iPort = 0;
                    Player.MPInteger stWidth = new Player.MPInteger();
                    Player.MPInteger stHeight = new Player.MPInteger();
                    if (!Player.getInstance().getPictureSize(m_iPort, stWidth, stHeight)) {
                        Log.e(TAG, "getPictureSize failed with error code:" + Player.getInstance().getLastError(m_iPort));
                        return;
                    }
                    int nSize = 5 * stWidth.value * stHeight.value;
                    byte[] picBuf = new byte[nSize];
                    Player.MPInteger stSize = new Player.MPInteger();
                    if (!Player.getInstance().getBMP(m_iPort, picBuf, nSize, stSize)) {
                        Log.e(TAG, "getBMP failed with error code:" + Player.getInstance().getLastError(m_iPort));
                        return;
                    }

                    File sd= Environment.getExternalStorageDirectory();
                    String path=sd.getPath()+"/hikvision/"+channelName+"/";
                    File file=new File(path);
                    if(!file.exists())
                        file.mkdir();

                    SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyy-MM-dd-hh:mm:ss");
                    String date = sDateFormat.format(new java.util.Date());
                    file = new File(path+date+ ".bmp");
                    if (!file.exists()){
                        file.createNewFile();
                    }
                    FileOutputStream fileOS = new FileOutputStream(file);
                    fileOS.write(picBuf, 0, stSize.value);
                    fileOS.close();
                    Log.i(TAG,"capture success!");
                } catch (Exception e) {
                    Log.e(TAG, "error: " + e.toString());
                }

            }
        }).start();
    }


    private ExceptionCallBack getExceptiongCbf() {
        ExceptionCallBack oExceptionCbf = new ExceptionCallBack() {
            public void fExceptionCallBack(int iType, int iUserID, int iHandle) {
                System.out.println("recv exception, type:" + iType);
            }
        };
        return oExceptionCbf;
    }

    private int getIdTypeLayout(String name){
        return getId(name,"layout");
    }
    private int getIdTypeId(String name){
        return getId(name,"id");
    }
    private int getId(String idName, String type){
        return getResources().getIdentifier(idName, type,getPackageName());
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == getIdTypeId("single_btn_back")){//返回
            finishThisActivity();
        }else if (id == getIdTypeId("single_btn_more")){//更多
            showPopupWindow();
        } else if (id == getIdTypeId("pop_capture_camera")){//拍照
            window.dismiss();
            captureCamera();
        }else if (id == getIdTypeId("pop_capture_record")){//拍照记录
            window.dismiss();
            Intent intent = new Intent(HikvisionSingleActivity.this,CaptureRecord.class);
            intent.putExtra("channelName",channelName);
            startActivity(intent);
        }

    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finishThisActivity();
    }

    private void finishThisActivity() {
        playSurfaceView.stopPreview();
        finish();
    }

    private PopupWindow window;
    private void initPopupWindow(){
        // 构建一个popupwindow的布局
        View popupView = getLayoutInflater().inflate(getIdTypeLayout("popupwindow_hikvision"), null);

//        // 为了演示效果，简单的设置了一些数据，实际中大家自己设置数据即可，相信大家都会。
//        ListView lsvMore = (ListView) popupView.findViewById(R.id.lsvMore);
//        lsvMore.setAdapter(new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_list_item_1, datas));
//        m_oPreviewBtn = (Button) popupView.findViewById(getIdTypeId("pop_preview"));
//        m_oPreviewBtn.setOnClickListener(this);
        popupView.findViewById(getIdTypeId("pop_capture_camera")).setOnClickListener(this);
        popupView.findViewById(getIdTypeId("pop_capture_record")).setOnClickListener(this);
        // 创建PopupWindow对象，指定宽度和高度
        window = new PopupWindow(popupView,  LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        // 设置动画
//        window.setAnimationStyle(R.style.popup_window_anim);
        // 设置背景颜色
        window.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#F8F8F8")));
        //  设置可以获取焦点
        window.setFocusable(true);
        // 设置可以触摸弹出框以外的区域
        window.setOutsideTouchable(true);
        // 更新popupwindow的状态
        window.update();
    }

    private void showPopupWindow(){

        // 以下拉的方式显示，并且可以设置显示的位置
        window.showAsDropDown(more, 0, 0);
    }


}
