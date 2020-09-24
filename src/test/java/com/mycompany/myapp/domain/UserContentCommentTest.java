package com.mycompany.myapp.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.mycompany.myapp.web.rest.TestUtil;

public class UserContentCommentTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(UserContentComment.class);
        UserContentComment userContentComment1 = new UserContentComment();
        userContentComment1.setId(1L);
        UserContentComment userContentComment2 = new UserContentComment();
        userContentComment2.setId(userContentComment1.getId());
        assertThat(userContentComment1).isEqualTo(userContentComment2);
        userContentComment2.setId(2L);
        assertThat(userContentComment1).isNotEqualTo(userContentComment2);
        userContentComment1.setId(null);
        assertThat(userContentComment1).isNotEqualTo(userContentComment2);
    }
}
