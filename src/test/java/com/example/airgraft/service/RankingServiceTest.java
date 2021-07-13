package com.example.airgraft.service;

import static java.util.Arrays.asList;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.example.airgraft.model.pojo.City;
import java.util.Map;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("test")
@SpringBootTest
class RankingServiceTest {

  @Autowired
  private RankingService rankingService;

  private final City near = City.builder()
      .geoNameId(111)
      .name("Near City")
      .longitude(1.0)
      .latitude(1.0)
      .build();

  private final City far = City.builder()
      .geoNameId(222)
      .name("Near But More Name City")
      .longitude(2.0)
      .latitude(2.0)
      .build();


  @Test
  void testNameRanking() {
    String cityName = "Near";
    Map<Integer, Double> result = rankingService.rank(cityName, null, null, asList(near, far));
    assertEquals(2, result.size());
    assertTrue(result.get(near.getGeoNameId()) > result.get(far.getGeoNameId()));
  }

  @Test
  void testCoordinateRanking() {
    String cityName = "Near";
    Map<Integer, Double> result = rankingService.rank(cityName, 1d, 1d, asList(near, far));
    assertEquals(2, result.size());
    assertTrue(result.get(near.getGeoNameId()) > result.get(far.getGeoNameId()));
  }

}
