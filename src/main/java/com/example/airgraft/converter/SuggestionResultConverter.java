package com.example.airgraft.converter;

import com.example.airgraft.model.SuggestResult;
import com.example.airgraft.model.dto.SuggestionResponseDTO;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class SuggestionResultConverter {

  public SuggestionResponseDTO convert(List<SuggestResult> resultList) {
    return SuggestionResponseDTO.builder()
        .suggestions(resultList)
        .build();
  }
}
