package practica2.usecase;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import practica2.Position;
import practica2.tree.narytree.LinkedTree;

public class VirtualFileSystem {

    private LinkedTree<VirtualFile> fileTree;
    private List<Position<VirtualFile>> fileList;

    public VirtualFileSystem() {
        fileList = new ArrayList<>();
        fileTree = new LinkedTree<>();
    }

    public void loadFileSystem(String path) {

        if(!fileTree.isEmpty()) {
            fileTree.remove(fileList.get(VirtualFile.ROOT_LEVEL));
            fileList.clear();
        }

        fileTree.addRoot(new VirtualFile(new File(path), VirtualFile.ROOT_LEVEL));
        fileList.add(fileTree.root());
        generateFileSystem(fileTree.root().getElement(), fileTree.root());

    }

    private void generateFileSystem(VirtualFile virtualFile, Position<VirtualFile> position) {

        VirtualFile vf;
        Position<VirtualFile> pvf;

        File[] files = virtualFile.getFile().listFiles();

        assert files != null;

        for(File f : files) {

            if (isHidden(f))
                continue;

            vf = new VirtualFile(f, virtualFile.getLevel() + 1);
            pvf = fileTree.add(vf, position);

            fileList.add(pvf);

            if(f.isDirectory())
                generateFileSystem(vf, pvf);
        }
    }

    /**
     * Función que simula el metodo nativo de los objetos tipo
     * File [ file.isHidden() ]. De forma que, considero ocultos
     * todos aquellos ficheros que comiencen por un "."
     * Como en los sistemas de tipo Unix.
     * @param f fichero
     * @return true si el fichero esta oculto.
     */
    private boolean isHidden(File f) {
        return f.getName().indexOf(".") == 0;
    }

    public String getFileSystem() {

        StringBuilder output = new StringBuilder();

        int index = 0;

        for (Position<VirtualFile> pvf : fileList) {
            output.append(compoundFileSystem(pvf.getElement(), index));
            index++;
        }

        return output.toString();
    }

    private String compoundFileSystem(VirtualFile element, int index) {

        StringBuilder output = new StringBuilder(index + " ");

        for (int i = 0; i < element.getLevel(); i++)
            output.append("\t");

        output.append(element.getFile().getName()).append("\n");

        return output.toString();
    }


    public void moveFileById(int idFile, int idTargetFolder) {
        throw new RuntimeException("Not yet implemented");
    }

    public void removeFileById(int idFile) {
        throw new RuntimeException("Not yet implemented");
    }

    public Iterable<String> findBySubstring(int idStartFile, String substring) {
        throw new RuntimeException("Not yet implemented");
    }

    public Iterable<String> findBySize(int idStartFile, long minSize, long maxSize) {
        throw new RuntimeException("Not yet implemented");
    }

    public String getFileVirtualPath(int idFile) {
        throw new RuntimeException("Not yet implemented");
    }

    public String getFilePath(int idFile) {
        throw new RuntimeException("Not yet implemented");
    }


}
