package com.example.airgraft.converter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import com.example.airgraft.model.dto.SuggestionResponseDTO;
import com.example.airgraft.model.pojo.City;
import com.example.airgraft.utils.CityMapProcessor;
import java.util.LinkedHashMap;
import java.util.Map;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@SpringBootTest
class SuggestionResultConverterTest {

  @Autowired
  private SuggestionResultConverter suggestionResultConverter;
  @MockBean
  private CityMapProcessor cityMapProcessor;
  @Mock
  private Map<Integer, City> cityMap;


  @Test
  void testConvertToSuggestionResponseDTO() {
    Map<Integer, Double> rankingResult = new LinkedHashMap<>();
    rankingResult.put(111, 0.9d);
    rankingResult.put(222, 1.0d);

    City firstCity = City.builder()
        .geoNameId(111)
        .name("First City")
        .longitude(1.0)
        .latitude(1.0)
        .countryCode("CA")
        .build();

    City secondCity = City.builder()
        .geoNameId(222)
        .name("Second City")
        .longitude(2.0)
        .latitude(2.0)
        .countryCode("US")
        .build();


    when(cityMapProcessor.getCityMap()).thenReturn(cityMap);
    when(cityMap.get(111)).thenReturn(firstCity);
    when(cityMap.get(222)).thenReturn(secondCity);

    SuggestionResponseDTO actual = suggestionResultConverter.convert(rankingResult);

    assertEquals(2, actual.getSuggestions().size());
    assertEquals(secondCity.getName().concat(" ").concat(secondCity.getCountryCode()), actual.getSuggestions().get(0).getName());
    assertEquals(firstCity.getName().concat(" ").concat(firstCity.getCountryCode()), actual.getSuggestions().get(1).getName());

  }
}
