package com.atguigu.retrofitdownload.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import com.atguigu.retrofitdownload.R;
import com.atguigu.retrofitdownload.listener.ResponseListener;
import com.atguigu.retrofitdownload.utils.RetrofitUtils;
import com.atguigu.retrofitdownload.view.MyProgressBar;

import java.io.File;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class DownloadActivity extends AppCompatActivity {

    @Bind(R.id.progresss)
    MyProgressBar progresss;
    @Bind(R.id.tv_progress)
    TextView tvProgress;
    @Bind(R.id.bt_download)
    Button btDownload;
    File file;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_download);
        ButterKnife.bind(this);
        file = new File(getExternalFilesDir(null), "1.apk");
    }

    @OnClick(R.id.bt_download)
    public void onClick() {
        btDownload.setText("下载中!");
        btDownload.setEnabled(false);
        RetrofitUtils.getInstance().download(file, new ResponseListener() {
            @Override
            public void onProgress(final long progress, final long total, boolean done) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        long l = progress * 100 / total;
                        progresss.setProgress((int) l);

                        String pro = (int) progress * 100 / (int) total + "%";
                        String p = RetrofitUtils.formetFileSize(progress);
                        String t = RetrofitUtils.formetFileSize(total);
                        tvProgress.setText(p + " / " + t + "\t" + pro);
                    }
                });
            }

            @Override
            public void onResponse() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        btDownload.setText("下载完成!");
                    }
                });
            }

            @Override
            public void onFailure(final String error) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        btDownload.setText("下载出错!");
                        btDownload.setEnabled(true);
                        Log.e("TAG", "DownloadActivity onFailure()" + error);
                    }
                });

            }
        });
    }
}
