package ru.skypro.homework.controller;

import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.util.Base64Utils;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
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
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
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
    @Container
    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:latest")
            .withUsername("postgres")
            .withPassword("postgres");

    static void postgresProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgres::getJdbcUrl);
        registry.add("spring.datasource.username", postgres::getUsername);
        registry.add("spring.datasource.password", postgres::getPassword);
    }

    private String base64Encoded(String login, String password) {
        return Base64Utils.encodeToString((login + ":" + password).getBytes(StandardCharsets.UTF_8));
    }

    private void addToDb() throws IOException {
        userRepository.deleteAll();
        Image image = new Image();
        image.setBytes(Files.readAllBytes(Paths.get("user-avatar.png")));
        image.setId(UUID.randomUUID().toString());
        imageRepository.save(image);
        User user = new User(1,
                "user@mail.com",
                "David",
                "Duchovny",
                "+7 777-77-77",
                image,
                "password",
                "user@mail.com",
                Role.USER);
        userRepository.save(user);
    }

    @Test
    public void updatePassword() throws Exception {
        addToDb();
        JSONObject newPass = new JSONObject();
        newPass.put("currentPassword", "password");
        newPass.put("newPassword", "password1");
        mockMvc.perform(post("/users/set_password")
                .contentType(MediaType.APPLICATION_JSON)
                .content(newPass.toString()))
                .andExpect(status().isOk());
    }

    @Test
    public void updatePassword_status_isForbidden() throws Exception {
        addToDb();
        JSONObject newPass = new JSONObject();
        newPass.put("currentPassword", "password1");
        newPass.put("newPassword", "password2");
        mockMvc.perform(post("/users/set_password")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(newPass.toString()))
                .andExpect(status().isForbidden());
    }
    @Test
    @WithMockUser(username = "user@mail.com", roles = "USER", password = "password")
    public void getInfoAboutUser_status_isOk() throws Exception {
        addToDb();
        mockMvc.perform(get("/users/me"))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "admin@mail.com", roles = "USER", password = "password1")
    public void getInformation_status_isUserNotFound() throws Exception {
        addToDb();
        mockMvc.perform(get("/users/me"))
                .andExpect(status().isNotFound());
    }

    @Test
    @WithMockUser(username = "user@mail.com", roles = "USER", password = "password")
    public void updateInformationAboutUser_status_isOk() throws Exception {
        addToDb();
        JSONObject updateUser = new JSONObject();
        updateUser.put("firstName", "David");
        updateUser.put("lastName", "Duchovny");
        updateUser.put("phone", "+7 777-77-77");
        mockMvc.perform(patch("/users/me")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(updateUser.toString()))
                .andExpect(status().isOk());
    }
}
