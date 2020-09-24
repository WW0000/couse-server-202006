package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.Course1802753139App;
import com.mycompany.myapp.domain.ContentType;
import com.mycompany.myapp.repository.ContentTypeRepository;
import com.mycompany.myapp.service.ContentTypeService;

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
 * Integration tests for the {@link ContentTypeResource} REST controller.
 */
@SpringBootTest(classes = Course1802753139App.class)
@AutoConfigureMockMvc
@WithMockUser
public class ContentTypeResourceIT {

    private static final String DEFAULT_CONTENT_TYPE_NAME = "AAAAAAAAAA";
    private static final String UPDATED_CONTENT_TYPE_NAME = "BBBBBBBBBB";

    private static final Integer DEFAULT_CONTENT_TYPE_SORT = 1;
    private static final Integer UPDATED_CONTENT_TYPE_SORT = 2;

    private static final ZonedDateTime DEFAULT_CONTENT_TYPE_TIME = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_CONTENT_TYPE_TIME = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final Long DEFAULT_CONTENT_TYPE_UPDATE_COUNT = 1L;
    private static final Long UPDATED_CONTENT_TYPE_UPDATE_COUNT = 2L;

    @Autowired
    private ContentTypeRepository contentTypeRepository;

    @Autowired
    private ContentTypeService contentTypeService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restContentTypeMockMvc;

    private ContentType contentType;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ContentType createEntity(EntityManager em) {
        ContentType contentType = new ContentType()
            .contentTypeName(DEFAULT_CONTENT_TYPE_NAME)
            .contentTypeSort(DEFAULT_CONTENT_TYPE_SORT)
            .contentTypeTime(DEFAULT_CONTENT_TYPE_TIME)
            .contentTypeUpdateCount(DEFAULT_CONTENT_TYPE_UPDATE_COUNT);
        return contentType;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ContentType createUpdatedEntity(EntityManager em) {
        ContentType contentType = new ContentType()
            .contentTypeName(UPDATED_CONTENT_TYPE_NAME)
            .contentTypeSort(UPDATED_CONTENT_TYPE_SORT)
            .contentTypeTime(UPDATED_CONTENT_TYPE_TIME)
            .contentTypeUpdateCount(UPDATED_CONTENT_TYPE_UPDATE_COUNT);
        return contentType;
    }

    @BeforeEach
    public void initTest() {
        contentType = createEntity(em);
    }

    @Test
    @Transactional
    public void createContentType() throws Exception {
        int databaseSizeBeforeCreate = contentTypeRepository.findAll().size();
        // Create the ContentType
        restContentTypeMockMvc.perform(post("/api/content-types")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(contentType)))
            .andExpect(status().isCreated());

        // Validate the ContentType in the database
        List<ContentType> contentTypeList = contentTypeRepository.findAll();
        assertThat(contentTypeList).hasSize(databaseSizeBeforeCreate + 1);
        ContentType testContentType = contentTypeList.get(contentTypeList.size() - 1);
        assertThat(testContentType.getContentTypeName()).isEqualTo(DEFAULT_CONTENT_TYPE_NAME);
        assertThat(testContentType.getContentTypeSort()).isEqualTo(DEFAULT_CONTENT_TYPE_SORT);
        assertThat(testContentType.getContentTypeTime()).isEqualTo(DEFAULT_CONTENT_TYPE_TIME);
        assertThat(testContentType.getContentTypeUpdateCount()).isEqualTo(DEFAULT_CONTENT_TYPE_UPDATE_COUNT);
    }

    @Test
    @Transactional
    public void createContentTypeWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = contentTypeRepository.findAll().size();

        // Create the ContentType with an existing ID
        contentType.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restContentTypeMockMvc.perform(post("/api/content-types")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(contentType)))
            .andExpect(status().isBadRequest());

        // Validate the ContentType in the database
        List<ContentType> contentTypeList = contentTypeRepository.findAll();
        assertThat(contentTypeList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllContentTypes() throws Exception {
        // Initialize the database
        contentTypeRepository.saveAndFlush(contentType);

        // Get all the contentTypeList
        restContentTypeMockMvc.perform(get("/api/content-types?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(contentType.getId().intValue())))
            .andExpect(jsonPath("$.[*].contentTypeName").value(hasItem(DEFAULT_CONTENT_TYPE_NAME)))
            .andExpect(jsonPath("$.[*].contentTypeSort").value(hasItem(DEFAULT_CONTENT_TYPE_SORT)))
            .andExpect(jsonPath("$.[*].contentTypeTime").value(hasItem(sameInstant(DEFAULT_CONTENT_TYPE_TIME))))
            .andExpect(jsonPath("$.[*].contentTypeUpdateCount").value(hasItem(DEFAULT_CONTENT_TYPE_UPDATE_COUNT.intValue())));
    }
    
    @Test
    @Transactional
    public void getContentType() throws Exception {
        // Initialize the database
        contentTypeRepository.saveAndFlush(contentType);

        // Get the contentType
        restContentTypeMockMvc.perform(get("/api/content-types/{id}", contentType.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(contentType.getId().intValue()))
            .andExpect(jsonPath("$.contentTypeName").value(DEFAULT_CONTENT_TYPE_NAME))
            .andExpect(jsonPath("$.contentTypeSort").value(DEFAULT_CONTENT_TYPE_SORT))
            .andExpect(jsonPath("$.contentTypeTime").value(sameInstant(DEFAULT_CONTENT_TYPE_TIME)))
            .andExpect(jsonPath("$.contentTypeUpdateCount").value(DEFAULT_CONTENT_TYPE_UPDATE_COUNT.intValue()));
    }
    @Test
    @Transactional
    public void getNonExistingContentType() throws Exception {
        // Get the contentType
        restContentTypeMockMvc.perform(get("/api/content-types/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateContentType() throws Exception {
        // Initialize the database
        contentTypeService.save(contentType);

        int databaseSizeBeforeUpdate = contentTypeRepository.findAll().size();

        // Update the contentType
        ContentType updatedContentType = contentTypeRepository.findById(contentType.getId()).get();
        // Disconnect from session so that the updates on updatedContentType are not directly saved in db
        em.detach(updatedContentType);
        updatedContentType
            .contentTypeName(UPDATED_CONTENT_TYPE_NAME)
            .contentTypeSort(UPDATED_CONTENT_TYPE_SORT)
            .contentTypeTime(UPDATED_CONTENT_TYPE_TIME)
            .contentTypeUpdateCount(UPDATED_CONTENT_TYPE_UPDATE_COUNT);

        restContentTypeMockMvc.perform(put("/api/content-types")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedContentType)))
            .andExpect(status().isOk());

        // Validate the ContentType in the database
        List<ContentType> contentTypeList = contentTypeRepository.findAll();
        assertThat(contentTypeList).hasSize(databaseSizeBeforeUpdate);
        ContentType testContentType = contentTypeList.get(contentTypeList.size() - 1);
        assertThat(testContentType.getContentTypeName()).isEqualTo(UPDATED_CONTENT_TYPE_NAME);
        assertThat(testContentType.getContentTypeSort()).isEqualTo(UPDATED_CONTENT_TYPE_SORT);
        assertThat(testContentType.getContentTypeTime()).isEqualTo(UPDATED_CONTENT_TYPE_TIME);
        assertThat(testContentType.getContentTypeUpdateCount()).isEqualTo(UPDATED_CONTENT_TYPE_UPDATE_COUNT);
    }

    @Test
    @Transactional
    public void updateNonExistingContentType() throws Exception {
        int databaseSizeBeforeUpdate = contentTypeRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restContentTypeMockMvc.perform(put("/api/content-types")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(contentType)))
            .andExpect(status().isBadRequest());

        // Validate the ContentType in the database
        List<ContentType> contentTypeList = contentTypeRepository.findAll();
        assertThat(contentTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteContentType() throws Exception {
        // Initialize the database
        contentTypeService.save(contentType);

        int databaseSizeBeforeDelete = contentTypeRepository.findAll().size();

        // Delete the contentType
        restContentTypeMockMvc.perform(delete("/api/content-types/{id}", contentType.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ContentType> contentTypeList = contentTypeRepository.findAll();
        assertThat(contentTypeList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
