package com.telia.repository;
import static org.assertj.core.api.Assertions.assertThat;
import com.telia.entity.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@DataJpaTest
public class UserRepositoryTests {
    @Autowired
    private UserRepository userRepository;
    private User user;

    @BeforeEach
    public void setup(){

       user = User.builder()
                .personalNumber(199006050018L)
                .fullName("Denis Lean")
                .dateOfBirth(LocalDate.of(1992, 3, 12))
               .emailAddress("denis@leal.com")
                .phoneNumber("9553069897")
                .build();
    }
    // JUnit test for save user operation
    @DisplayName("JUnit test for save user operation")
    @Test
    public void givenUserObject_whenSave_thenReturnSavedUser(){

        //given - precondition or setup
        User user = User.builder()
                .personalNumber(1995006050018L)
                .fullName("Syam Padamata")
                .dateOfBirth(LocalDate.of(1992, 3, 12))
                .emailAddress("syam@padamata.com")
                .phoneNumber("95530698978")
                .build();
        // when - action or the behaviour that we are going test
        User savedUser = userRepository.save(user);

        // then - verify the output
        assertThat(savedUser).isNotNull();
        assertThat(savedUser.getId()).isGreaterThan(0);
    }
    // JUnit test for get all users operation
    @DisplayName("JUnit test for get all users operation")
    @Test
    public void givenUsersList_whenFindAll_thenUsersList(){

        User user = User.builder()
                .personalNumber(1995006050018L)
                .fullName("Per John")
                .dateOfBirth(LocalDate.of(1992, 3, 12))
                .emailAddress("per@John.com")
                .phoneNumber("95530698978")
                .build();

        userRepository.save(user);

        // when -  action or the behaviour that we are going test
        List<User> userList = userRepository.findAll();

        // then - verify the output
        assertThat(userList).isNotNull();
        assertThat(userList.size()).isEqualTo(1);

    }
    // JUnit test for update user operation
    @DisplayName("JUnit test for update user operation")
    @Test
    public void givenUserObject_whenUpdateUser_thenReturnUpdatedUser(){
        // given - precondition or setup
        User user = User.builder()
                .personalNumber(1995006050018L)
                .fullName("Denis Lean1")
                .dateOfBirth(LocalDate.of(1992, 3, 12))
                .emailAddress("denis@leal.com")
                .phoneNumber("95530698978")
                .build();
        userRepository.save(user);

        // when -  action or the behaviour that we are going test
        User savedUser = userRepository.findById(user.getId()).get();
             savedUser.setPhoneNumber("9553069897");
             savedUser.setFullName("Ram");
        User updatedUser =  userRepository.save(savedUser);

        // then - verify the output
        assertThat(updatedUser.getPhoneNumber()).isEqualTo("9553069897");
        assertThat(updatedUser.getFullName()).isEqualTo("Ram");
    }

    // JUnit test for delete user operation
    @DisplayName("JUnit test for delete user operation")
    @Test
    public void givenUserObject_whenDelete_thenRemoveUser(){
        // given - precondition or setup
        User user = User.builder()
                .personalNumber(1995006050018L)
                .fullName("Denis Lean1")
                .dateOfBirth(LocalDate.of(1992, 3, 12)
                )
                .emailAddress("denis@leal.com")
                .phoneNumber("95530698978")
                .build();
        userRepository.save(user);

        // when -  action or the behaviour that we are going test
        userRepository.deleteById(user.getId());
        Optional<User> userOptional = userRepository.findById(user.getId());

        // then - verify the output
        assertThat(userOptional).isEmpty();
    }

}
