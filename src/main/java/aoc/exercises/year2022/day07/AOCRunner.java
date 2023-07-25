package aoc.exercises.year2022.day07;

import utilities.AbstractAOC;

/**
 * <pre>
 * Instructions are <a href="https://adventofcode.com/2022/day/7">here</a>
 * </pre>
 */
public class AOCRunner extends AbstractAOC {
    private final Directory mainDirectory = new Directory("/");
    private Directory currentDirectory;
    private Directory parentDirectory;
    private int minSizeThatMatch;

    @Override
    public void run() {
        for (String item : inputList) {
            parseLine(item);
        }

        solution1 = getMaxSize(mainDirectory, 100000);

        int currentSpace = (70000000 - mainDirectory.getTotalSize());
        int fileSizeToDelete = 30000000 - currentSpace;

        minSizeThatMatch = mainDirectory.getTotalSize();
        getFirstSize(mainDirectory, fileSizeToDelete);
        solution2 = minSizeThatMatch;
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
                .toList().get(0);
    }

    private void goToParent() {
        currentDirectory = parentDirectory;
        parentDirectory = getParentOfCurrentDirectory(mainDirectory);
    }

    private Directory getParentOfCurrentDirectory(Directory superDirectory) {
        if (superDirectory.getDirectories().contains(currentDirectory)) {
            return superDirectory;
        } else {
            for (Directory potentialParent : superDirectory.getDirectories()) {
                Directory parentDir = getParentOfCurrentDirectory(potentialParent);
                if (parentDir != null) {
                    return parentDir;
                }
            }
        }
        return null;
    }
}
