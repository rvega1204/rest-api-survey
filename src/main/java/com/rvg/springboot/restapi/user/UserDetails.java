/**
 * Entity class representing user details.
 * 
 * <p>This class is annotated with JPA annotations to map it to a database table.
 * It contains fields for user ID, name, and role, along with corresponding getters.
 * The class also provides constructors for creating instances and a toString method
 * for generating a string representation of the user details.</p>
 * 
 * <p>Annotations:</p>
 * <ul>
 *   <li>@Entity - Specifies that the class is an entity and is mapped to a database table.</li>
 *   <li>@Id - Specifies the primary key of an entity.</li>
 *   <li>@GeneratedValue - Provides for the specification of generation strategies for the values of primary keys.</li>
 * </ul>
 * 
 * <p>Fields:</p>
 * <ul>
 *   <li>id - The unique identifier for the user (primary key).</li>
 *   <li>name - The name of the user.</li>
 *   <li>role - The role of the user.</li>
 * </ul>
 * 
 * <p>Constructors:</p>
 * <ul>
 *   <li>UserDetails() - Default constructor.</li>
 *   <li>UserDetails(String name, String role) - Parameterized constructor to initialize name and role.</li>
 * </ul>
 * 
 * <p>Methods:</p>
 * <ul>
 *   <li>getId() - Returns the user ID.</li>
 *   <li>getName() - Returns the user name.</li>
 *   <li>getRole() - Returns the user role.</li>
 *   <li>toString() - Returns a string representation of the user details.</li>
 * </ul>
 */
package com.rvg.springboot.restapi.user;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

@Entity
public class UserDetails {

    public UserDetails() {
    }

    public UserDetails(String name, String role) {
        super();
        this.name = name;
        this.role = role;
    }

    @Id
    @GeneratedValue
    private Long id;

    private String name;
    private String role;

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getRolerole() {
        return role;
    }

    @Override
    public String toString() {
        return String.format("UserDetails [id= %s, name= %s, role= %s]", id, name, role);
    }
}
