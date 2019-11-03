package practicas.practica2.usecase;

import java.io.File;

class VirtualFile {

    static final int ROOT_LEVEL = 0;

    private File file;
    private int level;
    private int index;

    VirtualFile(File file, int level) {
        this.file = file;
        this.level = level;
        this.index = 0;
    }

    int getIndex() {
        return index;
    }

    void setIndex(int index) {
        this.index = index;
    }

    File getFile() {
        return file;
    }

    int getLevel() {
        return level;
    }

    void setLevel(int level) {
        this.level = level;
    }

    String getName() {
        return this.file.getName();
    }

    long length() {
        return this.file.length();
    }
}
