package com.example.airgraft.config;

import com.example.airgraft.utils.CityMapProcessor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Configuration
public class CityMapProcessorConfig {

  @Value("${city.input.CA}")
  private String caInput;
  @Value("${city.input.US}")
  private String usInput;

  @Bean
  public CityMapProcessor cityMapProcessor() {
    var cityMapProcessor = new CityMapProcessor();
    log.info("Processing raw data files...");
    cityMapProcessor.processFile(caInput);
    cityMapProcessor.processFile(usInput);
    return cityMapProcessor;
  }

}
