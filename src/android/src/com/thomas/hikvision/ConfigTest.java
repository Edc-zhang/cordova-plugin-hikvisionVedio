package com.thomas.hikvision;


import com.hikvision.netsdk.*;

public class ConfigTest {
	private final static String TAG						= "ConfigTest";
	private final static INT_PTR ptrINT = new INT_PTR();

	public static void Test_Time(int iUserID)
	{
		NET_DVR_TIME struTimeCfg = new NET_DVR_TIME();
		if(!HCNetSDK.getInstance().NET_DVR_GetDVRConfig(iUserID, HCNetSDK.getInstance().NET_DVR_GET_TIMECFG, 0, struTimeCfg))
		{
			System.out.println("NET_DVR_GET_TIMECFG faild!" + " err: " + HCNetSDK.getInstance().NET_DVR_GetErrorMsg(ptrINT));
		}
		else
		{
			System.out.println("NET_DVR_GET_TIMECFG succ:" + struTimeCfg.ToString());
		}
		if(!HCNetSDK.getInstance().NET_DVR_SetDVRConfig(iUserID, HCNetSDK.getInstance().NET_DVR_SET_TIMECFG, 0, struTimeCfg))
		{
			System.out.println("NET_DVR_SET_TIMECFG faild!" + " err: " + HCNetSDK.getInstance().NET_DVR_GetErrorMsg(ptrINT));
		}
		else
		{
			System.out.println("NET_DVR_SET_TIMECFG succ:" + struTimeCfg.ToString());
		}
	}
	public static void Test_XMLAbility(int iUserID)
	{
//		NET_DVR_SDKLOCAL_CFG	sdkCfg = new NET_DVR_SDKLOCAL_CFG();
//		HCNetSDK.getInstance().NET_DVR_GetSDKLocalConfig(sdkCfg);
//		sdkCfg.byEnableAbilityParse = 1;
//		HCNetSDK.getInstance().NET_DVR_SetSDKLocalConfig(sdkCfg);
//		String strSDCard = Environment.getExternalStorageDirectory().getPath();
//		String path=(getApplicationContext()).getPackageResourcePath();
//		HCNetSDK.getInstance().NET_DVR_SetSimAbilityPath(path, strSDCard);

		byte[] arrayOutBuf = new byte[64*1024];
		INT_PTR intPtr = new INT_PTR();
		String strInput = new String("<AudioVideoCompressInfo><AudioChannelNumber>1</AudioChannelNumber><VoiceTalkChannelNumber>1</VoiceTalkChannelNumber><VideoChannelNumber>1</VideoChannelNumber></AudioVideoCompressInfo>");
		byte[] arrayInBuf = new byte[8*1024];
		arrayInBuf = strInput.getBytes();
		if(!HCNetSDK.getInstance().NET_DVR_GetXMLAbility(iUserID, HCNetSDK.DEVICE_ENCODE_ALL_ABILITY_V20,arrayInBuf, strInput.length(), arrayOutBuf, 64*1024, intPtr))
		{
			System.out.println("get DEVICE_ENCODE_ALL_ABILITY_V20 faild!" + " err: " + HCNetSDK.getInstance().NET_DVR_GetLastError());
		}
		else
		{
			System.out.println("get DEVICE_ENCODE_ALL_ABILITY_V20 succ!");
		}
	}
	public static void Test_PTZProtocol(int iUserID)
	{
		 NET_DVR_PTZCFG struPtzCfg = new NET_DVR_PTZCFG();
		 if (!HCNetSDK.getInstance().NET_DVR_GetPTZProtocol(iUserID, struPtzCfg))
		 {
			System.out.println("NET_DVR_GetPTZProtocol faild!" + " err: " + HCNetSDK.getInstance().NET_DVR_GetLastError());
		}else
		{
			System.out.println("NET_DVR_GetPTZProtocol succ!");
		}
	}
	public static void Test_PresetName(int iUserID, int iChan)
	{
		NET_DVR_PRESET_NAME_ARRAY struPresetNameArray = new NET_DVR_PRESET_NAME_ARRAY();
		if(!HCNetSDK.getInstance().NET_DVR_GetDVRConfig(iUserID, HCNetSDK.NET_DVR_GET_PRESET_NAME, iChan, struPresetNameArray))
		{
			System.out.println("NET_DVR_GET_PRESET_NAME faild!" + " err: " + HCNetSDK.getInstance().NET_DVR_GetLastError());
		}
		else
		{
			System.out.println("NET_DVR_GET_PRESET_NAME succ!");
		}
	}
	public static void Test_ShowString(int iUserID, int iChan)
	{
		NET_DVR_SHOWSTRING_V30 struShowString = new NET_DVR_SHOWSTRING_V30();
		if(!HCNetSDK.getInstance().NET_DVR_GetDVRConfig(iUserID, HCNetSDK.getInstance().NET_DVR_GET_SHOWSTRING_V30, iChan, struShowString))
		{
			System.out.println("NET_DVR_GET_SHOWSTRING_V30 faild!" + " err: " + HCNetSDK.getInstance().NET_DVR_GetLastError());
		}
		else
		{
			System.out.println("NET_DVR_GET_SHOWSTRING_V30 succ!");
		}

		if(!HCNetSDK.getInstance().NET_DVR_SetDVRConfig(iUserID, HCNetSDK.getInstance().NET_DVR_SET_SHOWSTRING_V30, iChan, struShowString))
		{
			System.out.println("NET_DVR_SET_SHOWSTRING_V30 faild!" + " err: " + HCNetSDK.getInstance().NET_DVR_GetLastError());
		}
		else
		{
			System.out.println("NET_DVR_SET_SHOWSTRING_V30 succ!");
		}
	}
	public static void Test_DigitalChannelState(int iUserID)
	{
		NET_DVR_DIGITAL_CHANNEL_STATE struChanState = new NET_DVR_DIGITAL_CHANNEL_STATE();
		if(!HCNetSDK.getInstance().NET_DVR_GetDVRConfig(iUserID, HCNetSDK.getInstance().NET_DVR_GET_DIGITAL_CHANNEL_STATE, 0, struChanState))
		{
			System.out.println("NET_DVR_GET_DIGITAL_CHANNEL_STATE faild!" + " err: " + HCNetSDK.getInstance().NET_DVR_GetLastError());
		}
		else
		{
			System.out.println("NET_DVR_GET_DIGITAL_CHANNEL_STATE succ!");
			System.out.println("analog channel 1 and 2:" + (int)struChanState.byAnalogChanState[0] + "-" + (int)struChanState.byAnalogChanState[1] +
					",digital channel 1 and 2:" + (int)struChanState.byDigitalChanState[0] + "-" + (int)struChanState.byDigitalChanState[1]);
		}
	}

	public static void Test_DDNSPara(int iUserID)
	{
		NET_DVR_DDNSPARA_V30	struDDNS = new NET_DVR_DDNSPARA_V30();
		if(!HCNetSDK.getInstance().NET_DVR_GetDVRConfig(iUserID, HCNetSDK.getInstance().NET_DVR_GET_DDNSCFG_V30, 0, struDDNS))
		{
			System.out.println("NET_DVR_GET_DDNSCFG_V30 faild!" + " err: " + HCNetSDK.getInstance().NET_DVR_GetLastError());
		}
		else
		{
			System.out.println("NET_DVR_GET_DDNSCFG_V30 succ!");
		}

		struDDNS.struDDNS[4].sDomainName = CommonMethod.string2ASCII("111222333444", 64);
		if(!HCNetSDK.getInstance().NET_DVR_SetDVRConfig(iUserID, HCNetSDK.getInstance().NET_DVR_SET_DDNSCFG_V30, 0, struDDNS))
		{
			System.out.println("NET_DVR_SET_DDNSCFG_V30 faild!" + " err: " + HCNetSDK.getInstance().NET_DVR_GetLastError());
		}
		else
		{
			System.out.println("NET_DVR_SET_DDNSCFG_V30 succ!");
		}
	}
	public static void Test_APInfoList(int iUserID)
	{
		NET_DVR_AP_INFO_LIST struAPInfoList = new NET_DVR_AP_INFO_LIST();
		if(!HCNetSDK.getInstance().NET_DVR_GetDVRConfig(iUserID, HCNetSDK.getInstance().NET_DVR_GET_AP_INFO_LIST, 0, struAPInfoList))
		{
			System.out.println("NET_DVR_GET_AP_INFO_LIST faild!" + " err: " + HCNetSDK.getInstance().NET_DVR_GetLastError());
		}
		else
		{
			System.out.println("NET_DVR_GET_AP_INFO_LIST succ!");
			int i = 0;
			for(i = 0; i < struAPInfoList.dwCount; i++)
			{
				System.out.println("AP[" + i + "]SSID:[" + new String(struAPInfoList.struApInfo[i].sSsid) + "]");
			}
		}
	}
	public static void Test_WifiCfg(int iUserID)
	{
		NET_DVR_WIFI_CFG struWifiCfg = new NET_DVR_WIFI_CFG();
		if(!HCNetSDK.getInstance().NET_DVR_GetDVRConfig(iUserID, HCNetSDK.getInstance().NET_DVR_GET_WIFI_CFG, 0, struWifiCfg))
		{
			System.out.println("NET_DVR_GET_WIFI_CFG faild!" + " err: " + HCNetSDK.getInstance().NET_DVR_GetLastError());
		}
		else
		{
			System.out.println("NET_DVR_GET_WIFI_CFG succ!");
		}

		if(!HCNetSDK.getInstance().NET_DVR_SetDVRConfig(iUserID, HCNetSDK.getInstance().NET_DVR_SET_WIFI_CFG, 0, struWifiCfg))
		{
			System.out.println("NET_DVR_SET_WIFI_CFG faild!" + " err: " + HCNetSDK.getInstance().NET_DVR_GetLastError());
		}
		else
		{
			System.out.println("NET_DVR_SET_WIFI_CFG succ!");
		}
	}
	public static void Test_WifiStatus(int iUserID)
	{
		NET_DVR_WIFI_CONNECT_STATUS struStatus = new NET_DVR_WIFI_CONNECT_STATUS();
		if(!HCNetSDK.getInstance().NET_DVR_GetDVRConfig(iUserID, HCNetSDK.getInstance().NET_DVR_GET_WIFI_STATUS, 0, struStatus))
		{
			System.out.println("NET_DVR_GET_WIFI_STATUS faild!" + " err: " + HCNetSDK.getInstance().NET_DVR_GetLastError());
		}
		else
		{
			System.out.println("NET_DVR_GET_WIFI_STATUS succ!");
		}
	}
	public static void Test_UpnpNatState(int iUserID)
	{
		NET_DVR_UPNP_NAT_STATE struUpnpNat = new NET_DVR_UPNP_NAT_STATE();
		if(!HCNetSDK.getInstance().NET_DVR_GetUpnpNatState(iUserID, struUpnpNat))
		{
			System.out.println("NET_DVR_GetUpnpNatState faild!" + " err: " + HCNetSDK.getInstance().NET_DVR_GetLastError());
		}
		else
		{
			System.out.println("NET_DVR_GetUpnpNatState succ!");
		}
	}
	public static void Test_UserCfg(int iUserID)
	{
		NET_DVR_USER_V30 struUserCfg = new NET_DVR_USER_V30();
		if(!HCNetSDK.getInstance().NET_DVR_GetDVRConfig(iUserID, HCNetSDK.NET_DVR_GET_USERCFG_V30, 0, struUserCfg))
		{
			System.out.println("NET_DVR_GET_USERCFG_V30 faild!" + " err: " + HCNetSDK.getInstance().NET_DVR_GetLastError());
		}
		else
		{
			System.out.println("NET_DVR_GET_USERCFG_V30 succ!" + new String(struUserCfg.struUser[0].sUserName));
		}
		if(!HCNetSDK.getInstance().NET_DVR_SetDVRConfig(iUserID, HCNetSDK.NET_DVR_SET_USERCFG_V30, 0, struUserCfg))
		{
			System.out.println("NET_DVR_SET_USERCFG_V30 faild!" + " err: " + HCNetSDK.getInstance().NET_DVR_GetLastError());
		}
		else
		{
			System.out.println("NET_DVR_SET_USERCFG_V30 succ!");
		}
	}

	public static void Test_DeviceCfg(int iUserID)
	{
		NET_DVR_DEVICECFG struDeviceCfg = new NET_DVR_DEVICECFG();
		if(!HCNetSDK.getInstance().NET_DVR_GetDVRConfig(iUserID, HCNetSDK.NET_DVR_GET_DEVICECFG, 0, struDeviceCfg))
		{
			System.out.println("NET_DVR_GET_DEVICECFG faild!" + " err: " + HCNetSDK.getInstance().NET_DVR_GetLastError());
		}
		else
		{
			System.out.println("NET_DVR_GET_DEVICECFG succ!" );
		}
	    if(!HCNetSDK.getInstance().NET_DVR_SetDVRConfig(iUserID, HCNetSDK.NET_DVR_SET_DEVICECFG, 0, struDeviceCfg))
	    {
			System.out.println("NET_DVR_SET_DEVICECFG faild!" + " err: " + HCNetSDK.getInstance().NET_DVR_GetErrorMsg(ptrINT));
		}
		else
		{
			System.out.println("NET_DVR_SET_DEVICECFG succ!" );
		}

   }

public static void Test_DeviceCfg_V40(int iUserID)
	{
		NET_DVR_DEVICECFG_V40 struDeviceCfg = new NET_DVR_DEVICECFG_V40();
		if(!HCNetSDK.getInstance().NET_DVR_GetDVRConfig(iUserID, HCNetSDK.NET_DVR_GET_DEVICECFG_V40, 0, struDeviceCfg))
		{
			System.out.println("NET_DVR_GET_DEVICECFG_V40 faild!" + " err: " + HCNetSDK.getInstance().NET_DVR_GetLastError());
		}
		else
		{
			System.out.println("NET_DVR_GET_DEVICECFG_V40 succ!" + new String(struDeviceCfg.byDevTypeName));
		}
//	    if(!HCNetSDK.getInstance().NET_DVR_SetDVRConfig(iUserID, HCNetSDK.getInstance().NET_DVR_SET_DEVICECFG_V40, 0, struDeviceCfg))
//	    {
//			System.out.println("NET_DVR_SET_DEVICECFG_V40 faild!" + " err: " + HCNetSDK.getInstance().NET_DVR_GetErrorMsg(ptrINT));
//		}
//		else
//		{
//			System.out.println("NET_DVR_SET_DEVICECFG_V40 succ:" + struDeviceCfg.byDevTypeName);
//		}

      }

public static void Test_ExceptionCfg_V40(int iUserID, int iChan)
{
	NET_DVR_EXCEPTION_V40 struExceptionCfg = new NET_DVR_EXCEPTION_V40();
	if(!HCNetSDK.getInstance().NET_DVR_GetDVRConfig(iUserID, HCNetSDK.NET_DVR_GET_EXCEPTIONCFG_V40, iChan, struExceptionCfg))
	{
		System.out.println("NET_DVR_GET_EXCEPTIONCFG_V40 faild!" + " err: " + HCNetSDK.getInstance().NET_DVR_GetLastError());
	}
	else
	{
		System.out.println("NET_DVR_GET_EXCEPTIONCFG_V40 succ! " );
	}

  }

	public static void Test_PicCfg(int iUserID, int iChan)
	{
		NET_DVR_PICCFG_V30 struPiccfg = new NET_DVR_PICCFG_V30();
		if(!HCNetSDK.getInstance().NET_DVR_GetDVRConfig(iUserID, HCNetSDK.NET_DVR_GET_PICCFG_V30, iChan, struPiccfg))
		{
			System.out.println("NET_DVR_GET_PICCFG_V30 faild!" + " err: " + HCNetSDK.getInstance().NET_DVR_GetLastError());
		}
		else
		{
			System.out.println("NET_DVR_GET_PICCFG_V30 succ!" + new String(struPiccfg.sChanName));
		}
		for(int i = 0; i <= 14; i++)
		{
			for(int j = 0; j <= 21; j++)
			{
				struPiccfg.struMotion.byMotionScope[i][j] = 1;
			}
		}
		struPiccfg.struMotion.byEnableHandleMotion = 0;
		if(!HCNetSDK.getInstance().NET_DVR_SetDVRConfig(iUserID, HCNetSDK.NET_DVR_SET_PICCFG_V30, iChan, struPiccfg))
		{
			System.out.println("NET_DVR_SET_PICCFG_V30 faild!" + " err: " + HCNetSDK.getInstance().NET_DVR_GetLastError());
		}
		else
		{
			System.out.println("NET_DVR_SET_PICCFG_V30 succ!");
		}
	}
	public static void Test_ZeroChanCfg(int iUserID)
	{
		NET_DVR_ZEROCHANCFG struZeroChanCfg = new NET_DVR_ZEROCHANCFG();
		if(!HCNetSDK.getInstance().NET_DVR_GetDVRConfig(iUserID, HCNetSDK.NET_DVR_GET_ZEROCHANCFG, 1, struZeroChanCfg)){
			System.out.println("NET_DVR_GET_ZEROCHANCFG faild!" + " err: " + HCNetSDK.getInstance().NET_DVR_GetLastError());
		}else{
			System.out.println("NET_DVR_GET_ZEROCHANCFG succ!");
		}
		if(!HCNetSDK.getInstance().NET_DVR_SetDVRConfig(iUserID, HCNetSDK.NET_DVR_SET_ZEROCHANCFG, 1, struZeroChanCfg)){
			System.out.println("NET_DVR_SET_ZEROCHANCFG faild!" + " err: " + HCNetSDK.getInstance().NET_DVR_GetLastError());
		}else{
			System.out.println("NET_DVR_SET_ZEROCHANCFG succ!");
		}
	}
	public static void Test_WorkState(int iUserID)
	{
		NET_DVR_WORKSTATE_V30 struWorkState = new NET_DVR_WORKSTATE_V30();
		if (!HCNetSDK.getInstance().NET_DVR_GetDVRWorkState_V30(iUserID, struWorkState))
		{
			System.out.println("NET_DVR_GetDVRWorkState_V30 faild!" + " err: " + HCNetSDK.getInstance().NET_DVR_GetLastError());
		}else
		{
			System.out.println("NET_DVR_GetDVRWorkState_V30 succ!");
		}
	}
	public static void Test_RecordCfg(int iUserID, int iChan)
	{
		NET_DVR_RECORD_V30 struRecordCfg = new NET_DVR_RECORD_V30();
		if (!HCNetSDK.getInstance().NET_DVR_GetDVRConfig(iUserID, HCNetSDK.NET_DVR_GET_RECORDCFG_V30, iChan, struRecordCfg))
		{
			System.out.println("NET_DVR_GET_RECORDCFG_V30 faild!" + " err: " + HCNetSDK.getInstance().NET_DVR_GetLastError());
		}else
		{
			System.out.println("NET_DVR_GET_RECORDCFG_V30 succ!");
		}
		if (!HCNetSDK.getInstance().NET_DVR_SetDVRConfig(iUserID,HCNetSDK.NET_DVR_SET_RECORDCFG_V30, iChan, struRecordCfg))
		{
			System.out.println("NET_DVR_SET_RECORDCFG_V30 faild!" + " err: " + HCNetSDK.getInstance().NET_DVR_GetLastError());
		}else
		{
			System.out.println("NET_DVR_SET_RECORDCFG_V30 succ!");
		}
	}
	public static void Test_AuxAlarmCfg(int iUserID, int iChan)
	{
		NET_IPC_AUX_ALARMCFG struAuxAlarm = new NET_IPC_AUX_ALARMCFG();
		if (!HCNetSDK.getInstance().NET_DVR_GetDVRConfig(iUserID, HCNetSDK.NET_IPC_GET_AUX_ALARMCFG, iChan, struAuxAlarm))
		{
			System.out.println("NET_IPC_GET_AUX_ALARMCFG faild!" + " err: " + HCNetSDK.getInstance().NET_DVR_GetLastError());
		}else
		{
			System.out.println("NET_IPC_GET_AUX_ALARMCFG succ!");
		}
		if (!HCNetSDK.getInstance().NET_DVR_SetDVRConfig(iUserID,HCNetSDK.NET_IPC_SET_AUX_ALARMCFG, iChan, struAuxAlarm))
		{
			System.out.println("NET_IPC_GET_AUX_ALARMCFG faild!" + " err: " + HCNetSDK.getInstance().NET_DVR_GetLastError());
		}else{
			System.out.println("NET_IPC_GET_AUX_ALARMCFG succ!");
		}
	}
	public static void Test_AlarminCfg(int iUserID)
	{
		NET_DVR_ALARMINCFG_V30 struAlarmIn = new NET_DVR_ALARMINCFG_V30();
		if (!HCNetSDK.getInstance().NET_DVR_GetDVRConfig(iUserID, HCNetSDK.NET_DVR_GET_ALARMINCFG_V30, 0, struAlarmIn))
		{
			System.out.println("NET_DVR_GET_ALARMINCFG_V30 faild!" + " err: " + HCNetSDK.getInstance().NET_DVR_GetLastError());
		}else
		{
			System.out.println("NET_DVR_GET_ALARMINCFG_V30 succ!");
		}
		if (!HCNetSDK.getInstance().NET_DVR_SetDVRConfig(iUserID,HCNetSDK.NET_DVR_SET_ALARMINCFG_V30, 0, struAlarmIn))
		{
			System.out.println("NET_DVR_SET_ALARMINCFG_V30 faild!" + " err: " + HCNetSDK.getInstance().NET_DVR_GetLastError());
		}else
		{
			System.out.println("NET_DVR_SET_ALARMINCFG_V30 succ!");
		}
	}
	public static void Test_AlarmOutCfg(int iUserID)
	{
		NET_DVR_ALARMOUTCFG_V30 struAlarmOut = new NET_DVR_ALARMOUTCFG_V30();
		if (!HCNetSDK.getInstance().NET_DVR_GetDVRConfig(iUserID, HCNetSDK.NET_DVR_GET_ALARMOUTCFG_V30, 0, struAlarmOut))
		{
			System.out.println("NET_DVR_GET_ALARMOUTCFG_V30 faild!" + " err: " + HCNetSDK.getInstance().NET_DVR_GetLastError());
		}else
		{
			System.out.println("NET_DVR_GET_ALARMOUTCFG_V30 succ!");
		}
		if (!HCNetSDK.getInstance().NET_DVR_SetDVRConfig(iUserID,HCNetSDK.NET_DVR_SET_ALARMOUTCFG_V30, 0, struAlarmOut))
		{
			System.out.println("NET_DVR_SET_ALARMOUTCFG_V30 faild!" + " err: " + HCNetSDK.getInstance().NET_DVR_GetLastError());
		}else
		{
			System.out.println("NET_DVR_SET_ALARMOUTCFG_V30 succ!");
		}
	}
	public static void Test_DecoderCfg(int iUserID)
	{
		NET_DVR_DECODERCFG_V30  struDecoderCfg = new NET_DVR_DECODERCFG_V30();
		if (!HCNetSDK.getInstance().NET_DVR_GetDVRConfig(iUserID, HCNetSDK.NET_DVR_GET_DECODERCFG_V30, 1, struDecoderCfg))
		{
			System.out.println("NET_DVR_GET_DECODERCFG_V30 faild!" + " err: " + HCNetSDK.getInstance().NET_DVR_GetLastError());
		}else
		{
			System.out.println("NET_DVR_GET_DECODERCFG_V30 succ!");
		}
		struDecoderCfg.wDecoderAddress = 1;
		if (!HCNetSDK.getInstance().NET_DVR_SetDVRConfig(iUserID,HCNetSDK.NET_DVR_SET_DECODERCFG_V30, 1, struDecoderCfg))
		{
			System.out.println("NET_DVR_SET_DECODERCFG_V30 faild!" + " err: " + HCNetSDK.getInstance().NET_DVR_GetLastError());
		}else
		{
			System.out.println("NET_DVR_SET_DECODERCFG_V30 succ!");
		}
	}
	public static void Test_NTPPara(int iUserID)
	{
		NET_DVR_NTPPARA NtpPara = new NET_DVR_NTPPARA();
		if (!HCNetSDK.getInstance().NET_DVR_GetDVRConfig(iUserID, HCNetSDK.NET_DVR_GET_NTPCFG, 0, NtpPara))
		{
			System.out.println("get NtpPara faild!" + " err: " + HCNetSDK.getInstance().NET_DVR_GetLastError());
		}else
		{
			System.out.println("get NtpPara succ!");
		}
		NtpPara.byEnableNTP = 1;
		if (!HCNetSDK.getInstance().NET_DVR_SetDVRConfig(iUserID,HCNetSDK.NET_DVR_SET_NTPCFG, 0, NtpPara))
		{
			System.out.println("Set NtpPara faild!" + " err: " + HCNetSDK.getInstance().NET_DVR_GetLastError());
		}else
		{
			System.out.println("Set NtpPara succ!");
		}
	}
	public static void Test_IPAlarmOutCfg(int iUserID)
	{
		NET_DVR_IPALARMOUTCFG struIpAlarmOut = new NET_DVR_IPALARMOUTCFG();
		if(!HCNetSDK.getInstance().NET_DVR_GetDVRConfig(iUserID, HCNetSDK.NET_DVR_GET_IPALARMOUTCFG, 0, struIpAlarmOut))
		{
			System.out.println("NET_DVR_GET_IPALARMOUTCFG faild!" + " err: " + HCNetSDK.getInstance().NET_DVR_GetLastError());
		}
		else
		{
			System.out.println("NET_DVR_GET_IPALARMOUTCFG succ!");
		}
	}
	public static void Test_IPParaCfg(int iUserID)
	{
		NET_DVR_IPPARACFG_V40 IpPara = new NET_DVR_IPPARACFG_V40();
		if (!HCNetSDK.getInstance().NET_DVR_GetDVRConfig(iUserID, HCNetSDK.NET_DVR_GET_IPPARACFG_V40, 0, IpPara))
		{
			System.out.println("NET_DVR_GET_IPPARACFG_V40 faild!" + " err: " + HCNetSDK.getInstance().NET_DVR_GetLastError());
		}else
		{
			System.out.println("NET_DVR_GET_IPPARACFG_V40 succ!AChan:" + IpPara.dwAChanNum + ",DChan:" + IpPara.dwDChanNum);
		}
		if (!HCNetSDK.getInstance().NET_DVR_SetDVRConfig(iUserID,HCNetSDK.NET_DVR_SET_IPPARACFG_V40, 0, IpPara))
		{
			System.out.println("NET_DVR_SET_IPPARACFG_V40 faild!" + " err: " + HCNetSDK.getInstance().NET_DVR_GetLastError());
		}else
		{
			System.out.println("NET_DVR_SET_IPPARACFG_V40 succ!");
		}
	}
	public static void Test_NetCfg(int iUserID)
	{
		NET_DVR_NETCFG_V30 NetCfg = new NET_DVR_NETCFG_V30();
		if (!HCNetSDK.getInstance().NET_DVR_GetDVRConfig(iUserID,HCNetSDK.NET_DVR_GET_NETCFG_V30, 0, NetCfg))
		{
			System.out.println("get net cfg faied!"+ " err: " + HCNetSDK.getInstance().NET_DVR_GetLastError());
		}else
		{
			System.out.println("get net cfg succ!");
			System.out.println("alarm host ip: " + new String(NetCfg.struAlarmHostIpAddr.sIpV4));
			System.out.println("Etherner host ip: " + new String(NetCfg.struEtherNet[0].struDVRIP.sIpV4));
			System.out.println("Etherner mask: " + new String(NetCfg.struEtherNet[0].struDVRIPMask.sIpV4));
		}
		if (!HCNetSDK.getInstance().NET_DVR_SetDVRConfig(iUserID, HCNetSDK.NET_DVR_SET_NETCFG_V30, 0 , NetCfg))
		{
			System.out.println("Set net cfg faied!"+ " err: " + HCNetSDK.getInstance().NET_DVR_GetLastError());
		}else
		{
			System.out.println("Set net cfg succ!");
		}
	}
	public static void Test_CompressionCfg(int iUserID, int iChan)
	{
		NET_DVR_COMPRESSIONCFG_V30 CompressionCfg = new NET_DVR_COMPRESSIONCFG_V30();
		if (!HCNetSDK.getInstance().NET_DVR_GetDVRConfig(iUserID,HCNetSDK.NET_DVR_GET_COMPRESSCFG_V30, iChan, CompressionCfg))
		{
			System.out.println("get CompressionCfg failed!" + "err: " + HCNetSDK.getInstance().NET_DVR_GetLastError());
		}else
		{
			System.out.println("get CompressionCfg succ! resolution: " + CompressionCfg.struNetPara.byResolution);
		}
		CompressionCfg.struNetPara.dwVideoFrameRate = 1;
		if (!HCNetSDK.getInstance().NET_DVR_SetDVRConfig(iUserID,HCNetSDK.NET_DVR_SET_COMPRESSCFG_V30, iChan, CompressionCfg))
		{
			System.out.println("Set CompressionCfg failed!" + "err: " + HCNetSDK.getInstance().NET_DVR_GetLastError());
		}else
		{
			System.out.println("Set CompressionCfg succ!" );
		}
	}
	public static void Test_CompressCfgAud(int iUserID)
	{
		NET_DVR_COMPRESSION_AUDIO AudioCompress = new NET_DVR_COMPRESSION_AUDIO();
		if (!HCNetSDK.getInstance().NET_DVR_GetDVRConfig(iUserID,HCNetSDK.NET_DVR_GET_COMPRESSCFG_AUD, 1, AudioCompress))
		{
			System.out.println("get AudioCompress failed!" + "err: " + HCNetSDK.getInstance().NET_DVR_GetLastError());
		}else
		{
			System.out.println("get AudioCompress succ! type: " + AudioCompress.byAudioEncType);
		}
	}
	public static void Test_AlarmOutStatus(int iUserID)
	{
		NET_DVR_ALARMOUTSTATUS_V30 AlarmOut = new NET_DVR_ALARMOUTSTATUS_V30();
		if (!HCNetSDK.getInstance().NET_DVR_SetAlarmOut(iUserID, 0x00ff, 1))
		{
			System.out.println("Set alarm out failed!" + "err: " + HCNetSDK.getInstance().NET_DVR_GetLastError());
		}else
		{
			System.out.println("Set alarm out succ!");
		}
		if (!HCNetSDK.getInstance().NET_DVR_GetAlarmOut_V30(iUserID, AlarmOut))
		{
			System.out.println("Get alarm out failed!" + "err: " + HCNetSDK.getInstance().NET_DVR_GetLastError());
		}else
		{
			System.out.println("Get alarm out succ!");
			for (int i=0; i<HCNetSDK.MAX_ALARMOUT_V30; i++)
			{
				System.out.print((int)AlarmOut.Output[i]);
			}
			System.out.println();
		}


//		if (!HCNetSDK.getInstance().NET_DVR_GetAlarmOut_V30(iUserID, null))
//		{
//			System.out.println("Get alarm out failed!" + "err: " + HCNetSDK.getInstance().NET_DVR_GetLastError());
//		}else
//		{
//			System.out.println("Get alarm out succ!");
//			for (int i=0; i<HCNetSDK.MAX_ALARMOUT_V30; i++)
//			{
//				System.out.print((int)AlarmOut.Output[i]);
//			}
//			System.out.println();
//		}
	}
	public static void Test_VideoEffect(int iPreviewID)
	{
		NET_DVR_VIDEOEFFECT VideoEffect = new NET_DVR_VIDEOEFFECT();
		if (!HCNetSDK.getInstance().NET_DVR_ClientGetVideoEffect(iPreviewID, VideoEffect))
		{
			System.out.println("NET_DVR_ClientGetVideoEffect failed:" + HCNetSDK.getInstance().NET_DVR_GetLastError());
		}
		else
		{
			//System.out.println("NET_DVR_ClientGetVideoEffect succ"+ VideoEffect.iBrightValue +
					///VideoEffect.iContrastValue+VideoEffect.iSaturationValue+VideoEffect.iHueValue);
			System.out.println("NET_DVR_ClientGetVideoEffect succ"+ VideoEffect.byBrightnessLevel +
					VideoEffect.byContrastLevel+VideoEffect.bySaturationLevel+VideoEffect.byHueLevel);
		}
		//VideoEffect.iBrightValue += 1;
		VideoEffect.byBrightnessLevel += 1;
		if(!HCNetSDK.getInstance().NET_DVR_ClientSetVideoEffect(iPreviewID, VideoEffect))
		{
			System.out.println("NET_DVR_ClientSetVideoEffect failed:" +  + HCNetSDK.getInstance().NET_DVR_GetLastError());
		}
		else
		{
			System.out.println("NET_DVR_ClientSetVideoEffect succ");
		}
	}

//	public static void Test_InquestControlCDW(int iUserId)
//	{
//		NET_DVR_INQUEST_ROOM InquestRoom = new NET_DVR_INQUEST_ROOM();
//
//		InquestRoom.byRoomIndex = 1;
//		InquestRoom.byFileType = 1;
//		if (!HCNetSDK.getInstance().NET_DVR_RemoteControl(iUserId, HCNetSDK.NET_DVR_INQUEST_PAUSE_CDW, InquestRoom))
//		{
//			System.out.println("NET_DVR_INQUEST_PAUSE_CDW" + " err: " + HCNetSDK.getInstance().NET_DVR_GetLastError());
//		}
//		else
//		{
//			System.out.println("NET_DVR_INQUEST_PAUSE_CDW success");
//		}
//
//		if (!HCNetSDK.getInstance().NET_DVR_RemoteControl(iUserId, HCNetSDK.NET_DVR_INQUEST_RESUME_CDW, InquestRoom))
//		{
//			System.out.println("NET_DVR_INQUEST_RESUME_CDW" + " err: " + HCNetSDK.getInstance().NET_DVR_GetLastError());
//		}
//		else
//		{
//			System.out.println("NET_DVR_INQUEST_RESUME_CDW success");
//		}
//
//	}

//	public static void Test_AreaMaskCfg(int iUserId, int iChan)
//	{
//		NET_DVR_AREA_MASK_CFG AreaMaskCfg = new NET_DVR_AREA_MASK_CFG();
//
//		if (!HCNetSDK.getInstance().NET_DVR_GetDVRConfig(iUserId, HCNetSDK.NET_DVR_GET_AREA_MASK_CFG, 34, AreaMaskCfg))
//		{
//			System.out.println("NET_DVR_GET_AREA_MASK_CFG" + iChan + " err: " + HCNetSDK.getInstance().NET_DVR_GetLastError());
//		}
//		else
//		{
//			System.out.println("NET_DVR_GET_AREA_MASK_CFG success");
//		}
//
//		if (!HCNetSDK.getInstance().NET_DVR_SetDVRConfig(iUserId, HCNetSDK.NET_DVR_SET_AREA_MASK_CFG, 34, AreaMaskCfg))
//		{
//			System.out.println("NET_DVR_SET_AREA_MASK_CFG" + " err: " + HCNetSDK.getInstance().NET_DVR_GetLastError());
//		}
//		else
//		{
//			System.out.println("NET_DVR_SET_AREA_MASK_CFG success");
//		}
//	}

//	public static void Test_AudioDiaCritical(int iUserId, int iChan)
//	{
//		NET_DVR_AUDIO_DIACRITICAL_CFG AudioDiaCritical = new NET_DVR_AUDIO_DIACRITICAL_CFG();
//
//		if (!HCNetSDK.getInstance().NET_DVR_GetDVRConfig(iUserId, HCNetSDK.NET_DVR_GET_AUDIO_DIACRITICAL_CFG, 34, AudioDiaCritical))
//		{
//			System.out.println("NET_DVR_GET_AUDIO_DIACRITICAL_CFG" + " err: " + HCNetSDK.getInstance().NET_DVR_GetLastError());
//		}
//		else
//		{
//			System.out.println("NET_DVR_GET_AUDIO_DIACRITICAL_CFG success");
//		}
//
//		if (!HCNetSDK.getInstance().NET_DVR_SetDVRConfig(iUserId, HCNetSDK.NET_DVR_SET_AUDIO_DIACRITICAL_CFG, 34, AudioDiaCritical))
//		{
//			System.out.println("NET_DVR_SET_AUDIO_DIACRITICAL_CFG" + " err: " + HCNetSDK.getInstance().NET_DVR_GetLastError());
//		}
//		else
//		{
//			System.out.println("NET_DVR_SET_AUDIO_DIACRITICAL_CFG success");
//		}
//	}
	public static void Test_Preview_display(int iUserID, int iChan)
	{
//		NET_DVR_PREVIEW_DISPLAYCFG struPreviewDisplay = new NET_DVR_PREVIEW_DISPLAYCFG();
//		if (!HCNetSDK.getInstance().NET_DVR_GetDVRConfig(iUserID,HCNetSDK.NET_DVR_GET_PREVIEW_DISPLAYCFG, 1, struPreviewDisplay))
//		{
//			System.out.println("get Preview Display failed!" + "err: " + HCNetSDK.getInstance().NET_DVR_GetLastError());
//		}else
//		{
//			System.out.println("get Preview Display succ! Mode: " + struPreviewDisplay.byCorrectMode + " MountType:" + struPreviewDisplay.byMountType);
//			System.out.println("get Preview Display succ! RealTimeOutput: " + struPreviewDisplay.byRealTimeOutput);
//		}
//		//Set
//		struPreviewDisplay.byMountType = 2;
//		struPreviewDisplay.byRealTimeOutput = 3;
//		if(!HCNetSDK.getInstance().NET_DVR_SetDVRConfig(iUserID, HCNetSDK.NET_DVR_SET_PREVIEW_DISPLAYCFG, 1, struPreviewDisplay))
//		{
//			System.out.println("Set Preview Display failed!" + "err: " + HCNetSDK.getInstance().NET_DVR_GetLastError());
//		}
//		else
//		{
//			System.out.println("NET_DVR_SET_PREVIEW_DISPLAYCFG succ");
//		}
		NET_DVR_PREVIEW_DISPLAYCFG struPreviewDisplay = new NET_DVR_PREVIEW_DISPLAYCFG();
		if (!HCNetSDK.getInstance().NET_DVR_GetDVRConfig(iUserID,HCNetSDK.NET_DVR_GET_PREVIEW_DISPLAYCFG, 1, struPreviewDisplay))
		{
			System.out.println("get Preview Display failed!" + "err: " + HCNetSDK.getInstance().NET_DVR_GetLastError());
		}else
		{
			System.out.println("get Preview Display succ! Mode: " + struPreviewDisplay.byCorrectMode + " MountType:" + struPreviewDisplay.byMountType);
			System.out.println("get Preview Display succ! RealTimeOutput: " + struPreviewDisplay.byRealTimeOutput);
		}
		//Set
		//struPreviewDisplay.byMountType = 2;
		//struPreviewDisplay.byRealTimeOutput = 3;
		if(!HCNetSDK.getInstance().NET_DVR_SetDVRConfig(iUserID, HCNetSDK.NET_DVR_SET_PREVIEW_DISPLAYCFG, 1, struPreviewDisplay))
		{
			System.out.println("Set Preview Display failed!" + "err: " + HCNetSDK.getInstance().NET_DVR_GetLastError());
		}
		else
		{
			System.out.println("NET_DVR_SET_PREVIEW_DISPLAYCFG succ");
		}
	}
	public static void Text_FISHEYE_ABILITY(int iUserID)
	{

		byte[] arrayOutBuf = new byte[64*1024];
		INT_PTR intPtr = new INT_PTR();
		String strInput = new String("<FishEyeIPCAbility version==\"2.0\"><channelNO>1</channelNO></FishEyeIPCAbility>");
		byte[] arrayInBuf = new byte[8*1024];
		arrayInBuf = strInput.getBytes();
		if(!HCNetSDK.getInstance().NET_DVR_GetXMLAbility(iUserID, HCNetSDK.FISHEYE_ABILITY,arrayInBuf, strInput.length(), arrayOutBuf, 64*1024, intPtr))
		{
			System.out.println("get FISHEYE_ABILITY faild!" + " err: " + HCNetSDK.getInstance().NET_DVR_GetLastError());
		}
		else
		{
			System.out.println("get FISHEYE_ABILITY succ!");
		}
	}
	public static void Test_CAMERAPARAMCFG_EX(int iUserID, int iChan)
	{
		NET_DVR_CAMERAPARAMCFG_EX struCameraParamCfgEX = new NET_DVR_CAMERAPARAMCFG_EX();
		int i = 0;
		if (!HCNetSDK.getInstance().NET_DVR_GetDVRConfig(iUserID,HCNetSDK.NET_DVR_GET_CCDPARAMCFG_EX, iChan, struCameraParamCfgEX))
		{
			System.out.println("NET_DVR_GET_CCDPARAMCFG_EX failed!" + "err: " + HCNetSDK.getInstance().NET_DVR_GetLastError());
		}else
		{
			System.out.println("NET_DVR_GET_CCDPARAMCFG_EX succ!");
		}
		struCameraParamCfgEX.struVideoEffect.byBrightnessLevel = 39;
		struCameraParamCfgEX.struVideoEffect.byContrastLevel = 80;
		struCameraParamCfgEX.struVideoEffect.bySaturationLevel = 26;
		struCameraParamCfgEX.struVideoEffect.bySharpnessLevel = 82;
		if(!HCNetSDK.getInstance().NET_DVR_SetDVRConfig(iUserID, HCNetSDK.NET_DVR_SET_CCDPARAMCFG_EX, iChan, struCameraParamCfgEX))
		{
			System.out.println("NET_DVR_SET_CCDPARAMCFG_EX!" + "err: " + HCNetSDK.getInstance().NET_DVR_GetLastError());
		}
		else
		{
			System.out.println("NET_DVR_SET_CCDPARAMCFG_EX succ");
		}
		i = HCNetSDK.getInstance().NET_DVR_GetLastError();
	}
	public static void Test_WIRELESSDIAL_CFG(int iUserID)
	{
		NET_DVR_COND_INT struCardNo = new NET_DVR_COND_INT();
		struCardNo.iValue = 1;
		NET_DVR_WIRELESSSERVER_FULLVERSION_CFG struWirelessDialCfg = new NET_DVR_WIRELESSSERVER_FULLVERSION_CFG();
		int i = 0;
		if(!HCNetSDK.getInstance().NET_DVR_GetSTDConfig(iUserID, HCNetSDK.NET_DVR_GET_WIRELESSSERVER_FULLVERSION_CFG, struCardNo, null, struWirelessDialCfg)){
			System.out.println("NET_DVR_GET_WIRELESSSERVER_FULLVERSION_CFG faild!" + " err: " + HCNetSDK.getInstance().NET_DVR_GetLastError());
		}else{
			System.out.println("NET_DVR_GET_WIRELESSSERVER_FULLVERSION_CFG succ!");
		}
		i = HCNetSDK.getInstance().NET_DVR_GetLastError();
		if(!HCNetSDK.getInstance().NET_DVR_SetSTDConfig(iUserID, HCNetSDK.NET_DVR_SET_WIRELESSSERVER_FULLVERSION_CFG, struCardNo, struWirelessDialCfg, null)){
			System.out.println("NET_DVR_SET_WIRELESSSERVER_FULLVERSION_CFG faild!" + " err: " + HCNetSDK.getInstance().NET_DVR_GetLastError());
		}else{
			System.out.println("NET_DVR_SET_WIRELESSSERVER_FULLVERSION_CFG succ!");
		}
		i = HCNetSDK.getInstance().NET_DVR_GetLastError();
	}
	public static void Test_PostRadar_Capabilities(int iUserID)
	{
		byte[] byAbility = new byte[1*1024*1024];
		INT_PTR iRetLen = new INT_PTR();
		iRetLen.iValue = 0;
		int i = 0;
		if (!HCNetSDK.getInstance().NET_DVR_GetSTDAbility(iUserID, HCNetSDK.NET_DVR_GET_POSTRADAR_CAPABILITIES, null, 0, byAbility, 1024*1024, iRetLen))
		{
			System.out.println("NET_DVR_GET_POSTRADAR_CAPABILITIES" + " err: " + HCNetSDK.getInstance().NET_DVR_GetLastError());
		}
		else
		{
			System.out.println("NET_DVR_GET_POSTRADAR_CAPABILITIES success");
		}
		i = HCNetSDK.getInstance().NET_DVR_GetLastError();
	}


	public static void Test_Its_Overlap_Cfg_V50(int iUserID, int iChan)
	{
		/*
		NET_DVR_MULTI_STREAM_COMPRESSIONCFG_COND struItsOverlapCfgCond = new NET_DVR_MULTI_STREAM_COMPRESSIONCFG_COND();
		struItsOverlapCfgCond.struStreamInfo.dwChannel = iChan;
		struItsOverlapCfgCond.dwStreamType = 1;
		NET_DVR_MULTI_STREAM_COMPRESSIONCFG struItsOverlapCfgV50 = new NET_DVR_MULTI_STREAM_COMPRESSIONCFG();
		if (!HCNetSDK.getInstance().NET_DVR_GetDeviceConfig(iUserID,HCNetSDK.NET_DVR_GET_MULTI_STREAM_COMPRESSIONCFG, struItsOverlapCfgCond, struItsOverlapCfgV50))
		{
			System.out.println("NET_ITS_GET_OVERLAP_CFG_V50 failed!" + "err: " + HCNetSDK.getInstance().NET_DVR_GetLastError());
		}else
		{
			System.out.println("NET_ITS_GET_OVERLAP_CFG_V50 succ: ");
		}
		//Set
		//struPreviewDisplay.byMountType = 2;
		//struPreviewDisplay.byRealTimeOutput = 3;
		if(!HCNetSDK.getInstance().NET_DVR_SetDeviceConfig(iUserID, HCNetSDK.NET_DVR_SET_MULTI_STREAM_COMPRESSIONCFG, struItsOverlapCfgCond, struItsOverlapCfgV50))
		{
			System.out.println("NET_ITS_SET_OVERLAP_CFG_V50 failed!" + "err: " + HCNetSDK.getInstance().NET_DVR_GetLastError());
		}
		else
		{
			System.out.println("NET_ITS_SET_OVERLAP_CFG_V50 succ");
		}
		*/
	}
	public static void TextOSD(int iUserID, int iChan)
	{
		NET_VCA_FACESNAPCFG struPreviewDisplay = new NET_VCA_FACESNAPCFG();
		if (!HCNetSDK.getInstance().NET_DVR_GetDVRConfig(iUserID,HCNetSDK.NET_DVR_GET_FACESNAPCFG, 1, struPreviewDisplay))
		{
			System.out.println("NET_DVR_GET_FACESNAPCFG failed!" + "err: " + HCNetSDK.getInstance().NET_DVR_GetLastError());
		}else
		{
			System.out.println("NET_DVR_GET_FACESNAPCFG succ");
		}
		//Set
		//struPreviewDisplay.byMountType = 2;
		//struPreviewDisplay.byRealTimeOutput = 3;
		if(!HCNetSDK.getInstance().NET_DVR_SetDVRConfig(iUserID, HCNetSDK.NET_DVR_SET_FACESNAPCFG, 1, struPreviewDisplay))
		{
			System.out.println("NET_DVR_SET_FACESNAPCFG failed!" + "err: " + HCNetSDK.getInstance().NET_DVR_GetLastError());
		}
		else
		{
			System.out.println("NET_DVR_SET_FACESNAPCFG succ");
		}
	}

	public static void Test_EzvizCreate()
	{
		NET_DVR_OPEN_EZVIZ_USER_LOGIN_INFO struLoginInfo = new NET_DVR_OPEN_EZVIZ_USER_LOGIN_INFO();
		NET_DVR_DEVICEINFO_V30 struDeviceInfo = new NET_DVR_DEVICEINFO_V30();

        String strInput = new String("open.ys7.com");
        byte[] byInput = strInput.getBytes();
        System.arraycopy(byInput, 0, struLoginInfo.sEzvizServerAddress, 0, byInput.length);

    	struLoginInfo.wPort = 443;

        strInput = new String("at.8gebs2w79fiz3km4arwjec9i3kblmlpi-13ifmla84v-1j7webb-an1n5tl3w");
        byInput = strInput.getBytes();
        System.arraycopy(byInput, 0, struLoginInfo.sAccessToken, 0, byInput.length);

        strInput = new String("ae1b9af9dcac4caeb88da6dbbf2dd8d5");
        byInput = strInput.getBytes();
        System.arraycopy(byInput, 0, struLoginInfo.sAppID, 0, byInput.length);

        strInput = new String("78313dadecd92bd11623638d57aa5128");
        byInput = strInput.getBytes();
        System.arraycopy(byInput, 0, struLoginInfo.sFeatureCode, 0, byInput.length);

        strInput = new String("https://open.ys7.com:443/api/device/transmission");
        byInput = strInput.getBytes();
        System.arraycopy(byInput, 0, struLoginInfo.sUrl, 0, byInput.length);

        strInput = new String("201606271");
        byInput = strInput.getBytes();
        System.arraycopy(byInput, 0, struLoginInfo.sDeviceID, 0, byInput.length);

        strInput = new String("0");
        byInput = strInput.getBytes();
        System.arraycopy(byInput, 0, struLoginInfo.sClientType, 0, byInput.length);

        strInput = new String("UNKNOWN");
        byInput = strInput.getBytes();
        System.arraycopy(byInput, 0, struLoginInfo.sNetType, 0, byInput.length);

        strInput = new String("5.0.1");
        byInput = strInput.getBytes();
        System.arraycopy(byInput, 0, struLoginInfo.sOsVersion, 0, byInput.length);

        strInput = new String("v.5.1.5.30");
        byInput = strInput.getBytes();
        System.arraycopy(byInput, 0, struLoginInfo.sSdkVersion, 0, byInput.length);

        int iUserID = HCNetSDK.getInstance().NET_DVR_CreateOpenEzvizUser(struLoginInfo, struDeviceInfo);
    	if (-1 == iUserID)
    	{
    		System.out.println("NET_DVR_CreateEzvizUser" + " err: " + HCNetSDK.getInstance().NET_DVR_GetLastError());
		}
    	else
    	{
			System.out.println("NET_DVR_CreateEzvizUser success");
		}

		NET_DVR_XML_CONFIG_INPUT    struInput = new NET_DVR_XML_CONFIG_INPUT();
        NET_DVR_XML_CONFIG_OUTPUT   struOuput = new NET_DVR_XML_CONFIG_OUTPUT();
        strInput = new String("GET /ISAPI/SecurityCP/AlarmControlByPhone\r\n");
        byInput = strInput.getBytes();
        System.arraycopy(byInput, 0, struInput.lpRequestUrl, 0, byInput.length);
        struInput.dwRequestUrlLen = byInput.length;
        struOuput.dwOutBufferSize = HCNetSDK.MAX_XML_CONFIG_LEN;
        struOuput.dwStatusSize = struOuput.dwOutBufferSize;
        if (!HCNetSDK.getInstance().NET_DVR_STDXMLConfig(iUserID, struInput, struOuput))
		{
			System.out.println("NET_DVR_STDXMLConfig" + " err: " + HCNetSDK.getInstance().NET_DVR_GetLastError());
		}
		else
		{
			System.out.println("NET_DVR_STDXMLConfig success");
		}
        byte[] byOutput = new byte[struOuput.dwReturnedXMLSize];
        System.arraycopy(struOuput.lpOutBuffer, 0, byOutput, 0, struOuput.dwReturnedXMLSize);
        System.out.println(new String(byOutput));

        struInput = new NET_DVR_XML_CONFIG_INPUT();
        struOuput = new NET_DVR_XML_CONFIG_OUTPUT();

        strInput = new String("PUT /ISAPI/VideoIntercom/passwordAuthentication\r\n");
        byInput = strInput.getBytes();
        System.arraycopy(byInput, 0, struInput.lpRequestUrl, 0, byInput.length);
        struInput.dwRequestUrlLen = byInput.length;

        String strInBuffer = new String("<PasswordAuthenticationCfg version=\"2.0\" xmlns=\"http://www.isapi.org/ver20/XMLSchema\">\r\n<password>123456</password>\r\n</PasswordAuthenticationCfg>\r\n");
        byte[] byInBuffer = strInBuffer.getBytes();
        System.arraycopy(byInBuffer,  0, struInput.lpInBuffer, 0, byInBuffer.length);
        struInput.dwInBufferSize = strInBuffer.length();
        struOuput.dwOutBufferSize = HCNetSDK.MAX_XML_CONFIG_LEN;
        struOuput.dwStatusSize = struOuput.dwOutBufferSize;

        if (!HCNetSDK.getInstance().NET_DVR_STDXMLConfig(iUserID, struInput, struOuput))
		{
			System.out.println("NET_DVR_STDXMLConfig" + " err: " + HCNetSDK.getInstance().NET_DVR_GetLastError());
		}
		else
		{
			System.out.println("NET_DVR_STDXMLConfig success");
		}
        /*
        NET_DVR_VIDEO_CALL_PARAM struTest1 = new NET_DVR_VIDEO_CALL_PARAM();
		//Set
        struTest1.dwCmdType = 0;
        struTest1.wUnitNumber = 2;
		if(!HCNetSDK.getInstance().NET_DVR_SetDVRConfig(iUserID, HCNetSDK.NET_DVR_SET_CALL_SIGNAL, 1, struTest1))
		{
			System.out.println("NET_DVR_SET_FACESNAPCFG failed!" + "err: " + HCNetSDK.getInstance().NET_DVR_GetLastError());
		}
		else
		{
			System.out.println("NET_DVR_SET_FACESNAPCFG succ");
		}

		NET_DVR_CONTROL_GATEWAY struTest2 = new NET_DVR_CONTROL_GATEWAY();
		//Set
		struTest2.byCommand = 0;
		struTest2.byControlType = 1;
		String strTempString = new String("123456");
		byte[] byTemp = strTempString.getBytes();
        System.arraycopy(byTemp, 0, struTest2.byPassword, 0, byTemp.length);
		if(!HCNetSDK.getInstance().NET_DVR_RemoteControl(iUserID, HCNetSDK.NET_DVR_REMOTECONTROL_GATEWAY, struTest2))
		{
			System.out.println("NET_DVR_SET_FACESNAPCFG failed!" + "err: " + HCNetSDK.getInstance().NET_DVR_GetLastError());
		}
		else
		{
			System.out.println("NET_DVR_SET_FACESNAPCFG succ");
		}*/

		NET_DVR_ALARMIN_PARAM struTest3 = new NET_DVR_ALARMIN_PARAM();
		//Set
		struTest3.byAlarmType = 1;
        struTest3.wDetectorType = 1;
        struTest3.byType = 0;
		String strName = new String("123456");
		byte[] byName = strName.getBytes();
        System.arraycopy(byName, 0, struTest3.byName, 0, byName.length);
		if(!HCNetSDK.getInstance().NET_DVR_SetDVRConfig(iUserID, HCNetSDK.NET_DVR_SET_ALARMIN_PARAM, 1, struTest3))
		{
			System.out.println("NET_DVR_SET_ALARMIN_PARAM failed!" + "err: " + HCNetSDK.getInstance().NET_DVR_GetLastError());
		}
		else
		{
			System.out.println("NET_DVR_SET_ALARMIN_PARAM succ");
		}

		NET_DVR_MULTI_ALARMIN_COND struCond = new NET_DVR_MULTI_ALARMIN_COND();
		NET_DVR_ALARMIN_PARAM_LIST struList = new NET_DVR_ALARMIN_PARAM_LIST();

		for (int k = 0; k < 64; k++)
		{
			struCond.iZoneNo[k] = -1;
		}
		for(int m = 0; m < 32; m++)
		{
			struCond.iZoneNo[m] = m;
		}

        if (!HCNetSDK.getInstance().NET_DVR_GetSTDConfig(iUserID, HCNetSDK.NET_DVR_GET_ALARMIN_PARAM_LIST, struCond, null, struList))
		{
			System.out.println("NET_DVR_GetSTDConfig" + " err: " + HCNetSDK.getInstance().NET_DVR_GetLastError());
		}
		else
		{
			System.out.println("NET_DVR_GetSTDConfig success");
		}

		NET_DVR_CALL_STATUS struCallStatus = new NET_DVR_CALL_STATUS();
		if (!HCNetSDK.getInstance().NET_DVR_GetDeviceStatus(iUserID, HCNetSDK.NET_DVR_GET_CALL_STATUS, null, struCallStatus))
		{
			System.out.println("NET_DVR_GET_CALL_STATUS failed!" + "err: " + HCNetSDK.getInstance().NET_DVR_GetLastError());
		}
		else
		{
			System.out.println("NET_DVR_GET_CALL_STATUS succ");
		}
	}

	public static void Test_EzvizXMLConfig(int iUserID)
	{
		NET_DVR_XML_CONFIG_INPUT    struInput = new NET_DVR_XML_CONFIG_INPUT();
        NET_DVR_XML_CONFIG_OUTPUT   struOuput = new NET_DVR_XML_CONFIG_OUTPUT();
        String strInput = new String("GET /ISAPI/SecurityCP/AlarmControlByPhone\r\n");
        byte[] byInput = strInput.getBytes();
        System.arraycopy(byInput, 0, struInput.lpRequestUrl, 0, byInput.length);
        struInput.dwRequestUrlLen = byInput.length;
        struOuput.dwOutBufferSize = HCNetSDK.MAX_XML_CONFIG_LEN;
        struOuput.dwStatusSize = struOuput.dwOutBufferSize;
        //struInput.lpRequestUrl = strInput.getBytes();
        //struInput.lpInBuffer = null;
        if (!HCNetSDK.getInstance().NET_DVR_STDXMLConfig(iUserID, struInput, struOuput))
		{
			System.out.println("NET_DVR_STDXMLConfig" + " err: " + HCNetSDK.getInstance().NET_DVR_GetLastError());
		}
		else
		{
			System.out.println("NET_DVR_STDXMLConfig success");
		}
        byte[] byOutput = new byte[struOuput.dwReturnedXMLSize];
        System.arraycopy(struOuput.lpOutBuffer, 0, byOutput, 0, struOuput.dwReturnedXMLSize);
        System.out.println(new String(byOutput));

        //Set
        struInput = new NET_DVR_XML_CONFIG_INPUT();
        struOuput = new NET_DVR_XML_CONFIG_OUTPUT();
        strInput = new String("PUT /ISAPI/SecurityCP/AlarmControlByPhone\r\n");
        byInput = strInput.getBytes();
        System.arraycopy(byInput, 0, struInput.lpRequestUrl, 0, byInput.length);
        struInput.dwRequestUrlLen = byInput.length;
        String strInBuffer = new String("<AlarmControlByPhoneCfg version=\"2.0\" xmlns=\"http://www.isapi.org/ver20/XMLSchema\">\r\n<commandType>closeAlarm</commandType>\r\n</AlarmControlByPhoneCfg>\r\n");
        byte[] byInBuffer = strInBuffer.getBytes();
        System.arraycopy(byInBuffer,  0, struInput.lpInBuffer, 0, byInBuffer.length);
        struInput.dwInBufferSize = strInBuffer.length();
        struOuput.dwOutBufferSize = HCNetSDK.MAX_XML_CONFIG_LEN;
        struOuput.dwStatusSize = struOuput.dwOutBufferSize;
        if (!HCNetSDK.getInstance().NET_DVR_STDXMLConfig(iUserID, struInput, struOuput))
		{
			System.out.println("NET_DVR_STDXMLConfig" + " err: " + HCNetSDK.getInstance().NET_DVR_GetLastError());
		}
		else
		{
			System.out.println("NET_DVR_STDXMLConfig success");
			//strOutput = new String(struOuput.lpStatusBuffer);
		}
	}

	public static void Test_EzvizServerDeviceInfo(int iUserID)
	{
    	NET_DVR_SERVER_DEVICE_INFO struServerDeviceInfo = new NET_DVR_SERVER_DEVICE_INFO();
    	if (!HCNetSDK.getInstance().NET_DVR_GetDVRConfig(iUserID,HCNetSDK.NET_DVR_GET_SERVER_DEVICE_INFO, 0, struServerDeviceInfo))
		{
			System.out.println("NET_DVR_GET_SERVER_DEVICE_INFO failed!" + "err: " + HCNetSDK.getInstance().NET_DVR_GetLastError());
		}else
		{
			System.out.println("NET_DVR_GET_SERVER_DEVICE_INFO succ" + "deviceNum: " + struServerDeviceInfo.dwDeviceNum);
		}

    	NET_DVR_CALLER_INFO struCallerInfo = new NET_DVR_CALLER_INFO();
    	if (!HCNetSDK.getInstance().NET_DVR_GetDVRConfig(iUserID,HCNetSDK.NET_DVR_GET_CALLER_INFO, 1, struCallerInfo))
		{
			System.out.println("NET_DVR_GET_CALLER_INFO failed!" + "err: " + HCNetSDK.getInstance().NET_DVR_GetLastError());
		}else
		{
			System.out.println("NET_DVR_GET_CALLER_INFO succ" + "BuildingNo: " + struCallerInfo.wBuildingNo);
		}
	}

	public static void Test_EzvizAlarmInParamList(int iUserID)
	{
    	NET_DVR_MULTI_ALARMIN_COND struCond = new NET_DVR_MULTI_ALARMIN_COND();
    	for (int k = 0; k < 64; k++)
		{
			struCond.iZoneNo[k] = -1;
		}
		for(int m = 0; m < 32; m++)
		{
			struCond.iZoneNo[m] = m;
		}
    	NET_DVR_ALARMIN_PARAM_LIST struAlarmInParam = new NET_DVR_ALARMIN_PARAM_LIST();

		if(!HCNetSDK.getInstance().NET_DVR_GetSTDConfig(iUserID, HCNetSDK.NET_DVR_GET_ALARMIN_PARAM_LIST, struCond, null, struAlarmInParam))
		{
			System.out.println("NET_DVR_GET_ALARMIN_PARAM_LIST faild!" + " err: " + HCNetSDK.getInstance().NET_DVR_GetLastError());
		}else
		{
			System.out.println("NET_DVR_GET_ALARMIN_PARAM_LIST succ!" + "type: " + struAlarmInParam.struSingleAlarmInParam[0].byType);
		}
	}

	public static void Test_EzvizCallerInfo(int iUserID)
	{
    	NET_DVR_CALLER_INFO struCallerInfo = new NET_DVR_CALLER_INFO();
    	if (!HCNetSDK.getInstance().NET_DVR_GetDVRConfig(iUserID,HCNetSDK.NET_DVR_GET_CALLER_INFO, 1, struCallerInfo))
		{
			System.out.println("NET_DVR_GET_CALLER_INFO failed!" + "err: " + HCNetSDK.getInstance().NET_DVR_GetLastError());
		}else
		{
			System.out.println("NET_DVR_GET_CALLER_INFO succ" + "BuildingNo: " + struCallerInfo.wBuildingNo);
		}
	}

	public static void Test_EzvizRemoteGatway(int iUserID)
	{
    	NET_DVR_CONTROL_GATEWAY struTest2 = new NET_DVR_CONTROL_GATEWAY();
		//Set
		struTest2.byCommand = 0;
		struTest2.byControlType = 1;
		String strTempString = new String("123456");
		byte[] byTemp = strTempString.getBytes();
        System.arraycopy(byTemp, 0, struTest2.byPassword, 0, byTemp.length);
		if(!HCNetSDK.getInstance().NET_DVR_RemoteControl(iUserID, HCNetSDK.NET_DVR_REMOTECONTROL_GATEWAY, struTest2))
		{
			System.out.println("NET_DVR_REMOTECONTROL_GATEWAY failed!" + "err: " + HCNetSDK.getInstance().NET_DVR_GetLastError());
		}
		else
		{
			System.out.println("NET_DVR_REMOTECONTROL_GATEWAY succ");
		}
	}

	public static void Test_EzvizCallSignal(int iUserID)
	{
    	NET_DVR_VIDEO_CALL_PARAM struTest1 = new NET_DVR_VIDEO_CALL_PARAM();
		//Set
        struTest1.dwCmdType = 0;
        struTest1.wUnitNumber = 2;
		if(!HCNetSDK.getInstance().NET_DVR_SetDVRConfig(iUserID, HCNetSDK.NET_DVR_SET_CALL_SIGNAL, 1, struTest1))
		{
			System.out.println("NET_DVR_SET_CALL_SIGNAL failed!" + "err: " + HCNetSDK.getInstance().NET_DVR_GetLastError());
		}
		else
		{
			System.out.println("NET_DVR_SET_CALL_SIGNAL succ");
		}
	}

	public static void Test_EzvizPawdAuth(int iUserID)
	{
    	NET_DVR_XML_CONFIG_INPUT    struInput = new NET_DVR_XML_CONFIG_INPUT();
        NET_DVR_XML_CONFIG_OUTPUT   struOuput = new NET_DVR_XML_CONFIG_OUTPUT();

        String strInput = new String("PUT /ISAPI/VideoIntercom/passwordAuthentication\r\n");
        byte[] byInput = strInput.getBytes();
        System.arraycopy(byInput, 0, struInput.lpRequestUrl, 0, byInput.length);
        struInput.dwRequestUrlLen = byInput.length;

        String strInBuffer = new String("<PasswordAuthenticationCfg version=\"2.0\" xmlns=\"http://www.isapi.org/ver20/XMLSchema\">\r\n<password>123456</password>\r\n</PasswordAuthenticationCfg>\r\n");
        byte[] byInBuffer = strInBuffer.getBytes();
        System.arraycopy(byInBuffer,  0, struInput.lpInBuffer, 0, byInBuffer.length);
        struInput.dwInBufferSize = strInBuffer.length();
        struOuput.dwOutBufferSize = HCNetSDK.MAX_XML_CONFIG_LEN;
        struOuput.dwStatusSize = struOuput.dwOutBufferSize;
        if (!HCNetSDK.getInstance().NET_DVR_STDXMLConfig(iUserID, struInput, struOuput))
		{
			System.out.println("NET_DVR_STDXMLConfig PUT /ISAPI/VideoIntercom/passwordAuthentication" + " err: " + HCNetSDK.getInstance().NET_DVR_GetLastError());
		}
		else
		{
			System.out.println("NET_DVR_STDXMLConfig PUT /ISAPI/VideoIntercom/passwordAuthentication success");
			//strOutput = new String(struOuput.lpStatusBuffer);
		}
	}

	public static void TestAlarmHostMainStatus(int iUserID)
	{
		NET_DVR_ALARMHOST_MAIN_STATUS_V40 struStatus = new NET_DVR_ALARMHOST_MAIN_STATUS_V40();
		if (!HCNetSDK.getInstance().NET_DVR_GetDVRConfig(iUserID,HCNetSDK.NET_DVR_GET_ALARMHOST_MAIN_STATUS_V40, 0, struStatus))
		{
			System.out.println("NET_DVR_GET_ALARMHOST_MAIN_STATUS_V40 failed!" + "err: " + HCNetSDK.getInstance().NET_DVR_GetLastError());
		}else
		{
			System.out.println("NET_DVR_GET_ALARMHOST_MAIN_STATUS_V40 succ");
		}
	}

	//mutli
	public static void TestScreenConfig(int iUserID)
	{
//		NET_DVR_ALARMHOST_MAIN_STATUS_V40 struStatus = new NET_DVR_ALARMHOST_MAIN_STATUS_V40();
//		if (!HCNetSDK.getInstance().NET_DVR_GetDVRConfig(iUserID,HCNetSDK.NET_DVR_GET_ALARMHOST_MAIN_STATUS_V40, 0, struStatus))
//		{
//			System.out.println("NET_DVR_GET_ALARMHOST_MAIN_STATUS_V40 failed!" + "err: " + HCNetSDK.getInstance().NET_DVR_GetLastError());
//		}else
//		{
//			System.out.println("NET_DVR_GET_ALARMHOST_MAIN_STATUS_V40 succ");
//		}
	}

	public static void TestMCUAbility(int iUserID)
	{
		byte lpInBuf [] = new byte[1024];
		byte lpOutBuf[] = new byte[100*1024*10];

		INT_PTR iRetLen = new INT_PTR();
		//iRetLen.iValue = 0;
		iRetLen.iValue = lpOutBuf.length;
		if(HCNetSDK.getInstance().NET_DVR_GetSTDAbility(iUserID ,9152,null,0,lpOutBuf,100*1024*100,iRetLen))
		{
			System.out.println("NET_DVR_GetSTDAbility Success!" );
		}
		else
		{
			System.out.println("NET_DVR_GetSTDAbility Failed!" + " err: " + HCNetSDK.getInstance().NET_DVR_GetErrorMsg(ptrINT));
		}
	}

	public static void TextTrialMachine(int iUserID, int iChan)
	{
		/*
		NET_DVR_AUDIO_ACTIVATION_CFG struAudioActivation = new NET_DVR_AUDIO_ACTIVATION_CFG();
		if (!HCNetSDK.getInstance().NET_DVR_GetDVRConfig(iUserID,HCNetSDK.NET_DVR_GET_AUDIO_ACTIVATION_CFG, 1, struAudioActivation))
		{
			System.out.println("NET_DVR_GET_AUDIO_ACTIVATION_CFG failed!" + "err: " + HCNetSDK.getInstance().NET_DVR_GetLastError());
		}else
		{
			System.out.println("NET_DVR_GET_AUDIO_ACTIVATION_CFG succ");
		}
		//Set
		//struPreviewDisplay.byMountType = 2;
		//struPreviewDisplay.byRealTimeOutput = 3;
		if(!HCNetSDK.getInstance().NET_DVR_SetDVRConfig(iUserID, HCNetSDK.NET_DVR_SET_AUDIO_ACTIVATION_CFG, 1, struAudioActivation))
		{
			System.out.println("NET_DVR_SET_AUDIO_ACTIVATION_CFG failed!" + "err: " + HCNetSDK.getInstance().NET_DVR_GetLastError());
		}
		else
		{
			System.out.println("NET_DVR_SET_AUDIO_ACTIVATION_CFG succ");
		}

		NET_DVR_AUDIO_DIACRITICAL_CFG struAudioDiacriticl = new NET_DVR_AUDIO_DIACRITICAL_CFG();
		if (!HCNetSDK.getInstance().NET_DVR_GetDVRConfig(iUserID,HCNetSDK.NET_DVR_GET_AUDIO_DIACRITICAL_CFG, 33, struAudioDiacriticl))
		{
			System.out.println("NET_DVR_GET_AUDIO_DIACRITICAL_CFG failed!" + "err: " + HCNetSDK.getInstance().NET_DVR_GetLastError());
		}else
		{
			System.out.println("NET_DVR_GET_AUDIO_DIACRITICAL_CFG succ");
		}
		//Set
		//struPreviewDisplay.byMountType = 2;
		//struPreviewDisplay.byRealTimeOutput = 3;
		struAudioDiacriticl.byEnable = 1;
		if(!HCNetSDK.getInstance().NET_DVR_SetDVRConfig(iUserID, HCNetSDK.NET_DVR_SET_AUDIO_DIACRITICAL_CFG, 33, struAudioDiacriticl))
		{
			System.out.println("NET_DVR_SET_AUDIO_DIACRITICAL_CFG failed!" + "err: " + HCNetSDK.getInstance().NET_DVR_GetLastError());
		}
		else
		{
			System.out.println("NET_DVR_SET_AUDIO_DIACRITICAL_CFG succ");
		}

		NET_DVR_AREA_MASK_CFG struAreaMask = new NET_DVR_AREA_MASK_CFG();
		if (!HCNetSDK.getInstance().NET_DVR_GetDVRConfig(iUserID,HCNetSDK.NET_DVR_GET_AREA_MASK_CFG, 33, struAreaMask))
		{
			System.out.println("NET_DVR_GET_AREA_MASK_CFG failed!" + "err: " + HCNetSDK.getInstance().NET_DVR_GetLastError());
		}else
		{
			System.out.println("NET_DVR_GET_AREA_MASK_CFG succ");
		}
		//Set
		//struPreviewDisplay.byMountType = 2;
		//struPreviewDisplay.byRealTimeOutput = 3;
		struAreaMask.byEnable = 1;
		if(!HCNetSDK.getInstance().NET_DVR_SetDVRConfig(iUserID, HCNetSDK.NET_DVR_SET_AREA_MASK_CFG, 33, struAreaMask))
		{
			System.out.println("NET_DVR_SET_AREA_MASK_CFG failed!" + "err: " + HCNetSDK.getInstance().NET_DVR_GetLastError());
		}
		else
		{
			System.out.println("NET_DVR_SET_AREA_MASK_CFG succ");
		}

		NET_DVR_VOLUME_CFG struVolumeIn = new NET_DVR_VOLUME_CFG();
		if (!HCNetSDK.getInstance().NET_DVR_GetDVRConfig(iUserID,HCNetSDK.NET_DVR_GET_AUDIOIN_VOLUME_CFG, 1, struVolumeIn))
		{
			System.out.println("NET_DVR_GET_AUDIOIN_VOLUME_CFG failed!" + "err: " + HCNetSDK.getInstance().NET_DVR_GetLastError());
		}else
		{
			System.out.println("NET_DVR_GET_AUDIOIN_VOLUME_CFG succ");
		}
		//Set
		//struPreviewDisplay.byMountType = 2;
		//struPreviewDisplay.byRealTimeOutput = 3;
		if(!HCNetSDK.getInstance().NET_DVR_SetDVRConfig(iUserID, HCNetSDK.NET_DVR_SET_AUDIOIN_VOLUME_CFG, 1, struVolumeIn))
		{
			System.out.println("NET_DVR_SET_AUDIOIN_VOLUME_CFG failed!" + "err: " + HCNetSDK.getInstance().NET_DVR_GetLastError());
		}
		else
		{
			System.out.println("NET_DVR_SET_AUDIOIN_VOLUME_CFG succ");
		}

		NET_DVR_VOLUME_CFG struVolumeOut = new NET_DVR_VOLUME_CFG();
		if (!HCNetSDK.getInstance().NET_DVR_GetDVRConfig(iUserID,HCNetSDK.NET_DVR_GET_AUDIOOUT_VOLUME_CFG, 1, struVolumeOut))
		{
			System.out.println("NET_DVR_GET_AUDIOOUT_VOLUME_CFG failed!" + "err: " + HCNetSDK.getInstance().NET_DVR_GetLastError());
		}else
		{
			System.out.println("NET_DVR_GET_AUDIOOUT_VOLUME_CFG succ");
		}
		//Set
		//struPreviewDisplay.byMountType = 2;
		//struPreviewDisplay.byRealTimeOutput = 3;
		if(!HCNetSDK.getInstance().NET_DVR_SetDVRConfig(iUserID, HCNetSDK.NET_DVR_SET_AUDIOOUT_VOLUME_CFG, 1, struVolumeOut))
		{
			System.out.println("NET_DVR_SET_AUDIOOUT_VOLUME_CFG failed!" + "err: " + HCNetSDK.getInstance().NET_DVR_GetLastError());
		}
		else
		{
			System.out.println("NET_DVR_SET_AUDIOOUT_VOLUME_CFG succ");
		}
		*/
		NET_DVR_INFRARED_CMD_NAME_CFG struInfraredCmdName = new NET_DVR_INFRARED_CMD_NAME_CFG();
		if (!HCNetSDK.getInstance().NET_DVR_GetDVRConfig(iUserID,HCNetSDK.NET_DVR_GET_INFRARED_CMD_NAME_CFG, 1, struInfraredCmdName))
		{
			System.out.println("NET_DVR_GET_INFRARED_CMD_NAME_CFG failed!" + "err: " + HCNetSDK.getInstance().NET_DVR_GetLastError());
		}else
		{
			System.out.println("NET_DVR_GET_INFRARED_CMD_NAME_CFG succ");
		}
		//Set
		//struPreviewDisplay.byMountType = 2;
		//struPreviewDisplay.byRealTimeOutput = 3;
		if(!HCNetSDK.getInstance().NET_DVR_SetDVRConfig(iUserID, HCNetSDK.NET_DVR_SET_INFRARED_CMD_NAME_CFG, 1, struInfraredCmdName))
		{
			System.out.println("NET_DVR_SET_INFRARED_CMD_NAME_CFG failed!" + "err: " + HCNetSDK.getInstance().NET_DVR_GetLastError());
		}
		else
		{
			System.out.println("NET_DVR_SET_INFRARED_CMD_NAME_CFG succ");
		}
		/*

		NET_DVR_USER_V30 struUserV30 = new NET_DVR_USER_V30();
		if (!HCNetSDK.getInstance().NET_DVR_GetDVRConfig(iUserID,HCNetSDK.NET_DVR_GET_USERCFG_V30, 1, struUserV30))
		{
			System.out.println("NET_DVR_GET_USERCFG_V30 failed!" + "err: " + HCNetSDK.getInstance().NET_DVR_GetLastError());
		}else
		{
			System.out.println("NET_DVR_GET_USERCFG_V30 succ");
		}
		//Set
		//struPreviewDisplay.byMountType = 2;
		//struPreviewDisplay.byRealTimeOutput = 3;
		if(!HCNetSDK.getInstance().NET_DVR_SetDVRConfig(iUserID, HCNetSDK.NET_DVR_SET_USERCFG_V30, 1, struUserV30))
		{
			System.out.println("NET_DVR_SET_USERCFG_V30 failed!" + "err: " + HCNetSDK.getInstance().NET_DVR_GetLastError());
		}
		else
		{
			System.out.println("NET_DVR_SET_USERCFG_V30 succ");
		}

		NET_DVR_TRIAL_SYSTEM_INFO struTrialSystemInfo = new NET_DVR_TRIAL_SYSTEM_INFO();
		if (!HCNetSDK.getInstance().NET_DVR_GetDVRConfig(iUserID,HCNetSDK.NET_DVR_GET_TRIAL_SYSTEM_CFG, 1, struTrialSystemInfo))
		{
			System.out.println("NET_DVR_GET_TRIAL_SYSTEM_CFG failed!" + "err: " + HCNetSDK.getInstance().NET_DVR_GetLastError());
		}else
		{
			System.out.println("NET_DVR_GET_TRIAL_SYSTEM_CFG succ");
		}

		NET_DVR_INQUEST_ROOM struInquestRoom = new NET_DVR_INQUEST_ROOM();
		struInquestRoom.byRoomIndex = 1;
		if (!HCNetSDK.getInstance().NET_DVR_InquestStartCDW_V30(iUserID, struInquestRoom, false))
		{
			System.out.println("NET_DVR_InquestStartCDW_V30 failed!" + "err: " + HCNetSDK.getInstance().NET_DVR_GetLastError());
		}else
		{
			System.out.println("NET_DVR_InquestStartCDW_V30 succ");
		}

		if (!HCNetSDK.getInstance().NET_DVR_RemoteControl(iUserID, HCNetSDK.NET_DVR_INQUEST_PAUSE_CDW, struInquestRoom))
		{
			System.out.println("NET_DVR_INQUEST_PAUSE_CDW" + " err: " + HCNetSDK.getInstance().NET_DVR_GetLastError());
		}
		else
		{
			System.out.println("NET_DVR_INQUEST_PAUSE_CDW success");
		}

		if (!HCNetSDK.getInstance().NET_DVR_RemoteControl(iUserID, HCNetSDK.NET_DVR_INQUEST_RESUME_CDW, struInquestRoom))
		{
			System.out.println("NET_DVR_INQUEST_RESUME_CDW" + " err: " + HCNetSDK.getInstance().NET_DVR_GetLastError());
		}
		else
		{
			System.out.println("NET_DVR_INQUEST_RESUME_CDW success");
		}

		if (!HCNetSDK.getInstance().NET_DVR_InquestStopCDW_V30(iUserID, struInquestRoom, false))
		{
			System.out.println("NET_DVR_InquestStopCDW_V30 failed!" + "err: " + HCNetSDK.getInstance().NET_DVR_GetLastError());
		}else
		{
			System.out.println("NET_DVR_InquestStopCDW_V30 succ");
		}


		NET_DVR_INQUEST_PIP_STATUS_V40 struInquestPipStatus_V40 = new NET_DVR_INQUEST_PIP_STATUS_V40();
		if (!HCNetSDK.getInstance().NET_DVR_InquestGetPIPStatus_V40(iUserID, struInquestRoom, struInquestPipStatus_V40))
		{
			System.out.println("NET_DVR_InquestGetPIPStatus_V40 failed!" + "err: " + HCNetSDK.getInstance().NET_DVR_GetLastError());
		}else
		{
			System.out.println("NET_DVR_InquestGetPIPStatus_V40 succ");
		}
		if (!HCNetSDK.getInstance().NET_DVR_InquestSetPIPStatus_V40(iUserID, struInquestRoom, struInquestPipStatus_V40))
		{
			System.out.println("NET_DVR_InquestSetPIPStatus_V40 failed!" + "err: " + HCNetSDK.getInstance().NET_DVR_GetLastError());
		}else
		{
			System.out.println("NET_DVR_InquestSetPIPStatus_V40 succ");
		}

		NET_DVR_INFRARED_OUTPUT_CTRL_CFG struInfraredOutputCtrlCfg = new NET_DVR_INFRARED_OUTPUT_CTRL_CFG();
		struInfraredOutputCtrlCfg.byIRCmdIndex = 1;
		struInfraredOutputCtrlCfg.byIROutPort = 1;
		if (!HCNetSDK.getInstance().NET_DVR_RemoteControl(iUserID, HCNetSDK.NET_DVR_INFRARED_OUTPUT_CONTROL, struInfraredOutputCtrlCfg))
		{
			System.out.println("NET_DVR_INFRARED_OUTPUT_CONTROL" + " err: " + HCNetSDK.getInstance().NET_DVR_GetLastError());
		}
		else
		{
			System.out.println("NET_DVR_INFRARED_OUTPUT_CONTROL success");
		}


		NET_DVR_PREVIEW_SWITCH_COND struPreviewSwitchCond = new NET_DVR_PREVIEW_SWITCH_COND();
		NET_DVR_PREVIEW_SWITCH_CFG struPreviewSwitchCfg= new NET_DVR_PREVIEW_SWITCH_CFG();
		struPreviewSwitchCond.byGroup = 0;
		struPreviewSwitchCond.byVideoOutType = 1;
		if (!HCNetSDK.getInstance().NET_DVR_GetDeviceConfig(iUserID, NET_DVR_DEVICE_CONFIG_COMMAND.NET_DVR_GET_PREVIEW_SWITCH_CFG, struPreviewSwitchCond, struPreviewSwitchCfg))
		{
			System.out.println("NET_DVR_GET_PREVIEW_SWITCH_CFG failed!" + "err: " + HCNetSDK.getInstance().NET_DVR_GetLastError());
		}else
		{
			System.out.println("NET_DVR_GET_PREVIEW_SWITCH_CFG succ: ");
		}
		//Set
		//struPreviewDisplay.byMountType = 2;
		//struPreviewDisplay.byRealTimeOutput = 3;
		for (int i = 0; i < HCNetSDK.MAX_WINDOW_V40; i++)
		{
			struPreviewSwitchCfg.wSwitchSeq[i] = 0xFFFF;
		}
		if(!HCNetSDK.getInstance().NET_DVR_SetDeviceConfig(iUserID,  NET_DVR_DEVICE_CONFIG_COMMAND.NET_DVR_SET_PREVIEW_SWITCH_CFG, struPreviewSwitchCond, struPreviewSwitchCfg))
		{
			System.out.println("NET_DVR_SET_PREVIEW_SWITCH_CFG failed!" + "err: " + HCNetSDK.getInstance().NET_DVR_GetLastError());
		}
		else
		{
			System.out.println("NET_DVR_SET_PREVIEW_SWITCH_CFG succ");
		}
		*/
	}

	   public static void TestWIRELESSDIAL_CFG(int iUserID, int iChan)
	    {
	       NET_DVR_WIRELESSDIAL_CFG struWirelessDial = new NET_DVR_WIRELESSDIAL_CFG();
	       NET_DVR_COND_INT condInt = new NET_DVR_COND_INT();
	       condInt.iValue = 1;

//	       struWirelessDial.

	        String strInput = new String("疯");
	        byte[] byInput = strInput.getBytes();
	        System.arraycopy(byInput, 0, struWirelessDial.byUserName, 0, byInput.length);
//	       struWirelessDial.byUserName = "疯";

	        if (!HCNetSDK.getInstance().NET_DVR_SetSTDConfig(iUserID,HCNetSDK.NET_DVR_SET_WIRELESS_DIAL, condInt, struWirelessDial, null))
	        {
	            System.out.println("NET_DVR_GET_WIRELESS_DIAL failed!" + "err: " + HCNetSDK.getInstance().NET_DVR_GetLastError());
	        }else
	        {
	            System.out.println("NET_DVR_GET_WIRELESS_DIAL succ" + struWirelessDial.byUserName.toString());
	        }

	    }

		public static void Text_Trail_ABILITY(int iUserID)
		{

			byte[] arrayOutBuf = new byte[64*1024];
			INT_PTR intPtr = new INT_PTR();
			String strInput = new String("<TrialHostAbility version='2.0'></TrialHostAbility>");
			byte[] arrayInBuf = new byte[8*1024];
			arrayInBuf = strInput.getBytes();
			if(!HCNetSDK.getInstance().NET_DVR_GetXMLAbility(iUserID, HCNetSDK.DEVICE_ABILITY_INFO,arrayInBuf, strInput.length(), arrayOutBuf, 64*1024, intPtr))
			{
				System.out.println("get Trail_ABILITY faild!" + " err: " + HCNetSDK.getInstance().NET_DVR_GetLastError());
			}
			else
			{
				System.out.println("get Trail_ABILITY succ!");
			}
		}

   public static void Test_LEDArea(int iUserID)
    {
	   HCNetSDKByJNA.NET_DVR_STD_CONFIG struSTDConfig = new HCNetSDKByJNA.NET_DVR_STD_CONFIG();

	   HCNetSDKByJNA.NET_DVR_LED_AREA_COND struCond = new HCNetSDKByJNA.NET_DVR_LED_AREA_COND();
	   struCond.dwSize = struCond.size();
	   struCond.dwVideoWallNo = 1;
	   struCond.write();

	   HCNetSDKByJNA.NET_DVR_LED_AREA_INFO struArea = new HCNetSDKByJNA.NET_DVR_LED_AREA_INFO();
	   struArea.dwSize = struArea.size();
	   struArea.write();

	   HCNetSDKByJNA.NET_DVR_LED_AREA_INFO_LIST struAreaList = new HCNetSDKByJNA.NET_DVR_LED_AREA_INFO_LIST();
	   struAreaList.dwSize = struAreaList.size();
	   struAreaList.lpstruBuffer = struArea.getPointer();
	   struAreaList.dwBufferSize = struArea.dwSize;
	   struAreaList.write();

	   struSTDConfig.lpCondBuffer = struCond.getPointer();
	   struSTDConfig.dwCondSize = struCond.dwSize;
	   struSTDConfig.lpOutBuffer = struAreaList.getPointer();
	   struSTDConfig.dwOutSize = struAreaList.dwSize;
	   struSTDConfig.write();

	   if (!HCNetSDKJNAInstance.getInstance().NET_DVR_GetSTDConfig(iUserID,HCNetSDKByJNA.NET_DVR_GET_LED_AREA_INFO_LIST, struSTDConfig.getPointer()))
	   {
		   System.out.println("NET_DVR_GET_LED_AREA_INFO_LIST failed!" + "err: " + HCNetSDK.getInstance().NET_DVR_GetLastError());
	   }else
	   {
		   System.out.println("NET_DVR_GET_LED_AREA_INFO_LIST succ");
	   }
    }

	public static void TEST_Config(int iPreviewID, int iUserID, int iChan)
	{
//		Test_LEDArea(iUserID);
		TextTrialMachine(iUserID, iChan);
//		Test_EzvizPawdAuth(iUserID);
//		Test_EzvizCallSignal(iUserID);
//		Test_EzvizRemoteGatway(iUserID);
//		Test_EzvizCallerInfo(iUserID);
//		Test_EzvizAlarmInParamList(iUserID);
//		Test_EzvizServerDeviceInfo(iUserID);
//		Test_EzvizXMLConfig(iUserID);
//		Test_EzvizCreate();

//		TextOSD(iUserID,iChan);
//		Test_CAMERAPARAMCFG_EX(iUserID, iChan);
//		Test_XMLAbility(iUserID);
//		Test_PTZProtocol(iUserID);
//		Test_PresetName(iUserID, iChan);
//		Test_ShowString(iUserID, iChan);
//		Test_DigitalChannelState(iUserID);
//		Test_DDNSPara(iUserID);
//		Test_APInfoList(iUserID);
//		Test_WifiCfg(iUserID);
//		Test_WifiStatus(iUserID);
//		Test_UpnpNatState(iUserID);
//		Test_UserCfg(iUserID);
//		Test_DeviceCfg(iUserID);
//		Test_PicCfg(iUserID, iChan);
//		Test_ZeroChanCfg(iUserID);
//		Test_WorkState(iUserID);
//		Test_RecordCfg(iUserID, iChan);
//		Test_AuxAlarmCfg(iUserID, iChan);
//		Test_AlarminCfg(iUserID);
//		Test_AlarmOutCfg(iUserID);
//		Test_DecoderCfg(iUserID);
//		Test_NTPPara(iUserID);
//		Test_IPAlarmOutCfg(iUserID);
//		Test_IPParaCfg(iUserID);
//		Test_NetCfg(iUserID);
//		Test_CompressionCfg(iUserID, iChan);
//		Test_CompressCfgAud(iUserID);
//		Test_AlarmOutStatus(iUserID);
//		Test_VideoEffect(iPreviewID);
//		Test_DeviceCfg_V40(iUserID);
//		Test_ExceptionCfg_V40(iUserID, iChan);
//		Test_InquestControlCDW(iUserID);
//		Test_AreaMaskCfg(iUserID, iChan);
//		Test_AudioDiaCritical(iUserID, iChan);
//		Test_Preview_display(iUserID, iChan);
//		Text_FISHEYE_ABILITY(iUserID);
//		TextAlarmHostMainStatus(iUserID);
//		TestMCUAbility(iUserID);
	}
}
