package com.jungle.app.repository;

import com.jungle.app.domain.StudentOne;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the StudentOne entity.
 */
@SuppressWarnings("unused")
@Repository
public interface StudentOneRepository extends JpaRepository<StudentOne, Long> {

}
