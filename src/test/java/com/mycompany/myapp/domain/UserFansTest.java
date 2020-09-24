package com.mycompany.myapp.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.mycompany.myapp.web.rest.TestUtil;

public class UserFansTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(UserFans.class);
        UserFans userFans1 = new UserFans();
        userFans1.setId(1L);
        UserFans userFans2 = new UserFans();
        userFans2.setId(userFans1.getId());
        assertThat(userFans1).isEqualTo(userFans2);
        userFans2.setId(2L);
        assertThat(userFans1).isNotEqualTo(userFans2);
        userFans1.setId(null);
        assertThat(userFans1).isNotEqualTo(userFans2);
    }
}
