package www.jingkan.com.framework.view;

import android.content.Context;
import android.databinding.InverseBindingListener;
import android.databinding.InverseBindingMethod;
import android.databinding.InverseBindingMethods;
import android.util.AttributeSet;
import android.widget.SeekBar;

/**
 * Created by Sampson on 2018/3/29.
 * LastCPT
 */
@InverseBindingMethods({@InverseBindingMethod(type = DataBindingSeekBar.class, attribute = "progress", event = "progressAttrChanged")})

public class DataBindingSeekBar extends android.support.v7.widget.AppCompatSeekBar {


    private int progress = 0;
    private InverseBindingListener mInverseBindingListener;

    public DataBindingSeekBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                progress = i;

                //触发反向数据的传递
                if (mInverseBindingListener != null) {
                    mInverseBindingListener.onChange();
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    public void setProgress(int p) {


        if (progress != p) {
            progress = p;

            //这一句代码可以解决初始化阶段进度条显示的值正确，但没有及时更新UI的问题。
            //super.setProgress(progress);
        }
    }

    public int getProgress() {

        return progress;
    }

    public void setProgressAttrChanged(InverseBindingListener inverseBindingListener) {
        if (inverseBindingListener != null) {
            mInverseBindingListener = inverseBindingListener;
        }
    }
}
