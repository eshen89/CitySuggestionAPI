package com.example.airgraft.controller;

import com.example.airgraft.converter.SuggestionResultConverter;
import com.example.airgraft.model.SuggestResult;
import com.example.airgraft.model.dto.SuggestionResponseDTO;
import com.example.airgraft.service.SuggestionService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
public class SuggestionController {

  private final SuggestionService suggestionService;
  private final SuggestionResultConverter suggestionResultConverter;

  @GetMapping("/suggestion")
  public ResponseEntity<SuggestionResponseDTO> helloWorld(@RequestParam(name = "q", required = false) String query,
      @RequestParam(name = "lat", required = false) Double latitude,
      @RequestParam(name = "long", required = false) Double longitude) {
    List<SuggestResult> resultList = suggestionService.suggest(query, latitude, longitude);
    return ResponseEntity.ok(suggestionResultConverter.convert(resultList));
  }

}
