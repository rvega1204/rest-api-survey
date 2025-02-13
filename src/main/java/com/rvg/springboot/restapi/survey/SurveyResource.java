/**
 * This class is a REST controller for managing surveys and their questions.
 * It provides endpoints for retrieving, adding, updating, and deleting surveys and questions.
 * 
 * Endpoints:
 * - GET /surveys: Retrieve all surveys.
 * - GET /surveys/{surveyId}: Retrieve a specific survey by its ID.
 * - GET /surveys/{surveyId}/questions: Retrieve all questions for a specific survey.
 * - GET /surveys/{surveyId}/questions/{questionId}: Retrieve a specific question from a specific survey.
 * - POST /surveys/{surveyId}/questions: Add a new question to a specific survey.
 * - DELETE /surveys/{surveyId}/questions/{questionId}: Delete a specific question from a specific survey.
 * - PUT /surveys/{surveyId}/questions/{questionId}: Update a specific question in a specific survey.
 * 
 * This class uses the SurveyService to perform the actual operations on surveys and questions.
 * It handles HTTP requests and responses, including error handling for not found resources.
 * 
 * @author rvega
 */
package com.rvg.springboot.restapi.survey;

import java.net.URI;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/surveys")
public class SurveyResource {

    private final SurveyService surveyService;

    public SurveyResource(SurveyService surveyService) {
        this.surveyService = surveyService;
    }

    // Mapping HTTP GET requests to the specified URL pattern
    // The method retrieves all surveys
    @RequestMapping(method = RequestMethod.GET)
    public List<Survey> retrieveAllSurveys() {
        return surveyService.retrieveAllSurveys();
    }

    // Mapping HTTP GET requests to the specified URL pattern
    // The method retrieves a specific survey
    @RequestMapping(value = "/{surveyId}", method = RequestMethod.GET)
    public Survey retrieveSurvey(@PathVariable String surveyId) {
        Survey survey = surveyService.retrieveSurveyById(surveyId);
        if (survey != null) {
            return survey;
        }

        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Survey not found");
    }

    // Mapping HTTP POST requests to the specified URL pattern
    // The method adds all the survey questions
    @RequestMapping(value = "/{surveyId}/questions", method = RequestMethod.GET)
    public List<Question> retrieveAllSurveyQuestions(@PathVariable String surveyId) {
        List<Question> questions = surveyService.retrieveAllQuestions(surveyId);
        if (questions != null) {
            return questions;
        }

        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Questions for Survey: " + surveyId + " not found");
    }

    // Mapping HTTP GET requests to the specified URL pattern
    // The method retrieves a specific question from a specific survey
    @RequestMapping(value = "/{surveyId}/questions/{questionId}", method = RequestMethod.GET)
    public Question retrieveSpecificSurveyQuestion(@PathVariable String surveyId, @PathVariable String questionId) {
        Question question = surveyService.retrieveSpecificSurveyQuestion(surveyId, questionId);
        if (question != null) {
            return question;
        }
        
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Question not found");
    }

    /**
     * Adds a new question to the specified survey.
     *
     * @param surveyId the ID of the survey to which the question will be added
     * @param question the question to be added to the survey
     * @return a ResponseEntity with the location of the newly created question
     */
    @RequestMapping(value = "/{surveyId}/questions", method = RequestMethod.POST)
    public ResponseEntity<Object> addNewSurveyQuestion(@PathVariable String surveyId, @RequestBody Question question) {
        String questionId = surveyService.addNewSurveyQuestion(surveyId, question);
        URI location = URI.create(String.format("/surveys/%s/questions/%s", surveyId, questionId));
        return ResponseEntity.created(location).build();
    }

    // Mapping HTTP DELETE requests to the specified URL pattern
    @RequestMapping(value = "/{surveyId}/questions/{questionId}", method = RequestMethod.DELETE)
    public ResponseEntity<Void> deleteSurveyQuestion(@PathVariable String surveyId, @PathVariable String questionId) {
        // Attempt to delete the survey question
        String question = surveyService.deleteSurveyQuestion(surveyId, questionId);

        // If the question is not found or deletion fails, throw an exception with a not
        // found status
        if (question == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Error deleting question: " + questionId);
        }

        // Return a no-content response indicating successful deletion
        return ResponseEntity.noContent().build();
    }

    
    /**
     * Mapping HTTP PUT requests to the specified URL pattern for updating a survey question
     * @param surveyId
     * @param questionId
     * @param question
     * @return
     */
    @RequestMapping(value = "/{surveyId}/questions/{questionId}", method = RequestMethod.PUT)
    public ResponseEntity<Void> updateSurveyQuestion(@PathVariable String surveyId, @PathVariable String questionId,
            @RequestBody Question question) {
        // Attempt to update the survey question
        String updatedQuestionId = surveyService.updateSurveyQuestion(surveyId, questionId, question);

        // If the question is not found or update fails, throw an exception with a not
        // found status
        if (updatedQuestionId == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Error updating question: " + questionId);
        }

        // Return a no-content response indicating successful update
        return ResponseEntity.noContent().build();
    }
}
