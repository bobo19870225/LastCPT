package www.jingkan.com.view.adapter;


import java.io.File;

/**
 * Created by Sampson on 2019/1/4.
 * CPTTest
 */
public class ItemFile implements Item {
    private String fileName;
    private int id;
    private File file;

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }

    public String getFileName() {
        return fileName;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    @Override
    public Object getId() {
        return id;
    }
}
