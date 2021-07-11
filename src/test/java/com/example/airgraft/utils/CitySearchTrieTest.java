package com.example.airgraft.utils;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.example.airgraft.model.pojo.City;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class CitySearchTrieTest {

  private static final CitySearchTrie citySearchTrie = new CitySearchTrie();
  private static final String TEST_FILE_INPUT_PATH = "./src/test/resources/static/CA_test.txt";
  private static final String TEST_FILE_OUTPUT_PATH = "./src/test/resources/static/CA_test_map.properties";

  private static final CityRawDataProcessor cityRawDataProcessor = new CityRawDataProcessor();

  @BeforeAll()
  public static void init() {
    cityRawDataProcessor.processFile(TEST_FILE_INPUT_PATH, TEST_FILE_OUTPUT_PATH);
    Map<Integer, City> cityMap = cityRawDataProcessor.readFromProperties(TEST_FILE_OUTPUT_PATH);
    List<City> cityList = new LinkedList<>(cityMap.values());
    for(City city: cityList) {
      citySearchTrie.insert(city);
    }
  }

  @Test
  void testInsert() {
    assertFalse(citySearchTrie.isEmpty());
  }

  @Test
  void testSuggestSuccess() {
    String searchTerm = "River";
    int expectedGeoId = 4960922;
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
