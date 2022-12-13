package pure.java.io;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import pure.java.io.enums.ScannerType;

public class ScannerUtils {
    public static void multilineOutput(ScannerType type){
        try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in))){
            System.out.println("Enter something: ");
            String line;
            while((line = bufferedReader.readLine()) != null){
                if(type == ScannerType.PARAGRAPH) System.out.println(line);
                else if(type == ScannerType.SPACE) Arrays.stream(line.split(" ")).filter(string -> !string.isEmpty()).forEach(System.out::println);
                else if(type == ScannerType.ALL_INTEGER) System.out.println(Integer.parseInt(line));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Optional<List<String>> multilineScanner(){
        try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in))){
            System.out.println("Enter something: ");
            List<String> result = new ArrayList<>();
            String line;
            while((line = bufferedReader.readLine()) != null){
                result.add(line);
            }
            return Optional.of(result);
        } catch (IOException e) {
            System.err.println(e.getMessage());
            return Optional.empty();
        }
    }
}
