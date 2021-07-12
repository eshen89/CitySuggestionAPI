package com.example.airgraft.utils;

import com.example.airgraft.model.pojo.City;
import java.util.LinkedHashMap;
import java.util.Map;
import lombok.Data;

@Data
public class CityHashMap {

  private Map<Integer, City> cityMap;

  public CityHashMap() {
    this.cityMap = new LinkedHashMap<>();
  }

}
