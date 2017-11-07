package com.thomas.hikvision;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.BaseAdapter;
import android.widget.GridLayout;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Thomas.Wang on 2017/5/23.
 */

public class CaptureRecord extends Activity implements View.OnClickListener {
    private TextView title;
    private GridView contentGV;
    private final static String TAG = "CaptureRecord";
    private String channelName ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);//去掉标题栏 第一种方法
        setContentView(getIdTypeLayout("activity_capture_record"));
        getParams();
        findViews();
        setAdapter();
    }

    private void getParams() {
        channelName = getIntent().getStringExtra("channelName");
    }

    private void setAdapter() {
        //为GridView设置适配器
        contentGV.setAdapter(new MyAdapter());
    }

    private void findViews() {
        findViewById(getIdTypeId("record_btn_back")).setOnClickListener(this);
        title = (TextView) findViewById(getIdTypeId("record_tv_title"));
        contentGV= (GridView)findViewById(getIdTypeId("record_gv_content"));
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
        if (id==getIdTypeId("record_btn_back")){
            finish();
        }
    }
    class MyAdapter extends BaseAdapter {
        List<Bitmap> bitmaps = new ArrayList<Bitmap>();
        public MyAdapter() {
            //2.获取sd卡下的图片并显示
            List<String> list = getPictures(Environment.getExternalStorageDirectory() + "/hikvision/"+channelName+"/");
            if (list!=null){
                for (int i = 0;i<list.size();i++){
                    Bitmap bm = BitmapFactory.decodeFile(list.get(i));
                    bitmaps.add(bm);
                }
            }
        }

        @Override
        public int getCount() {
            return bitmaps.size();
        }

        @Override
        public Object getItem(int position) {
            return bitmaps.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ImageView imageView;
            if (convertView == null) {
                imageView = new ImageView(CaptureRecord.this);
                imageView.setLayoutParams(new GridView.LayoutParams(GridLayout.LayoutParams.MATCH_PARENT, GridLayout.LayoutParams.MATCH_PARENT));//设置ImageView对象布局
//                imageView.setAdjustViewBounds(false);//设置边界对齐
//                imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);//设置刻度的类型
//                imageView.setPadding(8, 8, 8, 8);//设置间距
            }
            else {
                imageView = (ImageView) convertView;
            }
            imageView.setImageBitmap(bitmaps.get(position));
            return imageView;
        }
    }
// 1.获取SDCard中某个目录下图片路径集合

    public List<String> getPictures(final String strPath) {
        List<String> list = new ArrayList<String>();
        File file = new File(strPath);
        File[] allfiles = file.listFiles();
        if (allfiles == null) {
            return null;
        }
        for(int k = 0; k < allfiles.length; k++) {
            final File fi = allfiles[k];
            if(fi.isFile()) {
                int idx = fi.getPath().lastIndexOf(".");
                if (idx <= 0) {
                    continue;
                }
                String suffix = fi.getPath().substring(idx);
                if (suffix.toLowerCase().equals(".jpg") ||
                        suffix.toLowerCase().equals(".jpeg") ||
                        suffix.toLowerCase().equals(".bmp") ||
                        suffix.toLowerCase().equals(".png") ||
                        suffix.toLowerCase().equals(".gif") ) {
                    list.add(fi.getPath());
                }
            }
        }
        return list;
    }




}
