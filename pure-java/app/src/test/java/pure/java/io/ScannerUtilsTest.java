package pure.java.io;

import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import com.github.javafaker.Faker;

import pure.java.io.enums.ScannerType;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;
import static pure.java.io.ScannerUtils.*;
import static pure.java.io.FileOperation.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class ScannerUtilsTest {
    private final int MAX_NUM = 50;
    String fileName = "for-createSampleData-method.txt";
    String folderPath = "./sampleData/io/";
    String input;

    @BeforeAll
    void createSampleData(){
        Faker faker = new Faker(Locale.getDefault());

        Random random = new Random();
        this.input = IntStream.range(0, random.nextInt(MAX_NUM))
            .mapToObj(i -> faker.pokemon().name()).collect(Collectors.joining("\n"));

        createTxtFile(fileName, input);
    }

    @Test
    void testMultilineScanner() {
        Optional<List<String>> result = multilineScanner();

        if(result.isEmpty()){
            fail("test failure");
        }

        System.out.println("pass");

        String output = result.get().stream().collect(Collectors.joining());

        assertEquals(this.input, output);
        
    }

    @Test
    void testMultilineOutput() {
        
    }

    public static void main(String[] args) {
        multilineOutput(ScannerType.PARAGRAPH);
    }
}
