package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.Course1802753139App;
import com.mycompany.myapp.domain.ContentInfo;
import com.mycompany.myapp.repository.ContentInfoRepository;
import com.mycompany.myapp.service.ContentInfoService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityManager;
import java.time.Instant;
import java.time.ZonedDateTime;
import java.time.ZoneOffset;
import java.time.ZoneId;
import java.util.List;

import static com.mycompany.myapp.web.rest.TestUtil.sameInstant;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link ContentInfoResource} REST controller.
 */
@SpringBootTest(classes = Course1802753139App.class)
@AutoConfigureMockMvc
@WithMockUser
public class ContentInfoResourceIT {

    private static final String DEFAULT_CONTENT_ACTOR = "AAAAAAAAAA";
    private static final String UPDATED_CONTENT_ACTOR = "BBBBBBBBBB";

    private static final String DEFAULT_CONTENT_INFO = "AAAAAAAAAA";
    private static final String UPDATED_CONTENT_INFO = "BBBBBBBBBB";

    private static final String DEFAULT_CONTENT_IMG = "AAAAAAAAAA";
    private static final String UPDATED_CONTENT_IMG = "BBBBBBBBBB";

    private static final ZonedDateTime DEFAULT_CONTENT_TIME = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_CONTENT_TIME = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final Long DEFAULT_CONTENT_PRAISE_COUNT = 1L;
    private static final Long UPDATED_CONTENT_PRAISE_COUNT = 2L;

    private static final Long DEFAULT_CONTENT_FAVORATE_COUNT = 1L;
    private static final Long UPDATED_CONTENT_FAVORATE_COUNT = 2L;

    private static final Long DEFAULT_CONTENT_COMMENT_COUNT = 1L;
    private static final Long UPDATED_CONTENT_COMMENT_COUNT = 2L;

    private static final String DEFAULT_CONTENT_IMG_LABEL = "AAAAAAAAAA";
    private static final String UPDATED_CONTENT_IMG_LABEL = "BBBBBBBBBB";

    @Autowired
    private ContentInfoRepository contentInfoRepository;

    @Autowired
    private ContentInfoService contentInfoService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restContentInfoMockMvc;

    private ContentInfo contentInfo;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ContentInfo createEntity(EntityManager em) {
        ContentInfo contentInfo = new ContentInfo()
            .contentActor(DEFAULT_CONTENT_ACTOR)
            .contentInfo(DEFAULT_CONTENT_INFO)
            .contentImg(DEFAULT_CONTENT_IMG)
            .contentTime(DEFAULT_CONTENT_TIME)
            .contentPraiseCount(DEFAULT_CONTENT_PRAISE_COUNT)
            .contentFavorateCount(DEFAULT_CONTENT_FAVORATE_COUNT)
            .contentCommentCount(DEFAULT_CONTENT_COMMENT_COUNT)
            .contentImgLabel(DEFAULT_CONTENT_IMG_LABEL);
        return contentInfo;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ContentInfo createUpdatedEntity(EntityManager em) {
        ContentInfo contentInfo = new ContentInfo()
            .contentActor(UPDATED_CONTENT_ACTOR)
            .contentInfo(UPDATED_CONTENT_INFO)
            .contentImg(UPDATED_CONTENT_IMG)
            .contentTime(UPDATED_CONTENT_TIME)
            .contentPraiseCount(UPDATED_CONTENT_PRAISE_COUNT)
            .contentFavorateCount(UPDATED_CONTENT_FAVORATE_COUNT)
            .contentCommentCount(UPDATED_CONTENT_COMMENT_COUNT)
            .contentImgLabel(UPDATED_CONTENT_IMG_LABEL);
        return contentInfo;
    }

    @BeforeEach
    public void initTest() {
        contentInfo = createEntity(em);
    }

    @Test
    @Transactional
    public void createContentInfo() throws Exception {
        int databaseSizeBeforeCreate = contentInfoRepository.findAll().size();
        // Create the ContentInfo
        restContentInfoMockMvc.perform(post("/api/content-infos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(contentInfo)))
            .andExpect(status().isCreated());

        // Validate the ContentInfo in the database
        List<ContentInfo> contentInfoList = contentInfoRepository.findAll();
        assertThat(contentInfoList).hasSize(databaseSizeBeforeCreate + 1);
        ContentInfo testContentInfo = contentInfoList.get(contentInfoList.size() - 1);
        assertThat(testContentInfo.getContentActor()).isEqualTo(DEFAULT_CONTENT_ACTOR);
        assertThat(testContentInfo.getContentInfo()).isEqualTo(DEFAULT_CONTENT_INFO);
        assertThat(testContentInfo.getContentImg()).isEqualTo(DEFAULT_CONTENT_IMG);
        assertThat(testContentInfo.getContentTime()).isEqualTo(DEFAULT_CONTENT_TIME);
        assertThat(testContentInfo.getContentPraiseCount()).isEqualTo(DEFAULT_CONTENT_PRAISE_COUNT);
        assertThat(testContentInfo.getContentFavorateCount()).isEqualTo(DEFAULT_CONTENT_FAVORATE_COUNT);
        assertThat(testContentInfo.getContentCommentCount()).isEqualTo(DEFAULT_CONTENT_COMMENT_COUNT);
        assertThat(testContentInfo.getContentImgLabel()).isEqualTo(DEFAULT_CONTENT_IMG_LABEL);
    }

    @Test
    @Transactional
    public void createContentInfoWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = contentInfoRepository.findAll().size();

        // Create the ContentInfo with an existing ID
        contentInfo.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restContentInfoMockMvc.perform(post("/api/content-infos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(contentInfo)))
            .andExpect(status().isBadRequest());

        // Validate the ContentInfo in the database
        List<ContentInfo> contentInfoList = contentInfoRepository.findAll();
        assertThat(contentInfoList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllContentInfos() throws Exception {
        // Initialize the database
        contentInfoRepository.saveAndFlush(contentInfo);

        // Get all the contentInfoList
        restContentInfoMockMvc.perform(get("/api/content-infos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(contentInfo.getId().intValue())))
            .andExpect(jsonPath("$.[*].contentActor").value(hasItem(DEFAULT_CONTENT_ACTOR)))
            .andExpect(jsonPath("$.[*].contentInfo").value(hasItem(DEFAULT_CONTENT_INFO)))
            .andExpect(jsonPath("$.[*].contentImg").value(hasItem(DEFAULT_CONTENT_IMG)))
            .andExpect(jsonPath("$.[*].contentTime").value(hasItem(sameInstant(DEFAULT_CONTENT_TIME))))
            .andExpect(jsonPath("$.[*].contentPraiseCount").value(hasItem(DEFAULT_CONTENT_PRAISE_COUNT.intValue())))
            .andExpect(jsonPath("$.[*].contentFavorateCount").value(hasItem(DEFAULT_CONTENT_FAVORATE_COUNT.intValue())))
            .andExpect(jsonPath("$.[*].contentCommentCount").value(hasItem(DEFAULT_CONTENT_COMMENT_COUNT.intValue())))
            .andExpect(jsonPath("$.[*].contentImgLabel").value(hasItem(DEFAULT_CONTENT_IMG_LABEL)));
    }

    @Test
    @Transactional
    public void getContentInfo() throws Exception {
        // Initialize the database
        contentInfoRepository.saveAndFlush(contentInfo);

        // Get the contentInfo
        restContentInfoMockMvc.perform(get("/api/content-infos/{id}", contentInfo.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(contentInfo.getId().intValue()))
            .andExpect(jsonPath("$.contentActor").value(DEFAULT_CONTENT_ACTOR))
            .andExpect(jsonPath("$.contentInfo").value(DEFAULT_CONTENT_INFO))
            .andExpect(jsonPath("$.contentImg").value(DEFAULT_CONTENT_IMG))
            .andExpect(jsonPath("$.contentTime").value(sameInstant(DEFAULT_CONTENT_TIME)))
            .andExpect(jsonPath("$.contentPraiseCount").value(DEFAULT_CONTENT_PRAISE_COUNT.intValue()))
            .andExpect(jsonPath("$.contentFavorateCount").value(DEFAULT_CONTENT_FAVORATE_COUNT.intValue()))
            .andExpect(jsonPath("$.contentCommentCount").value(DEFAULT_CONTENT_COMMENT_COUNT.intValue()))
            .andExpect(jsonPath("$.contentImgLabel").value(DEFAULT_CONTENT_IMG_LABEL));
    }
    @Test
    @Transactional
    public void getNonExistingContentInfo() throws Exception {
        // Get the contentInfo
        restContentInfoMockMvc.perform(get("/api/content-infos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateContentInfo() throws Exception {
        // Initialize the database
        contentInfoService.save("",contentInfo);

        int databaseSizeBeforeUpdate = contentInfoRepository.findAll().size();

        // Update the contentInfo
        ContentInfo updatedContentInfo = contentInfoRepository.findById(contentInfo.getId()).get();
        // Disconnect from session so that the updates on updatedContentInfo are not directly saved in db
        em.detach(updatedContentInfo);
        updatedContentInfo
            .contentActor(UPDATED_CONTENT_ACTOR)
            .contentInfo(UPDATED_CONTENT_INFO)
            .contentImg(UPDATED_CONTENT_IMG)
            .contentTime(UPDATED_CONTENT_TIME)
            .contentPraiseCount(UPDATED_CONTENT_PRAISE_COUNT)
            .contentFavorateCount(UPDATED_CONTENT_FAVORATE_COUNT)
            .contentCommentCount(UPDATED_CONTENT_COMMENT_COUNT)
            .contentImgLabel(UPDATED_CONTENT_IMG_LABEL);

        restContentInfoMockMvc.perform(put("/api/content-infos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedContentInfo)))
            .andExpect(status().isOk());

        // Validate the ContentInfo in the database
        List<ContentInfo> contentInfoList = contentInfoRepository.findAll();
        assertThat(contentInfoList).hasSize(databaseSizeBeforeUpdate);
        ContentInfo testContentInfo = contentInfoList.get(contentInfoList.size() - 1);
        assertThat(testContentInfo.getContentActor()).isEqualTo(UPDATED_CONTENT_ACTOR);
        assertThat(testContentInfo.getContentInfo()).isEqualTo(UPDATED_CONTENT_INFO);
        assertThat(testContentInfo.getContentImg()).isEqualTo(UPDATED_CONTENT_IMG);
        assertThat(testContentInfo.getContentTime()).isEqualTo(UPDATED_CONTENT_TIME);
        assertThat(testContentInfo.getContentPraiseCount()).isEqualTo(UPDATED_CONTENT_PRAISE_COUNT);
        assertThat(testContentInfo.getContentFavorateCount()).isEqualTo(UPDATED_CONTENT_FAVORATE_COUNT);
        assertThat(testContentInfo.getContentCommentCount()).isEqualTo(UPDATED_CONTENT_COMMENT_COUNT);
        assertThat(testContentInfo.getContentImgLabel()).isEqualTo(UPDATED_CONTENT_IMG_LABEL);
    }

    @Test
    @Transactional
    public void updateNonExistingContentInfo() throws Exception {
        int databaseSizeBeforeUpdate = contentInfoRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restContentInfoMockMvc.perform(put("/api/content-infos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(contentInfo)))
            .andExpect(status().isBadRequest());

        // Validate the ContentInfo in the database
        List<ContentInfo> contentInfoList = contentInfoRepository.findAll();
        assertThat(contentInfoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteContentInfo() throws Exception {
        // Initialize the database
        contentInfoService.save("",contentInfo);

        int databaseSizeBeforeDelete = contentInfoRepository.findAll().size();

        // Delete the contentInfo
        restContentInfoMockMvc.perform(delete("/api/content-infos/{id}", contentInfo.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ContentInfo> contentInfoList = contentInfoRepository.findAll();
        assertThat(contentInfoList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
