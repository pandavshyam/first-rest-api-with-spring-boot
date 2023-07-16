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

  @Autowired
  private TestRestTemplate template;

  @Test
  void retrieveSpecificSurveyQuestion_basicScenario() throws JSONException {
    ResponseEntity<String> responseEntity = template.getForEntity(SPECIFIC_QUESTION_URL, String.class);
    String expectedResponse = """
      {"id":"Question1","description":"Most Popular Cloud Platform Today","options":["AWS","Azure","Google Cloud","Oracle Cloud"],"correctAnswer":"AWS"}
        """;

    assertTrue(responseEntity.getStatusCode().is2xxSuccessful());
    assertEquals("application/json", responseEntity.getHeaders().get("Content-Type").get(0));
    JSONAssert.assertEquals(expectedResponse, responseEntity.getBody(), true);
  }
}
