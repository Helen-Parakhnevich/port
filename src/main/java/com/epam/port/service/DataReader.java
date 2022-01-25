package com.epam.port.service;

import com.epam.port.entity.Ship;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.IOException;
import java.util.List;

public class DataReader {

    public List<Ship> read(String path) throws IOException {

        ObjectMapper mapper = new ObjectMapper();
        TypeReference<List<Ship>> listTypeReference = new TypeReference<List<Ship>>() {};
        File file = new File(path);
        List<Ship> ships = mapper.readValue(file, listTypeReference);

        return ships;
    }

}
