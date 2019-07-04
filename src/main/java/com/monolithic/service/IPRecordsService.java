package com.monolithic.service;

import com.monolithic.service.dto.IPRecordsDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link com.monolithic.domain.IPRecords}.
 */
public interface IPRecordsService {

    /**
     * Save a iPRecords.
     *
     * @param iPRecordsDTO the entity to save.
     * @return the persisted entity.
     */
    IPRecordsDTO save(IPRecordsDTO iPRecordsDTO);

    /**
     * Get all the iPRecords.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<IPRecordsDTO> findAll(Pageable pageable);


    /**
     * Get the "id" iPRecords.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<IPRecordsDTO> findOne(Long id);

    /**
     * Delete the "id" iPRecords.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
