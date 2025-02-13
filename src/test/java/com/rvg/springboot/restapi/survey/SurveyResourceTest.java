package com.rvg.springboot.restapi.survey;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import java.util.Arrays;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.skyscreamer.jsonassert.JSONAssert;

// Test the SurveyResource class using the SpringExtension and WebMvcTest
// The WebMvcTest annotation is used to test the REST controller
// The MockBean annotation is used to mock the SurveyService dependency
// The MockMvc is used to perform HTTP requests in tests
// The RequestBuilder is used to build the HTTP request
@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = SurveyResource.class)
@AutoConfigureMockMvc(addFilters = false)
public class SurveyResourceTest {

    // Mock the SurveyService to simulate its behavior
    @MockBean
    private SurveyService surveyService;

    // Autowire the MockMvc to perform HTTP requests in tests
    @Autowired
    private MockMvc mockMvc;

    // URL for the specific survey question
    private String SPECIFIC_QUESTION_URL = "http://localhost:8080/surveys/Survey1/questions/Question1";

    // URL for generic questions
    private static String GENERIC_QUESTION_URL = "http://localhost:8080/surveys/Survey1/questions";

    @Test
    public void retrieveSpecificSurveyQuestion_basicScenario() throws Exception {
        // Create a GET request for the specific survey question URL
        RequestBuilder requestBuilder = MockMvcRequestBuilders.get(SPECIFIC_QUESTION_URL)
                .accept(MediaType.APPLICATION_JSON);

        // Create a sample Question object to be returned by the mock service
        Question question = new Question("Question1", "Most Popular Cloud Platform Today", "AWS",
                Arrays.asList("AWS", "Azure", "Google Cloud", "Oracle Cloud"));

        // Configure the mock service to return the sample Question object
        when(surveyService.retrieveSpecificSurveyQuestion("Survey1", "Question1")).thenReturn(question);

        // Perform the request and get the result
        MvcResult mvcResult = mockMvc.perform(requestBuilder).andReturn();

        // Expected JSON response
        String expectedResponse = """
                {
                    "id":"Question1",
                    "description":"Most Popular Cloud Platform Today",
                    "options":["AWS","Azure","Google Cloud","Oracle Cloud"],
                    "correctAnswer":"AWS"
                }
                """;

        // Get the response from the result
        MockHttpServletResponse response = mvcResult.getResponse();

        // Assert that the status is 200 OK
        assertEquals(200, response.getStatus());

        // Assert that the response content matches the expected JSON
        JSONAssert.assertEquals(expectedResponse, response.getContentAsString(), false);
    }

    @Test
    void retrieveSpecificSurveyQuestion_404Scenario() throws Exception {
        // Create a GET request for the specific survey question URL
        RequestBuilder requestBuilder = MockMvcRequestBuilders.get(SPECIFIC_QUESTION_URL)
                .accept(MediaType.APPLICATION_JSON);

        // Perform the request and get the result
        MvcResult mvcResult = mockMvc.perform(requestBuilder).andReturn();

        // Assert that the status is 404 Not Found
        assertEquals(404, mvcResult.getResponse().getStatus());
    }

    /**
     * Test case for adding a new survey question.
     * 
     * Scenario: Basic scenario where a new survey question is added successfully.
     * 
     * @throws Exception if any error occurs during the test execution
     * 
     * Given:
     * - A request body containing the survey question details.
     * - Mocking the surveyService to return a predefined question ID when a new question is added.
     * 
     * When:
     * - A POST request is made to the GENERIC_QUESTION_URL with the request body.
     * 
     * Then:
     * - The response status should be 201 (Created).
     * - The Location header in the response should contain the URL of the newly created question.
     */
    @Test
	void addNewSurveyQuestion_basicScenario() throws Exception {

		String requestBody = """
				{
				  "description": "Your Favorite Language",
				  "options": [
				    "Java",
				    "Python",
				    "JavaScript",
				    "Haskell"
				  ],
				  "correctAnswer": "Java"
				}
			""";
		
		when(surveyService.addNewSurveyQuestion(anyString(),any())).thenReturn("SOME_ID");

		RequestBuilder requestBuilder = 
				MockMvcRequestBuilders.post(GENERIC_QUESTION_URL)
				.accept(MediaType.APPLICATION_JSON)
				.content(requestBody).contentType(MediaType.APPLICATION_JSON);

		MvcResult mvcResult = mockMvc.perform(requestBuilder).andReturn();		
		
		MockHttpServletResponse response = mvcResult.getResponse();
		String locationHeader = response.getHeader("Location");
		
		assertEquals(201, response.getStatus());
        assertNotNull(locationHeader);
		assertTrue(locationHeader.contains("/surveys/Survey1/questions/SOME_ID"));
		
	}

}
