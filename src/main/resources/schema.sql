CREATE TABLE api_response (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    request_url VARCHAR(255) NOT NULL,
    query_params VARCHAR(255),
    path_variables VARCHAR(255),
    response_body TEXT
);
