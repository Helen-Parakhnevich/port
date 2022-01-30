package com.epam.port.service;

import com.epam.port.entity.Port;
import com.epam.port.entity.Ship;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class DataReader {

    private final Logger LOGGER = LogManager.getLogger(Port.class);

    public List<Ship> read(String path) throws IOException {
        List<Ship> ships;
        ObjectMapper mapper = new ObjectMapper();
        TypeReference<List<Ship>> listTypeReference = new TypeReference<List<Ship>>() {};
        try {
            File file = new File(path);
            ships = mapper.readValue(file, listTypeReference);
        } catch (IOException e) {
            LOGGER.error(e);
            throw e;
        }

        return ships;
    }

}
