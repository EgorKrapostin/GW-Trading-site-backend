package ru.skypro.homework.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import net.minidev.json.JSONObject;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMultipartHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.util.Base64Utils;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.junit.jupiter.Container;
import ru.skypro.homework.dto.authdto.Role;
import ru.skypro.homework.entity.Image;
import ru.skypro.homework.entity.User;
import ru.skypro.homework.repository.ImageRepository;
import ru.skypro.homework.repository.UserRepository;
import ru.skypro.homework.service.AuthService;
import ru.skypro.homework.service.UserService;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Testcontainers
public class UsersControllerTest {
    @Autowired
    MockMvc mockMvc;
    @Autowired
    private UserService userService;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ImageRepository imageRepository;
    @Autowired
    private AuthService authService;
    private PasswordEncoder passwordEncoder;
    @Autowired
    private ObjectMapper objectMapper;

    @Container
    private static final PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:latest")
            .withUsername("postgres")
            .withPassword("postgres");

    @DynamicPropertySource
    static void postgresProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgres::getJdbcUrl);
        registry.add("spring.datasource.username", postgres::getUsername);
        registry.add("spring.datasource.password", postgres::getPassword);
    }

    private String passEncoder(String login, String password) {
        return Base64Utils.encodeToString((login + ":" + password).getBytes(StandardCharsets.UTF_8));
    }
    @BeforeEach
    void addToDb() throws IOException {
        User user = userRepository.save(new User(1,
                "user@mail.ru",
                "David",
                "Duchovny",
                "+7 777-77-77",
                null,
                "$2a$12$8lTZ/silBE6jnRqlxIqxb.WYSgc0Lo2aT3vPHTNVXFUyOD8N5V1la",
                Role.USER));
        Image image = new Image();
        image.setBytes(Files.readAllBytes(Paths.get("user-avatar.png")));
        image.setId(user.getId().toString());
        imageRepository.save(image);
        user.setImage(image);
        userRepository.save(user);
    }

    @AfterEach
    void cleanDb() {
        userRepository.deleteAll();
    }

    @Test
    @WithMockUser(username = "user@mail.ru", roles = "USER", password = "password")
    public void updatePassword_status_isOk() throws Exception {
        JSONObject newPass = new JSONObject();
        newPass.put("currentPassword", "password");
        newPass.put("newPassword", "newPassword1");
        mockMvc.perform(post("/users/set_password")
                        .header(HttpHeaders.AUTHORIZATION, "Basic " + passEncoder("user@mail.ru", "password"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(newPass.toString()))
                .andExpect(status().isOk());
    }

    @Test
    public void updatePassword_status_isUnauthorized() throws Exception {

        JSONObject newPassword = new JSONObject();
        newPassword.put("currentPassword", "newPassword1");
        newPassword.put("newPassword", "newPassword2");
        mockMvc.perform(post("/users/set_password")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(newPassword.toString()))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockUser(username = "user@mail.ru", roles = "USER", password = "password")
    public void getUser_status_isOk() throws Exception {

        mockMvc.perform(get("/users/me"))
                .andExpect(status().isOk());
    }

    @Test
    public void getUser_status_isUnauthorized() throws Exception {

        mockMvc.perform(get("/users/me"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockUser(username = "user@mail.ru", roles = "USER", password = "password")
    public void updateInformationAboutUser_status_isOk() throws Exception {

        JSONObject updateUser = new JSONObject();
        updateUser.put("firstName", "Alex");
        updateUser.put("lastName", "Black");
        updateUser.put("phone", "+7 999-99-99");
        mockMvc.perform(patch("/users/me")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(updateUser.toString()))
                .andExpect(status().isOk());
    }

    @Test
    public void updateInformationAboutUser_status_isUnauthorized() throws Exception {

        JSONObject updateUser = new JSONObject();
        updateUser.put("firstName", "Alex");
        updateUser.put("lastName", "Black");
        updateUser.put("phone", "+7 999-99-99");
        mockMvc.perform(patch("/users/me")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(updateUser.toString()))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockUser(username = "user@mail.ru", roles = "USER", password = "password")
    public void updateUserImage_status_isOk() throws Exception {

        MockMultipartFile file = new MockMultipartFile(
                "image",
                "image.png",
                MediaType.IMAGE_PNG_VALUE,
                Files.readAllBytes(Paths.get("test-image.png"))
        );
        MockMultipartHttpServletRequestBuilder patchMultipart = (MockMultipartHttpServletRequestBuilder)
                MockMvcRequestBuilders.multipart("/users/me/image")
                        .with(rq -> {
                            rq.setMethod("PATCH");
                            return rq;
                        });
        mockMvc.perform(patchMultipart
                        .file(file))
                .andExpect(status().isOk());
    }


    @Test
    @WithMockUser(username = "user@mail.ru", roles = "USER", password = "password")
    public void getImage_status_isOk() throws Exception {

        User user = userRepository.findUserByEmail("user@mail.ru").orElseThrow();

        MvcResult result = mockMvc.perform(get("/users/{id}/image", user.getImage().getId()))
                .andExpect(status().isOk())
                .andReturn();
        byte[] resourceContent = result.getResponse().getContentAsByteArray();
        assertThat(resourceContent).isEmpty();
    }
}
