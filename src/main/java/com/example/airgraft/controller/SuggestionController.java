package com.example.airgraft.controller;

import com.example.airgraft.converter.SuggestionResultConverter;
import com.example.airgraft.model.dto.SuggestionResponseDTO;
import com.example.airgraft.model.pojo.City;
import com.example.airgraft.service.RankingService;
import com.example.airgraft.service.SuggestionService;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
public class SuggestionController {

  private final SuggestionService suggestionService;
  private final RankingService rankingService;
  private final SuggestionResultConverter suggestionResultConverter;

  @GetMapping("/suggestion")
  public ResponseEntity<SuggestionResponseDTO> getSuggestion(@RequestParam(name = "q") String query,
      @RequestParam(name = "lat", required = false) Double latitude,
      @RequestParam(name = "long", required = false) Double longitude) {
    return Optional.of(query)
        .map(suggestionService::suggest)
        .map(suggestedMap -> {
          if (!suggestedMap.isEmpty()) {
            return rankingService.rank(query, latitude, longitude, (List<City>) suggestedMap.values());
          }
          return new HashMap<Integer, Double>();
        })
        .map(suggestionResultConverter::convert)
        .map(ResponseEntity::ok)
        .orElseGet(() -> new ResponseEntity<>(HttpStatus.UNPROCESSABLE_ENTITY));
  }

}
