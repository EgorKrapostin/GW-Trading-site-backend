package ru.skypro.homework.constants;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import ru.skypro.homework.dto.authdto.Role;
import ru.skypro.homework.entity.Ad;
import ru.skypro.homework.entity.Comment;
import ru.skypro.homework.entity.User;
import ru.skypro.homework.repository.AdRepository;
import ru.skypro.homework.repository.CommentRepository;
import ru.skypro.homework.repository.UserRepository;

import java.util.Optional;

@Component
@AllArgsConstructor
public class InitialData {
    private final UserRepository userRepository;
    private final AdRepository adRepository;
    private final CommentRepository commentRepository;

    public User addUserToBase() {
        User user = new User();
        user.setFirstName("User");
        user.setLastName("User");
        user.setPhone("+7 222-22-22");
        user.setImage(null);
        user.setEmail("user@mail.ru");
        user.setPassword("$2a$10$9pYPYnYvQ0YLDTwzOkIoIeyTO2SDWe9d2Nj5kv8mss0hDKnpK2hX6");
        user.setUsername("user@mail.ru");
        user.setRole(Role.USER);
        return userRepository.save(user);
    }

    public User addRandomUserToBase() {
        User randomUser = new User();
        randomUser.setFirstName("RandomUser");
        randomUser.setLastName("RandomUser");
        randomUser.setPhone("+7 333-33-33");
        randomUser.setImage(null);
        randomUser.setEmail("randomUser@mail.ru");
        randomUser.setPassword("$2a$10$E9B/bUNifhkGrFbjvM7tpO.FLq8.JTr2u5PbxrIpfXjWS.w5Qu9si");
        randomUser.setUsername("randomUser@mail.ru");
        randomUser.setRole(Role.USER);
        return userRepository.save(randomUser);
    }

    public Optional<User> getAuthorizedUser() {
        return userRepository.findUserByEmail("user@mail.ru");
    }

    public Optional<User> getAuthorizedAdmin() {
        return userRepository.findUserByEmail("admin@mail.ru");
    }

    public Ad addAdsByUser() {
        Ad ad1ByUser = new Ad();
        ad1ByUser.setAuthor(getAuthorizedUser().orElseThrow());
        ad1ByUser.setTitle("Сумка");
        ad1ByUser.setDescription("Кожаная сумка на ремне");
        ad1ByUser.setPrice(500);
        return adRepository.save(ad1ByUser);
    }

    public Comment addCommentByAd() {
        Comment commentAd = new Comment();
        commentAd.setAuthor(getAuthorizedUser().orElseThrow());
        //userRepository.findFirstByEmail(addUserToBase().getUsername()).orElseThrow());
        commentAd.setAd(adRepository.findById(addAdsByUser().getId()).orElseThrow());
        commentAd.setText("Отличная сумка");
        return commentRepository.save(commentAd);
    }

    public User addAdminToBase() {
        User admin = new User();
        admin.setFirstName("Admin");
        admin.setLastName("Admin");
        admin.setPhone("+7 111-11-11");
        admin.setImage(null);
        admin.setEmail("user@mail.ru");
        admin.setPassword("$2a$10$zNTYBk4i9Lq2zMFKyCJTWO6jS9yJ3dwPN8eEsMxGJVS3LkCntijam");
        admin.setUsername("admin@mail.ru");
        admin.setRole(Role.ADMIN);
        return userRepository.save(admin);
    }
}