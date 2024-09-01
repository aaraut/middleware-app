package com.example.middleware.service;

import com.example.middleware.entity.ApiResponse;
import com.example.middleware.repository.ApiResponseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Service
public class MiddlewareService {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private ApiResponseRepository apiResponseRepository;

    private static final String BACKEND_BASE_URL = "http://localhost:8081/api";

    public String proxyRequest(String path, String queryParams) {
        String backendUrl = BACKEND_BASE_URL + path;

        if (queryParams != null && !queryParams.isEmpty()) {
            backendUrl += "?" + queryParams;
        }

        try {
            HttpHeaders headers = new HttpHeaders();
            HttpEntity<String> entity = new HttpEntity<>(headers);

            ResponseEntity<String> response = restTemplate.exchange(backendUrl, HttpMethod.GET, entity, String.class);

            ApiResponse apiResponse = ApiResponse.builder()
                    .path(path)
                    .queryParams(queryParams)
                    .responseBody(response.getBody())
                    .timestamp(java.time.LocalDateTime.now().toString())
                    .build();
            apiResponseRepository.save(apiResponse);

            return response.getBody();
        } catch (Exception e) {
            System.err.println("Error calling backend: " + e.getMessage());

            List<ApiResponse> storedResponses = apiResponseRepository.findByPathAndQueryParams(path, queryParams);

            if (!storedResponses.isEmpty()) {
                // Return the most recent or appropriate response if multiple are found
                ApiResponse latestResponse = storedResponses.stream()
                        .max(Comparator.comparing(ApiResponse::getTimestamp))
                        .orElse(null);

                return latestResponse != null ? latestResponse.getResponseBody() : "Error: No response available and backend is down";
            } else {
                return "Error: No response available and backend is down";
            }
        }
    }

}


//package com.example.middleware.service;
//
//import com.example.middleware.entity.ApiResponse;
//import com.example.middleware.repository.ApiResponseRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//import org.springframework.web.client.RestTemplate;
//import org.springframework.http.ResponseEntity;
//import org.springframework.http.HttpMethod;
//import org.springframework.http.HttpEntity;
//import org.springframework.http.HttpHeaders;
//
//import java.util.Optional;
//
//@Service
//public class MiddlewareService {
//
//    @Autowired
//    private RestTemplate restTemplate;
//
//    @Autowired
//    private ApiResponseRepository apiResponseRepository;
//
//    private static final String BACKEND_BASE_URL = "http://localhost:8081/api";
//
//    public Optional<String> fetchFromBackend(String path, String queryParams) {
//        String backendUrl = BACKEND_BASE_URL + path;
//
//        if (queryParams != null && !queryParams.isEmpty()) {
//            backendUrl += "?" + queryParams;
//        }
//
//        try {
//            HttpHeaders headers = new HttpHeaders();
//            HttpEntity<String> entity = new HttpEntity<>(headers);
//
//            ResponseEntity<String> response = restTemplate.exchange(backendUrl, HttpMethod.GET, entity, String.class);
//
//            ApiResponse apiResponse = ApiResponse.builder()
//                    .path(path)
//                    .queryParams(queryParams)
//                    .responseBody(response.getBody())
//                    .timestamp(java.time.LocalDateTime.now().toString())
//                    .build();
//            apiResponseRepository.save(apiResponse);
//
//            return Optional.of(response.getBody());
//        } catch (Exception e) {
//            System.err.println("Error calling backend: " + e.getMessage());
//
//            // Try to fetch from stored responses in the database
//            Optional<String> storedResponse = apiResponseRepository.findByPathAndQueryParams(path, queryParams)
//                    .map(ApiResponse::getResponseBody);
//
//            if (storedResponse.isPresent()) {
//                return storedResponse.get().describeConstable();
//            } else {
//                return "Error: No response available and backend is down".describeConstable();
//            }
//        }
//    }
//
//    public Optional<String> getStoredResponse(String path, String queryParams) {
//        return apiResponseRepository.findByPathAndQueryParams(path, queryParams)
//                .map(ApiResponse::getResponseBody);
//    }
//}
