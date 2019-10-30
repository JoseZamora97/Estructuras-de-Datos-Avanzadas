package practica2.usecase;

import java.io.File;

public class VirtualFile {

    static final int ROOT_LEVEL = 0;

    private File file;
    private int level;

    public VirtualFile(File file, int level) {
        this.file = file;
        this.level = level;
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public String getName() {
        return this.file.getName();
    }

    public long length() {
        return this.file.length();
    }
}
