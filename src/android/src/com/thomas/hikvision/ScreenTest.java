package com.thomas.hikvision;

import android.util.Log;

import com.hikvision.netsdk.*;

public class ScreenTest {
	public static String TAG = "ScreenTest";

	void test_ControlScreen(int iUserID)
	{
		NET_DVR_SCREEN_CONTROL_V41 struCtrlControl_V41 = new NET_DVR_SCREEN_CONTROL_V41();
		struCtrlControl_V41.byProtocol = 1;
		struCtrlControl_V41.bySerialNo = 1;
		struCtrlControl_V41.byWallNo = 1;
		struCtrlControl_V41.dwCommand = 1;
		struCtrlControl_V41.struControlParam.byInputSourceType = 0;
		struCtrlControl_V41.struControlParam.byColorType = 1;
		struCtrlControl_V41.struControlParam.byColorScale = 1;
		struCtrlControl_V41.struControlParam.byPosition = 1;
		struCtrlControl_V41.struControlParam.byPositionScale = 1;
		struCtrlControl_V41.struRect.dwHeight = 1920;
		struCtrlControl_V41.struRect.dwWidth = 100;
		struCtrlControl_V41.struRect.dwXCoordinate =0;
		struCtrlControl_V41.struRect.dwYCoordinate = 0;
		if(HCNetSDK.getInstance().NET_DVR_RemoteControl(iUserID,HCNetSDK.getInstance().NET_DVR_CONTROL_SCREEN,struCtrlControl_V41))
		{
			Log.i(TAG,"NET_DVR_CONTROL_SCREEN success!");
		}
		else
		{
			Log.e(TAG,"NET_DVR_CONTROL_SCREEN fail,error code = "+HCNetSDK.getInstance().NET_DVR_GetLastError());
		}
	}

	void test_SignalList(int iUserID)
	{
		NET_DVR_INPUT_SIGNAL_LIST struList = new NET_DVR_INPUT_SIGNAL_LIST();
		if(HCNetSDK.getInstance().NET_DVR_GetInputSignalList_V40(iUserID, 0, struList))
		{
			Log.i(TAG,"NET_DVR_GetInputSignalList_V40 success!"+struList.dwInputSignalNums);
			String string = new String(struList.struSignalList[0].sGroupName);
			for(int i=0;i<32;i++)
			{
				System.out.print(struList.struSignalList[0].sGroupName[i]);
			}
		}
		else
		{
			Log.e(TAG,"NET_DVR_GetInputSignalList_V40 fail,error code = "+HCNetSDK.getInstance().NET_DVR_GetLastError());
		}
	}

	void test_FileInfo(int iUserID)
	{
		NET_DVR_COND_INT condInt = new NET_DVR_COND_INT();
		NET_DVR_SCREEN_FILE_INFO screenFileInfo = new NET_DVR_SCREEN_FILE_INFO();

		condInt.iValue = 4;

		if (!HCNetSDK.getInstance().NET_DVR_GetSTDConfig(iUserID, HCNetSDK.NET_DVR_GET_SCREEN_FILEINFO, condInt, null, screenFileInfo))
		{
			Log.e(TAG,"NET_DVR_GET_SCREEN_FILEINFO" + " err: " + HCNetSDK.getInstance().NET_DVR_GetLastError());
		}
		else
		{
			String filename = new String(screenFileInfo.byFileName);
			Log.i(TAG,"java out filename:"+filename);
			//Log.i(TAG,"Filename;");
			for(int i=0;i<256;i++)
			{
				Log.i(TAG," java fileName:"+screenFileInfo.byFileName[i]);
			}
			Log.i(TAG,"type:"+screenFileInfo.byFileType);
			Log.i(TAG,"byPictureFormat:"+screenFileInfo.byPictureFormat);
		}
		if (!HCNetSDK.getInstance().NET_DVR_SetSTDConfig(iUserID, HCNetSDK.NET_DVR_SET_SCREEN_FILEINFO, condInt, screenFileInfo, null))
		{
			Log.e(TAG,"NET_DVR_SET_SCREEN_FILEINFO" + " err: " + HCNetSDK.getInstance().NET_DVR_GetLastError());
		}
		else
		{
			Log.i(TAG,"NET_DVR_SET_SCREEN_FILEINFO success");
		}
	}

	void test_SerialAbility(int iUserID)
	{
		byte lpInBuf [] = new byte[1024];
		String str = "<ScreenExchangeAbility version=\"2.0\"></ScreenExchangeAbility>";
		for(int i=0;i<str.length();i++)
		{
			lpInBuf[i]=(byte)str.charAt(i);
		}
		byte lpOutBuf[] = new byte[100*1024];

		if(HCNetSDK.getInstance().NET_DVR_GetDeviceAbility(iUserID,HCNetSDK.getInstance().DEVICE_SERIAL_ABILITY,lpInBuf,1024,lpOutBuf,100*1024))
		{
			Log.i(TAG,"NET_DVR_GetDeviceAbility success");
		}
		else
		{
			Log.e(TAG,"NET_DVR_GetDeviceAbility fail,error code = "+HCNetSDK.getInstance().NET_DVR_GetLastError());
		}
	}

	void test_loginCfg(int iUserID)
	{
		String strUrl = "GET /ISAPI/DisplayDev/Auxiliary/ScreenServer/loginCfg\r\n";
		NET_DVR_XML_CONFIG_INPUT struInput = new NET_DVR_XML_CONFIG_INPUT();

		struInput.lpRequestUrl= strUrl.getBytes();

		String strInputString = "<ServerLoginCfg version=\"2.0\" xmlns=\"http://www.std-cgi.org/ver20/XMLSchema\">"+
"<id>1</id><IpAddress><ipVersion >v4</ipVersion><ipAddress>10.17.132.113</ipAddress></IpAddress><portNo>8000</portNo><userName>admin</userName>"+
"<password>12345</password><inputNo>2</inputNo></ServerLoginCfg>";

		struInput.lpInBuffer = strInputString.getBytes();
		//struInput.lpInBuffer = null;

		NET_DVR_XML_CONFIG_OUTPUT struOutput = new NET_DVR_XML_CONFIG_OUTPUT();
		//struOutput.lpOutBuffer = null;
		if(HCNetSDK.getInstance().NET_DVR_STDXMLConfig(iUserID,struInput,struOutput))
		{
			Log.i(TAG,"NET_DVR_STDXMLConfig success");
			Log.i(TAG,"dwReturnXMLSize="+struOutput.dwReturnedXMLSize);
			String string = new String(struOutput.lpOutBuffer);
			string = string.substring(0, struOutput.dwReturnedXMLSize);
			System.out.println("----------lpOutBuffer-----------");
			System.out.println(string);
			System.out.println("----------lpOutBuffer-----------");
		}
		else
		{
			Log.e(TAG,"NET_DVR_STDXMLConfig fail,error code = "+HCNetSDK.getInstance().NET_DVR_GetLastError());

		}
		String string = new String(struOutput.lpStatusBuffer);
		System.out.println("-------------StatusBuffer-------------");
		System.out.println(string);
		System.out.println("--------------StatusBuffer-------------");
	}

	void test_PlayingPlan(int iUserID)
	{
		INT_PTR lpOutPtr = new INT_PTR();
		if(HCNetSDK.getInstance().NET_DVR_GetDVRConfig(iUserID,HCNetSDK.getInstance().NET_DVR_GET_PLAYING_PLAN, 1, lpOutPtr))
		{
			Log.i(TAG,"NET_DVR_GET_PLAYING_PLAN success");
		}
		else {
			Log.e(TAG,"NET_DVR_GET_PLAYING_PLAN fail,error code ="+HCNetSDK.getInstance().NET_DVR_GetLastError());
		}
	}

	void test_CtrlPlan(int iUserID)
	{
		NET_DVR_CONTROL_PARAM struCtrlParam = new NET_DVR_CONTROL_PARAM();
		struCtrlParam.byIndex = 1;
		struCtrlParam.dwControlParam = 2;
		struCtrlParam.sDeviceID[0]='a';
		struCtrlParam.sDeviceID[1]='b';
		if(HCNetSDK.getInstance().NET_DVR_RemoteControl(iUserID, HCNetSDK.getInstance().NET_DVR_CTRL_PLAN, struCtrlParam))
		{
			Log.i(TAG,"NET_DVR_CTRL_PLAN success");
		}
		else {
			Log.e(TAG,"NET_DVR_CTRL_PLAN fail,error code ="+HCNetSDK.getInstance().NET_DVR_GetLastError());
		}
	}

	void test_Plan(int iUserID)
	{
		NET_DVR_PLAN_CFG struPlanCfg= new NET_DVR_PLAN_CFG();
			struPlanCfg.byValid = 1;
			struPlanCfg.byWallNo = 1;
			struPlanCfg.byWorkMode = 1;
			struPlanCfg.byPlanName[0]='a';
			struPlanCfg.byPlanName[1]='b';
			struPlanCfg.byPlanName[2]='c';
			NET_DVR_TIME_EX struTimeEx = new NET_DVR_TIME_EX();
			struTimeEx.wYear = 2015;
			struTimeEx.byMonth = 10;
			struTimeEx.byDay = 17;
			struTimeEx.byHour =7;
			struTimeEx.byMinute = 30;
			struTimeEx.bySecond =1;
			struPlanCfg.struTime = struTimeEx;
			NET_DVR_CYCLE_TIME []pCycleTime = new NET_DVR_CYCLE_TIME[7];
			for(int i=0;i<7;i++)
			{
				pCycleTime[i]=new NET_DVR_CYCLE_TIME();
				pCycleTime[i].byValid = 1;
				pCycleTime[i].struTime = struTimeEx;
			}
			struPlanCfg.dwWorkCount = 3;
			struPlanCfg.struTimeCycle = pCycleTime;
			NET_DVR_PLAN_INFO []lpPlanInfo = new NET_DVR_PLAN_INFO[32];
			for(int i=0;i<32;i++)
			{
				lpPlanInfo[i] = new NET_DVR_PLAN_INFO();
				lpPlanInfo[i].byValid = 1;
				lpPlanInfo[i].byType = 1;
				lpPlanInfo[i].wLayoutNo =1;
				lpPlanInfo[i].byScreenStyle =1;
				lpPlanInfo[i].dwDelayTime = 20;
			}

			struPlanCfg.strPlanEntry = lpPlanInfo;
			if(HCNetSDK.getInstance().NET_DVR_SetDVRConfig(iUserID, HCNetSDK.getInstance().NET_DVR_SET_PLAN, 1,struPlanCfg))
			{
				Log.i(TAG,"NET_DVR_SET_PLAN success");
			}
			else {
			Log.e(TAG,"NET_DVR_SET_PLAN fail,error code ="+HCNetSDK.getInstance().NET_DVR_GetLastError());
		}
	}

	void test_VWParam(int iUserID)
	{
		int dwCount = 1 ;
			NET_DVR_VIDEO_WALL_INFO [] pstruWallInfo = new NET_DVR_VIDEO_WALL_INFO[1];
			pstruWallInfo[0]= new NET_DVR_VIDEO_WALL_INFO();
			//pstruWallInfo[0].dwWindowNo = 0x01010001;
			pstruWallInfo[0].dwWindowNo = 1<<24;
			pstruWallInfo[0].dwSceneNo = 1;
			NET_DVR_WALLSCENECFG []lpOut1 = new NET_DVR_WALLSCENECFG[1];
			for(int i=0;i<1;i++)
			{
			lpOut1[i]= new NET_DVR_WALLSCENECFG();
			}
			int []arrStatus1 = new int[1];
			INT_PTR intPtr1 = new INT_PTR();

			if(HCNetSDK.getInstance().NET_DVR_GetDeviceConfig(iUserID, HCNetSDK.getInstance().NET_DVR_GET_VW_SCENE_PARAM, dwCount, arrStatus1,pstruWallInfo,lpOut1,intPtr1))
			{
				Log.i(TAG,"NET_DVR_GET_VW_SCENE_PARAM success");
				Log.i(TAG,"intPtr.iValue="+intPtr1.iValue);
			}
			else {
			Log.e(TAG,"NET_DVR_GET_VW_SCENE_PARAM fail,error code ="+HCNetSDK.getInstance().NET_DVR_GetLastError());
		}
			dwCount = 1;
			int[] arrStatus = new int[1];

			if(HCNetSDK.getInstance().NET_DVR_SetDeviceConfig(iUserID, HCNetSDK.getInstance().NET_DVR_SET_VW_SCENE_PARAM, dwCount,pstruWallInfo, arrStatus,lpOut1))
			{
				Log.i(TAG,"NET_DVR_SET_VW_SCENE_PARAM success");
			}
			else {
			Log.e(TAG,"NET_DVR_SET_VW_SCENE_PARAM fail,error code ="+HCNetSDK.getInstance().NET_DVR_GetLastError());
		}

			int videoWallNo = 1;
			int sceneNo = 1;
	        NET_DVR_VIDEO_WALL_INFO[] wallInfo = new NET_DVR_VIDEO_WALL_INFO[1];
	        wallInfo[0] = new NET_DVR_VIDEO_WALL_INFO();
	        wallInfo[0].dwWindowNo = videoWallNo << 24;
	        wallInfo[0].dwSceneNo = sceneNo;

	        int[] status = new int[1];
	        status[0]=0;

	        NET_DVR_WALLSCENECFG[] sceneCfgArray = new NET_DVR_WALLSCENECFG[1];
	        sceneCfgArray[0] = new NET_DVR_WALLSCENECFG();

	        INT_PTR outLenPtr = new INT_PTR();

	        if (!HCNetSDK.getInstance().NET_DVR_GetDeviceConfig(iUserID, HCNetSDK.NET_DVR_GET_VW_SCENE_PARAM, 1,
	                status, wallInfo, sceneCfgArray, outLenPtr))
	        {
	        	Log.e(TAG,"NET_DVR_GET_VW_SCENE_PARAM fail,error code ="+HCNetSDK.getInstance().NET_DVR_GetLastError());
	            return ;
	        }
	        else {

	  				Log.i(TAG,"NET_DVR_GET_VW_SCENE_PARAM success");

		}
	}

	void test_CurrentScene(int iUserID)
	{
		NET_DVR_VIDEO_WALL_INFO  struWallInfo1 = new NET_DVR_VIDEO_WALL_INFO();
			struWallInfo1.dwWindowNo = 0x01000001;
			struWallInfo1.dwSceneNo = 1;
			INT_PTR lpOut = new INT_PTR();
			if(HCNetSDK.getInstance().NET_DVR_GetDeviceConfig(iUserID,HCNetSDK.NET_DVR_GET_CURRENT_SCENE,struWallInfo1,lpOut))
			{
				Log.i(TAG,"NET_DVR_GET_CURRENT_SCENE success!");
			}
			else {
			Log.e(TAG,"NET_DVR_GET_CURRENT_SCENE fail,error code="+HCNetSDK.getInstance().NET_DVR_GetLastError());
		}
	}

	void test_SceneControl(int iUserID)
	{
		NET_DVR_SCENE_CONTROL_INFO struCtrlInfo = new NET_DVR_SCENE_CONTROL_INFO();
			NET_DVR_VIDEO_WALL_INFO struWallInfo = new NET_DVR_VIDEO_WALL_INFO();
			struWallInfo.dwWindowNo = 0x01000001;
			struWallInfo.dwSceneNo = 1;
			struCtrlInfo.struVideoWallInfo = struWallInfo;
			struCtrlInfo.dwCmd = 2;
			if(HCNetSDK.getInstance().NET_DVR_RemoteControl(iUserID, HCNetSDK.getInstance().NET_DVR_SCENE_CONTROL, struCtrlInfo))
			{
				Log.i(TAG,"NET_DVR_SCENE_CONTROL success!");
			}
			else {
			Log.e(TAG,"NET_DVR_SCENE_CONTROL fail,error code="+HCNetSDK.getInstance().NET_DVR_GetLastError());
		}
	}

	void test_DecChanEnable(int iUserID)
	{
		int dwDecChanNum = 0x01010001;
			INT_PTR lpEnable = new INT_PTR();
			lpEnable.iValue = 1;

			if(HCNetSDK.getInstance().NET_DVR_MatrixGetDecChanEnable(iUserID,dwDecChanNum,lpEnable))
			{
				Log.i(TAG,"lpEnalbe="+lpEnable.iValue);
				Log.i(TAG,"NET_DVR_MatrixGetDecChanEnable success!");
			}
			else {
				Log.e(TAG,"NET_DVR_MatrixGetDecChanEnable  fail,error code ="+HCNetSDK.getInstance().NET_DVR_GetLastError());
		}
			lpEnable.iValue = 1;
			if(HCNetSDK.getInstance().NET_DVR_MaxtrixSetDecChanEnable(iUserID,dwDecChanNum,lpEnable.iValue))
			{
				Log.i(TAG,"NET_DVR_MaxtrixSetDecChanEnable success!");
			}
			else {
				Log.e(TAG,"NET_DVR_MaxtrixSetDecChanEnable  fail,error code ="+HCNetSDK.getInstance().NET_DVR_GetLastError());
		}

	}

	void test_SwitchWin(int iUserID)
	{
		INT_PTR lpWallNo = new INT_PTR();
			lpWallNo.iValue = 0x01010001;

			if(HCNetSDK.getInstance().NET_DVR_RemoteControl(iUserID,HCNetSDK.getInstance().NET_DVR_SWITCH_WIN_TOP,lpWallNo))
			{
				Log.i(TAG,"NET_DVR_SWITCH_WIN_TOP success!");
			}
			else {
				Log.e(TAG,"NET_DVR_SWITCH_WIN_TOP  fail,error code ="+HCNetSDK.getInstance().NET_DVR_GetLastError());
		}
			if(HCNetSDK.getInstance().NET_DVR_RemoteControl(iUserID,HCNetSDK.getInstance().NET_DVR_SWITCH_WIN_BOTTOM,lpWallNo))
			{
				Log.i(TAG,"NET_DVR_SWITCH_WIN_BOTTOM success!");
			}
			else {
				Log.e(TAG,"NET_DVR_SWITCH_WIN_BOTTOM  fail,error code ="+HCNetSDK.getInstance().NET_DVR_GetLastError());
		}
	}

	void test_WallInParam(int iUserID)
	{
		int iChannel = 0x01010001;
			NET_DVR_WALLWINPARAM struWallParam = new NET_DVR_WALLWINPARAM();
			if(HCNetSDK.getInstance().NET_DVR_GetDVRConfig(iUserID, HCNetSDK.getInstance().NET_DVR_WALLWINPARAM_GET, iChannel, struWallParam))
			{
				Log.i(TAG,"NET_DVR_WALLWINPARAM_GET success!");
			}
			else {
			Log.e(TAG,"NET_DVR_WALLWINPARAM_GET  fail,error code ="+HCNetSDK.getInstance().NET_DVR_GetLastError());
		}
			struWallParam.byWinMode = 4;
			if(HCNetSDK.getInstance().NET_DVR_SetDVRConfig(iUserID,HCNetSDK.getInstance().NET_DVR_WALLWINPARAM_SET,iChannel,struWallParam))
			{
				Log.i(TAG,"NET_DVR_WALLWINPARAM_SET success!");
			}
			else {
			Log.e(TAG,"NET_DVR_WALLWINPARAM_SET  fail,error code ="+HCNetSDK.getInstance().NET_DVR_GetLastError());
		}
	}

	void test_CloseAll(int iUserID)
	{
		INT_PTR lpNum = new INT_PTR();
			lpNum.iValue = 0x01000001;
			boolean bRet = HCNetSDK.getInstance().NET_DVR_RemoteControl(iUserID,HCNetSDK.getInstance().NET_DVR_VIDEOWALLWINDOW_CLOSEALL,lpNum);
			if(bRet)
			{
				Log.i(TAG,"NET_DVR_VIDEOWALLWINDOW_CLOSEALL success!");
			}
			else {
			Log.e(TAG,"NET_DVR_VIDEOWALLWINDOW_CLOSEALL  fail,error code ="+HCNetSDK.getInstance().NET_DVR_GetLastError());
		}
	}

	void test_Position(int iUserID)
	{
		int dwCount = 0xffffffff;
			COND_INT_PTR [] arrWallNo = new COND_INT_PTR[1];
			arrWallNo[0] = new COND_INT_PTR();
			arrWallNo[0].iValue=0x01000001;

			int [] arrStatus = new int[64];
			NET_DVR_VIDEOWALLWINDOWPOSITION [] arrV= new NET_DVR_VIDEOWALLWINDOWPOSITION[64];
			for(int i=0;i<64;i++)
			{
	  			arrV[i]=new NET_DVR_VIDEOWALLWINDOWPOSITION();
			}

			int [] arrOut = new int[64];
			INT_PTR nRet = new INT_PTR();

			boolean bRet = HCNetSDK.getInstance().NET_DVR_GetDeviceConfig(iUserID, HCNetSDK.getInstance().NET_DVR_GET_VIDEOWALLWINDOWPOSITION,dwCount, null, arrWallNo, arrV,nRet);
			if(bRet)
			{
				Log.i(TAG,"NET_DVR_GET_VIDEOWALLWINDOWPOSITION success!");
				Log.i(TAG,"nRet ="+nRet.iValue);
				System.out.println("arrV[0].byWndOperateMode="+arrV[0].byWndOperateMode);
				System.out.println("arrV[0].struRect="+arrV[0].struRect.dwHeight+","+arrV[0].struRect.dwWidth+","+arrV[0].struRect.dwXCoordinate+","+arrV[0].struRect.dwYCoordinate);
				System.out.println("arrV[0].struResolution="+arrV[0].struResolution.dwHeight+","+arrV[0].struResolution.dwWidth+","+arrV[0].struResolution.dwXCoordinate+","+arrV[0].struResolution.dwYCoordinate);
			}
			else {
			Log.e(TAG,"NET_DVR_GET_VIDEOWALLWINDOWPOSITION fail,error code ="+HCNetSDK.getInstance().NET_DVR_GetLastError());
		}


			dwCount = 1;
			arrV[0].byEnable = 0;
			arrV[0].byWndOperateMode = 1;
			arrV[0].struResolution.dwHeight = 2000;
			arrV[0].struResolution.dwWidth =2000;
			arrV[0].struResolution.dwXCoordinate=1;
			arrV[0].struResolution.dwYCoordinate = 1;
			 bRet = HCNetSDK.getInstance().NET_DVR_SetDeviceConfigEx(iUserID, HCNetSDK.getInstance().NET_DVR_SET_VIDEOWALLWINDOWPOSITION, dwCount, arrWallNo, arrV, arrStatus, 0,arrOut);
			if(bRet)
			{
				Log.i(TAG,"NET_DVR_SET_VIDEOWALLWINDOWPOSITION success!");
			}
			else {
			Log.e(TAG,"NET_DVR_SET_VIDEOWALLWINDOWPOSITION  fail,error code ="+HCNetSDK.getInstance().NET_DVR_GetLastError());
		}

	}

	void test_DisplayPosition(int iUserID)
	{
		int dwCount = 1;
			COND_INT_PTR [] arrWallNo = new COND_INT_PTR[1];
			arrWallNo[0] = new COND_INT_PTR();
			arrWallNo[0].iValue=0x01010001;

			int [] arrStatus = new int[1];
			NET_DVR_VIDEOWALLDISPLAYPOSITION [] arrV= new NET_DVR_VIDEOWALLDISPLAYPOSITION[1];
			for(int i=0;i<1;i++)
			{
	  			arrV[i]=new NET_DVR_VIDEOWALLDISPLAYPOSITION();
			}

			int [] arrOut = new int[1];
			INT_PTR nRet = new INT_PTR();
			boolean bRet = HCNetSDK.getInstance().NET_DVR_GetDeviceConfig(iUserID, HCNetSDK.getInstance().NET_DVR_GET_VIDEOWALLDISPLAYPOSITION,dwCount, arrStatus, arrWallNo, arrV,nRet);
			if(bRet)
			{
				Log.i(TAG,"NET_DVR_GET_VIDEOWALLDISPLAYPOSITION success!");
				Log.i(TAG,"nRet="+nRet.iValue);
			}
			else {
			Log.e(TAG,"NET_DVR_GET_VIDEOWALLDISPLAYPOSITION fail,error code ="+HCNetSDK.getInstance().NET_DVR_GetLastError());
		}


			dwCount = 1;

			//鐠佸墽鐤嗛悽浣冾潒婢ф瑧鐛ラ崣锝呭棘閺侊拷
			bRet = HCNetSDK.getInstance().NET_DVR_SetDeviceConfig(iUserID, HCNetSDK.getInstance().NET_DVR_SET_VIDEOWALLDISPLAYPOSITION, dwCount,arrWallNo, arrStatus,arrV);
			if(bRet)
			{
				Log.i(TAG,"NET_DVR_SET_VIDEOWALLDISPLAYPOSITION success!");
			}
			else {
			Log.e(TAG,"NET_DVR_SET_VIDEOWALLDISPLAYPOSITION  fail,error code ="+HCNetSDK.getInstance().NET_DVR_GetLastError());
		}

	}

	void test_WallOutput(int iUserID)
	{
		int dwCount = 1;
			Log.i(TAG,"dwCount ="+dwCount);
			COND_INT_PTR [] arrWallNo = new COND_INT_PTR[2];
			arrWallNo[0] = new COND_INT_PTR();
			arrWallNo[0].iValue=0x01080001;
			arrWallNo[1] = new COND_INT_PTR();
			arrWallNo[1].iValue=0x01080002;


			int [] arrStatus = new int[2];
			NET_DVR_WALLOUTPUTPARAM [] arrV= new NET_DVR_WALLOUTPUTPARAM[2];
			for(int i=0;i<2;i++)
			{
	  			arrV[i]=new NET_DVR_WALLOUTPUTPARAM();
			}

			int [] arrOut = new int[2];
			INT_PTR nRet = new INT_PTR();
			boolean bRet = HCNetSDK.getInstance().NET_DVR_GetDeviceConfig(iUserID, HCNetSDK.getInstance().NET_DVR_WALLOUTPUT_GET,dwCount, arrStatus, arrWallNo, arrV,nRet);
			if(bRet)
			{
				Log.i(TAG,"NET_DVR_WALLOUTPUT_GET success!");
				Log.i(TAG,"nRet="+nRet.iValue);
			}
			else {
			Log.e(TAG,"NET_DVR_WALLOUTPUT_GET fail,error code ="+HCNetSDK.getInstance().NET_DVR_GetLastError());
		}


			bRet = HCNetSDK.getInstance().NET_DVR_SetDeviceConfig(iUserID, HCNetSDK.getInstance().NET_DVR_WALLOUTPUT_SET, dwCount,arrWallNo, arrStatus,arrV);
			if(bRet)
			{
				Log.i(TAG,"NET_DVR_WALLOUTPUT_SET success!");
			}
			else {
			Log.e(TAG,"NET_DVR_WALLOUTPUT_SET  fail,error code ="+HCNetSDK.getInstance().NET_DVR_GetLastError());
		}
	}

	void test_PlanList(int iUserID)
	{
		int dwDevNum = 0;
			NET_DVR_PLAN_LIST planList = new NET_DVR_PLAN_LIST();

			boolean bRet = HCNetSDK.getInstance().NET_DVR_GetPlanList(iUserID, dwDevNum,planList);
			if(bRet)
			{
				Log.i(TAG,"NET_DVR_GetPlanList success!");
				Log.i(TAG,"planList.dwPlanNums="+planList.dwPlanNums);
				for(int i=0;i<planList.dwPlanNums;i++)
				{
					System.out.println("planList.struPlanCfg["+i+"]"+planList.struPlanCfg[i].byPlanNo);
					String strName = new String(planList.struPlanCfg[i].byPlanName);
					System.out.println("name="+strName);
				}

			}
			else {
			Log.e(TAG,"NET_DVR_GetPlanList fail,error code ="+HCNetSDK.getInstance().NET_DVR_GetLastError());
		}
	}

	/**
     * @fn     getRemoteConfigCbf
     * @author huyongxu
     * @brief  get remote configuration callback instance
     * @param  NULL [in]
     * @param  NULL [out]
     * @return callback instance
     */
	private RemoteConfigCallback getRemoteConfigCbf()
	{
		RemoteConfigCallback cbf = new RemoteConfigCallback()
        {
             public void fRemoteConfigCallback(int dwType, NET_DVR_CONFIG oConfig, byte[] pUserData)
             {
            	processRemoteConfigData(dwType, oConfig, pUserData);
             }
        };
        return cbf;
	}

	public void processRemoteConfigData(int dwType, NET_DVR_CONFIG oConfig, byte[] pUserData)
	{
		if(oConfig == null)
		{
			return;
		}
		switch(dwType)
		{
		case NET_SDK_CALLBACK_TYPE.NET_SDK_CALLBACK_TYPE_DATA:
			NET_DVR_SCREEN_RESPONSE_CMD oScreenRespone = (NET_DVR_SCREEN_RESPONSE_CMD)oConfig;
			if(oScreenRespone.byResponseCmd == 1 && oScreenRespone.struResonseParam.struPPTParam.byCurrentState == 9)
			{
				Log.i(TAG,"Response parameter callback succeed");
			}

			break;
		default:
			break;
		}
	}

	void test_ScreenCtrl(int iUserID)
	{
		RemoteConfigCallback fRemoteConfigCallback = getRemoteConfigCbf();
			int lHandle = HCNetSDK.getInstance().NET_DVR_StartRemoteConfig(iUserID, HCNetSDK.NET_DVR_START_SCREEN_CRTL, null, fRemoteConfigCallback, null);
			if(lHandle < 0)
			{
				Log.e(TAG,"NET_DVR_StartRemoteConfig NET_DVR_START_SCREEN_CRTL failed"+HCNetSDK.getInstance().NET_DVR_GetLastError());
			return;
			}
			Log.i(TAG,"NET_DVR_StartRemoteConfig NET_DVR_START_SCREEN_CRTL succeed");
			try {
			Thread.sleep(100);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


			NET_DVR_SCREEN_CTRL_CMD struCmd = new NET_DVR_SCREEN_CTRL_CMD();

			/*struCmd.byCmdType = 8;
			NET_DVR_MEDIA_LIST_PARAM struMedia = new NET_DVR_MEDIA_LIST_PARAM();
			struMedia.byOperateCmd = 1;
			struCmd.struScreenCtrlParam.struMediaListParam = struMedia;
			if(!HCNetSDK.getInstance().NET_DVR_SendRemoteConfig((int)lHandle, LONG_CFG_SEND_DATA_TYPE_ENUM.ENUM_DVR_SCREEN_CTRL_CMD, struCmd))
			{
				Log.e(TAG,"NET_DVR_SendRemoteConfig NET_DVR_START_SCREEN_CRTL failed");
				return;
			}
			Log.i(TAG,"NET_DVR_SendRemoteConfig NET_DVR_START_SCREEN_CRTL succeed");
			try {
			Thread.sleep(100);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/

			struCmd.byCmdType = 5;
			NET_DVR_REMOTE_CTRL_PARAM struRemoteCtrl = new NET_DVR_REMOTE_CTRL_PARAM();
			struRemoteCtrl.byRemoteCtrlCmd = 3;
			struRemoteCtrl.dwCtrlParam = 1;
			struCmd.struScreenCtrlParam.struRemoteCtrlParam = struRemoteCtrl;
			if(!HCNetSDK.getInstance().NET_DVR_SendRemoteConfig((int)lHandle, LONG_CFG_SEND_DATA_TYPE_ENUM.ENUM_DVR_SCREEN_CTRL_CMD, struCmd))
			{
				Log.e(TAG,"NET_DVR_SendRemoteConfig NET_DVR_START_SCREEN_CRTL failed");
				return;
			}
			Log.i(TAG,"NET_DVR_SendRemoteConfig NET_DVR_START_SCREEN_CRTL succeed");
			try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

			struCmd.byCmdType = 4;
			NET_DVR_PPT_PARAM struPPT = new NET_DVR_PPT_PARAM();
			struPPT.byPPTAction = 1;
			struCmd.struScreenCtrlParam.struPPTParam = struPPT;
			if(!HCNetSDK.getInstance().NET_DVR_SendRemoteConfig((int)lHandle, LONG_CFG_SEND_DATA_TYPE_ENUM.ENUM_DVR_SCREEN_CTRL_CMD, struCmd))
			{
				Log.e(TAG,"NET_DVR_SendRemoteConfig NET_DVR_START_SCREEN_CRTL failed");
				return;
			}
			Log.i(TAG,"NET_DVR_SendRemoteConfig NET_DVR_START_SCREEN_CRTL succeed");
			try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

			HCNetSDK.getInstance().NET_DVR_StopRemoteConfig(lHandle);

			/*struCmd.byCmdType = 4;
			//NET_DVR_PPT_PARAM struPPT = new NET_DVR_PPT_PARAM();
			struPPT.byPPTAction = 6;
			struCmd.struScreenCtrlParam.struPPTParam = struPPT;
			if(!HCNetSDK.getInstance().NET_DVR_SendRemoteConfig((int)lHandle, LONG_CFG_SEND_DATA_TYPE_ENUM.ENUM_DVR_SCREEN_CTRL_CMD, struCmd))
			{
				Log.e(TAG,"NET_DVR_SendRemoteConfig NET_DVR_START_SCREEN_CRTL failed");
				return;
			}
			Log.i(TAG,"NET_DVR_SendRemoteConfig NET_DVR_START_SCREEN_CRTL succeed");
			try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/

			/*struCmd.byCmdType = 4;
			//NET_DVR_PPT_PARAM struPPT = new NET_DVR_PPT_PARAM();
			struPPT.byPPTAction = 2;
			struCmd.struScreenCtrlParam.struPPTParam = struPPT;
			if(!HCNetSDK.getInstance().NET_DVR_SendRemoteConfig((int)lHandle, LONG_CFG_SEND_DATA_TYPE_ENUM.ENUM_DVR_SCREEN_CTRL_CMD, struCmd))
			{
				Log.e(TAG,"NET_DVR_SendRemoteConfig NET_DVR_START_SCREEN_CRTL failed");
				return;
			}
			Log.i(TAG,"NET_DVR_SendRemoteConfig NET_DVR_START_SCREEN_CRTL succeed");
			try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/

			/*INT_PTR pState = new INT_PTR();
			if(!HCNetSDK.getInstance().NET_DVR_GetRemoteConfigState((int)lHandle, pState))
			{
				Log.e(TAG,"NET_DVR_GetRemoteConfigState NET_DVR_START_SCREEN_CRTL failed");
			return;
			}
			Log.i(TAG,"NET_DVR_GetRemoteConfigState NET_DVR_START_SCREEN_CRTL succeed");
			try {
			Thread.sleep(100);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

			struCmd.byCmdType = 7;
			NET_DVR_TOUCHPAD_PARAM struTouch = new NET_DVR_TOUCHPAD_PARAM();
			struTouch.byMouseEvent = 1;
			struTouch.iXDisplacement = 100;
			struTouch.iYDisplacement = 100;
			struCmd.struScreenCtrlParam.struTouchPadParam = struTouch;
			if(!HCNetSDK.getInstance().NET_DVR_SendRemoteConfig((int)lHandle, LONG_CFG_SEND_DATA_TYPE_ENUM.ENUM_DVR_SCREEN_CTRL_CMD, struCmd))
			{
				Log.e(TAG,"NET_DVR_SendRemoteConfig NET_DVR_START_SCREEN_CRTL failed");
				return;
			}
			Log.i(TAG,"NET_DVR_SendRemoteConfig NET_DVR_START_SCREEN_CRTL succeed");
			try {
			Thread.sleep(100);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

			struCmd.byCmdType = 6;
			NET_DVR_SPOTLIGHT_PARAM struSpot = new NET_DVR_SPOTLIGHT_PARAM();
			struSpot.byCmd = 1;
			struSpot.dwRadius = 1;
			NET_DVR_SCREEN_POINT struPoint = new NET_DVR_SCREEN_POINT();
			struPoint.wX = 200;
			struPoint.wY = 200;
			struSpot.struPoint = struPoint;
			struCmd.struScreenCtrlParam.struSpotLight= struSpot;
			if(!HCNetSDK.getInstance().NET_DVR_SendRemoteConfig((int)lHandle, LONG_CFG_SEND_DATA_TYPE_ENUM.ENUM_DVR_SCREEN_CTRL_CMD, struCmd))
			{
				Log.e(TAG,"NET_DVR_SendRemoteConfig NET_DVR_START_SCREEN_CRTL failed");
				return;
			}
			Log.i(TAG,"NET_DVR_SendRemoteConfig NET_DVR_START_SCREEN_CRTL succeed");
			try {
			Thread.sleep(100);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

			struCmd.byCmdType = 5;
			NET_DVR_REMOTE_CTRL_PARAM struRemote = new NET_DVR_REMOTE_CTRL_PARAM();
			struRemote.byRemoteCtrlCmd = 1;
			struCmd.struScreenCtrlParam.struRemoteCtrlParam = struRemote;
			if(!HCNetSDK.getInstance().NET_DVR_SendRemoteConfig((int)lHandle, LONG_CFG_SEND_DATA_TYPE_ENUM.ENUM_DVR_SCREEN_CTRL_CMD, struCmd))
			{
				Log.e(TAG,"NET_DVR_SendRemoteConfig NET_DVR_START_SCREEN_CRTL failed");
				return;
			}
			Log.i(TAG,"NET_DVR_SendRemoteConfig NET_DVR_START_SCREEN_CRTL succeed");
			try {
			Thread.sleep(100);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


			struCmd.byCmdType = 3;
			NET_DVR_KEYBOARD_PARAM struKey = new NET_DVR_KEYBOARD_PARAM();
			struKey.dwKeyValue = 96;
			struCmd.struScreenCtrlParam.struKeyboardInfo = struKey;
			if(!HCNetSDK.getInstance().NET_DVR_SendRemoteConfig((int)lHandle, LONG_CFG_SEND_DATA_TYPE_ENUM.ENUM_DVR_SCREEN_CTRL_CMD, struCmd))
			{
				Log.e(TAG,"NET_DVR_SendRemoteConfig NET_DVR_START_SCREEN_CRTL failed");
				return;
			}
			Log.i(TAG,"NET_DVR_SendRemoteConfig NET_DVR_START_SCREEN_CRTL succeed");
			try {
			Thread.sleep(100);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

			struCmd.byCmdType = 2;
			NET_DVR_MARK_PARAM struMark = new NET_DVR_MARK_PARAM();
			struMark.byMarkEvent = 1;
			struPoint = new NET_DVR_SCREEN_POINT();
			struPoint.wX = 200;
			struPoint.wY = 200;
			struMark.struPoint = struPoint;
			NET_DVR_RGB_COLOR struColor = new NET_DVR_RGB_COLOR();
			struColor.byBlue = 100;
			struColor.byGreen = 100;
			struColor.byRed = 100;
			struMark.struColor = struColor;
			struCmd.struScreenCtrlParam.struMarkParam = struMark;
			if(!HCNetSDK.getInstance().NET_DVR_SendRemoteConfig((int)lHandle, LONG_CFG_SEND_DATA_TYPE_ENUM.ENUM_DVR_SCREEN_CTRL_CMD, struCmd))
			{
				Log.e(TAG,"NET_DVR_SendRemoteConfig NET_DVR_START_SCREEN_CRTL failed");
				return;
			}
			Log.i(TAG,"NET_DVR_SendRemoteConfig NET_DVR_START_SCREEN_CRTL succeed");
			try {
			Thread.sleep(100);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

			struCmd.byCmdType = 1;
			NET_DVR_MOUSE_PARAM  struMouse = new NET_DVR_MOUSE_PARAM ();
		    struPoint = new NET_DVR_SCREEN_POINT();
			struPoint.wX = 200;
			struPoint.wY = 200;
			struMouse.struMousePoint = struPoint;
			struMouse.byMouseEvent = 1;
			struCmd.struScreenCtrlParam.struMouseParam = struMouse;

			if(!HCNetSDK.getInstance().NET_DVR_SendRemoteConfig((int)lHandle, LONG_CFG_SEND_DATA_TYPE_ENUM.ENUM_DVR_SCREEN_CTRL_CMD, struCmd))
			{
				Log.e(TAG,"NET_DVR_SendRemoteConfig NET_DVR_START_SCREEN_CRTL failed");
				return;
			}
			Log.i(TAG,"NET_DVR_SendRemoteConfig NET_DVR_START_SCREEN_CRTL succeed");

			struCmd.byCmdType = 2;

			struMark.byMarkEvent = 1;

			struPoint.wX = 200;
			struPoint.wY = 200;
			struMark.struPoint = struPoint;

			struColor.byBlue = 100;
			struColor.byGreen = 100;
			struColor.byRed = 100;
			struMark.struColor = struColor;
			struCmd.struScreenCtrlParam.struMarkParam = struMark;
			if(!HCNetSDK.getInstance().NET_DVR_SendRemoteConfig((int)lHandle, LONG_CFG_SEND_DATA_TYPE_ENUM.ENUM_DVR_SCREEN_CTRL_CMD, struCmd))
			{
				Log.e(TAG,"NET_DVR_SendRemoteConfig NET_DVR_START_SCREEN_CRTL failed");
				return;
			}
			Log.i(TAG,"NET_DVR_SendRemoteConfig NET_DVR_START_SCREEN_CRTL succeed");
			try {
			Thread.sleep(100);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

			struCmd.byCmdType = 1;
		    struPoint = new NET_DVR_SCREEN_POINT();
			struPoint.wX = 200;
			struPoint.wY = 200;
			struMouse.struMousePoint = struPoint;
			struMouse.byMouseEvent = 1;
			struCmd.struScreenCtrlParam.struMouseParam = struMouse;

			if(!HCNetSDK.getInstance().NET_DVR_SendRemoteConfig((int)lHandle, LONG_CFG_SEND_DATA_TYPE_ENUM.ENUM_DVR_SCREEN_CTRL_CMD, struCmd))
			{
				Log.e(TAG,"NET_DVR_SendRemoteConfig NET_DVR_START_SCREEN_CRTL failed");
				return;
			}
			Log.i(TAG,"NET_DVR_SendRemoteConfig NET_DVR_START_SCREEN_CRTL succeed");

			if(!HCNetSDK.getInstance().NET_DVR_StopRemoteConfig((int)lHandle))
			{
				Log.e(TAG,"NET_DVR_StopRemoteConfig NET_DVR_START_SCREEN_CRTL failed");
			return;
			}
			Log.i(TAG,"NET_DVR_StopRemoteConfig NET_DVR_START_SCREEN_CRTL succeed");*/

	}

	void test_UploadFile(int iUserID)
	{
		NET_DVR_SCREEM_FILE_UPLOAD_PARAM uploadParam = new NET_DVR_SCREEM_FILE_UPLOAD_PARAM();
			uploadParam.byFileType = 1;
			uploadParam.byPictureFormat = 2;
			String str = "test_54815.png";
			System.arraycopy(str.getBytes(), 0, uploadParam.byFileName, 0, str.length());
			///mnt/sdcard/Pictures/Screenshots/Screenshot_2015-10-12-21-27-05.png
			String strFileName = "/mnt/sdcard/test_54815.png";
			int dwUploadType =NET_SDK_UPLOAD_TYPE.UPLOAD_SCREEN_FILE;
			NET_DVR_SCREEN_UPLOAD_RET ret =new NET_DVR_SCREEN_UPLOAD_RET();

			int lHandle = HCNetSDK.getInstance().NET_DVR_UploadFile_V40(iUserID, dwUploadType, uploadParam, strFileName, ret);
			if (lHandle < 0)
			{
				Log.e(TAG, "NET_DVR_UploadFile_V40 UPLOAD_SCREEN_FILE is failed!Err:"+HCNetSDK.getInstance().NET_DVR_GetLastError());
				return;
			}
			else
			{
				Log.i(TAG, "NET_DVR_UploadFile_V40 UPLOAD_SCREEN_FILE Success!");
			}

			int iLoop = 1;
			while(iLoop == 1)
			{
				INT_PTR press = new INT_PTR();
				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				int iRet = HCNetSDK.getInstance().NET_DVR_GetUploadState((int) lHandle,press);
				if(iRet < 0)
				{
					Log.e(TAG, "NET_DVR_GetUploadState error!");
				}
				else
				{
					Log.i(TAG, "NET_DVR_GetUploadState Success!");
				}
				if(iRet == 1)
				{
					break;
				}
				Log.i(TAG, "upload progress:"+press.iValue+"%");
				if(press.iValue == 100)
				{
					boolean bRet = HCNetSDK.getInstance().NET_DVR_UploadClose((int) lHandle);
					if(bRet)
					{
						Log.e(TAG, "NET_DVR_StopDownload Success!");
					}
					else
					{
						Log.i(TAG, "NET_DVR_StopDownload error!");
					}
					break;
				}
			}
	}

	void test_Download(int iUserID)
	{
		NET_DVR_SCREEM_FILE_DOWNLOAD_PARAM downloadParam = new NET_DVR_SCREEM_FILE_DOWNLOAD_PARAM();
		downloadParam.dwFileIndex = 4;

		int dwDownloadType = NET_SDK_DOWNLOAD_TYPE.NET_DVR_DOWNLOAD_SCREEN_FILE;
		int dwInBufferSize = 113602;
		String sFileName = "/mnt/sdcard/ctrip.android.view/Share/test.bmp";
		System.out.println("m_iLogID=" + iUserID);
		System.out.println("dwDownloadType=" + dwDownloadType);
		System.out.println("downloadParam=" + downloadParam);
		System.out.println("dwInBufferSize=" + dwInBufferSize);
		System.out.println("sFileName=" + sFileName);

		int lHandle = HCNetSDK.getInstance().NET_DVR_StartDownload(iUserID,
				dwDownloadType, downloadParam, sFileName);

		if (lHandle < 0) {
			Log.e(TAG, "NET_DVR_StartDownload is failed!Err:"
					+ HCNetSDK.getInstance().NET_DVR_GetLastError());
			return;
		}

		for (int i = 0; i < 100; i++) {
			INT_PTR press = new INT_PTR();
			HCNetSDK.getInstance().NET_DVR_GetDownloadState(
					(int) lHandle, press);
			Log.i(TAG, "i=" + i + "  =" + press.iValue);
			try {
				Thread.sleep(200);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if (press.iValue == 100) {
				boolean bRet = HCNetSDK.getInstance().NET_DVR_StopDownload(
						(int) lHandle);
				if (bRet) {
					Log.i(TAG, "NET_DVR_StopDownload Success!");
				} else {
					Log.i(TAG, "NET_DVR_StopDownload error!");
				}
				break;
			}
		}
	}

	void test_ScreenFileList(int iUserID)
	{
			NET_DVR_SCREEN_FILE_COND struCond = new NET_DVR_SCREEN_FILE_COND();
			struCond.byFileType = 1;
			int lHandle = HCNetSDK.getInstance().NET_DVR_StartRemoteConfig(iUserID, HCNetSDK.NET_DVR_GET_SCREEN_FLIE_LIST, struCond, null, null);
			if(lHandle < 0)
			{
				Log.e(TAG,"NET_DVR_StartRemoteConfig NET_DVR_GET_SCREEN_FLIE_LIST failed");
			return;
			}
			Log.i(TAG,"NET_DVR_StartRemoteConfig NET_DVR_GET_SCREEN_FLIE_LIST succeed");

			INT_PTR pState = new INT_PTR();
			if(!HCNetSDK.getInstance().NET_DVR_GetRemoteConfigState((int)lHandle, pState))
			{
				Log.e(TAG,"NET_DVR_GetRemoteConfigState NET_DVR_GET_SCREEN_FLIE_LIST failed");
			return;
			}
			Log.i(TAG,"NET_DVR_GetRemoteConfigState NET_DVR_GET_SCREEN_FLIE_LIST succeed");

			NET_DVR_SCREEN_FILE_INFO struFileInfo = new NET_DVR_SCREEN_FILE_INFO();
			int lRet = -1;
			int iCond = 1;
			while(iCond == 1)
			{
				lRet = HCNetSDK.getInstance().NET_DVR_GetNextRemoteConfig((int)lHandle, HCNetSDK.NET_DVR_GET_SCREEN_FLIE_LIST, struFileInfo);
	 			if(lRet < 0)
	 			{
	 				Log.e(TAG,"NET_DVR_GetNextRemoteConfig NET_DVR_GET_SCREEN_FLIE_LIST failed "+HCNetSDK.getInstance().NET_DVR_GetLastError());
	 				return;
	 			}
	 			Log.i(TAG,"NET_DVR_GetNextRemoteConfig NET_DVR_GET_SCREEN_FLIE_LIST succeed"+struFileInfo.byFileName);
	 			if(lRet == NET_SDK_GET_NEXT_STATUS.NET_SDK_GET_NEXT_STATUS_SUCCESS || lRet == NET_SDK_GET_NEXT_STATUS.NET_SDK_GET_NETX_STATUS_NEED_WAIT)
	 			{
	 				continue;
	 			}
	 			else if(lRet == NET_SDK_GET_NEXT_STATUS.NET_SDK_GET_NEXT_STATUS_FINISH)
	 			{
	 				Log.i(TAG,"NET_DVR_GetNextRemoteConfig NET_DVR_GET_SCREEN_FLIE_LIST finished");
	 				break;
	 			}
	 			else if(lRet == NET_SDK_GET_NEXT_STATUS.NET_SDK_GET_NEXT_STATUS_FAILED)
	 			{
	 				Log.e(TAG,"NET_DVR_GetNextRemoteConfig NET_DVR_GET_SCREEN_FLIE_LIST error");
	 				break;
	 			}
			}

			if(!HCNetSDK.getInstance().NET_DVR_StopRemoteConfig((int)lHandle))
			{
				Log.e(TAG,"NET_DVR_StopRemoteConfig NET_DVR_GET_SCREEN_FLIE_LIST failed");
			return;
			}
			Log.i(TAG,"NET_DVR_StopRemoteConfig NET_DVR_GET_SCREEN_FLIE_LIST succeed");

	}

	void test_ScreenConfig(int iUserID)
	{
		NET_DVR_COND_INT condInt = new NET_DVR_COND_INT();
		NET_DVR_SCREEN_CONFIG screenConfig = new NET_DVR_SCREEN_CONFIG();

		if (!HCNetSDK.getInstance().NET_DVR_GetSTDConfig(iUserID, HCNetSDK.NET_DVR_GET_SCREEN_CONFIG, null, null, screenConfig))
		{
			Log.e(TAG,"NET_DVR_GET_SCREEN_CONFIG" + " err: " + HCNetSDK.getInstance().NET_DVR_GetLastError());
		}
		else
		{
			Log.i(TAG,"NET_DVR_GET_SCREEN_CONFIG succ" + "volume: " +screenConfig.byVolume);
		}
	}

	void test_ScreenConfigCap(int iUserID)
	{
		byte[] byAbility = new byte[1*1024*1024];
		INT_PTR iRetLen = new INT_PTR();
		iRetLen.iValue = 0;
		int i = 0;
		if (!HCNetSDK.getInstance().NET_DVR_GetSTDAbility(iUserID, HCNetSDK.NET_DVR_GET_SCREEN_CONFIG_CAP, null, 0, byAbility, 1024*1024, iRetLen))
		{
			System.out.println("NET_DVR_GET_SCREEN_CONFIG_CAP" + " err: " + HCNetSDK.getInstance().NET_DVR_GetLastError());
		}
		else
		{
			System.out.println("NET_DVR_GET_SCREEN_CONFIG_CAP success");
		}
		i = HCNetSDK.getInstance().NET_DVR_GetLastError();
	}

	void test_WallAbility(int iUserID)
	{
		byte lpInBuf [] = new byte[1024];
		String str = "<WallAbility version=\"2.0\"></WallAbility>";
		for(int i=0;i<str.length();i++)
		{
			lpInBuf[i]=(byte)str.charAt(i);
		}
		byte lpOutBuf[] = new byte[100*1024];

		if(HCNetSDK.getInstance().NET_DVR_GetDeviceAbility(iUserID,HCNetSDK.getInstance().WALL_ABILITY,lpInBuf,1024,lpOutBuf,100*1024))
		{
			Log.i(TAG,"NET_DVR_GetDeviceAbility WallAbility success");
		}
		else
		{
			Log.e(TAG,"NET_DVR_GetDeviceAbility WallAbility fail,error code = "+HCNetSDK.getInstance().NET_DVR_GetLastError());
		}
	}

	void test_LEDCard(int iUserID)
	{
		String strInput = new String("PUT /ISAPI/LED/RecvCard/System/restore\r\n");
		byte[] byInput = strInput.getBytes();
		NET_DVR_XML_CONFIG_INPUT    struInput = new NET_DVR_XML_CONFIG_INPUT();
        NET_DVR_XML_CONFIG_OUTPUT   struOuput = new NET_DVR_XML_CONFIG_OUTPUT();

        struInput.lpRequestUrl = strInput.getBytes();
        struInput.dwRequestUrlLen = byInput.length;
        String strInBuffer = new String("<RecvCardArea version=\"2.0\"><outputNo>1</outputNo><Area><startLine>1</startLine><startColumn>1</startColumn><endLine>2</endLine><endColumn>2</endColumn></Area></RecvCardArea>\r\n");
        //byte[] byOutput1 = new byte[100*1024];
        //byOutput1 = strInBuffer.getBytes();
        struInput.lpInBuffer = strInBuffer.getBytes();
        //struInput.dwInBufferSize = struInput.lpInBuffer.length;
        struInput.dwInBufferSize = strInBuffer.length();
//        struOuput.lpOutBuffer = null;
//        struOuput.dwOutBufferSize = 0;
        if (!HCNetSDK.getInstance().NET_DVR_STDXMLConfig(iUserID, struInput, struOuput))
		{
        	System.out.println("NET_DVR_STDXMLConfig(PUT) faild!" + " err: " + HCNetSDK.getInstance().NET_DVR_GetLastError());
		}
		else
		{
			System.out.println("NET_DVR_STDXMLConfig(PUT) Success!:");
		}
	}

	public static void TEST_Screen(int iUserID)
	{
		ScreenTest test = new ScreenTest();

//		test.test_ControlScreen(iUserID);
//		test.test_SignalList(iUserID);
//		test.test_FileInfo(iUserID);
//		test.test_SerialAbility(iUserID);
//		test.test_loginCfg(iUserID);
//		test.test_PlayingPlan(iUserID);
//		test.test_CtrlPlan(iUserID);
//		test.test_Plan(iUserID);
//		test.test_VWParam(iUserID);
//		test.test_CurrentScene(iUserID);
//		test.test_SceneControl(iUserID);
//		test.test_DecChanEnable(iUserID);
//		test.test_SwitchWin(iUserID);
//		test.test_WallInParam(iUserID);
//		test.test_CloseAll(iUserID);
//		test.test_Position(iUserID);
//		test.test_DisplayPosition(iUserID);
//		test.test_WallOutput(iUserID);
//		test.test_PlanList(iUserID);

//		test.test_FileInfo(iUserID);
		test.test_ScreenCtrl(iUserID);
//		test.test_UploadFile(iUserID);
//		test.test_Download(iUserID);
//		test.test_ScreenFileList(iUserID);
//		test.test_ScreenConfig(iUserID);
//		test.test_ScreenConfigCap(iUserID);

//		test.test_WallAbility(iUserID);
//		test.test_LEDCard(iUserID);
	}
}
