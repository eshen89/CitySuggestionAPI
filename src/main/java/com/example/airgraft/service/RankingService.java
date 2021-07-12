package com.example.airgraft.service;

import com.example.airgraft.model.pojo.City;
import java.awt.geom.Point2D;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Service;

@Service
public class RankingService {

  public Map<Integer, java.lang.Double> rank(String cityName, Double latitude, Double longitude, List<City> candidates){
    var lat = latitude == null ? 0d : latitude;
    var longi = longitude == null ? 0d : longitude;
    if(lat == 0d && longi == 0d) {
      return rankByCityName(cityName, candidates);
    }
    return rankByCoordinates(lat, longi, candidates);
  }

  private Map<Integer, java.lang.Double> rankByCityName(String cityName, List<City> candidates){
    Map<Integer, java.lang.Double> scoreMap = new LinkedHashMap<>();
    var cityNameLen = BigDecimal.valueOf(cityName.length());

    candidates.forEach(city -> {
      var candidateCityNameLen = BigDecimal.valueOf(city.getName().length());
      var relevanceScore = cityNameLen.divide(candidateCityNameLen,2, RoundingMode.HALF_UP).doubleValue();
      scoreMap.put(city.getGeoNameId(), relevanceScore);
    });
    return scoreMap;
  }

  private Map<Integer, java.lang.Double> rankByCoordinates(double latitude, double longitude, List<City> candidates) {
    Map<Integer, java.lang.Double> scoreMap = new LinkedHashMap<>();
    var basePoint = new Point2D.Double(latitude, longitude);
    var maxRange = findMaxRange(basePoint, candidates);

    candidates.forEach(city -> {
      var candidatePoint = new Point2D.Double(city.getLatitude(), city.getLongitude());
      double distance = basePoint.distance(candidatePoint);
      var relevanceScore = BigDecimal.ONE
          .subtract(BigDecimal.valueOf(distance).divide(maxRange, 2, RoundingMode.HALF_UP));
      scoreMap.put(city.getGeoNameId(), relevanceScore.doubleValue());
    });
    return scoreMap;
  }

  private BigDecimal findMaxRange(Point2D.Double base, List<City> candidates) {
    var maxDistance = 0d;

    for(City city: candidates) {
      var candidatePoint = new Point2D.Double(city.getLatitude(), city.getLongitude());
      double distance = Math.abs(base.distance(candidatePoint));
      maxDistance = Double.max(maxDistance, distance);
    }
    return BigDecimal.valueOf(maxDistance);
  }

}
