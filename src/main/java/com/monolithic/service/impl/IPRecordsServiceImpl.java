package com.monolithic.service.impl;

import com.monolithic.service.IPRecordsService;
import com.monolithic.domain.IPRecords;
import com.monolithic.repository.IPRecordsRepository;
import com.monolithic.service.dto.IPRecordsDTO;
import com.monolithic.service.mapper.IPRecordsMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link IPRecords}.
 */
@Service
@Transactional
public class IPRecordsServiceImpl implements IPRecordsService {

    private final Logger log = LoggerFactory.getLogger(IPRecordsServiceImpl.class);

    private final IPRecordsRepository iPRecordsRepository;

    private final IPRecordsMapper iPRecordsMapper;


    public IPRecordsServiceImpl(IPRecordsRepository iPRecordsRepository, IPRecordsMapper iPRecordsMapper) {
        this.iPRecordsRepository = iPRecordsRepository;
        this.iPRecordsMapper = iPRecordsMapper;
    }

    /**
     * Save a iPRecords.
     *
     * @param iPRecordsDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public IPRecordsDTO save(IPRecordsDTO iPRecordsDTO) {
        log.debug("Request to save IPRecords : {}", iPRecordsDTO);
        IPRecords iPRecords = iPRecordsMapper.toEntity(iPRecordsDTO);
        iPRecords = iPRecordsRepository.save(iPRecords);
        return iPRecordsMapper.toDto(iPRecords);
    }

    /**
     * Get all the iPRecords.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<IPRecordsDTO> findAll(Pageable pageable) {
        log.debug("Request to get all IPRecords");
        return iPRecordsRepository.findAll(pageable)
            .map(iPRecordsMapper::toDto);
    }


    /**
     * Get one iPRecords by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<IPRecordsDTO> findOne(Long id) {
        log.debug("Request to get IPRecords : {}", id);
        return iPRecordsRepository.findById(id)
            .map(iPRecordsMapper::toDto);
    }

    /**
     * Delete the iPRecords by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete IPRecords : {}", id);
        iPRecordsRepository.deleteById(id);
    }
}
