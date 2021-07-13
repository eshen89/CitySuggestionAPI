package com.example.airgraft.utils;

import static org.junit.jupiter.api.Assertions.assertNotEquals;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("test")
@SpringBootTest
@RequiredArgsConstructor
class CityMapProcessorTest {

  private static final String TEST_FILE_INPUT_PATH = "./src/test/resources/static/CA_test.txt";

  private final static CityMapProcessor cityMapProcessor = new CityMapProcessor();

  @BeforeAll()
  public static void init() {
    cityMapProcessor.processFile(TEST_FILE_INPUT_PATH);
  }

  @Test
  void testProcessRawData() {
    assertNotEquals(0, cityMapProcessor.getCityMap().size());
  }

}
