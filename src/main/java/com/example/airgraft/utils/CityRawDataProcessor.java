package com.example.airgraft.utils;

import com.example.airgraft.model.pojo.City;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import com.fasterxml.jackson.databind.ObjectWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;
import java.util.Properties;
import java.util.Scanner;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class CityRawDataProcessor {

  private static final String TAB_DELIMITER = "\t";

  private final ObjectWriter objWriter = new ObjectMapper().writer();
  private final ObjectReader objReader = new ObjectMapper().reader();

  public void processFile(String inputFilePath, String outputFilePath) {
    log.info("Starting process input file: {}", inputFilePath);
    Optional.of(inputFilePath)
        .map(this::readFromFile)
        .ifPresent(properties -> {
          try {
            saveToFile(properties, outputFilePath);
          } catch (IOException e) {
            e.printStackTrace();
          }
        });
  }

  public Map<Integer, City> readFromProperties(String propertiesFilePath) {
    log.info("Reading properties from {}", propertiesFilePath);
    Map<Integer, City> cityMap = new LinkedHashMap<>();
    var properties = new Properties();
    return Optional.of(propertiesFilePath)
        .map(path -> {
          try {
            properties.load(new FileInputStream(propertiesFilePath));
            for (String key : properties.stringPropertyNames()) {
              cityMap.put(Integer.valueOf(key), objReader.readValue(properties.get(key).toString(), City.class));
            }
          } catch (IOException e) {
            e.printStackTrace();
          }
          return cityMap;
        }).orElseThrow();
  }

  private Properties readFromFile(String inputFilePath) {
    var properties = new Properties();

    try (var input = new Scanner(new File(inputFilePath))) {
      input.useDelimiter(TAB_DELIMITER);
      input.useLocale(Locale.ENGLISH);

      log.info("Processing raw city data.......");

      while (input.hasNext()) {
        var geoNameId = input.nextInt();
        String name = input.next();
        String asciiName = input.next();
        String alternateNames = input.next();
        var latitude = input.nextDouble();
        var longitude = input.nextDouble();
        String featureClass = input.next();
        String featureCode = input.next();
        String countryCode = input.next();
        String cc2 = input.next();
        String admin1Code = input.next();
        String admin2Code = input.next();
        String admin3Code = input.next();
        String admin4Code = input.next();
        var population = input.nextInt();
        String elevation = input.next();
        String dem = input.next();
        String timezone = input.next();
        String modificationDate = input.next();

        var city = City.builder()
            .geoNameId(geoNameId)
            .name(name)
            .countryCode(countryCode)
            .latitude(latitude)
            .longitude(longitude)
            .build();

        properties.put(String.valueOf(city.getGeoNameId()), objWriter.writeValueAsString(city));

        if (input.hasNextLine()) {
          input.nextLine();
        }
      }

      log.info("Raw data process done, city map Property Size: {}", properties.size());

    } catch (IOException e) {
      e.printStackTrace();
    }
    return properties;
  }

  private void saveToFile(Properties properties, String outputFilePath) throws IOException {
    log.info("Properties is saving to {}", outputFilePath);
    properties.store(new FileOutputStream(outputFilePath), null);
  }
}
