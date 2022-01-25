package com.epam.port.core;

import com.epam.port.entity.Ship;
import com.epam.port.service.DataReader;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class PortManager {

    private static final String FILE_PATH = "src/main/resources/ships.json";
    private static final Logger LOGGER = LogManager.getLogger(PortManager.class);

    public static void main(String[] args) {
        try {
            DataReader reader = new DataReader();
            List<Ship> ships = reader.read(FILE_PATH);

            ExecutorService service = Executors.newFixedThreadPool(ships.size());
            ships.forEach(service::submit);
            service.shutdown();

        } catch (IOException e) {
            LOGGER.error(e);
        }
    }
}
