package com.example.airgraft.utils;

import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.example.airgraft.model.pojo.City;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@SpringBootTest
@RequiredArgsConstructor
class CityRawDataProcessorTest {

  private static final String TEST_FILE_INPUT_PATH = "./src/test/resources/static/CA_test.txt";
  private static final String TEST_FILE_OUTPUT_PATH = "./src/test/resources/static/CA_test_map.properties";

  private static final CityRawDataProcessor cityRawDataProcessor = new CityRawDataProcessor();

  @BeforeAll()
  public static void init() {
    cityRawDataProcessor.processFile(TEST_FILE_INPUT_PATH, TEST_FILE_OUTPUT_PATH);
  }

  @Test
  void testProcessRawData() throws IOException {
    FileInputStream fileInputStream = new FileInputStream(TEST_FILE_OUTPUT_PATH);
    assertNotEquals(0, fileInputStream.available());
  }

  @Test
  void testReadFromProperties() {
    Map<Integer, City> cityMap = cityRawDataProcessor.readFromProperties(TEST_FILE_OUTPUT_PATH);
    assertNotEquals(0, cityMap.size());
  }

}
