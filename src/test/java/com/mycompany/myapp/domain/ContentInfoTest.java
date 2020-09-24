package com.mycompany.myapp.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.mycompany.myapp.web.rest.TestUtil;

public class ContentInfoTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ContentInfo.class);
        ContentInfo contentInfo1 = new ContentInfo();
        contentInfo1.setId(1L);
        ContentInfo contentInfo2 = new ContentInfo();
        contentInfo2.setId(contentInfo1.getId());
        assertThat(contentInfo1).isEqualTo(contentInfo2);
        contentInfo2.setId(2L);
        assertThat(contentInfo1).isNotEqualTo(contentInfo2);
        contentInfo1.setId(null);
        assertThat(contentInfo1).isNotEqualTo(contentInfo2);
    }
}
