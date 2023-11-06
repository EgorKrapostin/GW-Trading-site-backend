package ru.skypro.homework.controller;

import net.minidev.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import ru.skypro.homework.constants.InitialData;
import ru.skypro.homework.entity.User;
import ru.skypro.homework.repository.AdRepository;
import ru.skypro.homework.repository.CommentRepository;
import ru.skypro.homework.repository.UserRepository;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@Testcontainers
public class AdsControllerTests {
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

    @Autowired
    private DataSource dataSource;
    @Autowired
    MockMvc mockMvc;
    @Autowired
    CommentRepository commentRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    AdRepository adRepository;
    @Autowired
    private InitialData initialData;

    @BeforeEach
    void cleanTables() {
        commentRepository.deleteAll();
        adRepository.deleteAll();
        userRepository.deleteAll();
    }

    @Test
    void testPostgresql() throws SQLException {
        try (Connection conn = dataSource.getConnection()) {
            assertThat(conn).isNotNull();
        }
    }

    //COMMENT TESTS
    @Test
    public void givenCommentList_whenGetAdId_thenReturnList() throws Exception {
        User user = initialData.addUserToBase();
        initialData.addAdsByUser();
        int adId = initialData.addCommentByAd().getAd().getId();
        mockMvc.perform(get("/ads/{id}/comments", adId)
                        .with(user(user))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpectAll(
                        status().isOk(),
                        jsonPath("$.count").value(List.of(initialData.addCommentByAd()).size()),
                        jsonPath("$.results[0].text").value(initialData.addCommentByAd().getText())
                );
    }

    @Test
    public void givenCommentList_whenNonAd_thenReturnEmptyList() throws Exception {
        User user = initialData.addUserToBase();
        initialData.addAdsByUser();
        int adId = initialData.addCommentByAd().getAd().getId();
        mockMvc.perform(get("/ads/{id}/comments", adId + 1)
                        .with(user(user))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpectAll(
                        status().isOk(),
                        jsonPath("$.count").value(0),
                        jsonPath("$.results").isEmpty()
                );
    }

    @Test
    public void givenCommentList_whenExitAd_NonAuthorizedUser() throws Exception {
        initialData.addUserToBase();
        initialData.addAdsByUser();
        int adId = initialData.addCommentByAd().getAd().getId();
        mockMvc.perform(get("/ads/{id}/comments", adId + 1)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void createComment_whenGetAdId_thenReturnComment() throws Exception {
        User user = initialData.addUserToBase();
        initialData.addAdsByUser();
        int adId = initialData.addCommentByAd().getAd().getId();
        JSONObject newComment = new JSONObject();
        newComment.put("text", "bla-bla-bla");
        mockMvc.perform(post("/ads/{id}/comments", adId)
                        .with(user(user))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(newComment.toString()))
                .andExpect(status().isOk());
    }

    @Test
    void createComment_whenNonAd_thenReturnAdNotFound() throws Exception {
        User user = initialData.addUserToBase();
        initialData.addAdsByUser();
        int adId = initialData.addCommentByAd().getAd().getId() + 1;
        JSONObject newComment = new JSONObject();
        newComment.put("text", "ha-ha-ha");
        mockMvc.perform(post("/ads/{id}/comments", adId)
                        .with(user(user))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(newComment.toString()))
                .andExpect(status().isNotFound());
    }

    @Test
    void createComment_whenOtherUser_thenReturnComment() throws Exception {
        User otherUser = initialData.addRandomUserToBase();
        initialData.addUserToBase();
        initialData.addAdsByUser();
        int adId = initialData.addCommentByAd().getAd().getId();
        JSONObject newComment = new JSONObject();
        newComment.put("text", "ha-ha-ha");
        mockMvc.perform(post("/ads/{id}/comments", adId)
                        .with(user(otherUser))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(newComment.toString()))
                .andExpect(status().isOk());
    }

    @Test
    void createComment_whenNonAuthorizedUser() throws Exception {
        initialData.addUserToBase();
        initialData.addAdsByUser();
        int adId = initialData.addCommentByAd().getAd().getId();
        JSONObject newComment = new JSONObject();
        newComment.put("text", "ha-ha-ha");
        mockMvc.perform(post("/ads/{id}/comments", adId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(newComment.toString()))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void deleteComment_whenGetAdId() throws Exception {
        User user = initialData.addUserToBase();
        initialData.addAdsByUser();
        int adId = initialData.addCommentByAd().getAd().getId();
        int commentId = commentRepository.findFirstCommentId(adId);
        mockMvc.perform(delete("/ads/{adId}/comments/{commentId}", adId, commentId)
                        .with(user(user)))
                .andExpect(status().isOk());
    }

    @Test
    void deleteComment_whenGetAdIdNonComment_thenReturnCommentNotFound() throws Exception {
        User user = initialData.addUserToBase();
        initialData.addAdsByUser();
        int adId = initialData.addCommentByAd().getAd().getId();
        int commentId = initialData.addCommentByAd().getId();
        mockMvc.perform(delete("/ads/{adId}/comments/{commentId}", adId, commentId)
                        .with(user(user)))
                .andExpect(status().isNotFound());
    }

    @Test
    void deleteComment_whenOtherUser_thenReturnForbidden() throws Exception {
        initialData.addUserToBase();
        initialData.addAdsByUser();
        User otherUser = initialData.addRandomUserToBase();
        int adId = initialData.addCommentByAd().getAd().getId();
        int commentId = commentRepository.findFirstCommentId(adId);
        mockMvc.perform(delete("/ads/{adId}/comments/{commentId}", adId, commentId)
                        .with(user(otherUser)))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(username = "user@mail.ru", roles = "Admin", password = "11111111")
    void deleteComment_whenAdmin() throws Exception {
        initialData.addUserToBase();
        initialData.addAdsByUser();
        int adId = initialData.addCommentByAd().getAd().getId();
        int commentId = commentRepository.findFirstCommentId(adId);
        mockMvc.perform(delete("/ads/{adId}/comments/{commentId}", adId, commentId))
                .andExpect(status().isOk());
    }

    @Test
    void deleteComment_whenExitComment_NonAuthorizedUser() throws Exception {
        initialData.addUserToBase();
        initialData.addAdsByUser();
        int adId = initialData.addCommentByAd().getAd().getId();
        int commentId = commentRepository.findFirstCommentId(adId);
        mockMvc.perform(delete("/ads/{adId}/comments/{commentId}", adId, commentId))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void updateComment_whenGetAdId_thenReturnUpdateComment() throws Exception {
        User user = initialData.addUserToBase();
        initialData.addAdsByUser();
        int adId = initialData.addCommentByAd().getAd().getId();
        int commentId = commentRepository.findFirstCommentId(adId);
        JSONObject updateComment = new JSONObject();
        updateComment.put("text", "new worlds");
        mockMvc.perform(patch("/ads/{adId}/comments/{commentId}", adId, commentId)
                        .with(user(user))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(updateComment.toString()))
                .andExpectAll(
                        status().isOk(),
                        jsonPath("$.text").value("new worlds")
                );
    }

    @Test
    void updateComment_whenGetAdIdNonComment_thenReturnCommentNotFound() throws Exception {
        User user = initialData.addUserToBase();
        initialData.addAdsByUser();
        int adId = initialData.addCommentByAd().getAd().getId();
        int commentId = initialData.addCommentByAd().getId();
        JSONObject updateComment = new JSONObject();
        updateComment.put("text", "new worlds");
        mockMvc.perform(patch("/ads/{adId}/comments/{commentId}", adId, commentId)
                        .with(user(user))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(updateComment.toString()))
                .andExpect(status().isNotFound());
    }

    @Test
    void updateComment_whenOtherUser_thenReturnForbidden() throws Exception {
        initialData.addUserToBase();
        initialData.addAdsByUser();
        User otherUser = initialData.addRandomUserToBase();
        int adId = initialData.addCommentByAd().getAd().getId();
        int commentId = commentRepository.findFirstCommentId(adId);
        JSONObject updateComment = new JSONObject();
        updateComment.put("text", "new worlds");
        mockMvc.perform(patch("/ads/{adId}/comments/{commentId}", adId, commentId)
                        .with(user(otherUser))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(updateComment.toString()))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(username = "user@mail.ru", roles = "Admin", password = "11111111")
    void updateComment_whenAdmin_thenReturnUpdateComment() throws Exception {
        initialData.addUserToBase();
        initialData.addAdsByUser();
        int adId = initialData.addCommentByAd().getAd().getId();
        int commentId = commentRepository.findFirstCommentId(adId);
        JSONObject updateComment = new JSONObject();
        updateComment.put("text", "new worlds1");
        mockMvc.perform(patch("/ads/{adId}/comments/{commentId}", adId, commentId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(updateComment.toString()))
                .andExpectAll(
                        status().isOk(),
                        jsonPath("$.text").value("new worlds1")
                );
    }

    @Test
    void updateComment_whenExitComment_NonAuthorizedUser() throws Exception {
        initialData.addUserToBase();
        initialData.addAdsByUser();
        int adId = initialData.addCommentByAd().getAd().getId();
        int commentId = commentRepository.findFirstCommentId(adId);
        JSONObject updateComment = new JSONObject();
        updateComment.put("text", "new worlds");
        mockMvc.perform(patch("/ads/{adId}/comments/{commentId}", adId, commentId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(updateComment.toString()))
                .andExpect(status().isUnauthorized());
    }
}
