package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.Course1802753139App;
import com.mycompany.myapp.domain.UserFavorateItem;
import com.mycompany.myapp.repository.UserFavorateItemRepository;
import com.mycompany.myapp.service.UserFavorateItemService;

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
 * Integration tests for the {@link UserFavorateItemResource} REST controller.
 */
@SpringBootTest(classes = Course1802753139App.class)
@AutoConfigureMockMvc
@WithMockUser
public class UserFavorateItemResourceIT {

    private static final ZonedDateTime DEFAULT_FAVORATE_TIME = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_FAVORATE_TIME = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    @Autowired
    private UserFavorateItemRepository userFavorateItemRepository;

    @Autowired
    private UserFavorateItemService userFavorateItemService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restUserFavorateItemMockMvc;

    private UserFavorateItem userFavorateItem;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static UserFavorateItem createEntity(EntityManager em) {
        UserFavorateItem userFavorateItem = new UserFavorateItem()
            .favorateTime(DEFAULT_FAVORATE_TIME);
        return userFavorateItem;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static UserFavorateItem createUpdatedEntity(EntityManager em) {
        UserFavorateItem userFavorateItem = new UserFavorateItem()
            .favorateTime(UPDATED_FAVORATE_TIME);
        return userFavorateItem;
    }

    @BeforeEach
    public void initTest() {
        userFavorateItem = createEntity(em);
    }

    @Test
    @Transactional
    public void createUserFavorateItem() throws Exception {
        int databaseSizeBeforeCreate = userFavorateItemRepository.findAll().size();
        // Create the UserFavorateItem
        restUserFavorateItemMockMvc.perform(post("/api/user-favorate-items")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(userFavorateItem)))
            .andExpect(status().isCreated());

        // Validate the UserFavorateItem in the database
        List<UserFavorateItem> userFavorateItemList = userFavorateItemRepository.findAll();
        assertThat(userFavorateItemList).hasSize(databaseSizeBeforeCreate + 1);
        UserFavorateItem testUserFavorateItem = userFavorateItemList.get(userFavorateItemList.size() - 1);
        assertThat(testUserFavorateItem.getFavorateTime()).isEqualTo(DEFAULT_FAVORATE_TIME);
    }

    @Test
    @Transactional
    public void createUserFavorateItemWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = userFavorateItemRepository.findAll().size();

        // Create the UserFavorateItem with an existing ID
        userFavorateItem.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restUserFavorateItemMockMvc.perform(post("/api/user-favorate-items")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(userFavorateItem)))
            .andExpect(status().isBadRequest());

        // Validate the UserFavorateItem in the database
        List<UserFavorateItem> userFavorateItemList = userFavorateItemRepository.findAll();
        assertThat(userFavorateItemList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllUserFavorateItems() throws Exception {
        // Initialize the database
        userFavorateItemRepository.saveAndFlush(userFavorateItem);

        // Get all the userFavorateItemList
        restUserFavorateItemMockMvc.perform(get("/api/user-favorate-items?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(userFavorateItem.getId().intValue())))
            .andExpect(jsonPath("$.[*].favorateTime").value(hasItem(sameInstant(DEFAULT_FAVORATE_TIME))));
    }
    
    @Test
    @Transactional
    public void getUserFavorateItem() throws Exception {
        // Initialize the database
        userFavorateItemRepository.saveAndFlush(userFavorateItem);

        // Get the userFavorateItem
        restUserFavorateItemMockMvc.perform(get("/api/user-favorate-items/{id}", userFavorateItem.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(userFavorateItem.getId().intValue()))
            .andExpect(jsonPath("$.favorateTime").value(sameInstant(DEFAULT_FAVORATE_TIME)));
    }
    @Test
    @Transactional
    public void getNonExistingUserFavorateItem() throws Exception {
        // Get the userFavorateItem
        restUserFavorateItemMockMvc.perform(get("/api/user-favorate-items/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateUserFavorateItem() throws Exception {
        // Initialize the database
        userFavorateItemService.save(userFavorateItem);

        int databaseSizeBeforeUpdate = userFavorateItemRepository.findAll().size();

        // Update the userFavorateItem
        UserFavorateItem updatedUserFavorateItem = userFavorateItemRepository.findById(userFavorateItem.getId()).get();
        // Disconnect from session so that the updates on updatedUserFavorateItem are not directly saved in db
        em.detach(updatedUserFavorateItem);
        updatedUserFavorateItem
            .favorateTime(UPDATED_FAVORATE_TIME);

        restUserFavorateItemMockMvc.perform(put("/api/user-favorate-items")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedUserFavorateItem)))
            .andExpect(status().isOk());

        // Validate the UserFavorateItem in the database
        List<UserFavorateItem> userFavorateItemList = userFavorateItemRepository.findAll();
        assertThat(userFavorateItemList).hasSize(databaseSizeBeforeUpdate);
        UserFavorateItem testUserFavorateItem = userFavorateItemList.get(userFavorateItemList.size() - 1);
        assertThat(testUserFavorateItem.getFavorateTime()).isEqualTo(UPDATED_FAVORATE_TIME);
    }

    @Test
    @Transactional
    public void updateNonExistingUserFavorateItem() throws Exception {
        int databaseSizeBeforeUpdate = userFavorateItemRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restUserFavorateItemMockMvc.perform(put("/api/user-favorate-items")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(userFavorateItem)))
            .andExpect(status().isBadRequest());

        // Validate the UserFavorateItem in the database
        List<UserFavorateItem> userFavorateItemList = userFavorateItemRepository.findAll();
        assertThat(userFavorateItemList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteUserFavorateItem() throws Exception {
        // Initialize the database
        userFavorateItemService.save(userFavorateItem);

        int databaseSizeBeforeDelete = userFavorateItemRepository.findAll().size();

        // Delete the userFavorateItem
        restUserFavorateItemMockMvc.perform(delete("/api/user-favorate-items/{id}", userFavorateItem.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<UserFavorateItem> userFavorateItemList = userFavorateItemRepository.findAll();
        assertThat(userFavorateItemList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
