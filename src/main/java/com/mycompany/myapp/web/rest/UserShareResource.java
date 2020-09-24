package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.domain.UserShare;
import com.mycompany.myapp.service.UserShareService;
import com.mycompany.myapp.web.rest.errors.BadRequestAlertException;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
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
 * REST controller for managing {@link com.mycompany.myapp.domain.UserShare}.
 */
@RestController
@RequestMapping("/api")
public class UserShareResource {

    private final Logger log = LoggerFactory.getLogger(UserShareResource.class);

    private static final String ENTITY_NAME = "userShare";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final UserShareService userShareService;

    public UserShareResource(UserShareService userShareService) {
        this.userShareService = userShareService;
    }

    /**
     * {@code POST  /user-shares} : Create a new userShare.
     *
     * @param userShare the userShare to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new userShare, or with status {@code 400 (Bad Request)} if the userShare has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/user-shares")
    public ResponseEntity<UserShare> createUserShare(@RequestBody UserShare userShare) throws URISyntaxException {
        log.debug("REST request to save UserShare : {}", userShare);
        if (userShare.getId() != null) {
            throw new BadRequestAlertException("A new userShare cannot already have an ID", ENTITY_NAME, "idexists");
        }
        UserShare result = userShareService.save(userShare);
        return ResponseEntity.created(new URI("/api/user-shares/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /user-shares} : Updates an existing userShare.
     *
     * @param userShare the userShare to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated userShare,
     * or with status {@code 400 (Bad Request)} if the userShare is not valid,
     * or with status {@code 500 (Internal Server Error)} if the userShare couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/user-shares")
    public ResponseEntity<UserShare> updateUserShare(@RequestBody UserShare userShare) throws URISyntaxException {
        log.debug("REST request to update UserShare : {}", userShare);
        if (userShare.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        UserShare result = userShareService.save(userShare);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, userShare.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /user-shares} : get all the userShares.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of userShares in body.
     */
    @GetMapping("/user-shares")
    public ResponseEntity<List<UserShare>> getAllUserShares(Pageable pageable) {
        log.debug("REST request to get a page of UserShares");
        Page<UserShare> page = userShareService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /user-shares/:id} : get the "id" userShare.
     *
     * @param id the id of the userShare to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the userShare, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/user-shares/{id}")
    public ResponseEntity<UserShare> getUserShare(@PathVariable Long id) {
        log.debug("REST request to get UserShare : {}", id);
        Optional<UserShare> userShare = userShareService.findOne(id);
        return ResponseUtil.wrapOrNotFound(userShare);
    }

    /**
     * {@code DELETE  /user-shares/:id} : delete the "id" userShare.
     *
     * @param id the id of the userShare to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/user-shares/{id}")
    public ResponseEntity<Void> deleteUserShare(@PathVariable Long id) {
        log.debug("REST request to delete UserShare : {}", id);
        userShareService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
