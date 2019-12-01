package io.bar.beerhub.util;

import io.bar.beerhub.util.factory.FileUtil;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class FileUtilImpl implements FileUtil {
    @Override
    public <E> void writeFile(String filePath, List<E> items) throws IOException {
        File file = new File(filePath);
        FileWriter fileWriter = new FileWriter(file);
        BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);

        for(E item: items){
            bufferedWriter.write(items.toString());
            bufferedWriter.write(System.lineSeparator());
        }

        bufferedWriter.close();
        fileWriter.close();
    }
}
