package com.rvg.springboot.restapi.survey;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Service;

@Service
public class SurveyService {

    private static List<Survey> surveys = new ArrayList<>();

    static {
        // Initialize Data for the Survey
        Question question1 = new Question("Question1", "What is the most popular programming language?", "JavaScript",
                Arrays.asList("Java", "Python", "JavaScript", "C#"));
        Question question2 = new Question("Question2", "Which company developed the Java programming language?",
                "Sun Microsystems",
                Arrays.asList("Microsoft", "Apple", "Sun Microsystems", "Google"));
        Question question3 = new Question("Question3", "What does HTML stand for?", "HyperText Markup Language",
                Arrays.asList("HyperText Markup Language", "HyperText Machine Language", "HighText Markup Language",
                        "HyperText and links Markup Language"));
        Question question4 = new Question("Question4",
                "What is the name of the version control system created by Linus Torvalds?", "Git",
                Arrays.asList("Subversion", "Mercurial", "Git", "CVS"));

        List<Question> questions = new ArrayList<>(Arrays.asList(question1, question2, question3, question4));

        Survey survey = new Survey("Survey1", "My Favorite Survey", "Description of the Survey", questions);

        surveys.add(survey);
    }

    /**
     * Retrieves all surveys.
     * @return a list of all surveys
     */
    public List<Survey> retrieveAllSurveys() {
        return surveys;
    }


    /**
     * Retrieves a survey by its ID.
     * @param surveyId
     * @return the survey with the specified ID, or null if no survey is found
     */
    public Survey retrieveSurveyById(String surveyId) {
        return surveys.stream().filter(survey -> survey.getId().equals(surveyId)).findFirst().orElse(null);
    }


    /**
     * Retrieves all questions for a survey.
     * @param surveyId
     * @return a list of questions for the specified survey, or null if the survey is not found
     */
    public List<Question> retrieveAllQuestions(String surveyId) {
        Survey survey = retrieveSurveyById(surveyId);
        if (survey == null) {
            return null;
        }

        return survey.getQuestions();
    }

    
    /**
     * Retrieves a specific question from a survey.
     * @param surveyId
     * @param questionId
     * @return the question with the specified ID, or null if the question is not found
     */
    public Question retrieveSpecificSurveyQuestion(String surveyId, String questionId) {
        Survey survey = retrieveSurveyById(surveyId);
        if (survey == null) {
            return null;
        }

        return survey.getQuestions().stream().filter(question -> question.getId().equals(questionId)).findFirst()
                .orElse(null);
    }

    /**
     * Adds a new question to the specified survey.
     *
     * @param surveyId the ID of the survey to which the question will be added
     * @param question the question to be added to the survey
     * @return the ID of the newly created question
     */
    public String addNewSurveyQuestion(String surveyId, Question question) {
        List<Question> allQuestions = retrieveAllQuestions(surveyId);
        question.setId(getRandomId());
        if (allQuestions != null) {
            allQuestions.add(question);
        }

        return question.getId();
    }

    // Generate a random ID for the question
    private String getRandomId() {
        // Create a new SecureRandom instance
        SecureRandom secureRandom = new SecureRandom();
        // Generate a random 32-character ID
        String randomId = new BigInteger(32, secureRandom).toString();
        return randomId;
    }

    /**
     * Deletes a question from a survey.
     *
     * @param surveyId   the ID of the survey from which the question will be
     *                   deleted
     * @param questionId the ID of the question to be deleted
     * @return the ID of the deleted question if the question was found and deleted,
     *         otherwise returns null
     */
    public String deleteSurveyQuestion(String surveyId, String questionId) {
        Survey survey = retrieveSurveyById(surveyId);
        if (survey == null) {
            return null;
        }

        boolean removed = survey.getQuestions().removeIf(question -> question.getId().equals(questionId));
        return removed ? questionId : null;
    }

    /**
     * Updates a question in a survey.
     *
     * @param surveyId   the ID of the survey containing the question to be updated
     * @param questionId the ID of the question to be updated
     * @param question   the updated question
     * @return the ID of the updated question if the question was found and updated,
     *         otherwise returns null
     */
    public String updateSurveyQuestion(String surveyId, String questionId, Question question) {
        // Retrieve all questions for the given survey ID
        List<Question> questions = retrieveAllQuestions(surveyId);

        // Remove the question with the specified question ID
        boolean removed = questions.removeIf(q -> q.getId().equalsIgnoreCase(questionId));

        // If no question was removed, return null indicating the question was not found
        if (!removed) {
            return null;
        }

        // Add the updated question to the list
        questions.add(question);

        // Return the ID of the updated question
        return questionId;
    }

}
