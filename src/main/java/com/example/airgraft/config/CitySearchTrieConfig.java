package com.example.airgraft.config;

import com.example.airgraft.model.pojo.City;
import com.example.airgraft.utils.CityMapProcessor;
import com.example.airgraft.utils.CitySearchTrie;
import java.util.LinkedList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class CitySearchTrieConfig {

  private final CityMapProcessor cityMapProcessor;

  @Bean
  @DependsOn(value = {"cityMapProcessor"})
  public CitySearchTrie citySearchTrie() {
    log.info("Initializing CitySearchTrie bean...");
    var citySearchTrie = new CitySearchTrie();
    List<City> cityList = new LinkedList<>(cityMapProcessor.getCityMap().values());

    for(City city: cityList) {
      citySearchTrie.insert(city);
    }

    log.info("Done CitySearchTrie initialization.");
    return citySearchTrie;
  }

}
