package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.domain.UserContentComment;
import com.mycompany.myapp.service.UserContentCommentService;
import com.mycompany.myapp.service.dto.CommentTreeItem;
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
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.mycompany.myapp.domain.UserContentComment}.
 */
@RestController
@RequestMapping("/api")
public class UserContentCommentResource {

    private final Logger log = LoggerFactory.getLogger(UserContentCommentResource.class);

    private static final String ENTITY_NAME = "userContentComment";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final UserContentCommentService userContentCommentService;

    public UserContentCommentResource(UserContentCommentService userContentCommentService) {
        this.userContentCommentService = userContentCommentService;
    }

    /**
     * {@code POST  /user-content-comments} : Create a new userContentComment.
     *
     * @param userContentComment the userContentComment to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new userContentComment, or with status {@code 400 (Bad Request)} if the userContentComment has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/user-content-comments")
    public ResponseEntity<UserContentComment> createUserContentComment(@RequestBody UserContentComment userContentComment) throws URISyntaxException {
        log.debug("REST request to save UserContentComment : {}", userContentComment);
        if (userContentComment.getId() != null) {
            throw new BadRequestAlertException("A new userContentComment cannot already have an ID", ENTITY_NAME, "idexists");
        }
        UserContentComment result = userContentCommentService.save(userContentComment);
        return ResponseEntity.created(new URI("/api/user-content-comments/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /user-content-comments} : Updates an existing userContentComment.
     *
     * @param userContentComment the userContentComment to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated userContentComment,
     * or with status {@code 400 (Bad Request)} if the userContentComment is not valid,
     * or with status {@code 500 (Internal Server Error)} if the userContentComment couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/user-content-comments")
    public ResponseEntity<UserContentComment> updateUserContentComment(@RequestBody UserContentComment userContentComment) throws URISyntaxException {
        log.debug("REST request to update UserContentComment : {}", userContentComment);
        if (userContentComment.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        UserContentComment result = userContentCommentService.save(userContentComment);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, userContentComment.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /user-content-comments} : get all the userContentComments.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of userContentComments in body.
     */
    @GetMapping("/user-content-comments")
    public ResponseEntity<List<UserContentComment>> getAllUserContentComments(Pageable pageable) {
        log.debug("REST request to get a page of UserContentComments");
        Page<UserContentComment> page = userContentCommentService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /user-content-comments/:id} : get the "id" userContentComment.
     *
     * @param id the id of the userContentComment to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the userContentComment, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/user-content-comments/{id}")
    public ResponseEntity<UserContentComment> getUserContentComment(@PathVariable Long id) {
        log.debug("REST request to get UserContentComment : {}", id);
        Optional<UserContentComment> userContentComment = userContentCommentService.findOne(id);
        return ResponseUtil.wrapOrNotFound(userContentComment);
    }

    /**
     * {@code DELETE  /user-content-comments/:id} : delete the "id" userContentComment.
     *
     * @param id the id of the userContentComment to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/user-content-comments/{id}")
    public ResponseEntity<Void> deleteUserContentComment(@PathVariable Long id) {
        log.debug("REST request to delete UserContentComment : {}", id);
        userContentCommentService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
    @ApiOperation(value = "获取评论数")
    @GetMapping("/user-content-comments/tree/{contentId}")
    public ResponseEntity getCommentTree(@PathVariable Long contentId){
        List<CommentTreeItem> result=userContentCommentService.getCommentTree(11L,contentId);
        return ResponseEntity.ok(result);
    }
    @ApiOperation(value = "发表评论")
    @PostMapping("/user-content-comments/add/{contentId}/{parentId}/{comment}")
    public ResponseEntity addComment(@PathVariable Long contentId,@PathVariable Long parentId,@PathVariable String comment){
        List<String> addcomment=new ArrayList<>();
        addcomment.add(contentId.toString());
        addcomment.add(parentId.toString());
        addcomment.add(comment);
        return ResponseEntity.ok(addcomment);
    }
}
