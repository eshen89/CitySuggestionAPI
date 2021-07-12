package com.example.airgraft.service;

import com.example.airgraft.model.SuggestResult;
import com.example.airgraft.utils.CityHashMap;
import com.example.airgraft.utils.CitySearchTrie;
import java.util.List;
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

  public List<SuggestResult> suggest(String query, Double longitude, Double latitude) {
    return null;
  }
}
