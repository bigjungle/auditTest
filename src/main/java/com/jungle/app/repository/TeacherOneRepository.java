package com.jungle.app.repository;

import com.jungle.app.domain.TeacherOne;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the TeacherOne entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TeacherOneRepository extends JpaRepository<TeacherOne, Long> {

}
