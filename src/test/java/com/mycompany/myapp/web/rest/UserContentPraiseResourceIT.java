package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.Course1802753139App;
import com.mycompany.myapp.domain.UserContentPraise;
import com.mycompany.myapp.repository.UserContentPraiseRepository;
import com.mycompany.myapp.service.UserContentPraiseService;

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
 * Integration tests for the {@link UserContentPraiseResource} REST controller.
 */
@SpringBootTest(classes = Course1802753139App.class)
@AutoConfigureMockMvc
@WithMockUser
public class UserContentPraiseResourceIT {

    private static final ZonedDateTime DEFAULT_PRAISE_TIME = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_PRAISE_TIME = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    @Autowired
    private UserContentPraiseRepository userContentPraiseRepository;

    @Autowired
    private UserContentPraiseService userContentPraiseService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restUserContentPraiseMockMvc;

    private UserContentPraise userContentPraise;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static UserContentPraise createEntity(EntityManager em) {
        UserContentPraise userContentPraise = new UserContentPraise()
            .praiseTime(DEFAULT_PRAISE_TIME);
        return userContentPraise;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static UserContentPraise createUpdatedEntity(EntityManager em) {
        UserContentPraise userContentPraise = new UserContentPraise()
            .praiseTime(UPDATED_PRAISE_TIME);
        return userContentPraise;
    }

    @BeforeEach
    public void initTest() {
        userContentPraise = createEntity(em);
    }

    @Test
    @Transactional
    public void createUserContentPraise() throws Exception {
        int databaseSizeBeforeCreate = userContentPraiseRepository.findAll().size();
        // Create the UserContentPraise
        restUserContentPraiseMockMvc.perform(post("/api/user-content-praises")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(userContentPraise)))
            .andExpect(status().isCreated());

        // Validate the UserContentPraise in the database
        List<UserContentPraise> userContentPraiseList = userContentPraiseRepository.findAll();
        assertThat(userContentPraiseList).hasSize(databaseSizeBeforeCreate + 1);
        UserContentPraise testUserContentPraise = userContentPraiseList.get(userContentPraiseList.size() - 1);
        assertThat(testUserContentPraise.getPraiseTime()).isEqualTo(DEFAULT_PRAISE_TIME);
    }

    @Test
    @Transactional
    public void createUserContentPraiseWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = userContentPraiseRepository.findAll().size();

        // Create the UserContentPraise with an existing ID
        userContentPraise.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restUserContentPraiseMockMvc.perform(post("/api/user-content-praises")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(userContentPraise)))
            .andExpect(status().isBadRequest());

        // Validate the UserContentPraise in the database
        List<UserContentPraise> userContentPraiseList = userContentPraiseRepository.findAll();
        assertThat(userContentPraiseList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllUserContentPraises() throws Exception {
        // Initialize the database
        userContentPraiseRepository.saveAndFlush(userContentPraise);

        // Get all the userContentPraiseList
        restUserContentPraiseMockMvc.perform(get("/api/user-content-praises?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(userContentPraise.getId().intValue())))
            .andExpect(jsonPath("$.[*].praiseTime").value(hasItem(sameInstant(DEFAULT_PRAISE_TIME))));
    }
    
    @Test
    @Transactional
    public void getUserContentPraise() throws Exception {
        // Initialize the database
        userContentPraiseRepository.saveAndFlush(userContentPraise);

        // Get the userContentPraise
        restUserContentPraiseMockMvc.perform(get("/api/user-content-praises/{id}", userContentPraise.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(userContentPraise.getId().intValue()))
            .andExpect(jsonPath("$.praiseTime").value(sameInstant(DEFAULT_PRAISE_TIME)));
    }
    @Test
    @Transactional
    public void getNonExistingUserContentPraise() throws Exception {
        // Get the userContentPraise
        restUserContentPraiseMockMvc.perform(get("/api/user-content-praises/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateUserContentPraise() throws Exception {
        // Initialize the database
        userContentPraiseService.save(userContentPraise);

        int databaseSizeBeforeUpdate = userContentPraiseRepository.findAll().size();

        // Update the userContentPraise
        UserContentPraise updatedUserContentPraise = userContentPraiseRepository.findById(userContentPraise.getId()).get();
        // Disconnect from session so that the updates on updatedUserContentPraise are not directly saved in db
        em.detach(updatedUserContentPraise);
        updatedUserContentPraise
            .praiseTime(UPDATED_PRAISE_TIME);

        restUserContentPraiseMockMvc.perform(put("/api/user-content-praises")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedUserContentPraise)))
            .andExpect(status().isOk());

        // Validate the UserContentPraise in the database
        List<UserContentPraise> userContentPraiseList = userContentPraiseRepository.findAll();
        assertThat(userContentPraiseList).hasSize(databaseSizeBeforeUpdate);
        UserContentPraise testUserContentPraise = userContentPraiseList.get(userContentPraiseList.size() - 1);
        assertThat(testUserContentPraise.getPraiseTime()).isEqualTo(UPDATED_PRAISE_TIME);
    }

    @Test
    @Transactional
    public void updateNonExistingUserContentPraise() throws Exception {
        int databaseSizeBeforeUpdate = userContentPraiseRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restUserContentPraiseMockMvc.perform(put("/api/user-content-praises")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(userContentPraise)))
            .andExpect(status().isBadRequest());

        // Validate the UserContentPraise in the database
        List<UserContentPraise> userContentPraiseList = userContentPraiseRepository.findAll();
        assertThat(userContentPraiseList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteUserContentPraise() throws Exception {
        // Initialize the database
        userContentPraiseService.save(userContentPraise);

        int databaseSizeBeforeDelete = userContentPraiseRepository.findAll().size();

        // Delete the userContentPraise
        restUserContentPraiseMockMvc.perform(delete("/api/user-content-praises/{id}", userContentPraise.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<UserContentPraise> userContentPraiseList = userContentPraiseRepository.findAll();
        assertThat(userContentPraiseList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
