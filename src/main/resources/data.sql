-- Insert example data into the api_response table
INSERT INTO api_response (request_url, query_params, path_variables, response_body)
VALUES
('/api/proxy/testPath', 'queryParam=testQuery', 'testPath', 'This is a test response');
