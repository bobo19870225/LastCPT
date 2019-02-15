/*
 * Copyright (c) 2018. 代码著作权归卢声波所有。
 */

package www.jingkan.com.util;

import java.io.File;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class FileUtil {

    public static List<File> sort(File[] listFiles) {

        List<File> list = Arrays.asList(listFiles);

        Collections.sort(list, new FileComparator());

        return list;
    }

}
