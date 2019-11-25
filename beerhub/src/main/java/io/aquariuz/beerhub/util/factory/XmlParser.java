package io.aquariuz.beerhub.util.factory;

import javax.xml.bind.JAXBException;
import java.io.BufferedReader;

public interface XmlParser {
    <O> O getFromXml(BufferedReader bReader, Class<O> obj) throws JAXBException;

    <O> String saveRootDtoToXml(O obj, String path) throws JAXBException;
}
