package com.example.kotlin.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.kotlin.R;
import com.example.kotlin.data.SerializableData;
import com.example.kotlin.utils.AssetsUtils;
import com.example.kotlin.utils.ToastUtil;
import com.example.kotlin.utils.UriToPath;

import java.io.File;
import java.io.FileInputStream;
import java.security.MessageDigest;

public class MD5Activity extends AppCompatActivity {
    Button send_data_btn;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_md5);
        send_data_btn = findViewById(R.id.send_data_btn);
        send_data_btn.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                SerializableData tmp = new SerializableData();
                tmp.setYWDXBH("bundle");
                tmp.setZLWH("bundle");
                tmp.setZCZLBH("bundle");

                Bundle bundle = new Bundle();
                bundle.putSerializable("data", tmp);
                Intent intent = new Intent(MD5Activity.this, com.example.kotlin.activity.PickViewActivity.class);
                intent.putExtra("data_bundle", bundle);
                intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);
                return false;
            }
        });

        AssetsUtils assetsUtils = new AssetsUtils();
        String treeData = assetsUtils.readAssetsText(this, "tree.json");
        ((TextView) findViewById(R.id.local_data)).setText(treeData);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        if (intent.getSerializableExtra("data") != null) {
            ToastUtil.showTextViewPrompt("其中的一个参数为" + ((SerializableData) intent.getSerializableExtra("data")).getYWDXBH());
        } else {
            Bundle bundle = intent.getBundleExtra("data_bundle");
            SerializableData data = (SerializableData) bundle.getSerializable("data");
            ToastUtil.showTextViewPrompt("其中的一个参数为" + data.getYWDXBH());
        }
    }

    public void pick(View view) {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType("*/*");
        this.startActivityForResult(intent, 1);
    }

//    //MD5加密文件
//    private void getFile() {
//        File file = new File(file.getAbsolutePath());//文件路径
//        String md5 = getFileMD5(file);//得到MD5码
//        Log.i(TAG, "加密文件的MD5为:======="+md5);
//    }

    public static String getFileMD5(File file) {
        if (!file.isFile()) {
            return null;
        }
        MessageDigest digest = null;
        FileInputStream in = null;
        byte buffer[] = new byte[1024];
        int len;
        try {
            digest = MessageDigest.getInstance("MD5");
            in = new FileInputStream(file);
            while ((len = in.read(buffer, 0, 1024)) != -1) {
                digest.update(buffer, 0, len);
            }
            in.close();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return bytesToHexString(digest.digest());
    }


    public static String bytesToHexString(byte[] src) {
        StringBuilder stringBuilder = new StringBuilder("");
        if (src == null || src.length <= 0) {
            return null;
        }
        for (int i = 0; i < src.length; i++) {
            int v = src[i] & 0xFF;
            String hv = Integer.toHexString(v);
            if (hv.length() < 2) {
                stringBuilder.append(0);
            }
            stringBuilder.append(hv);
        }
        return stringBuilder.toString();
    }


    public void pickFile(View view) {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType("*/*");
        this.startActivityForResult(intent, 1);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data == null) {
            // 用户未选择任何文件，直接返回
            return;
        }
        Uri uri = data.getData(); // 获取用户选择文件的URI
        ToastUtil.showTextViewPrompt(UriToPath.getFileAbsolutePath(this, uri));
    }


}


