package com.example.airgraft.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SuggestResult {

  private String name;
  private double latitude;
  private double longitude;
  private double score;

}
