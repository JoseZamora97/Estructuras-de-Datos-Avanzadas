package practica2.usecase;

import java.io.File;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Deque;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Predicate;

import practica2.Position;
import practica2.iterators.BFSIterator;
import practica2.iterators.PreorderIterator;
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
            vf.setIndex(fileList.indexOf(pvf));

            if(f.isDirectory())
                generateFileSystem(vf, pvf);
        }
    }

    private boolean isHidden(File f) {
        return f.getName().indexOf(".") == 0;
    }

    public String getFileSystem() {
        StringBuilder output = new StringBuilder();

        for (Position<VirtualFile> pvf : fileList)
            output.append(compoundFileSystem(pvf.getElement()));

        return output.toString();
    }

    private String compoundFileSystem(VirtualFile element) {

        int index = element.getIndex();

        StringBuilder output = new StringBuilder(index + " ");

        for (int i = 0; i < element.getLevel(); i++)
            output.append("\t");

        output.append(element.getFile().getName()).append("\n");

        return output.toString();
    }

    public void moveFileById(int idFile, int idTargetFolder) {
        checkId(idFile);

        BFSIterator<VirtualFile> bfsIt;
        PreorderIterator<VirtualFile> pIt;

        if(!fileList.get(idTargetFolder).getElement().getFile().isDirectory())
            throw new RuntimeException("Target can't be a file.");

        bfsIt = new BFSIterator<>(fileTree, fileList.get(idFile));
        while(bfsIt.hasNext()) {
            if (bfsIt.next() == fileList.get(idTargetFolder))
                throw new RuntimeException("A file can't be a subdirectory of itself.");
        }

        Position<VirtualFile> src = fileList.get(idFile);
        Position<VirtualFile> dst = fileList.get(idTargetFolder);

        for(Position<VirtualFile> p : fileTree.children(dst))
            if(p == src)
                return;

        fileTree.moveSubtree(src, dst);

        Position<VirtualFile> aux = src;
        bfsIt = new BFSIterator<>(fileTree, aux);

        // Update index.
        while(bfsIt.hasNext()) {
            int parentLvl = fileTree.parent(aux).getElement().getLevel();
            aux.getElement().setLevel(parentLvl+1);
            fileList.remove(aux);
            aux = bfsIt.next();
        }

        // Regenerar la lista.
        fileList = new ArrayList<>();
        pIt = new PreorderIterator<>(fileTree, fileTree.root());
        while(pIt.hasNext())
            fileList.add(pIt.next());

    }

    public void removeFileById(int idFile) {
        checkId(idFile);

        Position<VirtualFile> pvf = fileList.remove(idFile);

        PreorderIterator<VirtualFile> it =
                new PreorderIterator<>(fileTree, pvf);

        while(it.hasNext())
            fileList.remove(it.next());

        fileTree.remove(pvf);
    }

    private void checkId(int idFile) {
        if(idFile >= fileList.size())
            throw new RuntimeException("Invalid ID.");
    }

    public Iterable<String> findBySubstring(int idStartFile, String substring) {
        checkId(idStartFile);

        Predicate<Position<VirtualFile>> predicate = vf -> vf.getElement()
                .getFile().getName().contains(substring);

        return filter(predicate, idStartFile, false);
    }

    public Iterable<String> findBySize(int idStartFile, long minSize, long maxSize) {
        checkId(idStartFile);

        if (maxSize<minSize)
            throw new RuntimeException("Invalid range.");

        Predicate<Position<VirtualFile>> predicate =
                vf -> vf.getElement().length()
                        >= minSize && vf.getElement().length() <= maxSize;

        return filter(predicate, idStartFile, true);
    }

    private Iterable<String> filter(Predicate<Position<VirtualFile>> predicate, int id, boolean onlyFiles) {

        List<String> iterable = new ArrayList<>();

        PreorderIterator<VirtualFile> it =
                new PreorderIterator<>(fileTree, fileList.get(id), predicate);

        while(it.hasNext()) {
            Position<VirtualFile> position = it.next();
            if(position != null)
                if(!position.getElement().getFile().isDirectory())
                    iterable.add(fileList.indexOf(position) + "\t" + position.getElement().getName());
                else
                    if(!onlyFiles)
                        iterable.add(fileList.indexOf(position) + "\t" + position.getElement().getName());
        }

        return iterable;
    }

    public String getFileVirtualPath(int idFile) {
        checkId(idFile);

        Deque<String> stackFiles = new LinkedList<>();
        Position<VirtualFile> pvf = fileList.get(idFile);

        while(pvf != fileTree.root()) {
            stackFiles.push(pvf.getElement().getName());
            pvf = fileTree.parent(pvf);
        }

        StringBuilder output = new StringBuilder("vfs:/" + fileTree.root()
                .getElement().getName());

        for (String f : stackFiles)
            output.append("/").append(f);

        return output.toString();
    }

    public String getFilePath(int idFile) {
        checkId(idFile);
        return fileList.get(idFile).getElement().getFile().getPath()
                .replace("\\", "/");
    }


}
