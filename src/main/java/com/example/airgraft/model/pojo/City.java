package com.example.airgraft.model.pojo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class City {

  private int geoNameId;
  private String name;
  private double latitude;
  private double longitude;
  private String countryCode;

}
