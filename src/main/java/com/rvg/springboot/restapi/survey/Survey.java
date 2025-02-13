/**
 * Represents a survey with an id, title, description, and a list of questions.
 */
package com.rvg.springboot.restapi.survey;

import java.util.List;

public class Survey {

    public Survey() {
        
    }
    
    // Constructor to initialize the fields of the Survey object
    public Survey(String id, String title, String description, List<Question> questions) {
        super();
        this.id = id;
        this.title = title;
        this.description = description;
        this.questions = questions;
    }
    
    private String id;
    private String title;
    private String description;
    private List<Question> questions;

    // Getters for the fields of the Survey object
    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public List<Question> getQuestions() {
        return questions;
    }

    // toString method to print the Survey object
    @Override
    public String toString() {
        return String.format("Survey [id= %s, title= %s, description= %s, questions= %s]", id, title,
                description, questions);
    }

    
}
