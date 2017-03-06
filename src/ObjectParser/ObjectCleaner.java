package ObjectParser;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Daniel on 06-Mar-17.
 */
public class ObjectCleaner {

    private static final String MATERIAL = "usemtl";
    private File file;

    public ObjectCleaner(File file) {
        this.file = file;
    }

    private boolean isFileObj() {
        return file.toString().endsWith(".obj");
    }

    public int clean() {
        if (!isFileObj())
            return 0;
        try{
            FileReader fileReader = new FileReader(file);
            BufferedReader reader = new BufferedReader(fileReader);
            List<String> outStrings = new ArrayList<>();
            String line;

            while ((line = reader.readLine()) != null) {
                if (!line.contains(MATERIAL)) {
                    outStrings.add(line);
                }
            }
            fileReader.close();
            reader.close();

            FileWriter fileWriter = new FileWriter(file);
            BufferedWriter writer = new BufferedWriter(fileWriter);

            for (String s: outStrings) {
                writer.write(s + '\n');
            }

            writer.flush();
            writer.close();
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
            return 0;
        }

        return 1;
    }
}
