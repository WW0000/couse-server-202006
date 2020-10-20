package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.domain.UserContentPraise;
import com.mycompany.myapp.security.SecurityUtils;
import com.mycompany.myapp.service.UserContentPraiseService;
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
 * REST controller for managing {@link com.mycompany.myapp.domain.UserContentPraise}.
 */
@RestController
@RequestMapping("/api")
public class UserContentPraiseResource {

    private final Logger log = LoggerFactory.getLogger(UserContentPraiseResource.class);

    private static final String ENTITY_NAME = "userContentPraise";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final UserContentPraiseService userContentPraiseService;

    public UserContentPraiseResource(UserContentPraiseService userContentPraiseService) {
        this.userContentPraiseService = userContentPraiseService;
    }

    /**
     * {@code POST  /user-content-praises} : Create a new userContentPraise.
     *
     * @param userContentPraise the userContentPraise to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new userContentPraise, or with status {@code 400 (Bad Request)} if the userContentPraise has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/user-content-praises")
    public ResponseEntity<UserContentPraise> createUserContentPraise(@RequestBody UserContentPraise userContentPraise) throws URISyntaxException {
        log.debug("REST request to save UserContentPraise : {}", userContentPraise);
        if (userContentPraise.getId() != null) {
            throw new BadRequestAlertException("A new userContentPraise cannot already have an ID", ENTITY_NAME, "idexists");
        }
        UserContentPraise result = userContentPraiseService.save(userContentPraise);
        return ResponseEntity.created(new URI("/api/user-content-praises/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /user-content-praises} : Updates an existing userContentPraise.
     *
     * @param userContentPraise the userContentPraise to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated userContentPraise,
     * or with status {@code 400 (Bad Request)} if the userContentPraise is not valid,
     * or with status {@code 500 (Internal Server Error)} if the userContentPraise couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/user-content-praises")
    public ResponseEntity<UserContentPraise> updateUserContentPraise(@RequestBody UserContentPraise userContentPraise) throws URISyntaxException {
        log.debug("REST request to update UserContentPraise : {}", userContentPraise);
        if (userContentPraise.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        UserContentPraise result = userContentPraiseService.save(userContentPraise);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, userContentPraise.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /user-content-praises} : get all the userContentPraises.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of userContentPraises in body.
     */
    @GetMapping("/user-content-praises")
    public ResponseEntity<List<UserContentPraise>> getAllUserContentPraises(Pageable pageable) {
        log.debug("REST request to get a page of UserContentPraises");
        Page<UserContentPraise> page = userContentPraiseService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /user-content-praises/:id} : get the "id" userContentPraise.
     *
     * @param id the id of the userContentPraise to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the userContentPraise, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/user-content-praises/{id}")
    public ResponseEntity<UserContentPraise> getUserContentPraise(@PathVariable Long id) {
        log.debug("REST request to get UserContentPraise : {}", id);
        Optional<UserContentPraise> userContentPraise = userContentPraiseService.findOne(id);
        return ResponseUtil.wrapOrNotFound(userContentPraise);
    }

    /**
     * {@code DELETE  /user-content-praises/:id} : delete the "id" userContentPraise.
     *
     * @param id the id of the userContentPraise to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @ApiOperation("取消点赞")
    @DeleteMapping("/user-content-praises/{id}")
    public ResponseEntity deleteUserContentPraise(@PathVariable Long id) {
        log.debug("REST request to delete UserContentPraise : {}", id);
        Optional<String> loginOptional=SecurityUtils.getCurrentUserLogin();
        if (!loginOptional.isPresent()){
            return ResponseEntity.badRequest().body("未登录，无权使用API");
        }
        String login=loginOptional.get();
        try {
            userContentPraiseService.delete(login,id);
            return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body(e.getMessage());
        }

    }

    @ApiOperation("提交点赞")
    @PostMapping("/user-content-praise/praise")
    public ResponseEntity praise(@RequestBody UserContentPraise userContentPraise){

        Optional<String> loginOptional= SecurityUtils.getCurrentUserLogin();
        if(!loginOptional.isPresent()){
            return ResponseEntity.badRequest().body("未登录");

        }
        String login=loginOptional.get();
        userContentPraise=this.userContentPraiseService.praise(login,userContentPraise);
        return  ResponseEntity.ok(userContentPraise);
    }



}


