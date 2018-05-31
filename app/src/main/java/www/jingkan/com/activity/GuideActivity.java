package www.jingkan.com.activity;

import android.net.Uri;
import android.os.Environment;
import android.view.View;
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
        videoView.start();
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

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ok:
                DOWNLOAD_URL =
                        "http://image.baidu.com/search/detail?ct=503316480&z=0&ipn=d&word=pdf&step_word=&hs=0&pn=10&spn=0&di=74950192900&pi=0&rn=1&tn=baiduimagedetail&is=0%2C0&istype=2&ie=utf-8&oe=utf-8&in=&cl=2&lm=-1&st=-1&cs=2997184068%2C3595397134&os=2321460520%2C1807971895&simid=4208437194%2C398415641&adpicid=0&lpn=0&ln=1882&fr=&fmq=1523432333706_R&fm=index&ic=0&s=undefined&se=&sme=&tab=0&width=&height=&face=undefined&ist=&jit=&cg=&bdtype=0&oriquery=&objurl=http%3A%2F%2Fpic.5577.com%2Fup%2F2014-8%2F2014811105940.jpg&fromurl=ippr_z2C%24qAzdH3FAzdH3Fooo_z%26e3Bcc00_z%26e3Bv54AzdH3FhAzdH3Fr1u&gsm=0&rpstart=0&rpnum=0&islist=&querylist=";
                Aria.download(this)
                        .load(DOWNLOAD_URL)
                        .setFilePath(Environment.getExternalStorageDirectory().getPath() + "/test.jpg")
                        .start();
                break;
        }
    }


}
