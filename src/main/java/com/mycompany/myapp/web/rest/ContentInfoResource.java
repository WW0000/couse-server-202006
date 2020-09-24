package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.domain.ContentInfo;
import com.mycompany.myapp.domain.ContentType;
import com.mycompany.myapp.service.ContentInfoService;
import com.mycompany.myapp.web.rest.errors.BadRequestAlertException;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.mycompany.myapp.domain.ContentInfo}.
 */
@RestController
@RequestMapping("/api")
public class ContentInfoResource {

    private final Logger log = LoggerFactory.getLogger(ContentInfoResource.class);

    private static final String ENTITY_NAME = "contentInfo";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ContentInfoService contentInfoService;

    public ContentInfoResource(ContentInfoService contentInfoService) {
        this.contentInfoService = contentInfoService;
    }

    /**
     * {@code POST  /content-infos} : Create a new contentInfo.
     *
     * @param contentInfo the contentInfo to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new contentInfo, or with status {@code 400 (Bad Request)} if the contentInfo has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/content-infos")
    public ResponseEntity<ContentInfo> createContentInfo(@RequestBody ContentInfo contentInfo) throws URISyntaxException {
        log.debug("REST request to save ContentInfo : {}", contentInfo);
        if (contentInfo.getId() != null) {
            throw new BadRequestAlertException("A new contentInfo cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ContentInfo result = contentInfoService.save(contentInfo);
        return ResponseEntity.created(new URI("/api/content-infos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /content-infos} : Updates an existing contentInfo.
     *
     * @param contentInfo the contentInfo to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated contentInfo,
     * or with status {@code 400 (Bad Request)} if the contentInfo is not valid,
     * or with status {@code 500 (Internal Server Error)} if the contentInfo couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/content-infos")
    public ResponseEntity<ContentInfo> updateContentInfo(@RequestBody ContentInfo contentInfo) throws URISyntaxException {
        log.debug("REST request to update ContentInfo : {}", contentInfo);
        if (contentInfo.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ContentInfo result = contentInfoService.save(contentInfo);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, contentInfo.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /content-infos} : get all the contentInfos.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of contentInfos in body.
     */
    @GetMapping("/content-infos")
    public ResponseEntity<List<ContentInfo>> getAllContentInfos(Pageable pageable) {
        log.debug("REST request to get a page of ContentInfos");
        Page<ContentInfo> page = contentInfoService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /content-infos/:id} : get the "id" contentInfo.
     *
     * @param id the id of the contentInfo to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the contentInfo, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/content-infos/{id}")
    public ResponseEntity<ContentInfo> getContentInfo(@PathVariable Long id) {
        log.debug("REST request to get ContentInfo : {}", id);
        Optional<ContentInfo> contentInfo = contentInfoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(contentInfo);
    }

    /**
     * {@code DELETE  /content-infos/:id} : delete the "id" contentInfo.
     *
     * @param id the id of the contentInfo to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/content-infos/{id}")
    public ResponseEntity<Void> deleteContentInfo(@PathVariable Long id) {
        log.debug("REST request to delete ContentInfo : {}", id);
        contentInfoService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }

}
