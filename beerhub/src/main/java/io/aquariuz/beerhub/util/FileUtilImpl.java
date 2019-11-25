package io.aquariuz.beerhub.util;

import io.aquariuz.beerhub.util.factory.FileUtil;

import java.io.*;

public class FileUtilImpl implements FileUtil {
    public FileUtilImpl() {
    }

    @Override
    public BufferedReader readFile(String path) throws FileNotFoundException {
        File file = new File(path);
        FileInputStream fileStream = new FileInputStream(file);
        InputStreamReader inputStream = new InputStreamReader(fileStream);
        BufferedReader bReader = new BufferedReader(inputStream);
        return bReader;
    }

    @Override
    public String getXmltoString(String path) throws IOException {
        BufferedReader inputStream = readFile(path);
        StringBuilder sBuilder = new StringBuilder();

        String line = inputStream.readLine();
        while (line != null && !line.isBlank() && !line.isEmpty()) {
            sBuilder.append(line).append(System.lineSeparator());
            line = inputStream.readLine();
        }
        return sBuilder.toString();
    }
}