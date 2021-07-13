package com.example.airgraft.service;

import static java.util.Collections.singletonList;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import com.example.airgraft.model.pojo.City;
import com.example.airgraft.utils.CityMapProcessor;
import com.example.airgraft.utils.CitySearchTrie;
import java.util.Map;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@SpringBootTest
class SuggestionServiceTest {

  @Autowired
  private SuggestionService suggestionServiceMock;
  @MockBean
  private CitySearchTrie citySearchTrie;
  @MockBean
  private CityMapProcessor cityMapProcessor;

  @Mock
  private Map<Integer, City> cityMapMock;

  @Test
  void testSuggest() {
    City city = City.builder().build();
    int expectedId = 123;

    when(citySearchTrie.suggest(any())).thenReturn(singletonList(expectedId));
    when(cityMapProcessor.getCityMap()).thenReturn(cityMapMock);
    when(cityMapMock.get(expectedId)).thenReturn(city);

    Map<Integer, City> actual = suggestionServiceMock.suggest("test");

    assertTrue(actual.containsKey(expectedId));
  }

}
