package com.example.airgraft.converter;

import static java.util.Collections.emptyList;

import com.example.airgraft.model.SuggestResult;
import com.example.airgraft.model.dto.SuggestionResponseDTO;
import com.example.airgraft.utils.CityHashMap;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SuggestionResultConverter {

  private final CityHashMap cityHashMap;

  public SuggestionResponseDTO convert(Map<Integer, Double> rankingResult) {
    SuggestionResponseDTO dto = SuggestionResponseDTO.builder().suggestions(emptyList()).build();
    if (!rankingResult.isEmpty()) {
      List<SuggestResult> suggestResults = new LinkedList<>();
      rankingResult.keySet().forEach(key -> {
        var city = cityHashMap.getCityMap().get(key);
        var suggestResult = SuggestResult.builder()
            .name(city.getName().concat(" ").concat(city.getCountryCode()))
            .latitude(city.getLatitude())
            .longitude(city.getLongitude())
            .score(rankingResult.get(key))
            .build();
        suggestResults.add(suggestResult);
      });
      sortByScore(suggestResults);
      dto.setSuggestions(suggestResults);
    }
    return dto;
  }

  private void sortByScore(List<SuggestResult> suggestResults) {
    suggestResults.sort(Comparator.comparingDouble(SuggestResult::getScore).reversed());
  }

}
