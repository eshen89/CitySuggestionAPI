package com.example.airgraft.model.dto;

import com.example.airgraft.model.SuggestResult;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SuggestionResponseDTO {

  private List<SuggestResult> suggestions;
}
