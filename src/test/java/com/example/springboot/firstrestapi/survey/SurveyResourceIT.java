package com.example.springboot.firstrestapi.survey;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.json.JSONException;
import org.junit.jupiter.api.Test;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class SurveyResourceIT {

  String str = """
    {
      "id": "Question1",
      "description": "Most Popular Cloud Platform Today",
      "options": ["AWS", "Azure", "Google Cloud", "Oracle Cloud"],
      "correctAnswer": "AWS"
    }
      """;

  private static String SPECIFIC_QUESTION_URL = "/surveys/Survey1/questions/Question1";
  private static String ALL_SURVEY_QUESTIONS = "/surveys/Survey1/questions";

  @Autowired
  private TestRestTemplate template;

  @Test
  void retrieveSpecificSurveyQuestion_basicScenario() throws JSONException {
    ResponseEntity<String> responseEntity = template.getForEntity(SPECIFIC_QUESTION_URL, String.class);
    String expectedResponse =
    """
      {"id":"Question1","description":"Most Popular Cloud Platform Today","options":["AWS","Azure","Google Cloud","Oracle Cloud"],"correctAnswer":"AWS"}
    """;

    assertTrue(responseEntity.getStatusCode().is2xxSuccessful());
    assertEquals("application/json", responseEntity.getHeaders().get("Content-Type").get(0));
    JSONAssert.assertEquals(expectedResponse, responseEntity.getBody(), true);
  }

  @Test
  void retrieveAllSurveyQuestions_basicScenario() throws JSONException {
    ResponseEntity<String> responseEntity = template.getForEntity(ALL_SURVEY_QUESTIONS, String.class);
    String expectedResponse =
    """
      [{"id":"Question1","description":"Most Popular Cloud Platform Today","options":["AWS","Azure","Google Cloud","Oracle Cloud"],"correctAnswer":"AWS"},{"id":"Question2","description":"Fastest Growing Cloud Platform","options":["AWS","Azure","Google Cloud","Oracle Cloud"],"correctAnswer":"Google Cloud"},{"id":"Question3","description":"Most Popular DevOps Tool","options":["Kubernetes","Docker","Terraform","Azure DevOps"],"correctAnswer":"Kubernetes"}]
    """;

    assertTrue(responseEntity.getStatusCode().is2xxSuccessful());
    assertEquals("application/json", responseEntity.getHeaders().get("Content-Type").get(0));
    JSONAssert.assertEquals(expectedResponse, responseEntity.getBody(), true);
  }
}
