package hello.hellospring.recommendation;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class PythonScriptExecutor {

    public static double calculateSimilarity(String text1, String text2) throws Exception {
        String python = "C:\\Users\\lois5\\AppData\\Local\\Microsoft\\WindowsApps\\python.exe";
        String script = "C:\\Users\\lois5\\OneDrive\\바탕 화면\\newhj\\team7-backend\\src\\main\\java\\hello\\hellospring\\recommendation\\text_similarity.py";
        ProcessBuilder processBuilder =
                new ProcessBuilder(python,script, text1, text2);
        processBuilder.redirectErrorStream(true);
        Process process = processBuilder.start();

        BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
        String line;
        StringBuilder output = new StringBuilder();

        while ((line = reader.readLine()) != null) {
            output.append(line);
        }

        int exitCode = process.waitFor();
        if (exitCode != 0) {
            throw new RuntimeException("Python script execution failed");
        }

        return Double.parseDouble(output.toString().trim());
    }
}