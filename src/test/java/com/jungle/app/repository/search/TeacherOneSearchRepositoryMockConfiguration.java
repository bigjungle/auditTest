package com.jungle.app.repository.search;

import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Configuration;

/**
 * Configure a Mock version of TeacherOneSearchRepository to test the
 * application without starting Elasticsearch.
 */
@Configuration
public class TeacherOneSearchRepositoryMockConfiguration {

    @MockBean
    private TeacherOneSearchRepository mockTeacherOneSearchRepository;

}
