package com.mycompany.myapp.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.mycompany.myapp.web.rest.TestUtil;

public class UserShareTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(UserShare.class);
        UserShare userShare1 = new UserShare();
        userShare1.setId(1L);
        UserShare userShare2 = new UserShare();
        userShare2.setId(userShare1.getId());
        assertThat(userShare1).isEqualTo(userShare2);
        userShare2.setId(2L);
        assertThat(userShare1).isNotEqualTo(userShare2);
        userShare1.setId(null);
        assertThat(userShare1).isNotEqualTo(userShare2);
    }
}
