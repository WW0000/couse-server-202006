package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.Course1802753139App;
import com.mycompany.myapp.domain.UserContentComment;
import com.mycompany.myapp.repository.UserContentCommentRepository;
import com.mycompany.myapp.service.UserContentCommentService;

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
 * Integration tests for the {@link UserContentCommentResource} REST controller.
 */
@SpringBootTest(classes = Course1802753139App.class)
@AutoConfigureMockMvc
@WithMockUser
public class UserContentCommentResourceIT {

    private static final Long DEFAULT_COMMENT_PID = 1L;
    private static final Long UPDATED_COMMENT_PID = 2L;

    private static final ZonedDateTime DEFAULT_COMMENT_TIME = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_COMMENT_TIME = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final String DEFAULT_COMMENT_CONTENT = "AAAAAAAAAA";
    private static final String UPDATED_COMMENT_CONTENT = "BBBBBBBBBB";

    private static final String DEFAULT_CLIENT_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_CLIENT_TYPE = "BBBBBBBBBB";

    private static final Long DEFAULT_PRAISE_COUNT = 1L;
    private static final Long UPDATED_PRAISE_COUNT = 2L;

    @Autowired
    private UserContentCommentRepository userContentCommentRepository;

    @Autowired
    private UserContentCommentService userContentCommentService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restUserContentCommentMockMvc;

    private UserContentComment userContentComment;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static UserContentComment createEntity(EntityManager em) {
        UserContentComment userContentComment = new UserContentComment()
            .commentPid(DEFAULT_COMMENT_PID)
            .commentTime(DEFAULT_COMMENT_TIME)
            .commentContent(DEFAULT_COMMENT_CONTENT)
            .clientType(DEFAULT_CLIENT_TYPE)
            .praiseCount(DEFAULT_PRAISE_COUNT);
        return userContentComment;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static UserContentComment createUpdatedEntity(EntityManager em) {
        UserContentComment userContentComment = new UserContentComment()
            .commentPid(UPDATED_COMMENT_PID)
            .commentTime(UPDATED_COMMENT_TIME)
            .commentContent(UPDATED_COMMENT_CONTENT)
            .clientType(UPDATED_CLIENT_TYPE)
            .praiseCount(UPDATED_PRAISE_COUNT);
        return userContentComment;
    }

    @BeforeEach
    public void initTest() {
        userContentComment = createEntity(em);
    }

    @Test
    @Transactional
    public void createUserContentComment() throws Exception {
        int databaseSizeBeforeCreate = userContentCommentRepository.findAll().size();
        // Create the UserContentComment
        restUserContentCommentMockMvc.perform(post("/api/user-content-comments")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(userContentComment)))
            .andExpect(status().isCreated());

        // Validate the UserContentComment in the database
        List<UserContentComment> userContentCommentList = userContentCommentRepository.findAll();
        assertThat(userContentCommentList).hasSize(databaseSizeBeforeCreate + 1);
        UserContentComment testUserContentComment = userContentCommentList.get(userContentCommentList.size() - 1);
        assertThat(testUserContentComment.getCommentPid()).isEqualTo(DEFAULT_COMMENT_PID);
        assertThat(testUserContentComment.getCommentTime()).isEqualTo(DEFAULT_COMMENT_TIME);
        assertThat(testUserContentComment.getCommentContent()).isEqualTo(DEFAULT_COMMENT_CONTENT);
        assertThat(testUserContentComment.getClientType()).isEqualTo(DEFAULT_CLIENT_TYPE);
        assertThat(testUserContentComment.getPraiseCount()).isEqualTo(DEFAULT_PRAISE_COUNT);
    }

    @Test
    @Transactional
    public void createUserContentCommentWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = userContentCommentRepository.findAll().size();

        // Create the UserContentComment with an existing ID
        userContentComment.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restUserContentCommentMockMvc.perform(post("/api/user-content-comments")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(userContentComment)))
            .andExpect(status().isBadRequest());

        // Validate the UserContentComment in the database
        List<UserContentComment> userContentCommentList = userContentCommentRepository.findAll();
        assertThat(userContentCommentList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllUserContentComments() throws Exception {
        // Initialize the database
        userContentCommentRepository.saveAndFlush(userContentComment);

        // Get all the userContentCommentList
        restUserContentCommentMockMvc.perform(get("/api/user-content-comments?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(userContentComment.getId().intValue())))
            .andExpect(jsonPath("$.[*].commentPid").value(hasItem(DEFAULT_COMMENT_PID.intValue())))
            .andExpect(jsonPath("$.[*].commentTime").value(hasItem(sameInstant(DEFAULT_COMMENT_TIME))))
            .andExpect(jsonPath("$.[*].commentContent").value(hasItem(DEFAULT_COMMENT_CONTENT)))
            .andExpect(jsonPath("$.[*].clientType").value(hasItem(DEFAULT_CLIENT_TYPE)))
            .andExpect(jsonPath("$.[*].praiseCount").value(hasItem(DEFAULT_PRAISE_COUNT.intValue())));
    }
    
    @Test
    @Transactional
    public void getUserContentComment() throws Exception {
        // Initialize the database
        userContentCommentRepository.saveAndFlush(userContentComment);

        // Get the userContentComment
        restUserContentCommentMockMvc.perform(get("/api/user-content-comments/{id}", userContentComment.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(userContentComment.getId().intValue()))
            .andExpect(jsonPath("$.commentPid").value(DEFAULT_COMMENT_PID.intValue()))
            .andExpect(jsonPath("$.commentTime").value(sameInstant(DEFAULT_COMMENT_TIME)))
            .andExpect(jsonPath("$.commentContent").value(DEFAULT_COMMENT_CONTENT))
            .andExpect(jsonPath("$.clientType").value(DEFAULT_CLIENT_TYPE))
            .andExpect(jsonPath("$.praiseCount").value(DEFAULT_PRAISE_COUNT.intValue()));
    }
    @Test
    @Transactional
    public void getNonExistingUserContentComment() throws Exception {
        // Get the userContentComment
        restUserContentCommentMockMvc.perform(get("/api/user-content-comments/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateUserContentComment() throws Exception {
        // Initialize the database
        userContentCommentService.save(userContentComment);

        int databaseSizeBeforeUpdate = userContentCommentRepository.findAll().size();

        // Update the userContentComment
        UserContentComment updatedUserContentComment = userContentCommentRepository.findById(userContentComment.getId()).get();
        // Disconnect from session so that the updates on updatedUserContentComment are not directly saved in db
        em.detach(updatedUserContentComment);
        updatedUserContentComment
            .commentPid(UPDATED_COMMENT_PID)
            .commentTime(UPDATED_COMMENT_TIME)
            .commentContent(UPDATED_COMMENT_CONTENT)
            .clientType(UPDATED_CLIENT_TYPE)
            .praiseCount(UPDATED_PRAISE_COUNT);

        restUserContentCommentMockMvc.perform(put("/api/user-content-comments")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedUserContentComment)))
            .andExpect(status().isOk());

        // Validate the UserContentComment in the database
        List<UserContentComment> userContentCommentList = userContentCommentRepository.findAll();
        assertThat(userContentCommentList).hasSize(databaseSizeBeforeUpdate);
        UserContentComment testUserContentComment = userContentCommentList.get(userContentCommentList.size() - 1);
        assertThat(testUserContentComment.getCommentPid()).isEqualTo(UPDATED_COMMENT_PID);
        assertThat(testUserContentComment.getCommentTime()).isEqualTo(UPDATED_COMMENT_TIME);
        assertThat(testUserContentComment.getCommentContent()).isEqualTo(UPDATED_COMMENT_CONTENT);
        assertThat(testUserContentComment.getClientType()).isEqualTo(UPDATED_CLIENT_TYPE);
        assertThat(testUserContentComment.getPraiseCount()).isEqualTo(UPDATED_PRAISE_COUNT);
    }

    @Test
    @Transactional
    public void updateNonExistingUserContentComment() throws Exception {
        int databaseSizeBeforeUpdate = userContentCommentRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restUserContentCommentMockMvc.perform(put("/api/user-content-comments")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(userContentComment)))
            .andExpect(status().isBadRequest());

        // Validate the UserContentComment in the database
        List<UserContentComment> userContentCommentList = userContentCommentRepository.findAll();
        assertThat(userContentCommentList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteUserContentComment() throws Exception {
        // Initialize the database
        userContentCommentService.save(userContentComment);

        int databaseSizeBeforeDelete = userContentCommentRepository.findAll().size();

        // Delete the userContentComment
        restUserContentCommentMockMvc.perform(delete("/api/user-content-comments/{id}", userContentComment.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<UserContentComment> userContentCommentList = userContentCommentRepository.findAll();
        assertThat(userContentCommentList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
