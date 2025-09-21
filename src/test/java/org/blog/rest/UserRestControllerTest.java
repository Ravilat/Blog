package org.blog.rest;

import org.blog.AbstractMockMvcTest;
import org.blog.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.test.context.support.WithMockUser;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class UserRestControllerTest extends AbstractMockMvcTest {

    public static String URL_GET = "/api/users/id/{id}";
    public static String URL_GET_BY_LOGIN = "/api/users/login/{login}";

    @Autowired
    private UserService userService;

    @Test
    @WithMockUser(username = "Alisa")
    void getRestUserTest() throws Exception {

        mockMvc.perform(get(URL_GET, 1))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.login").value("Rav"))
                .andExpect(jsonPath("$.email").value("ravv@mail.ru"));

    }

    @Test
    @WithMockUser(username = "Alisa")
    void getRestUserByLoginTest() throws Exception {

        mockMvc.perform(get(URL_GET_BY_LOGIN, "Rav"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.login").value("Rav"));

    }

}
