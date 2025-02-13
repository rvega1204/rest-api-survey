package com.rvg.springboot.restapi.survey;

import java.util.List;

/**
 * Represents a question in a survey.
 * 
 * <p>This class encapsulates the details of a survey question, including its
 * unique identifier, description, correct answer, and a list of possible
 * options.</p>
 * 
 * <p>Example usage:</p>
 * <pre>
 * {@code
 * List<String> options = Arrays.asList("Option1", "Option2", "Option3", "Option4");
 * Question question = new Question("1", "What is the capital of France?", "Paris", options);
 * }
 * </pre>
 * 
 * @author rvega
 */
public class Question {

    public Question(String id, String description, String correctAnswer, List<String> options) {
        this.id = id;
        this.description = description;
        this.correctAnswer = correctAnswer;
        this.options = options;
    }

    private String id;
    private String description;
    private String correctAnswer;
    private List<String> options;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public String getCorrectAnswer() {
        return correctAnswer;
    }

    public List<String> getOptions() {
        return options;
    }

    @Override
    public String toString() {
        return String.format("Question [id= %s, description= %s, correctAnswer= %s, options= %s]", id, description,
                correctAnswer, options);
    }

}
