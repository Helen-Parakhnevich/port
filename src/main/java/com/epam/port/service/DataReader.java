package com.epam.port.service;

import com.epam.port.entity.Ship;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.jackson.Log4jJsonObjectMapper;
import org.xml.sax.InputSource;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class DataReader {

    private final Logger LOGGER = LogManager.getLogger(DataReader.class);

    public List<Ship> read(String path) throws IOException {

        ObjectMapper mapper = new ObjectMapper();
        InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream("ships.json");
        TypeReference<List<Ship>> listTypeReference = new TypeReference<List<Ship>>() {};
        List<Ship> ships = mapper.readValue(new File(path), listTypeReference);

        return ships;

    }

}
