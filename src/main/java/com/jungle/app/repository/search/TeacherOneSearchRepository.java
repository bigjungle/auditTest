package com.jungle.app.repository.search;

import com.jungle.app.domain.TeacherOne;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the TeacherOne entity.
 */
public interface TeacherOneSearchRepository extends ElasticsearchRepository<TeacherOne, Long> {
}
