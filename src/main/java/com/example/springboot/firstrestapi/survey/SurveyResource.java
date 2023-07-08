package com.example.springboot.firstrestapi.survey;

import java.util.List;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SurveyResource {

  SurveyService surveyService;

  public SurveyResource(SurveyService surveyService) {
    super();
    this.surveyService = surveyService;
  }

  @RequestMapping("/surveys")
  public List<Survey> retrieveAllSurveys(){
    return surveyService.retrieveAllSurveys();
  }
}
