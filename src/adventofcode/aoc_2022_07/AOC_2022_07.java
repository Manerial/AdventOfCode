package adventofcode.aoc_2022_07;

import template.AOC;
import utilities.FileLoader;
import utilities.Printer;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;

public class AOC_2022_07 extends AOC {
    private Directory mainDirectory = new Directory("/");
    private Directory currentDirectory;
    private Directory parentDirectory;
    private int minSizeThatMatch;

    public void run(String file) {
        try {
            List<String> list = FileLoader.readListFromFile(file);
            for (String item : list) {
                parseLine(item);
            }

            int sum = getMaxSize(mainDirectory, 100000);
            Printer.println("Solution 1 : " + sum);

            int currentSpace = (70000000 - mainDirectory.getTotalSize());
            int fileSizeToDelete = 30000000 - currentSpace;

            minSizeThatMatch = mainDirectory.getTotalSize();
            getFirstSize(mainDirectory, fileSizeToDelete);
            Printer.println("Solution 2 : " + minSizeThatMatch);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private int getMaxSize(Directory currentDirectory, int maxSize) {
        int size = 0;
        Iterator<String> iter = currentDirectory.getDirectories().keySet().iterator();
        while (iter.hasNext()) {
            String name = iter.next();
            Object object = currentDirectory.getDirectories().get(name);
            if(object instanceof Directory) {
                // subDir
                size += getMaxSize((Directory) object, maxSize);
                // currentDir
                int sizeCurrentDirectory = ((Directory) object).getTotalSize();
                size += (sizeCurrentDirectory <= maxSize) ? sizeCurrentDirectory : 0;
            }
        }
        return size;
    }

    private void getFirstSize(Directory currentDirectory, int minSize) {
        int size = currentDirectory.getTotalSize();
        if(size >= minSize && size < minSizeThatMatch) {
            minSizeThatMatch = size;
        }
        // for each dir in the current dir
        Iterator<String> iter = currentDirectory.getDirectories().keySet().iterator();
        while (iter.hasNext()) {
            String name = iter.next();
            Object object = currentDirectory.getDirectories().get(name);
            if(object instanceof Directory) {
                // if the directory matches the size
                int subDirSize = ((Directory) object).getTotalSize();
                if(subDirSize >= minSize) {
                    // get the min size of the directory
                    getFirstSize((Directory) object, minSize);
                }
            }
        }
    }

    private void parseLine(String item) {
        if (item.startsWith("$")) {
            parseInput(item);
        } else {
            parseOutput(item);
        }
    }

    private void parseInput(String item) {
        String[] elts = item.replace("$", "").trim().split(" ");
        String cmd = elts[0];
        if (cmd.equals("ls")) {
            return;
        } else if (cmd.equals("cd")) {
            String target = elts[1];
            if (target.equals("/")) {
                currentDirectory = mainDirectory;
            } else if (target.equals("..")) {
                currentDirectory = parentDirectory;
                if (currentDirectory == mainDirectory) {
                    parentDirectory = null;
                } else {
                    parentDirectory = getParentDirectory(mainDirectory);
                }
            } else {
                parentDirectory = currentDirectory;
                currentDirectory = (Directory) currentDirectory.getDirectories().get(target);
            }
        }
    }

    private void parseOutput(String item) {
        String[] elts = item.replace("$", "").trim().split(" ");
        if (item.startsWith("dir")) {
            String dirName = elts[1];
            // On ne peut pas autoréférencer un objet
            Directory newDir = new Directory(dirName);
            currentDirectory.getDirectories().put(dirName, newDir);
        } else {
            int fileSize = Integer.parseInt(elts[0]);
            String fileName = elts[1];
            currentDirectory.getDirectories().put(fileName, fileSize);
        }
    }

    private Directory getParentDirectory(Directory directory) {
        Iterator<String> iter = directory.getDirectories().keySet().iterator();
        while (iter.hasNext()) {
            String dirName = iter.next();
            Object object = directory.getDirectories().get(dirName);
            if (object == currentDirectory) {
                return directory;
            } else if (object instanceof Directory) {
                if (getParentDirectory((Directory) object) != null) {
                    return getParentDirectory((Directory) object);
                }
            }
        }
        return null;
    }
}
