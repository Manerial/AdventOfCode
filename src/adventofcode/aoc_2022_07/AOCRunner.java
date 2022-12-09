package adventofcode.aoc_2022_07;

import template.AOC;
import utilities.FileLoader;
import utilities.Printer;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

public class AOCRunner implements AOC {
    private final Directory mainDirectory = new Directory("/");
    private Directory currentDirectory;
    private Directory parentDirectory;
    private int minSizeThatMatch;

    @Override
    public void run(String file) {
        try {
            List<String> list = FileLoader.readListFromFile(file);
            for (String item : list) {
                parseLine(item);
            }

            Printer.println(mainDirectory);

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
        for (Directory directory : currentDirectory.getDirectories()) {
            // subDir
            size += getMaxSize(directory, maxSize);
            // currentDir
            int sizeCurrentDirectory = (directory).getTotalSize();
            size += (sizeCurrentDirectory <= maxSize) ? sizeCurrentDirectory : 0;
        }
        return size;
    }

    private void getFirstSize(Directory currentDirectory, int minSize) {
        int size = currentDirectory.getTotalSize();
        if (size >= minSize && size < minSizeThatMatch) {
            minSizeThatMatch = size;
        }

        // for each dir in the current dir
        for (Directory directory : currentDirectory.getDirectories()) {
            // if the directory matches the size
            int subDirSize = directory.getTotalSize();
            if (subDirSize >= minSize) {
                // get the min size of the directory
                getFirstSize(directory, minSize);
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
        String[] elements = item.replace("$", "").trim().split(" ");
        String cmd = elements[0];
        if (cmd.equals("cd")) {
            String target = elements[1];
            if (target.equals("/")) {
                currentDirectory = mainDirectory;
            } else if (target.equals("..")) {
                goToParent();
            } else {
                goToChild(target);
            }
        }
    }

    private void parseOutput(String item) {
        String[] elements = item.replace("$", "").trim().split(" ");
        if (item.startsWith("dir")) {
            String dirName = elements[1];
            currentDirectory.getDirectories().add(new Directory(dirName));
        } else {
            int fileSize = Integer.parseInt(elements[0]);
            String fileName = elements[1];
            currentDirectory.getFiles().add(new File(fileName, fileSize));
        }
    }

    private void goToChild(String target) {
        parentDirectory = currentDirectory;
        currentDirectory = currentDirectory.getDirectories().stream()
                .filter(directory -> directory.getName().equals(target))
                .collect(Collectors.toList()).get(0);
    }

    private void goToParent() {
        currentDirectory = parentDirectory;
        parentDirectory = getParentOfCurrentDirectory(mainDirectory);
    }

    private Directory getParentOfCurrentDirectory(Directory superDirectory) {
        if(superDirectory.getDirectories().contains(currentDirectory)) {
            return superDirectory;
        } else {
            for(Directory potentialParent : superDirectory.getDirectories()) {
                Directory parentDir = getParentOfCurrentDirectory(potentialParent);
                if(parentDir != null) {
                    return parentDir;
                }
            }
        }
        return null;
    }
}
