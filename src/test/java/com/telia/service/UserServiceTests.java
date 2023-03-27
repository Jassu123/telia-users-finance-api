package com.telia.service;
import com.telia.dto.UserDto;
import com.telia.entity.User;
import com.telia.repository.UserRepository;
import com.telia.service.impl.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.assertj.core.api.Assertions.assertThat;
import java.util.List;
import java.util.Optional;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class UserServiceTests {
    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserServiceImpl userService;

    private User user;
    private User user2;

    private UserDto userDto;
    private List<User> usersList;


    @BeforeEach
    public void setup(){
        user = User.builder()
                .id(1L)
                .personalNumber(199006050018L)
                .fullName("Denis Lean")
                .emailAddress("denis.leal@gmail.com")
                .dateOfBirth("12131992")
                .phoneNumber("9553069897")
                .build();
        user2 = User.builder()
                .id(2L)
                .personalNumber(199206010023L)
                .fullName("John Doe")
                .emailAddress("john.doe@gmail.com")
                .dateOfBirth("07081990")
                .phoneNumber("9553069898")
                .build();
        usersList = List.of(user, user2);

        userDto = UserDto.builder()
                .id(1L)
                .personalNumber(199006050018L)
                .fullName("Denis Lean")
                .emailAddress("denis.leal.updated@gmail.com")
                .dateOfBirth("12131992")
                .phoneNumber("9553069897")
                .build();
    }


    @DisplayName("JUnit test for save User method")
    @ParameterizedTest
    @CsvSource({
            "1, 199006050018, 'Denis Lean', 'denis.leal@gmail.com', '12131992', '9553069897'",
            "2, 199206010023, 'John Doe', 'john.doe@gmail.com', '07081990', '9553069898'"
    })
    public void givenUserObject_whenSaveUser_thenReturnUserObject(
            Long id, Long personalNumber, String fullName, String emailAddress, String dateOfBirth, String phoneNumber) {
        // given - precondition or setup
        UserDto userDto = UserDto.builder()
                .id(id)
                .personalNumber(personalNumber)
                .fullName(fullName)
                .emailAddress(emailAddress)
                .dateOfBirth(dateOfBirth)
                .phoneNumber(phoneNumber)
                .build();
        given(userRepository.findByPersonalNumber(userDto.getPersonalNumber())).willReturn(Optional.empty());
        given(userRepository.save(any(User.class))).willAnswer(invocation -> invocation.getArgument(0));

        // when - execute the method under test
        UserDto savedUser = userService.createUser(userDto);

        // then - verify the output
        assertThat(savedUser).isNotNull();
        assertThat(savedUser.getId()).isEqualTo(id);
        assertThat(savedUser.getPersonalNumber()).isEqualTo(personalNumber);
        assertThat(savedUser.getFullName()).isEqualTo(fullName);
        assertThat(savedUser.getEmailAddress()).isEqualTo(emailAddress);
        assertThat(savedUser.getDateOfBirth()).isEqualTo(dateOfBirth);
        assertThat(savedUser.getPhoneNumber()).isEqualTo(phoneNumber);
    }
    @Test
    @DisplayName("JUnit test for all users method")
    public void givenUsersList_whenGetAllUsers_thenReturnUsersList() {
        // given - precondition or setup
        given(userRepository.findAll()).willReturn(usersList);

        // when -  action or the behaviour that we are going test
        List<UserDto> userList = userService.getAllUsers();

        // then - verify the output
        assertThat(userList).isNotNull();
        assertThat(userList.size()).isEqualTo(usersList.size());
    }
    @Test
    @DisplayName("JUnit test for update users method")
    public void givenUser_whenUpdateUser_thenReturnUpdatedUser() {
        // given - precondition or setup
        given(userRepository.findById(user.getId())).willReturn(Optional.of(user));
        given(userRepository.save(user)).willReturn(user);

        // when - action or the behavior that we are going to test
        UserDto updatedUser = userService.updateUser(userDto);

        // then - verify the output
        assertThat(updatedUser).isNotNull();
        assertThat(updatedUser.getEmailAddress()).isEqualTo(userDto.getEmailAddress());
    }

    @Test
    @DisplayName("JUnit test for deleteUser method")
    public void givenUserId_whenDeleteUser_thenUserShouldBeDeleted() {
        // given - precondition or setup
        Long userId = 1L;
        User user = User.builder()
                .id(userId)
                .personalNumber(199006050018L)
                .fullName("Denis Lean")
                .emailAddress("denis.leal@gmail.com")
                .dateOfBirth("12131992")
                .phoneNumber("9553069897")
                .build();
        given(userRepository.findById(userId)).willReturn(Optional.of(user));

        // when - action or the behavior that we are going to test
        userService.deleteUser(userId);

        // then - verify the output
        verify(userRepository, times(1)).deleteById(userId);
    }

}
