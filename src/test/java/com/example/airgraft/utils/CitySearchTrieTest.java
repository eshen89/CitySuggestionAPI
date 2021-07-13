package com.example.airgraft.utils;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.example.airgraft.model.pojo.City;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("test")
@SpringBootTest
@RequiredArgsConstructor
class CitySearchTrieTest {

  private static final CitySearchTrie citySearchTrie = new CitySearchTrie();

  @BeforeAll()
  public static void init() {
    City city = City.builder()
        .geoNameId(123)
        .name("Montreal")
        .latitude(1.0)
        .longitude(2.0)
        .countryCode("CA")
        .build();
    citySearchTrie.insert(city);
  }

  @Test
  void testInsert() {
    assertFalse(citySearchTrie.isEmpty());
  }

  @Test
  void testSuggestSuccess() {
    String searchTerm = "Montreal";
    int expectedGeoId = 123;
    List<Integer> geoIds = citySearchTrie.suggest(searchTerm);

    assertTrue(geoIds.contains(expectedGeoId));
  }

  @Test
  void testSuggestNotExist() {
    String searchTerm = "NotExist";
    List<Integer> geoIds = citySearchTrie.suggest(searchTerm);

    assertTrue(geoIds.isEmpty());
  }
}
