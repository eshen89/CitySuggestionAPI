package com.example.airgraft.service;

import com.example.airgraft.model.pojo.City;
import com.example.airgraft.utils.CityHashMap;
import com.example.airgraft.utils.CitySearchTrie;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class SuggestionService {

  private CitySearchTrie citySearchTrie;
  private CityHashMap cityHashMap;

  @Autowired
  public SuggestionService(final CitySearchTrie citySearchTrie, final CityHashMap cityHashMap) {
    this.cityHashMap = cityHashMap;
    this.citySearchTrie = citySearchTrie;
  }

  public Map<Integer, City> suggest(String query) {
    return Optional.of(query)
        .map(citySearchTrie::suggest)
        .map(suggestedGeoIds -> {
          Map<Integer, City> suggestedMap = new LinkedHashMap<>();
          suggestedGeoIds.forEach(key -> suggestedMap.put(key, cityHashMap.getCityMap().get(key)));
          return suggestedMap;
        })
        .orElse(Collections.emptyMap());
  }

}
