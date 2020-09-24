package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.Course1802753139App;
import com.mycompany.myapp.domain.UserShare;
import com.mycompany.myapp.repository.UserShareRepository;
import com.mycompany.myapp.service.UserShareService;

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
 * Integration tests for the {@link UserShareResource} REST controller.
 */
@SpringBootTest(classes = Course1802753139App.class)
@AutoConfigureMockMvc
@WithMockUser
public class UserShareResourceIT {

    private static final ZonedDateTime DEFAULT_SHARE_TIME = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_SHARE_TIME = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    @Autowired
    private UserShareRepository userShareRepository;

    @Autowired
    private UserShareService userShareService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restUserShareMockMvc;

    private UserShare userShare;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static UserShare createEntity(EntityManager em) {
        UserShare userShare = new UserShare()
            .shareTime(DEFAULT_SHARE_TIME);
        return userShare;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static UserShare createUpdatedEntity(EntityManager em) {
        UserShare userShare = new UserShare()
            .shareTime(UPDATED_SHARE_TIME);
        return userShare;
    }

    @BeforeEach
    public void initTest() {
        userShare = createEntity(em);
    }

    @Test
    @Transactional
    public void createUserShare() throws Exception {
        int databaseSizeBeforeCreate = userShareRepository.findAll().size();
        // Create the UserShare
        restUserShareMockMvc.perform(post("/api/user-shares")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(userShare)))
            .andExpect(status().isCreated());

        // Validate the UserShare in the database
        List<UserShare> userShareList = userShareRepository.findAll();
        assertThat(userShareList).hasSize(databaseSizeBeforeCreate + 1);
        UserShare testUserShare = userShareList.get(userShareList.size() - 1);
        assertThat(testUserShare.getShareTime()).isEqualTo(DEFAULT_SHARE_TIME);
    }

    @Test
    @Transactional
    public void createUserShareWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = userShareRepository.findAll().size();

        // Create the UserShare with an existing ID
        userShare.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restUserShareMockMvc.perform(post("/api/user-shares")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(userShare)))
            .andExpect(status().isBadRequest());

        // Validate the UserShare in the database
        List<UserShare> userShareList = userShareRepository.findAll();
        assertThat(userShareList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllUserShares() throws Exception {
        // Initialize the database
        userShareRepository.saveAndFlush(userShare);

        // Get all the userShareList
        restUserShareMockMvc.perform(get("/api/user-shares?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(userShare.getId().intValue())))
            .andExpect(jsonPath("$.[*].shareTime").value(hasItem(sameInstant(DEFAULT_SHARE_TIME))));
    }
    
    @Test
    @Transactional
    public void getUserShare() throws Exception {
        // Initialize the database
        userShareRepository.saveAndFlush(userShare);

        // Get the userShare
        restUserShareMockMvc.perform(get("/api/user-shares/{id}", userShare.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(userShare.getId().intValue()))
            .andExpect(jsonPath("$.shareTime").value(sameInstant(DEFAULT_SHARE_TIME)));
    }
    @Test
    @Transactional
    public void getNonExistingUserShare() throws Exception {
        // Get the userShare
        restUserShareMockMvc.perform(get("/api/user-shares/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateUserShare() throws Exception {
        // Initialize the database
        userShareService.save(userShare);

        int databaseSizeBeforeUpdate = userShareRepository.findAll().size();

        // Update the userShare
        UserShare updatedUserShare = userShareRepository.findById(userShare.getId()).get();
        // Disconnect from session so that the updates on updatedUserShare are not directly saved in db
        em.detach(updatedUserShare);
        updatedUserShare
            .shareTime(UPDATED_SHARE_TIME);

        restUserShareMockMvc.perform(put("/api/user-shares")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedUserShare)))
            .andExpect(status().isOk());

        // Validate the UserShare in the database
        List<UserShare> userShareList = userShareRepository.findAll();
        assertThat(userShareList).hasSize(databaseSizeBeforeUpdate);
        UserShare testUserShare = userShareList.get(userShareList.size() - 1);
        assertThat(testUserShare.getShareTime()).isEqualTo(UPDATED_SHARE_TIME);
    }

    @Test
    @Transactional
    public void updateNonExistingUserShare() throws Exception {
        int databaseSizeBeforeUpdate = userShareRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restUserShareMockMvc.perform(put("/api/user-shares")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(userShare)))
            .andExpect(status().isBadRequest());

        // Validate the UserShare in the database
        List<UserShare> userShareList = userShareRepository.findAll();
        assertThat(userShareList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteUserShare() throws Exception {
        // Initialize the database
        userShareService.save(userShare);

        int databaseSizeBeforeDelete = userShareRepository.findAll().size();

        // Delete the userShare
        restUserShareMockMvc.perform(delete("/api/user-shares/{id}", userShare.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<UserShare> userShareList = userShareRepository.findAll();
        assertThat(userShareList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
