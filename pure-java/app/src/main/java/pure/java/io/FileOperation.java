package pure.java.io;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;

public class FileOperation {
    public static final String TARGET_FOLDER_PATH = "workspace/sampleData/pure-java/io";
    public static final Path BASE_FOLDER_PATH = Paths.get(System.getProperty("user.home"));
    public static final Path FOLDER_PATH = BASE_FOLDER_PATH.resolve(TARGET_FOLDER_PATH);

    public static void createTxtFile(String fileName, String text){
        Path outputPath = FOLDER_PATH.resolve(fileName);
        File outputFile = outputPath.toFile();

        try{
            Files.createDirectories(FOLDER_PATH);

            try(PrintWriter printWriter = new PrintWriter(new BufferedWriter(new FileWriter(outputFile)))){
                printWriter.println(text);
                System.out.printf("copy complete! to %s\n", outputPath.toString());
            }
        }catch(IOException e){
            System.err.println(e.getMessage());
            System.exit(1);
        }
    }

    public static Optional<List<String>> readLineTxtAndSeptate(String fileName){
        Path outputPath = FOLDER_PATH.resolve(fileName);
        Charset charset = Charset.forName("UTF-8");

        try {
            List<String> result = Files.readAllLines(outputPath, charset);
            return Optional.of(result);
        } catch (IOException e) {
            System.err.println(e.getMessage());
            return Optional.empty();
        }
    } 
}
