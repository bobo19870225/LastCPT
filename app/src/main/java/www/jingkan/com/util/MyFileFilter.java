/*
 * Copyright (c) 2018. 代码著作权归卢声波所有。
 */

package www.jingkan.com.util;

import java.io.File;
import java.io.FileFilter;

public class MyFileFilter implements FileFilter {

    @Override
    public boolean accept(File pathname) {
        return pathname.getName().endsWith("W.txt");
    }

}
