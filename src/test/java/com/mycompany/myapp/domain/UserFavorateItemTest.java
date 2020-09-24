package com.mycompany.myapp.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.mycompany.myapp.web.rest.TestUtil;

public class UserFavorateItemTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(UserFavorateItem.class);
        UserFavorateItem userFavorateItem1 = new UserFavorateItem();
        userFavorateItem1.setId(1L);
        UserFavorateItem userFavorateItem2 = new UserFavorateItem();
        userFavorateItem2.setId(userFavorateItem1.getId());
        assertThat(userFavorateItem1).isEqualTo(userFavorateItem2);
        userFavorateItem2.setId(2L);
        assertThat(userFavorateItem1).isNotEqualTo(userFavorateItem2);
        userFavorateItem1.setId(null);
        assertThat(userFavorateItem1).isNotEqualTo(userFavorateItem2);
    }
}
