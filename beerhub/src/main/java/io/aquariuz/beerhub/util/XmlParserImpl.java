package io.aquariuz.beerhub.util;

import io.aquariuz.beerhub.util.factory.XmlParser;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.BufferedReader;
import java.io.File;

public class XmlParserImpl implements XmlParser {
    public XmlParserImpl() {
    }

    @Override
    public <O> O getFromXml(BufferedReader bReader, Class<O> obj) throws JAXBException {
        JAXBContext context = JAXBContext.newInstance(obj);
        Unmarshaller unmarshaller = context.createUnmarshaller();
        O newObj = (O) unmarshaller.unmarshal(bReader);
        return newObj;
    }

    @Override
    public <O> String saveRootDtoToXml(O obj, String path) throws JAXBException {
        JAXBContext context = JAXBContext.newInstance(obj.getClass());
        Marshaller marshaller = context.createMarshaller();

        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        File exportFile = new File(path);
        marshaller.marshal(obj, exportFile);
        return "Ok";
    }
}