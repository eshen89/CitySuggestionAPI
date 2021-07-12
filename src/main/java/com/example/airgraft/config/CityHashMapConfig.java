package com.example.airgraft.config;

import com.example.airgraft.model.pojo.City;
import com.example.airgraft.utils.CityHashMap;
import com.example.airgraft.utils.CityRawDataProcessor;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class CityHashMapConfig {

  private static final String CA_PROPERTIES_PATH = "src/main/resources/static/CA_map.properties";
  private static final String US_PROPERTIES_PATH = "src/main/resources/static/US_map.properties";

  private final CityRawDataProcessor cityRawDataProcessor;

  @Bean
  @DependsOn(value = {"cityRawDataProcessor"})
  public CityHashMap cityHashMap() {
    log.info("Initializing CityHashMap bean...");
    var cityHashMap = new CityHashMap();
    Map<Integer, City> caCityMap = cityRawDataProcessor.readFromProperties(CA_PROPERTIES_PATH);
    Map<Integer, City> usCityMap = cityRawDataProcessor.readFromProperties(US_PROPERTIES_PATH);
    cityHashMap.getCityMap().putAll(caCityMap);
    cityHashMap.getCityMap().putAll(usCityMap);
    log.info("Done, CityHashMap size: {}.", cityHashMap.getCityMap().size());
    return cityHashMap;
  }

}
