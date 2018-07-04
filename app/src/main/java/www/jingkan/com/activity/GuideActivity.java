package www.jingkan.com.activity;

import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.net.Uri;
import android.os.Environment;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.VideoView;

import com.arialyy.annotations.Download;
import com.arialyy.aria.core.Aria;
import com.arialyy.aria.core.download.DownloadTask;

import www.jingkan.com.R;
import www.jingkan.com.annotation.BindView;
import www.jingkan.com.base.BaseActivity;

/**
 * Created by Sampson on 2018/4/10.
 * LastCPT
 */
public class GuideActivity extends BaseActivity {
    @BindView(id = R.id.video)
    private VideoView videoView;
    @BindView(id = R.id.ok, click = true)
    private TextView ok;
    private String DOWNLOAD_URL;

    @Override
    protected void setView() {
        setToolBar("视频教学");
        String path = "android.resource://" + getPackageName() + "/" + R.raw.test;
        Uri uri = Uri.parse(path);
        videoView.setMediaController(new MediaController(this));
        videoView.setVideoURI(uri);
        //videoView.start();
        Aria.download(this).register();
        Aria.download(this).removeAllTask(false);
    }

    //在这里处理任务执行中的状态，如进度进度条的刷新
    @Download.onTaskRunning
    protected void running(DownloadTask task) {
        if (task.getKey().equals(DOWNLOAD_URL)) {

        }
        int p = task.getPercent();    //任务进度百分比
        String speed = task.getConvertSpeed();    //转换单位后的下载速度，单位转换需要在配置文件中打开
//        String speed1 = task.getSpeed(); //原始byte长度速度
    }

    @Download.onTaskComplete
    void taskComplete(DownloadTask task) {
        //在这里处理任务完成的状态
        showToast("下载完成");
    }

    @Override
    public int initView() {
        return R.layout.activity_guide;
    }

    private long mTaskId;
    private DownloadManager downloadManager;
    //广播接受者，接收下载状态
    private BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            checkDownloadStatus();//检查下载状态
        }
    };

    //检查下载状态
    private void checkDownloadStatus() {
        DownloadManager.Query query = new DownloadManager.Query();
        query.setFilterById(mTaskId);//筛选下载任务，传入任务ID，可变参数
        Cursor c = downloadManager.query(query);
        if (c.moveToFirst()) {
            int status = c.getInt(c.getColumnIndex(DownloadManager.COLUMN_STATUS));
            switch (status) {
                case DownloadManager.STATUS_PAUSED:
                    showToast(">>>下载暂停");
                case DownloadManager.STATUS_PENDING:
                    showToast(">>>下载延迟");
                case DownloadManager.STATUS_RUNNING:
                    showToast(">>>正在下载");
                    break;
                case DownloadManager.STATUS_SUCCESSFUL:
                    showToast(">>>下载完成");
                    //下载完成安装APK
                    //downloadPath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getAbsolutePath() + File.separator + versionName;
                    break;
                case DownloadManager.STATUS_FAILED:
                    showToast(">>>下载失败");
                    break;
            }
        }
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ok:
                DOWNLOAD_URL = "http://101.132.103.25:8080/NewRDL/upload/sms.pdf";

                DownloadManager.Request request = new DownloadManager.Request(Uri.parse(DOWNLOAD_URL));
                request.setAllowedOverRoaming(false);//漫游网络是否可以下载
                request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI);
                //设置文件类型，可以在下载结束后自动打开该文件
                MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
                String mimeString = mimeTypeMap.getMimeTypeFromExtension(MimeTypeMap.getFileExtensionFromUrl(DOWNLOAD_URL));
                request.setMimeType(mimeString);

                //在通知栏中显示，默认就是显示的
                request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE);
                request.setVisibleInDownloadsUi(true);

//                //sdcard的目录下的download文件夹，必须设置
//                request.setDestinationInExternalPublicDir("/download/", "test.pdf");
                /**
                 * 方法1:
                 * 目录: Android -> data -> com.app -> files -> Download -> dxtj.apk
                 * 这个文件是你的应用所专用的,软件卸载后，下载的文件将随着卸载全部被删除
                 */
                request.setDestinationInExternalFilesDir(this, Environment.DIRECTORY_DOWNLOADS, "sms.pdf");


                //将下载请求加入下载队列
                downloadManager = (DownloadManager) getApplicationContext().getSystemService(Context.DOWNLOAD_SERVICE);
                //加入下载队列后会给该任务返回一个long型的id，
                //通过该id可以取消任务，重启任务等等，看上面源码中框起来的方法
                if (downloadManager != null) {
                    mTaskId = downloadManager.enqueue(request);
                }
                //注册广播接收者，监听下载状态
                getApplicationContext().registerReceiver(receiver,
                        new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE));

//                Aria.download(this)
//                        .load(DOWNLOAD_URL)
//                        .setFilePath(Environment.getExternalStorageDirectory().getPath() + "/test.pdf")
//                        .start();
                break;
        }
    }


}
