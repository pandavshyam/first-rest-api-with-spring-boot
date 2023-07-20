package com.example.springboot.firstrestapi.survey;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import java.util.Arrays;

import org.junit.jupiter.api.Test;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

@WebMvcTest(controllers = SurveyResource.class)
public class SurveyResourceTest {

  @MockBean
  private SurveyService surveyService;

  @Autowired
  private MockMvc mockMvc;

  private static String SPECIFIC_QUESTION_URL = "http://localhost:8080/surveys/Survey1/questions/Question1";
  private static String CREATE_NEW_QUESTION = "http://localhost:8080/surveys/Survey1/questions";

  @Test
  void retrieveSpecificSurveyQuestion_404Scenario() throws Exception{
    RequestBuilder requestBuilder = MockMvcRequestBuilders.get(SPECIFIC_QUESTION_URL).accept(MediaType.APPLICATION_JSON);
    MvcResult mvcResult = mockMvc.perform(requestBuilder).andReturn();
    assertEquals(404, mvcResult.getResponse().getStatus());
  }

  @Test
  void retrieveSpecificSurveyQuestion_basicScenario() throws Exception{
    Question question = new Question("Question1", "Most Popular Cloud Platform Today", Arrays.asList("AWS", "Azure", "Google Cloud", "Oracle Cloud"), "AWS");
    when(surveyService.retrieveSpecificSurveyQuestion("Survey1", "Question1")).thenReturn(question);

    RequestBuilder requestBuilder = MockMvcRequestBuilders.get(SPECIFIC_QUESTION_URL).accept(MediaType.APPLICATION_JSON);
    MvcResult mvcResult = mockMvc.perform(requestBuilder).andReturn();
    String expectedResponse =
    """
      {
        "id": "Question1",
        "description": "Most Popular Cloud Platform Today",
        "options": ["AWS","Azure","Google Cloud","Oracle Cloud"],
        "correctAnswer": "AWS"
      }
    """;

    assertEquals(200, mvcResult.getResponse().getStatus());
    JSONAssert.assertEquals(expectedResponse, mvcResult.getResponse().getContentAsString(), true);
  }

  @Test
  void addNewSurveyQuestion_basicScenario() throws Exception{
    String requestBody =
    """
    {
      "id": "Question 4",
      "description": "Some text here",
      "options": [
          "AWS",
          "Azure",
          "Google Cloud",
          "Oracle Cloud",
          "Rackspace"
      ],
      "correctAnswer": "AWS"
    }
    """;
    when(surveyService.addNewSurveyQuestion(anyString(), any())).thenReturn("Question5");
    RequestBuilder requestBuilder = MockMvcRequestBuilders.post(CREATE_NEW_QUESTION).accept(MediaType.APPLICATION_JSON).content(requestBody).contentType(MediaType.APPLICATION_JSON);
    MvcResult mvcResult = mockMvc.perform(requestBuilder).andReturn();
    assertEquals(201, mvcResult.getResponse().getStatus());
    assertEquals(mvcResult.getResponse().getHeader("Location"), "http://localhost:8080/surveys/Survey1/questions/Question5");
  }
}
