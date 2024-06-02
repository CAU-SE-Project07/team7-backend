package hello.hellospring.repository;

import hello.hellospring.recommendation.PythonScriptExecutor;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class PythonScriptExecutorTest {

    @Test
    public void testCalculateSimilarity_equalTexts() throws Exception {
        PythonScriptExecutor executor = new PythonScriptExecutor();
        String text1 = "hello world";
        String text2 = "hello world";
        double result = executor.calculateSimilarity(text1, text2);
        assertEquals(1.0, result, 0.01, "The similarity score should be close to 1.0 for identical texts");
    }

    @Test
    public void testCalculateSimilarity_similarTexts() throws Exception {
        PythonScriptExecutor executor = new PythonScriptExecutor();
        String text1 = "hello world";
        String text2 = "hello";
        double result = executor.calculateSimilarity(text1, text2);
        assertTrue(result > 0.0 && result < 1.0, "The similarity score should be between 0.0 and 1.0 for similar texts");
    }

    @Test
    public void testCalculateSimilarity_differentTexts() throws Exception {
        PythonScriptExecutor executor = new PythonScriptExecutor();
        String text1 = "hello";
        String text2 = "world";
        double result = executor.calculateSimilarity(text1, text2);
        assertTrue(result >= 0.0 && result < 0.5, "The similarity score should be close to 0.0 for different texts");
    }

    @Test
    public void testCalculateSimilarity_nullInput() {
        PythonScriptExecutor executor = new PythonScriptExecutor();
        assertThrows(RuntimeException.class, () -> {
            executor.calculateSimilarity(null, "hello");
        });
    }

    @Test
    public void testCalculateSimilarity_bothNullInputs() {
        PythonScriptExecutor executor = new PythonScriptExecutor();
        assertThrows(RuntimeException.class, () -> {
            executor.calculateSimilarity(null, null);
        });
    }
}
