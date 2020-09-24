package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.domain.UserFavorateItem;
import com.mycompany.myapp.service.UserFavorateItemService;
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
 * REST controller for managing {@link com.mycompany.myapp.domain.UserFavorateItem}.
 */
@RestController
@RequestMapping("/api")
public class UserFavorateItemResource {

    private final Logger log = LoggerFactory.getLogger(UserFavorateItemResource.class);

    private static final String ENTITY_NAME = "userFavorateItem";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final UserFavorateItemService userFavorateItemService;

    public UserFavorateItemResource(UserFavorateItemService userFavorateItemService) {
        this.userFavorateItemService = userFavorateItemService;
    }

    /**
     * {@code POST  /user-favorate-items} : Create a new userFavorateItem.
     *
     * @param userFavorateItem the userFavorateItem to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new userFavorateItem, or with status {@code 400 (Bad Request)} if the userFavorateItem has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/user-favorate-items")
    public ResponseEntity<UserFavorateItem> createUserFavorateItem(@RequestBody UserFavorateItem userFavorateItem) throws URISyntaxException {
        log.debug("REST request to save UserFavorateItem : {}", userFavorateItem);
        if (userFavorateItem.getId() != null) {
            throw new BadRequestAlertException("A new userFavorateItem cannot already have an ID", ENTITY_NAME, "idexists");
        }
        UserFavorateItem result = userFavorateItemService.save(userFavorateItem);
        return ResponseEntity.created(new URI("/api/user-favorate-items/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /user-favorate-items} : Updates an existing userFavorateItem.
     *
     * @param userFavorateItem the userFavorateItem to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated userFavorateItem,
     * or with status {@code 400 (Bad Request)} if the userFavorateItem is not valid,
     * or with status {@code 500 (Internal Server Error)} if the userFavorateItem couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/user-favorate-items")
    public ResponseEntity<UserFavorateItem> updateUserFavorateItem(@RequestBody UserFavorateItem userFavorateItem) throws URISyntaxException {
        log.debug("REST request to update UserFavorateItem : {}", userFavorateItem);
        if (userFavorateItem.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        UserFavorateItem result = userFavorateItemService.save(userFavorateItem);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, userFavorateItem.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /user-favorate-items} : get all the userFavorateItems.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of userFavorateItems in body.
     */
    @GetMapping("/user-favorate-items")
    public ResponseEntity<List<UserFavorateItem>> getAllUserFavorateItems(Pageable pageable) {
        log.debug("REST request to get a page of UserFavorateItems");
        Page<UserFavorateItem> page = userFavorateItemService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /user-favorate-items/:id} : get the "id" userFavorateItem.
     *
     * @param id the id of the userFavorateItem to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the userFavorateItem, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/user-favorate-items/{id}")
    public ResponseEntity<UserFavorateItem> getUserFavorateItem(@PathVariable Long id) {
        log.debug("REST request to get UserFavorateItem : {}", id);
        Optional<UserFavorateItem> userFavorateItem = userFavorateItemService.findOne(id);
        return ResponseUtil.wrapOrNotFound(userFavorateItem);
    }

    /**
     * {@code DELETE  /user-favorate-items/:id} : delete the "id" userFavorateItem.
     *
     * @param id the id of the userFavorateItem to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/user-favorate-items/{id}")
    public ResponseEntity<Void> deleteUserFavorateItem(@PathVariable Long id) {
        log.debug("REST request to delete UserFavorateItem : {}", id);
        userFavorateItemService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
