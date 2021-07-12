package com.example.airgraft.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doCallRealMethod;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.example.airgraft.converter.SuggestionResultConverter;
import com.example.airgraft.model.SuggestResult;
import com.example.airgraft.service.SuggestionService;
import java.util.LinkedList;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(SuggestionController.class)
class SuggestionControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private SuggestionService suggestionService;
  @MockBean
  private SuggestionResultConverter suggestionResultConverter;

  @Test
  void suggestionAPITest200() throws Exception {
    List<SuggestResult> expected = new LinkedList<>();
    when(suggestionService.suggest(any(), any(), any())).thenReturn(expected);
    doCallRealMethod().when(suggestionResultConverter).convert(any());


    this.mockMvc.perform(get("/suggestion")
          .queryParam("q", "test")
          .queryParam("lat", "10.00")
          .queryParam("long", "20.00"))
        .andDo(print())
        .andExpect(status().isOk());
  }

  @Test
  void suggestionAPITest400() throws Exception {
    List<SuggestResult> expected = new LinkedList<>();
    when(suggestionService.suggest(any(), any(), any())).thenReturn(expected);
    doCallRealMethod().when(suggestionResultConverter).convert(any());

    this.mockMvc.perform(get("/suggestion"))
        .andDo(print())
        .andExpect(status().isBadRequest());
  }

}
