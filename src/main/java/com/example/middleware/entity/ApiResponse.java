package com.example.middleware.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Entity
@Table(name = "api_response", uniqueConstraints = @UniqueConstraint(columnNames = {"path", "queryParams"}))
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ApiResponse {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String path;

    @Column
    private String queryParams;

    @Column(length = 4096)  // Assuming response body can be long
    private String responseBody;

    @Column
    private String timestamp;




    // Additional constructors, getters, and setters if not using Lombok
}
