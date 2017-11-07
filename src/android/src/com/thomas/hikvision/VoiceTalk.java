package com.thomas.hikvision;

import com.hikvision.audio.*;
import com.hikvision.audio.AudioCodecParam.AudioBitRate;
import com.hikvision.audio.AudioCodecParam.AudioBitWidth;
import com.hikvision.audio.AudioCodecParam.AudioChannel;
import com.hikvision.audio.AudioCodecParam.AudioEncodeType;
import com.hikvision.audio.AudioCodecParam.AudioSampleRate;
import com.hikvision.audio.AudioEngineCallBack.RecordDataCallBack;
import com.hikvision.netsdk.*;

public class VoiceTalk {
	private static int m_iVoiceTalkID = -1;
	private static VoiceDataCallBack TalkCbf = null;
	private static AudioEngine audio = null;
	private static int iRet = -1;
	private static RecordDataCallBack	AudioCbf = null;

	public static int startVoiceTalk(int iUserID)//only support G711A/U and G722, but G722 Non publication function
	{
		//get the device current valid audio type
		NET_DVR_COMPRESSION_AUDIO compressAud = new NET_DVR_COMPRESSION_AUDIO();
		if(!HCNetSDK.getInstance().NET_DVR_GetCurrentAudioCompress(iUserID, compressAud))
		{
			System.out.println("NET_DVR_GetCurrentAudioCompress failed, error:" + HCNetSDK.getInstance().NET_DVR_GetLastError());
			return -1;
		}

		AudioCodecParam AudioParam = new AudioCodecParam();
		AudioParam.nVolume = 100; //the volume is between 0~100
		AudioParam.nChannel = AudioChannel.AUDIO_CHANNEL_MONO;
		AudioParam.nBitWidth = AudioBitWidth.AUDIO_WIDTH_16BIT;
		if(compressAud.byAudioEncType == 1)//G711_U
		{
			AudioParam.nCodecType = AudioEncodeType.AUDIO_TYPE_G711U;
			AudioParam.nSampleRate = AudioSampleRate.AUDIO_SAMPLERATE_8K;
			AudioParam.nBitRate = AudioBitRate.AUDIO_BITRATE_16K;
		}
		else if(compressAud.byAudioEncType == 2)//G711_A
		{
			AudioParam.nCodecType = AudioEncodeType.AUDIO_TYPE_G711A;
			AudioParam.nSampleRate = AudioSampleRate.AUDIO_SAMPLERATE_8K;
			AudioParam.nBitRate = AudioBitRate.AUDIO_BITRATE_16K;
		}
		else if(compressAud.byAudioEncType == 0) //G722
		{
			AudioParam.nCodecType = AudioEncodeType.AUDIO_TYPE_G722;
			AudioParam.nSampleRate = AudioSampleRate.AUDIO_SAMPLERATE_16K;
			AudioParam.nBitRate = AudioBitRate.AUDIO_BITRATE_16K;
		}
		else
		{
			System.out.println("the device audio type is not support by AudioEngineSDK for android ,type:" + compressAud.byAudioEncType);
			return -1;
		}
		//start AudioEngine
		if(!startAudioEngine(AudioParam))
		{
			return -1;
		}
		//start HCNetSDK
		if (TalkCbf==null)
		{
			TalkCbf = new VoiceDataCallBack()
			{
				public void fVoiceDataCallBack(int lVoiceComHandle, byte[] pDataBuffer, int iDataSize, int iAudioFlag)
				{
					processDeviceVoiceData(lVoiceComHandle, pDataBuffer, iDataSize, iAudioFlag);
				}
			};
		}
		m_iVoiceTalkID = HCNetSDK.getInstance().NET_DVR_StartVoiceCom_MR_V30(iUserID, 1, TalkCbf);
		if (-1 == m_iVoiceTalkID)
		{
			stopVoiceTalk();
			System.out.println("NET_DVR_StartVoiceCom_MR_V30 failed,error:" + HCNetSDK.getInstance().NET_DVR_GetLastError());
		}
		return m_iVoiceTalkID;
	}
	public static boolean stopVoiceTalk()
	{
		HCNetSDK.getInstance().NET_DVR_StopVoiceCom(m_iVoiceTalkID);
		audio.stopRecord();
		audio.stopPlay();
		audio.close();
		return true;
	}
	private static boolean startAudioEngine(AudioCodecParam AudioParam)
	{
		if(audio == null)
		{
			audio = new AudioEngine(AudioEngine.CAE_INTERCOM);
		}
		//open audio engine
		iRet =audio.open();
		if(iRet != 0)
		{
			System.out.println("audio engine open failed, error:" + iRet);
			return false;
		}
		//set parameter
		iRet = audio.setAudioParam(AudioParam, AudioEngine.PARAM_MODE_PLAY);
		if(iRet != 0)
		{
			System.out.println("audio.setAudioParam PARAM_MODE_PLAY failed, error:" + iRet);
			audio.close();
			return false;
		}
		iRet = audio.setAudioParam(AudioParam, AudioEngine.PARAM_MODE_RECORDE);
		if(iRet != 0)
		{
			System.out.println("audio.setAudioParam PARAM_MODE_RECORDE failed, error:" + iRet);
			audio.close();
			return false;
		}
		//set callback
		if(AudioCbf == null)
		{
			AudioCbf = new RecordDataCallBack()
			{
				public void onRecordDataCallBack(byte[] buf, int size)
				{
					processLocalVoiceData(buf, size);
				}
			};
		}
		iRet = audio.setAudioCallBack(AudioCbf, AudioEngine.RECORDE_DATA_CALLBACK);
		if(iRet != 0)
		{
			System.out.println("audio.setAudioCallBack RECORDE_DATA_CALLBACK failed, error:" + iRet);
			audio.close();
			return false;
		}
		iRet = audio.startPlay();
		if(iRet != 0)
		{
			System.out.println("audio.startPlay failed, error:" + iRet);
			audio.close();
			return false;
		}
		iRet = audio.startRecord();
		if(iRet != 0)
		{
			System.out.println("audio.startRecord failed, error:" + iRet);
			audio.stopPlay();
			audio.close();
			return false;
		}
		return true;
	}
	private static void processDeviceVoiceData(int lVoiceComHandle, byte[] pDataBuffer, int iDataSize, int iAudioFlag)
    {
    	audio.inputData(pDataBuffer, iDataSize)	;
    }
	private static void processLocalVoiceData(byte[] buf, int size)
	{
		HCNetSDK.getInstance().NET_DVR_VoiceComSendData(m_iVoiceTalkID, buf, size);
	}


	public static void TEST_VoiceTalk(int iUserID)
	{
		VoiceTalk.startVoiceTalk(iUserID);
		Thread thread = new Thread()
		{
	   		public void run()
	   		{
	   			try
	   			{
					sleep(30*1000);
				} catch (InterruptedException e)
				{
					e.printStackTrace();
				}
	   			VoiceTalk.stopVoiceTalk();
	   		}
	   	};
	   	thread.start();
	}
}
