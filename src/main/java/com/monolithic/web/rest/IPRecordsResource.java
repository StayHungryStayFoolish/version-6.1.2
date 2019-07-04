package com.monolithic.web.rest;

import com.monolithic.service.IPRecordsService;
import com.monolithic.service.dto.IPRecordsDTO;
import com.monolithic.web.rest.errors.BadRequestAlertException;
import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.monolithic.domain.IPRecords}.
 */
@RestController
@RequestMapping("/api")
public class IPRecordsResource {

    private final Logger log = LoggerFactory.getLogger(IPRecordsResource.class);

    private static final String ENTITY_NAME = "iPRecords";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final IPRecordsService iPRecordsService;

    public IPRecordsResource(IPRecordsService iPRecordsService) {
        this.iPRecordsService = iPRecordsService;
    }

    /**
     * {@code POST  /ip-records} : Create a new iPRecords.
     *
     * @param iPRecordsDTO the iPRecordsDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new iPRecordsDTO, or with status {@code 400 (Bad Request)} if the iPRecords has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/ip-records")
    public ResponseEntity<IPRecordsDTO> createIPRecords(@Valid @RequestBody IPRecordsDTO iPRecordsDTO) throws URISyntaxException {
        log.debug("REST request to save IPRecords : {}", iPRecordsDTO);
        if (iPRecordsDTO.getId() != null) {
            throw new BadRequestAlertException("A new iPRecords cannot already have an ID", ENTITY_NAME, "idexists");
        }
        IPRecordsDTO result = iPRecordsService.save(iPRecordsDTO);
        return ResponseEntity.created(new URI("/api/ip-records/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /ip-records} : Updates an existing iPRecords.
     *
     * @param iPRecordsDTO the iPRecordsDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated iPRecordsDTO,
     * or with status {@code 400 (Bad Request)} if the iPRecordsDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the iPRecordsDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/ip-records")
    public ResponseEntity<IPRecordsDTO> updateIPRecords(@Valid @RequestBody IPRecordsDTO iPRecordsDTO) throws URISyntaxException {
        log.debug("REST request to update IPRecords : {}", iPRecordsDTO);
        if (iPRecordsDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        IPRecordsDTO result = iPRecordsService.save(iPRecordsDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, iPRecordsDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /ip-records} : get all the iPRecords.
     *
     * @param pageable the pagination information.
     * @param queryParams a {@link MultiValueMap} query parameters.
     * @param uriBuilder a {@link UriComponentsBuilder} URI builder.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of iPRecords in body.
     */
    @GetMapping("/ip-records")
    public ResponseEntity<List<IPRecordsDTO>> getAllIPRecords(Pageable pageable, @RequestParam MultiValueMap<String, String> queryParams, UriComponentsBuilder uriBuilder) {
        log.debug("REST request to get a page of IPRecords");
        Page<IPRecordsDTO> page = iPRecordsService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(uriBuilder.queryParams(queryParams), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /ip-records/:id} : get the "id" iPRecords.
     *
     * @param id the id of the iPRecordsDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the iPRecordsDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/ip-records/{id}")
    public ResponseEntity<IPRecordsDTO> getIPRecords(@PathVariable Long id) {
        log.debug("REST request to get IPRecords : {}", id);
        Optional<IPRecordsDTO> iPRecordsDTO = iPRecordsService.findOne(id);
        return ResponseUtil.wrapOrNotFound(iPRecordsDTO);
    }

    /**
     * {@code DELETE  /ip-records/:id} : delete the "id" iPRecords.
     *
     * @param id the id of the iPRecordsDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/ip-records/{id}")
    public ResponseEntity<Void> deleteIPRecords(@PathVariable Long id) {
        log.debug("REST request to delete IPRecords : {}", id);
        iPRecordsService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
