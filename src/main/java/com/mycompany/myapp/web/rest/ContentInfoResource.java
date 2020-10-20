package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.domain.ContentInfo;
import com.mycompany.myapp.domain.ContentType;
import com.mycompany.myapp.security.SecurityUtils;
import com.mycompany.myapp.service.ContentInfoService;
import com.mycompany.myapp.web.rest.errors.BadRequestAlertException;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import liquibase.pro.packaged.S;
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
        ContentInfo result = contentInfoService.save("",contentInfo);
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
        ContentInfo result = contentInfoService.save("",contentInfo);
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
    @ApiOperation("删除我发布的内容，支持分页")
    @DeleteMapping("/content-infos/{id}")
    public ResponseEntity deleteContentInfo(@PathVariable Long id) {
        log.debug("REST request to delete ContentInfo : {}", id);
        Optional<String> loginOptional=SecurityUtils.getCurrentUserLogin();
        if(!loginOptional.isPresent()){
            return ResponseEntity.badRequest().body("未登录,无权使用API");
        }
        String login=loginOptional.get();
        try {
            contentInfoService.delete(login,id);
            return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @ApiOperation(value = "根据分类获取发布的内容")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "typeName",value = "分类名称,传空值表示取全部"),
        @ApiImplicitParam(name = "index",value = "分页页码，起始页为0"),
        @ApiImplicitParam(name = "size",value = "分页页长，默认为10")
    })

    @GetMapping("/content-infos/typename")
    public ResponseEntity getContentByType(
        @RequestParam String typeName,
        @RequestParam(required = false,defaultValue = "0") Integer index,
        @RequestParam(required = false,defaultValue = "10")  Integer size){

        Page<ContentInfo> result=
            this.contentInfoService
                .getContentByTypeName(typeName,index,size);
        return ResponseEntity.ok(result);
    }

    @ApiOperation(value = "根据关键字、分类查询内容列表")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "keyword",value = "关键字"),
        @ApiImplicitParam(name = "typeName",value = "分类名称"),
        @ApiImplicitParam(name = "index",value = "页码"),
        @ApiImplicitParam(name = "size",value = "页长")
    })

    @GetMapping("/content-infos/index")
    public ResponseEntity getIndexContent(
        @RequestParam(required = false,defaultValue = "") String keyword,
        @RequestParam(required = false,defaultValue = "ALL") String typeName,
        @RequestParam(required = false,defaultValue = "0") Integer index,
        @RequestParam(required = false,defaultValue = "10") Integer size
    ){
        Page<ContentInfo> result = this.contentInfoService.getAllContent(keyword,typeName,index,size);
        return ResponseEntity.ok(result);
    }

    @ApiOperation(value = "获取当前登录账户已关注的内容列表，支持分页")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "keyword",value = "关键字"),
        @ApiImplicitParam(name = "index",value = "页码"),
        @ApiImplicitParam(name = "size",value = "页长")
    })
    @GetMapping("/content-infos/fans")
    public ResponseEntity getSubContent(
        @RequestParam(required = false,defaultValue = "") String keywords,
        @RequestParam(required = false,defaultValue = "0") Integer index,
        @RequestParam(required = false,defaultValue = "10") Integer size
    ){
            Optional<String> loginOptional= SecurityUtils.getCurrentUserLogin();
            if(!loginOptional.isPresent()){
                return ResponseEntity.badRequest().body("未登录");

            }
            String login=loginOptional.get();
            Page<ContentInfo> result=this.contentInfoService.getSubContent(login,keywords,index,size);
            return ResponseEntity.ok(result);
    }

    @ApiOperation(value = "我的点赞列表，支持分页")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "index",value = "页码"),
        @ApiImplicitParam(name = "size",value = "页长")
    })
    @GetMapping("/content-infos/praises/")
    public ResponseEntity myPraise(
        @RequestParam(required = false,defaultValue = "0") Integer index,
        @RequestParam(required = false,defaultValue = "10") Integer size
    ){
        Optional<String> loginOptional= SecurityUtils.getCurrentUserLogin();
        if(!loginOptional.isPresent()){
            return ResponseEntity.badRequest().body("未登录");

        }
        String login=loginOptional.get();
        Page<ContentInfo> result=this.contentInfoService.myPraise(login,index,size);
        return ResponseEntity.ok(result);
    }

    @ApiOperation(value = "我的收藏列表，支持分页")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "index",value = "页码"),
        @ApiImplicitParam(name = "size",value = "页长")
    })
    @GetMapping("/content-infos/favorate/")
    public ResponseEntity myFavorate(
        @RequestParam(required = false,defaultValue = "0") Integer index,
        @RequestParam(required = false,defaultValue = "10") Integer size
    ){
        Optional<String> loginOptional= SecurityUtils.getCurrentUserLogin();
        if(!loginOptional.isPresent()){
            return ResponseEntity.badRequest().body("未登录");

        }
        String login=loginOptional.get();
        Page<ContentInfo> result=this.contentInfoService.myFavorate(login,index,size);
        return ResponseEntity.ok(result);
    }

    @ApiOperation(value = "我发布的内容列表，支持分页")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "index",value = "页码"),
        @ApiImplicitParam(name = "size",value = "页长")
    })
    @GetMapping("/content-infos/mine")
    public ResponseEntity getMyContent(
        @RequestParam(required = false,defaultValue = "0") Integer index,
        @RequestParam(required = false,defaultValue = "10") Integer size
    ){
        Optional<String> loginOptional= SecurityUtils.getCurrentUserLogin();
        if(!loginOptional.isPresent()){
            return ResponseEntity.badRequest().body("未登录");

        }
        String login=loginOptional.get();
        Page<ContentInfo> result=this.contentInfoService.myContent(login,index,size);
        return  ResponseEntity.ok(result);

    }



}



