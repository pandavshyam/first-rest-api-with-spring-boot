package com.example.springboot.firstrestapi.survey;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

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

  @RequestMapping("/surveys/{surveyId}")
  public Survey retrieveSurveyById(@PathVariable String surveyId){
    Survey survey = surveyService.retrieveSurveyById(surveyId);
    if (survey == null)
      throw new ResponseStatusException(HttpStatus.NOT_FOUND);
    return survey;
  }
}
