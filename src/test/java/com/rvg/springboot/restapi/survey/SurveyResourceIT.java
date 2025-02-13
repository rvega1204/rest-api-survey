/**
 * Integration test for the SurveyResource REST API.
 * 
 * This test class uses Spring Boot's TestRestTemplate to perform HTTP requests
 * and validate the responses. It tests the retrieval of a specific survey question.
 * 
 * The test class is annotated with @SpringBootTest to load the full application context
 * and run the tests in a random port.
 * 
 * The test method retrieveSpecificSurveyQuestion_basicScenario() sends a GET request
 * to the specific question URL and verifies the response using JSONAssert.
 * 
 * Dependencies:
 * - org.json:json
 * - org.junit.jupiter.api.Test
 * - org.skyscreamer.jsonassert.JSONAssert
 * - org.springframework.beans.factory.annotation.Autowired
 * - org.springframework.boot.test.context.SpringBootTest
 * - org.springframework.boot.test.web.client.TestRestTemplate
 * - org.springframework.http.ResponseEntity
 */
package com.rvg.springboot.restapi.survey;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Base64;

import org.json.JSONException;
import org.junit.jupiter.api.Test;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class SurveyResourceIT {

    // URL for the specific question
    private static String SPECIFIC_QUESTION_URL = "/surveys/Survey1/questions/Question1";

    // URL for all the questions
    private static String GENERIC_QUESTIONS_URL = "/surveys/Survey1/questions";

    // TestRestTemplate to perform HTTP requests
    @Autowired
    private TestRestTemplate template;

    // JSON string representing the expected response for a specific question
    String str = """
            {
              "id": "Question1",
              "description": "What is the most popular programming language?",
              "options": [
                "C#",
                "Java",
                "Python",
                "JavaScript"
              ],
              "correctAnswer": "JavaScript"
            }
            """;

    @SuppressWarnings("null")
    @Test
    void retrieveSpecificSurveyQuestion_basicScenario() throws JSONException {
        // Create the headers
        HttpHeaders headers = createHttpContentTypeAndAuthHeaders();

        // Create the request entity with null and headers
        HttpEntity<String> httpEntity = new HttpEntity<String>(null, headers);

        // Send a POST request to the generic questions URL to get a specific question
        ResponseEntity<String> responseEntity = template.exchange(SPECIFIC_QUESTION_URL, HttpMethod.GET, httpEntity,
                String.class);
        
        // Expected JSON response for the specific question
        String expectedResponse = """
                {
                    "id":"Question1",
                    "description":"What is the most popular programming language?",
                    "correctAnswer":"JavaScript"
                }
                """;

        // Compare the expected response with the actual response
        JSONAssert.assertEquals(expectedResponse, responseEntity.getBody(), false);

        // Check the content type of the response
        assertTrue(responseEntity.getHeaders().getContentType() != null
                && responseEntity.getHeaders().getContentType().toString().contains("application/json"));

        // Check the status code
        assertTrue(responseEntity.getStatusCode().is2xxSuccessful());

        // Check that the response body is not null
        assertTrue(responseEntity.getBody() != null);

        // Check that the response contains the correct question ID
        assertTrue(responseEntity.getBody().contains("\"id\":\"Question1\""));

        // Check that the response contains the correct description
        assertTrue(responseEntity.getBody()
                .contains("\"description\":\"What is the most popular programming language?\""));

        // Check that the response contains the correct correctAnswer
        assertTrue(responseEntity.getBody().contains("\"correctAnswer\":\"JavaScript\""));
    }

    @SuppressWarnings("null")
    @Test
    void retrieveAllSurveyQuestions_basicScenario() throws JSONException {
        // Create the headers
        HttpHeaders headers = createHttpContentTypeAndAuthHeaders();

        // Create the request entity with null and headers
        HttpEntity<String> httpEntity = new HttpEntity<String>(null, headers);

        // Send a POST request to the generic questions URL to get all the questions
        ResponseEntity<String> responseEntity = template.exchange(GENERIC_QUESTIONS_URL, HttpMethod.GET, httpEntity,
                String.class);
        
        // Expected JSON response for all questions
        String expectedResponse = """
                        [
                        {
                            "id": "Question1"
                        },
                        {
                            "id": "Question2"
                        },
                        {
                            "id": "Question3"
                        },
                        {
                            "id": "Question4"
                        }
                        ]
                """;

        // Check the status code
        assertTrue(responseEntity.getStatusCode().is2xxSuccessful());

        // Check the content type of the response
        assertEquals("application/json", responseEntity.getHeaders().get("Content-Type").get(0));

        // Compare the expected response with the actual response
        JSONAssert.assertEquals(expectedResponse, responseEntity.getBody(), false);
    }

    @SuppressWarnings("null")
    @Test
    void addNewSurveyQuestion_basicScenario() throws InterruptedException {

        String requestBody = """
                {
                    "description": "Your Favorite Cloud Platform",
                    "options": [
                        "AWS",
                        "Azure",
                        "Google Cloud",
                        "Oracle Cloud"
                    ],
                    "correctAnswer": "AWS"
                }
                """;

        // Create the headers
        HttpHeaders headers = createHttpContentTypeAndAuthHeaders();

        // Create the request entity with the request body and headers
        HttpEntity<String> httpEntity = new HttpEntity<String>(requestBody, headers);

        // Send a POST request to the generic questions URL to add a new question
        ResponseEntity<String> responseEntity = template.exchange(GENERIC_QUESTIONS_URL, HttpMethod.POST, httpEntity,
                String.class);

        // Check the status code
        assertTrue(responseEntity.getStatusCode().is2xxSuccessful());

        // Check the location header in the response
        String locationHeader = responseEntity.getHeaders().get("Location").get(0);
        assertTrue(locationHeader.contains("/surveys/Survey1/questions/"));

        // Send a DELETE request to the locationHeader to delete the question
        ResponseEntity<String> responseEntityDelete = template.exchange(locationHeader, HttpMethod.DELETE, httpEntity,
                String.class);

        assertTrue(responseEntityDelete.getStatusCode().is2xxSuccessful());
    }

    /**
     * Creates and returns HttpHeaders with "Content-Type" set to "application/json"
     * and "Authorization" set to a Basic Auth encoded string for the provided
     * username and password.
     *
     * @return HttpHeaders with the necessary headers for making authenticated requests.
     */
    private HttpHeaders createHttpContentTypeAndAuthHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
        headers.add("Authorization", "Basic " + performBasicAuthEncoding("admin", "admin"));
        return headers;
    }

    /**
     * Encodes the provided username and password into a Base64 encoded string suitable for HTTP Basic Authentication.
     *
     * @param user the username to be encoded
     * @param password the password to be encoded
     * @return a Base64 encoded string in the format "username:password"
     */
    String performBasicAuthEncoding(String user, String password) {
        String auth = user + ":" + password;
        return Base64.getEncoder().encodeToString(auth.getBytes());
    }

}
