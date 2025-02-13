/**
 * This class contains unit tests for JSON assertions using the JSONAssert library.
 * It verifies that the actual JSON response matches the expected JSON response.
 * 
 * <p>Tested scenarios include:</p>
 * <ul>
 *   <li>Basic JSON structure comparison</li>
 * </ul>
 * 
 * <p>Dependencies:</p>
 * <ul>
 *   <li>org.json: Provides JSON handling capabilities</li>
 *   <li>org.skyscreamer.jsonassert: Provides JSON assertion capabilities</li>
 *   <li>org.junit.jupiter.api: Provides JUnit 5 testing framework</li>
 * </ul>
 * 
 * <p>Example usage:</p>
 * <pre>
 * {@code
 * JSONAssert.assertEquals(expectedResponse, actualResponse, true);
 * }
 * </pre>
 * 
 * @see org.skyscreamer.jsonassert.JSONAssert
 * @see org.json.JSONException
 * @see org.junit.jupiter.api.Test
 */
package com.rvg.springboot.restapi.survey;

import org.json.JSONException;
import org.junit.jupiter.api.Test;
import org.skyscreamer.jsonassert.JSONAssert;


public class JsonAssertTest {

    @Test
    void jsonAssertBasic() throws JSONException {
        String expectedResponse =
				"""
				{
					"id":"Question1",
					"description":"What is the most popular programming language?",
					"correctAnswer":"JavaScript"
				}
				""";

        String actualResponse =
                """
                {
                    "id":"Question1",
                    "description":"What is the most popular programming language?",
                    "correctAnswer":"JavaScript"
                }
                """;

        JSONAssert.assertEquals(expectedResponse, actualResponse, false);
    }
}
