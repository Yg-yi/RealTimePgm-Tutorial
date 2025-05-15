import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class FileCounter {
    private static int javaFileCount = 0;
    private static int issueCount = 0;
    private static final Object lock = new Object();
    private static List<String> filesWithIssues = new ArrayList<>();

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter the directory path: ");
        String directoryPath = scanner.nextLine();
        scanner.close();

        System.out.println("Checking for Java files and issues, please wait...");

        Runnable javaFileCounter = () -> {
            int count = countJavaFilesRecursively(directoryPath);
            synchronized (lock) {
                javaFileCount = count;
            }
        };

        Runnable issueCounter = () -> {
            int issues = findFilesWithCompilationIssues(directoryPath);
            synchronized (lock) {
                issueCount = issues;
            }
        };

        Thread javaFileThread = new Thread(javaFileCounter);
        Thread issueThread = new Thread(issueCounter);

        javaFileThread.start();
        issueThread.start();

        try {
            javaFileThread.join();
            issueThread.join();
        } catch (InterruptedException e) {
        }

        System.out.println("\nNumber of Java Files = " + javaFileCount);
        System.out.println("Number of Issues = " + issueCount);

        if (!filesWithIssues.isEmpty()) {
            System.out.println("\nJava Files with Issues:");
            for (String filePath : filesWithIssues) {
                System.out.println(filePath);
            }
        }
    }

    public static int countJavaFilesRecursively(String directoryPath) {
        File directory = new File(directoryPath);
        if (!directory.exists() || !directory.isDirectory()) {
            return 0;
        }

        File[] files = directory.listFiles();
        if (files == null) {
            return 0;
        }

        int count = 0;
        for (File file : files) {
            if (file.isDirectory()) {
                count += countJavaFilesRecursively(file.getAbsolutePath());
            } else if (file.getName().endsWith(".java")) {
                count++;
            }
        }
        return count;
    }

    public static int findFilesWithCompilationIssues(String directoryPath) {
        File directory = new File(directoryPath);
        if (!directory.exists() || !directory.isDirectory()) {
            return 0;
        }

        File[] files = directory.listFiles();
        if (files == null) {
            return 0;
        }

        int count = 0;

        for (File file : files) {
            if (file.isDirectory()) {
                count += findFilesWithCompilationIssues(file.getAbsolutePath());
            } else if (file.getName().endsWith(".java")) {
                if (checkCompilationErrors(file)) {
                    synchronized (lock) {
                        filesWithIssues.add(file.getAbsolutePath());
                    }
                    count++;
                }
            }
        }
        return count;
    }

    private static boolean checkCompilationErrors(File javaFile) {
        try {
            ProcessBuilder processBuilder = new ProcessBuilder("javac", javaFile.getAbsolutePath());
            Process process = processBuilder.start();
            int exitCode = process.waitFor();
            return exitCode != 0;
        } catch (IOException | InterruptedException e) {
            return true;
        }
    }
}