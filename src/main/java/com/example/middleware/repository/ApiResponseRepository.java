package com.example.middleware.repository;

import com.example.middleware.entity.ApiResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ApiResponseRepository extends JpaRepository<ApiResponse, Long> {

    @Query("SELECT a FROM ApiResponse a WHERE a.path = :path AND a.queryParams = :queryParams")
    List<ApiResponse> findByPathAndQueryParams(@Param("path") String path, @Param("queryParams") String queryParams);

}

//@Repository
//public interface ApiResponseRepository extends JpaRepository<ApiResponse, Long> {
//
//    // Use Optional to handle cases where no results are found
////    Optional<ApiResponse> findByPathAndQueryParams(String path, String queryParams);
//    List<ApiResponse> findByPathAndQueryParams(String path, String queryParams);
//}
