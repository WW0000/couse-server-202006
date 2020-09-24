package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.Course1802753139App;
import com.mycompany.myapp.domain.UserFans;
import com.mycompany.myapp.repository.UserFansRepository;
import com.mycompany.myapp.service.UserFansService;

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
 * Integration tests for the {@link UserFansResource} REST controller.
 */
@SpringBootTest(classes = Course1802753139App.class)
@AutoConfigureMockMvc
@WithMockUser
public class UserFansResourceIT {

    private static final ZonedDateTime DEFAULT_FANS_TIME = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_FANS_TIME = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    @Autowired
    private UserFansRepository userFansRepository;

    @Autowired
    private UserFansService userFansService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restUserFansMockMvc;

    private UserFans userFans;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static UserFans createEntity(EntityManager em) {
        UserFans userFans = new UserFans()
            .fansTime(DEFAULT_FANS_TIME);
        return userFans;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static UserFans createUpdatedEntity(EntityManager em) {
        UserFans userFans = new UserFans()
            .fansTime(UPDATED_FANS_TIME);
        return userFans;
    }

    @BeforeEach
    public void initTest() {
        userFans = createEntity(em);
    }

    @Test
    @Transactional
    public void createUserFans() throws Exception {
        int databaseSizeBeforeCreate = userFansRepository.findAll().size();
        // Create the UserFans
        restUserFansMockMvc.perform(post("/api/user-fans")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(userFans)))
            .andExpect(status().isCreated());

        // Validate the UserFans in the database
        List<UserFans> userFansList = userFansRepository.findAll();
        assertThat(userFansList).hasSize(databaseSizeBeforeCreate + 1);
        UserFans testUserFans = userFansList.get(userFansList.size() - 1);
        assertThat(testUserFans.getFansTime()).isEqualTo(DEFAULT_FANS_TIME);
    }

    @Test
    @Transactional
    public void createUserFansWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = userFansRepository.findAll().size();

        // Create the UserFans with an existing ID
        userFans.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restUserFansMockMvc.perform(post("/api/user-fans")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(userFans)))
            .andExpect(status().isBadRequest());

        // Validate the UserFans in the database
        List<UserFans> userFansList = userFansRepository.findAll();
        assertThat(userFansList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllUserFans() throws Exception {
        // Initialize the database
        userFansRepository.saveAndFlush(userFans);

        // Get all the userFansList
        restUserFansMockMvc.perform(get("/api/user-fans?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(userFans.getId().intValue())))
            .andExpect(jsonPath("$.[*].fansTime").value(hasItem(sameInstant(DEFAULT_FANS_TIME))));
    }
    
    @Test
    @Transactional
    public void getUserFans() throws Exception {
        // Initialize the database
        userFansRepository.saveAndFlush(userFans);

        // Get the userFans
        restUserFansMockMvc.perform(get("/api/user-fans/{id}", userFans.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(userFans.getId().intValue()))
            .andExpect(jsonPath("$.fansTime").value(sameInstant(DEFAULT_FANS_TIME)));
    }
    @Test
    @Transactional
    public void getNonExistingUserFans() throws Exception {
        // Get the userFans
        restUserFansMockMvc.perform(get("/api/user-fans/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateUserFans() throws Exception {
        // Initialize the database
        userFansService.save(userFans);

        int databaseSizeBeforeUpdate = userFansRepository.findAll().size();

        // Update the userFans
        UserFans updatedUserFans = userFansRepository.findById(userFans.getId()).get();
        // Disconnect from session so that the updates on updatedUserFans are not directly saved in db
        em.detach(updatedUserFans);
        updatedUserFans
            .fansTime(UPDATED_FANS_TIME);

        restUserFansMockMvc.perform(put("/api/user-fans")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedUserFans)))
            .andExpect(status().isOk());

        // Validate the UserFans in the database
        List<UserFans> userFansList = userFansRepository.findAll();
        assertThat(userFansList).hasSize(databaseSizeBeforeUpdate);
        UserFans testUserFans = userFansList.get(userFansList.size() - 1);
        assertThat(testUserFans.getFansTime()).isEqualTo(UPDATED_FANS_TIME);
    }

    @Test
    @Transactional
    public void updateNonExistingUserFans() throws Exception {
        int databaseSizeBeforeUpdate = userFansRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restUserFansMockMvc.perform(put("/api/user-fans")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(userFans)))
            .andExpect(status().isBadRequest());

        // Validate the UserFans in the database
        List<UserFans> userFansList = userFansRepository.findAll();
        assertThat(userFansList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteUserFans() throws Exception {
        // Initialize the database
        userFansService.save(userFans);

        int databaseSizeBeforeDelete = userFansRepository.findAll().size();

        // Delete the userFans
        restUserFansMockMvc.perform(delete("/api/user-fans/{id}", userFans.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<UserFans> userFansList = userFansRepository.findAll();
        assertThat(userFansList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
