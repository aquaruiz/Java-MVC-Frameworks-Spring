package io.bar.beerhub.util.factory;

import java.io.IOException;
import java.util.List;

public interface FileUtil {
    <E> void writeFile(String filePath, List<E> items) throws IOException;
}
