package com.monolithic.repository;

import com.monolithic.domain.IPRecords;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the IPRecords entity.
 */
@SuppressWarnings("unused")
@Repository
public interface IPRecordsRepository extends JpaRepository<IPRecords, Long> {

}
