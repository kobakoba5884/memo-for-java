package pure.java.io;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ScannerUtils {
    public static void multilineOutput(){
        try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in))){
            System.out.println("Enter something: ");
            String line;
            while((line = bufferedReader.readLine()) != null){
                System.out.println(line);
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
