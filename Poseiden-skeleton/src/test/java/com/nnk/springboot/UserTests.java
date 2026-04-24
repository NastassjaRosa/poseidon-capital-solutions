package com.nnk.springboot;

import com.nnk.springboot.domain.User;
import com.nnk.springboot.repositories.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

@SpringBootTest
class UserTests {

    @Autowired
    private UserRepository userRepository;

    @Test
    void userTest() {
        // Le mot de passe respecte la contrainte : 8 car min, 1 maj, 1 chiffre, 1 symbole
        User user = new User("userTest", "Test1234!", "User Test", "USER");

        // Save
        user = userRepository.save(user);
        Assertions.assertNotNull(user.getId());

        // Update
        user.setUsername("userUpdate");
        user = userRepository.save(user);
        Assertions.assertEquals("userUpdate", user.getUsername());

        // Find
        List<User> list = userRepository.findAll();
        Assertions.assertFalse(list.isEmpty());

        // Delete
        Integer id = user.getId();
        userRepository.delete(user);
        Optional<User> deleted = userRepository.findById(id);
        Assertions.assertFalse(deleted.isPresent());
    }
}