package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.domain.ContentInfo;
import com.mycompany.myapp.domain.ContentType;
import com.mycompany.myapp.service.ContentTypeService;
import com.mycompany.myapp.service.impl.ContentInfoServiceImpl;
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
 * REST controller for managing {@link com.mycompany.myapp.domain.ContentType}.
 */
@RestController
@RequestMapping("/api")
public class ContentTypeResource {

    private final Logger log = LoggerFactory.getLogger(ContentTypeResource.class);

    private static final String ENTITY_NAME = "contentType";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ContentTypeService contentTypeService;
    private ContentInfoServiceImpl contentInfoService;

    public ContentTypeResource(ContentTypeService contentTypeService) {
        this.contentTypeService = contentTypeService;
    }

    /**
     * {@code POST  /content-types} : Create a new contentType.
     *
     * @param contentType the contentType to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new contentType, or with status {@code 400 (Bad Request)} if the contentType has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/content-types")
    public ResponseEntity<ContentType> createContentType(@RequestBody ContentType contentType) throws URISyntaxException {
        log.debug("REST request to save ContentType : {}", contentType);
        if (contentType.getId() != null) {
            throw new BadRequestAlertException("A new contentType cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ContentType result = contentTypeService.save(contentType);
        return ResponseEntity.created(new URI("/api/content-types/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /content-types} : Updates an existing contentType.
     *
     * @param contentType the contentType to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated contentType,
     * or with status {@code 400 (Bad Request)} if the contentType is not valid,
     * or with status {@code 500 (Internal Server Error)} if the contentType couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/content-types")
    public ResponseEntity<ContentType> updateContentType(@RequestBody ContentType contentType) throws URISyntaxException {
        log.debug("REST request to update ContentType : {}", contentType);
        if (contentType.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ContentType result = contentTypeService.save(contentType);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, contentType.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /content-types} : get all the contentTypes.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of contentTypes in body.
     */
    @GetMapping("/content-types")
    public ResponseEntity<List<ContentType>> getAllContentTypes(Pageable pageable) {
        log.debug("REST request to get a page of ContentTypes");
        Page<ContentType> page = contentTypeService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /content-types/:id} : get the "id" contentType.
     *
     * @param id the id of the contentType to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the contentType, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/content-types/{id}")
    public ResponseEntity<ContentType> getContentType(@PathVariable Long id) {
        log.debug("REST request to get ContentType : {}", id);
        Optional<ContentType> contentType = contentTypeService.findOne(id);
        return ResponseUtil.wrapOrNotFound(contentType);
    }

    /**
     * {@code DELETE  /content-types/:id} : delete the "id" contentType.
     *
     * @param id the id of the contentType to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/content-types/{id}")
    public ResponseEntity<Void> deleteContentType(@PathVariable Long id) {
        log.debug("REST request to delete ContentType : {}", id);
        contentTypeService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
    @ApiOperation(value = "获取全部分类")
    @GetMapping("/content-types/all")
    public ResponseEntity getAllType(){
        //返回所有分类
        List<ContentType> result=this.contentTypeService.getAllType();
        return ResponseEntity.ok(result);
    }
    /**
     * 根据关键字、分类查询内容列表
     *
     * @param keyword 查询关键字
     * @param typeName 类型
     * @param index 起始页
     * @param size 每页大小
     *
     * @return Page<ContentInfo>
     * */
    @ApiOperation("根据关键字、分类查询内容列表")
    @GetMapping("/content-infos/index")
    public ResponseEntity getIndexContent( String keyword, String typeName,
                                           Integer index,Integer size){
        Page<ContentInfo> contentInfos=this.contentInfoService.getContent(keyword,typeName,index,size);
        return ResponseEntity.ok(contentInfos);
    }
}
