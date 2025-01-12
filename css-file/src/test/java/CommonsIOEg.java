import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.File;

@SpringBootTest
public class CommonsIOEg {


    @Test
    public void test() {
        // Copy a file
        File source = new File("source.txt");
        File destination = new File("destination.txt");
        FileUtils.copyFile(source, destination);

        // Read a file into a string
        String content = FileUtils.readFileToString(source, "UTF-8");
        System.out.println(content);

        // Write a string to a file
        FileUtils.writeStringToFile(destination, "Hello, World!", "UTF-8");

        // Delete a file
        FileUtils.forceDelete(destination);
    }
}
