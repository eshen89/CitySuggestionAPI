package com.example.airgraft.config;

import com.example.airgraft.model.pojo.City;
import com.example.airgraft.utils.CityHashMap;
import com.example.airgraft.utils.CitySearchTrie;
import java.util.LinkedList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class CitySearchTrieConfig {

  private final ApplicationContext applicationContext;

  @Bean
  @DependsOn(value = {"cityHashMap"})
  public CitySearchTrie citySearchTrie() {
    log.info("Initializing CitySearchTrie bean...");
    var citySearchTrie = new CitySearchTrie();
    var cityHashMap = (CityHashMap) applicationContext.getBean("cityHashMap");
    List<City> cityList = new LinkedList<>(cityHashMap.getCityMap().values());

    for(City city: cityList) {
      citySearchTrie.insert(city);
    }
    log.info("Done CitySearchTrie initialization.");
    return citySearchTrie;
  }



}
