package com.example.airgraft.service;

import com.example.airgraft.model.SuggestResult;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SuggestionService {

  public List<SuggestResult> suggest(String query, Double longitude, Double latitude) {
    return null;
  }
}
