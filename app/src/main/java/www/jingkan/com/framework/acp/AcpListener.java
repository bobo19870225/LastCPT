/*
 * Copyright (c) 2018. 代码著作权归卢声波所有。
 */

package www.jingkan.com.framework.acp;

import java.util.List;

/**
 * Created by hupei on 2016/4/26.
 */
public interface AcpListener {
    /**
     * 同意
     */
    void onGranted();

    /**
     * 拒绝
     */
    void onDenied(List<String> permissions);
}
