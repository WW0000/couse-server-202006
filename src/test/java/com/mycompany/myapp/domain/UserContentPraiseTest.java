package com.mycompany.myapp.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.mycompany.myapp.web.rest.TestUtil;

public class UserContentPraiseTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(UserContentPraise.class);
        UserContentPraise userContentPraise1 = new UserContentPraise();
        userContentPraise1.setId(1L);
        UserContentPraise userContentPraise2 = new UserContentPraise();
        userContentPraise2.setId(userContentPraise1.getId());
        assertThat(userContentPraise1).isEqualTo(userContentPraise2);
        userContentPraise2.setId(2L);
        assertThat(userContentPraise1).isNotEqualTo(userContentPraise2);
        userContentPraise1.setId(null);
        assertThat(userContentPraise1).isNotEqualTo(userContentPraise2);
    }
}
