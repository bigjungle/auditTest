package com.jungle.app.repository.search;

import com.jungle.app.domain.StudentOne;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the StudentOne entity.
 */
public interface StudentOneSearchRepository extends ElasticsearchRepository<StudentOne, Long> {
}
