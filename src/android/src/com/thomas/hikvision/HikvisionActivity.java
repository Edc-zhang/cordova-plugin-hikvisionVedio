/**
 * <p>HikvisonActivity Class</p>
 * @author zhuzhenlei 2014-7-17
 * @version V1.0
 * @modificationHistory
 * @modify by user:
 * @modify by reason:
 */
package com.thomas.hikvision;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.SurfaceHolder;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSONArray;
import com.hikvision.netsdk.ExceptionCallBack;
import com.hikvision.netsdk.HCNetSDK;
import com.hikvision.netsdk.INT_PTR;
import com.hikvision.netsdk.NET_DVR_DEVICEINFO_V30;

import java.util.Arrays;
import java.util.List;


/**
 * <pre>
 *  ClassName  HikvisonActivity Class
 * </pre>
 *
 * @author zhuzhenlei
 * @version V1.0
 * @modificationHistory
 */
public class HikvisionActivity extends Activity {
	private Button m_oback = null;
	private FrameLayout surfaceView =null;
	private Button tv_title;

	private NET_DVR_DEVICEINFO_V30 m_oNetDvrDeviceInfoV30 = null;

	private int m_iLogID = -1; // return by NET_DVR_Login_v30

	private int m_iPort = -1; // play port
	private int m_iStartChan = 0; // start channel no
	private static PlaySurfaceView[] playView ;
	private final String TAG = "HikvisonActivity";
	private List<CanteenBean> canteenBeanList;
	private String[] datas;

	private Dialog mDialog;
	private int oldPosition = 0;
	private int position = 0;
	private int offset = 2;

	private int cameraNum;
	private String ip ;
	private String port ;
	private String userName ;
	private String password ;
	private String title;

	private boolean loginFlag = false;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		CrashUtil crashUtil = CrashUtil.getInstance();
		crashUtil.init(this);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);//去掉标题栏 第一种方法
		setContentView(getId("activity_hikvision","layout"));

		if (!initeSdk()) {
			this.finish();
			return;
		}
		if (!initeActivity()) {
			this.finish();
			return;
		}
		initPopupWindow();
		new MyLoginAsyncTask().execute("");
		ChangeSingleSurFace();
	}

	private void getParams(){
		Intent intent = getIntent();
		String array = intent.getStringExtra("array");
		canteenBeanList = JSONArray.parseArray(array,CanteenBean.class);
		int size = canteenBeanList.size();
		datas = new String[size];
		for (int i = 0;i<size;i++){
			datas[i] = canteenBeanList.get(i).getCanteenName();
		}
		changeCanteen(0);
	}

	private void changeCanteen(int index){
		CanteenBean canteenBean = canteenBeanList.get(index);
		cameraNum = canteenBean.getCameraNum();
		ip = canteenBean.getPortId();
		port = canteenBean.getAppPortNo();
		userName = canteenBean.getUserName();
		password = canteenBean.getPwd();
		title = canteenBean.getCanteenName();
	}

	private void initPopupWindow() {
		mDialog = new Dialog(this,getId("transparent_dialog","style"));
		View outerView = getLayoutInflater().inflate(getIdTypeLayout("wheel_view"), null);
		mDialog.setContentView(outerView);
		WheelView wv = (WheelView) outerView.findViewById(getIdTypeId("wheel_view_wv"));
		outerView.findViewById(getIdTypeId("wheel_view_cancel")).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				mDialog.dismiss();
			}
		});
		outerView.findViewById(getIdTypeId("wheel_view_complete")).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				mDialog.dismiss();
				if (position ==oldPosition){
					return;
				}
				oldPosition = position;
				tv_title.setText(datas[position]);
				logout();
				changeCanteen(position);
				logindraw();

			}
		});
		wv.setOffset(offset);
		wv.setItems(Arrays.asList(datas));
		wv.setOnWheelViewListener(new WheelView.OnWheelViewListener() {
			@Override
			public void onSelected(int selectedIndex, String item) {
				Log.d(TAG, "[Dialog]selectedIndex: " + selectedIndex + ", item: " + item);
				position = selectedIndex-offset;
			}
		});
		// 在底部显示
		Window mWindow = mDialog.getWindow();
		mWindow.setWindowAnimations(getId("dialog_animation","style"));
		mWindow.setGravity(Gravity.BOTTOM);
		setAspectRatio(mDialog,1,0);

	}

	/**
	 * 登录和绘制窗口
	 */
	private void logindraw(){
		new MyLoginAsyncTask().execute("");
		ChangeSingleSurFace();
	}
	/**
	 * 登出
	 */
	private void logout(){
		new MyLogoutAsyncTask().execute("");
	}

	class MyLogoutAsyncTask extends AsyncTask<String,Integer,Integer>{
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			loginFlag = false;
			stopMultiPreview();
			surfaceView.removeAllViews();
			for (int i = 0;i<cameraNum;i++){
				playView[i] = null;
			}
			m_iLogID = -1;
		}

		@Override
		protected Integer doInBackground(String... strings) {
			// whether we have logout
			if (m_iLogID>=0)
				HCNetSDK.getInstance().NET_DVR_Logout_V30(m_iLogID);
			return null;
		}

		@Override
		protected void onPostExecute(Integer integer) {
			super.onPostExecute(integer);
		}
	}

	/**
	 * 设置Dialog对话框相对于屏幕的宽高显示的比例
	 *
	 * @param dialog
	 *            Dialog对话框对象
	 * @param widthRatio
	 *            Dialog对话框相对于屏幕的宽度显示的比例，如果widthRatio <= 0，则默认0.9
	 * @param heightRatio
	 *            Dialog对话框相对于屏幕的高度显示的比例，如果heightRatio <= 0，则默认wrap_content
	 */
	public void setAspectRatio(Dialog dialog, double widthRatio, double heightRatio) {
		// 获取WindowManager的两种方式
		WindowManager wm = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
		// WindowManager wm = ((Activity) mContext).getWindowManager();
		// 获取屏幕宽、高用
		Display d = wm.getDefaultDisplay();
		// 获取对话框当前的参数值
		WindowManager.LayoutParams lp = dialog.getWindow().getAttributes();

		// 设置Dialog的宽度
		if (widthRatio <= 0) {
			lp.width = (int) (d.getWidth() * 0.9);
		} else {
			lp.width = (int) (d.getWidth() * widthRatio);
		}
		// 设置Dialog的高度
		if (heightRatio > 0) {
			lp.height = (int) (d.getHeight() * heightRatio);
		}

		dialog.getWindow().setAttributes(lp);
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
	protected void onSaveInstanceState(Bundle outState) {
		outState.putInt("m_iPort", m_iPort);
		super.onSaveInstanceState(outState);
		Log.i(TAG, "onSaveInstanceState");
	}

	@Override
	protected void onRestoreInstanceState(Bundle savedInstanceState) {
		m_iPort = savedInstanceState.getInt("m_iPort");
		super.onRestoreInstanceState(savedInstanceState);
		Log.i(TAG, "onRestoreInstanceState");
	}

	private boolean initeSdk() {
		// init net sdk
		if (!HCNetSDK.getInstance().NET_DVR_Init()) {
			Log.e(TAG, "HCNetSDK init is failed!");
			return false;
		}
		HCNetSDK.getInstance().NET_DVR_SetLogToFile(3, "/mnt/sdcard/sdklog/", true);
		return true;
	}

	// GUI init
	private boolean initeActivity() {
		getParams();
		findViews();
		setListeners();

		return true;
	}
	// get controller instance
	private void findViews() {
		surfaceView = (FrameLayout) findViewById(getId("Sur_Player","id"));
		m_oback= (Button) findViewById(getId("btn_back","id"));
		tv_title= (Button) findViewById(getId("tv_title","id"));
		tv_title.setText(title);
	}

	// listen
	private void setListeners() {
		m_oback.setOnClickListener(Back_Listener);
		tv_title.setOnClickListener(ChangeTitle_Listener);
	}

	private void ChangeSingleSurFace() {
		DisplayMetrics metric = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(metric);
		playView= new PlaySurfaceView[cameraNum];
		for (int i = 0; i < cameraNum; i++) {
			if (playView[i] == null) {
				playView[i] = new PlaySurfaceView(this,i);
				playView[i].setParam(metric.widthPixels);
				FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.WRAP_CONTENT,
						FrameLayout.LayoutParams.WRAP_CONTENT);
				params.topMargin = (i / 2) * playView[i].getM_iHeight();
				params.leftMargin = (i % 2) * playView[i].getM_iWidth();
				surfaceView.addView(playView[i],params);
				playView[i].setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						PlaySurfaceView playSurfaceView = (PlaySurfaceView) v;
						int index = playSurfaceView.getIndex();
						if(playSurfaceView.getM_iPreviewHandle()<0){
							Toast toast = Toast.makeText(HikvisionActivity.this,"预览失败！",Toast.LENGTH_LONG);
							toast.setGravity(Gravity.CENTER, 0, 0);
							toast.show();
							return;
						}
						Intent intent = new Intent(HikvisionActivity.this,HikvisionSingleActivity.class);
						intent.putExtra("m_iLogID",m_iLogID);
						intent.putExtra("ip",ip);
						intent.putExtra("port",port);
						intent.putExtra("userName",userName);
						intent.putExtra("password",password);
						intent.putExtra("index",index);
						intent.putExtra("m_iStartChan",m_iStartChan);
						startActivity(intent);
					}
				});
				playView[i].setPlaySurfaceViewCallBack(new PlaySurfaceViewCallBack() {
					@Override
					public void surfaceCreated(SurfaceHolder surfaceHolder, int index) {
						new MyStartPreviewTask().execute(index);
					}
				});
			}
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

		if (m_oNetDvrDeviceInfoV30.byChanNum > 0) {
			m_iStartChan = m_oNetDvrDeviceInfoV30.byStartChan;
//			m_iChanNum = m_oNetDvrDeviceInfoV30.byChanNum;
		} else if (m_oNetDvrDeviceInfoV30.byIPChanNum > 0) {
			m_iStartChan = m_oNetDvrDeviceInfoV30.byStartDChan;
//			m_iChanNum = 1;
//			m_iChanNum = m_oNetDvrDeviceInfoV30.byIPChanNum;
			/*
						 * m_oNetDvrDeviceInfoV30.byIPChanNum +
						 * m_oNetDvrDeviceInfoV30.byHighDChanNum * 256
						 */
		}
//		NET_DVR_IPPARACFG_V40 netDvrIpparacfgV40 = new NET_DVR_IPPARACFG_V40();
//		HCNetSDK.getInstance().NET_DVR_GetDVRConfig(iLogID,HCNetSDK.NET_DVR_GET_IPPARACFG_V40,0xFFFFFFFF,netDvrIpparacfgV40);
//		int dwDChanNum = netDvrIpparacfgV40.dwDChanNum;
//		int dwGroupNum = netDvrIpparacfgV40.dwGroupNum;
//		NET_DVR_IPCHANINFO[] netDvrIpchaninfos = netDvrIpparacfgV40.struIPChanInfo;
//		m_iChanNum = 0;
//		for (int i = 0;i<dwDChanNum;i++){
//			if (netDvrIpchaninfos[i].byEnable == 1){
//				m_iChanNum++;
//			}
//		}
		Log.i(TAG, "NET_DVR_Login is Successful!");
		return iLogID;
	}


	private void stopMultiPreview() {
		for (int i = 0; i < cameraNum; i++) {
			playView[i].stopPreview();
		}
	}


	private ExceptionCallBack getExceptiongCbf() {
		ExceptionCallBack oExceptionCbf = new ExceptionCallBack() {
			public void fExceptionCallBack(int iType, int iUserID, int iHandle) {
				System.out.println("recv exception, type:" + iType);
			}
		};
		return oExceptionCbf;
	}

	private OnClickListener ChangeTitle_Listener = new OnClickListener() {
		@Override
		public void onClick(View v) {
			mDialog.show();
		}
	};

	private OnClickListener Back_Listener = new OnClickListener() {
		@Override
		public void onClick(View v) {
			finishThisActivity();
		}
	};

	@Override
	public void onBackPressed() {
		super.onBackPressed();
		finishThisActivity();
	}

	private void finishThisActivity(){
		logout();
		finish();
	}



	class MyStartPreviewTask extends AsyncTask<Integer,Integer,Integer>{

		@Override
		protected Integer doInBackground(Integer... integers) {
			while (!loginFlag);
			return integers[0];
		}

		@Override
		protected void onPostExecute(Integer integer) {
			super.onPostExecute(integer);
			playView[integer].startPreview(m_iLogID, m_iStartChan + integer);
		}
	}
	class MyLoginAsyncTask extends AsyncTask<String,Integer,Integer>{
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			loginFlag = false;
			m_iLogID=-1;
		}
		@Override
		protected Integer doInBackground(String... strings) {

			return loginNormalDevice();
		}

		@Override
		protected void onPostExecute(Integer integer) {
			super.onPostExecute(integer);
			loginFlag = true;
			m_iLogID = integer;
			System.out.println("m_iLogID=" + m_iLogID);
			if (m_iLogID < 0) {
				Log.e(TAG, "This device logins failed!");
				Toast toast = Toast.makeText(HikvisionActivity.this,"登录失败！",Toast.LENGTH_LONG);
				toast.setGravity(Gravity.CENTER, 0, 0);
				toast.show();
				return;
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
		}
	}
}
