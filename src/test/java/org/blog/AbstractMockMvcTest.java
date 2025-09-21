package org.blog;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;

public abstract class AbstractMockMvcTest  extends BaseTests{
    @Autowired
    public MockMvc mockMvc;
}
