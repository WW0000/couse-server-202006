package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.domain.UserFans;
import com.mycompany.myapp.domain.UserFavorateItem;
import com.mycompany.myapp.security.SecurityUtils;
import com.mycompany.myapp.service.UserFansService;
import com.mycompany.myapp.web.rest.errors.BadRequestAlertException;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.checkerframework.checker.units.qual.A;
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
 * REST controller for managing {@link com.mycompany.myapp.domain.UserFans}.
 */
@RestController
@RequestMapping("/api")
public class UserFansResource {

    private final Logger log = LoggerFactory.getLogger(UserFansResource.class);

    private static final String ENTITY_NAME = "userFans";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final UserFansService userFansService;

    public UserFansResource(UserFansService userFansService) {
        this.userFansService = userFansService;
    }

    /**
     * {@code POST  /user-fans} : Create a new userFans.
     *
     * @param userFans the userFans to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new userFans, or with status {@code 400 (Bad Request)} if the userFans has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/user-fans")
    public ResponseEntity<UserFans> createUserFans(@RequestBody UserFans userFans) throws URISyntaxException {
        log.debug("REST request to save UserFans : {}", userFans);
        if (userFans.getId() != null) {
            throw new BadRequestAlertException("A new userFans cannot already have an ID", ENTITY_NAME, "idexists");
        }
        UserFans result = userFansService.save(userFans);
        return ResponseEntity.created(new URI("/api/user-fans/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /user-fans} : Updates an existing userFans.
     *
     * @param userFans the userFans to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated userFans,
     * or with status {@code 400 (Bad Request)} if the userFans is not valid,
     * or with status {@code 500 (Internal Server Error)} if the userFans couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/user-fans")
    public ResponseEntity<UserFans> updateUserFans(@RequestBody UserFans userFans) throws URISyntaxException {
        log.debug("REST request to update UserFans : {}", userFans);
        if (userFans.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        UserFans result = userFansService.save(userFans);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, userFans.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /user-fans} : get all the userFans.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of userFans in body.
     */
    @GetMapping("/user-fans")
    public ResponseEntity<List<UserFans>> getAllUserFans(Pageable pageable) {
        log.debug("REST request to get a page of UserFans");
        Page<UserFans> page = userFansService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /user-fans/:id} : get the "id" userFans.
     *
     * @param id the id of the userFans to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the userFans, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/user-fans/{id}")
    public ResponseEntity<UserFans> getUserFans(@PathVariable Long id) {
        log.debug("REST request to get UserFans : {}", id);
        Optional<UserFans> userFans = userFansService.findOne(id);
        return ResponseUtil.wrapOrNotFound(userFans);
    }

    /**
     * {@code DELETE  /user-fans/:id} : delete the "id" userFans.
     *
     * @param id the id of the userFans to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/user-fans/{id}")
    public ResponseEntity<Void> deleteUserFans(@PathVariable Long id) {
        log.debug("REST request to delete UserFans : {}", id);
        userFansService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
