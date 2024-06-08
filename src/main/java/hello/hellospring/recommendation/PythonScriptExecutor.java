package hello.hellospring.recommendation;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.file.Paths;

public class PythonScriptExecutor {

    private final String python;
    private final String script;
    private final ProcessBuilder processBuilder;

    public PythonScriptExecutor() {
        this.python = "python";
        this.script = Paths.get("src", "main", "java", "hello", "hellospring", "recommendation", "text_similarity.py").toString();
        this.processBuilder = new ProcessBuilder(python, script);
        this.processBuilder.redirectErrorStream(true);
    }

    public double calculateSimilarity(String text1, String text2) throws Exception {
        processBuilder.command(python, script, text1, text2);
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
