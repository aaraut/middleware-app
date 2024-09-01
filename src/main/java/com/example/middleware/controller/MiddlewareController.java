package com.example.middleware.controller;

import com.example.middleware.service.MiddlewareService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

@RestController
@RequestMapping("/api/proxy")
public class MiddlewareController {

    @Autowired
    private MiddlewareService middlewareService;

    @GetMapping("/**")
    public String proxyRequest(HttpServletRequest request, @RequestParam(required = false) String queryParams) {
        // Extract the actual path from the request
        String fullPath = request.getRequestURI();
        String path = fullPath.replace("/api/proxy", "");
        return middlewareService.proxyRequest(path, queryParams);

//        // Try to fetch response from the backend
//        Optional<String> response = middlewareService.fetchFromBackend(path, queryParams);
//
//        // If backend response is not available, check stored responses
//        if (response.isEmpty()) {
//            response = middlewareService.getStoredResponse(path, queryParams);
//        }
//
//        return response.orElse("Error: No response available");
    }
}


//package com.example.middleware.controller;
//
//import com.example.middleware.entity.ApiResponse;
//import com.example.middleware.service.MiddlewareService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.Optional;
//
//@RestController
//@RequestMapping("/api")
//public class MiddlewareController {
//
//    @Autowired
//    private MiddlewareService middlewareService;
//
//
//    @GetMapping("/proxy/{pathVariable}/{id}")
//    public String handleRequest(@PathVariable String pathVariable,
//                                @PathVariable(required = false) String id,
//                                @RequestParam(required = false) String queryParam) {
//        // Construct the endpoint and query parameters
//        String endpoint = "/api/" + pathVariable;
//        if (id != null) {
//            endpoint += "/" + id;
//        }
//        String queryParams = queryParam != null ? "queryParam=" + queryParam : "";
//
//        // Print the URL for debugging
//        System.out.println("Forwarding request to: http://localhost:8081" + endpoint + (queryParams.isEmpty() ? "" : "?" + queryParams));
//
//        // Forward the request to the actual backend
//        return middlewareService.forwardRequest(endpoint, queryParams, id);
//    }
//
//    @GetMapping("/fallback/{pathVariable}")
//    public String fallbackResponse(@PathVariable String pathVariable,
//                                   @RequestParam(required = false) String queryParam) {
//        String url = "/api/" + pathVariable;
//        String queryParams = queryParam != null ? "queryParam=" + queryParam : "";
//        String pathVariables = pathVariable;
//
//        Optional<ApiResponse> response = middlewareService.getApiResponse(url, queryParams, pathVariables);
//        return response.map(ApiResponse::getResponseBody).orElse("Fallback response: default data");
//    }
//}
