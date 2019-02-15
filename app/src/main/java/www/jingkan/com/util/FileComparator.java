/*
 * Copyright (c) 2018. 代码著作权归卢声波所有。
 */

package www.jingkan.com.util;

import java.io.File;
import java.util.Comparator;

public class FileComparator implements Comparator<File> {

    @Override
    public int compare(File f1, File f2) {
        //1.先比较文件夹，让文件夹排在列表的最前边，并且以A-Z的字典顺序排列
        if (f1.isDirectory() && f2.isDirectory()) {
            return f1.getName().compareToIgnoreCase(f2.getName());
        } else {
            //2.比较文件夹和文件
            if (f1.isDirectory() && f2.isFile()) {
                return -1;
            } else if (f1.isFile() && f2.isDirectory()) {//3.比较文件和文件夹
                return 1;
            } else {
                //4.比较文件
                return f1.getName().compareToIgnoreCase(f2.getName());
            }

        }

    }

}
