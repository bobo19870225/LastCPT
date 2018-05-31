/*
 * Copyright (c) 2018. 代码著作权归卢声波所有。
 */

package www.jingkan.com.framework.email;

import android.content.Context;
import android.os.Looper;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by lushengbo on 2017/12/14.
 * 发送邮件线程
 */

public class MailSenderRunnable implements Runnable {

    private String user;
    private String subject;
    private String body;
    private String receiver;
    private MailSendUtils sender;
    private String attachment;
    private Context context;

    public MailSenderRunnable(Context context, String user, String password) {
        this.context = context;
        this.user = user;
        sender = new MailSendUtils(user, password);
        String mailHost = user.substring(user.lastIndexOf("@") + 1, user.lastIndexOf("."));
        if (!mailHost.equals("gmail")) {
            mailHost = "smtp." + mailHost + ".com";
            Log.i("hello", mailHost);
            sender.setMailHost(mailHost);
        }
    }

    public void setMail(String subject, String body, String receiver, String attachment) {
        this.subject = subject;
        this.body = body;
        this.receiver = receiver;
        this.attachment = attachment;
    }

    @Override
    public void run() {
        try {
            sender.sendMail(subject, body, user, receiver, attachment);
        } catch (Exception e) {
            Looper.prepare();
            Toast.makeText(context, "发送失败", Toast.LENGTH_LONG).show();
            Looper.loop();
        }
    }
}
