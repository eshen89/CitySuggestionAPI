package com.example.airgraft.utils;

import com.example.airgraft.model.pojo.City;
import java.io.File;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;
import java.util.Scanner;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CityMapProcessor {

  private static final String TAB_DELIMITER = "\t";

  private final Map<Integer, City> cityMap;

  public CityMapProcessor() {
    this.cityMap = new LinkedHashMap<>();
  }

  public void processFile(String inputFilePath) {
    log.info("Starting process input file: {}", inputFilePath);
    Optional.of(inputFilePath)
        .ifPresent(this::constructMapFromFile);
  }

  public Map<Integer, City> getCityMap() {
    return this.cityMap;
  }

  private void constructMapFromFile(String inputFilePath) {
    try (var input = new Scanner(new File(inputFilePath))) {
      input.useDelimiter(TAB_DELIMITER);
      input.useLocale(Locale.ENGLISH);

      log.info("Processing raw city data from {}...", inputFilePath);

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

        this.cityMap.put(city.getGeoNameId(), city);

        if (input.hasNextLine()) {
          input.nextLine();
        }
      }

      log.info("Raw data process done, city map Property Size: {}", cityMap.size());

    } catch (IOException e) {
      e.printStackTrace();
    }
  }

}
