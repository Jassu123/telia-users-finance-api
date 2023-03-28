package com.telia.integration;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.telia.dto.UserDto;
import com.telia.entity.User;
import com.telia.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class UserControllerITests {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ObjectMapper objectMapper;
    @BeforeEach
    void setup(){
        userRepository.deleteAll();
    }

    @Test
    public void givenUserObject_whenCreateUser_thenReturnSavedUser() throws Exception{

        // given - precondition or setup
        UserDto user = UserDto.builder()
                .personalNumber(19920605008l)
                .phoneNumber("0764550738")
                .emailAddress("siva.nadupuru@gmail.com")
                .dateOfBirth(LocalDate.of(1992, 3, 12))
                .fullName("Siva Nadupuru")
                .build();
        DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE;
        String formattedDate = formatter.format(user.getDateOfBirth());

        // when - action or behaviour that we are going test
        ResultActions response = mockMvc.perform(post("/api/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(user)));

        // then - verify the result or output using assert statements
        response.andDo(print()).
                andExpect(status().isCreated())
                .andExpect(jsonPath("$.personalNumber",
                        is(user.getPersonalNumber())))
                .andExpect(jsonPath("$.fullName",
                        is(user.getFullName())))
                .andExpect(jsonPath("$.dateOfBirth",
                        is(formattedDate)))
                .andExpect(jsonPath("$.emailAddress",
                        is(user.getEmailAddress())))
        .andExpect(jsonPath("$.phoneNumber",
                is(user.getPhoneNumber())));

    }

    // JUnit test for Get All users REST API
    @Test
    public void givenListOfUsers_whenGetAllUsers_thenReturnUsersList() throws Exception{
        // given - precondition or setup
        List<User> listOfUsers = new ArrayList<User>();
        listOfUsers.add(User.builder().fullName("Siva Nadupuru").dateOfBirth(LocalDate.of(1992, 3, 12)).personalNumber(199206050018l).emailAddress("siva.nadupuru@gmail.com").build());
        listOfUsers.add(User.builder().fullName("Tony Richard").dateOfBirth(LocalDate.of(1992, 3, 12)).personalNumber(199306050018L).emailAddress("tony.richard@gmail.com").build());
        userRepository.saveAll(listOfUsers);
        // when -  action or the behaviour that we are going test
        ResultActions response = mockMvc.perform(get("/api/users"));
        // then - verify the output
        response.andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.size()",
                        is(listOfUsers.size())));
    }
    // JUnit test for update user REST API - positive scenario
    @Test
    public void givenUpdatedUser_whenUpdateUser_thenReturnUpdateUserObject() throws Exception{
        // given - precondition or setup
        User savedUser = User.builder()
                .personalNumber(199006050018L)
                .fullName("Denis Lean")
                .emailAddress("denis.leal@gmail.com")
                .dateOfBirth(LocalDate.of(1992, 3, 12))
                .phoneNumber("9553069897")
                .build();
        userRepository.save(savedUser);
        User updatedUser = User.builder()
                .personalNumber(199006050018L)
                .fullName("Denis Lean")
                .emailAddress("denis.leal@gmail.com")
                .dateOfBirth(LocalDate.of(1992, 3, 12))
                .phoneNumber("9553069899")
                .build();
        // when -  action or the behaviour that we are going test
        ResultActions response = mockMvc.perform(put("/api/users/{id}", savedUser.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updatedUser)));

        DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE;
        String formattedDate = formatter.format(updatedUser.getDateOfBirth());
        // then - verify the output
        response.andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.phoneNumber", is(updatedUser.getPhoneNumber())))
                .andExpect(jsonPath("$.fullName", is(updatedUser.getFullName())))
                .andExpect(jsonPath("$.personalNumber", is(updatedUser.getPersonalNumber())))
                 .andExpect(jsonPath("$.dateOfBirth", is(formattedDate)))
                .andExpect(jsonPath("$.emailAddress", is(updatedUser.getEmailAddress())));

    }
    // JUnit test for delete user REST API
    @Test
    public void givenUserId_whenDeleteUser_thenReturn200() throws Exception{
        // given - precondition or setup
        long userid = 1L;
        User savedUser = User.builder()
                .personalNumber(199006050018L)
                .fullName("Denis Lean")
                .emailAddress("denis.leal@gmail.com")
                .dateOfBirth(LocalDate.of(1992, 3, 12))
                .phoneNumber("9553069897")
                .build();
        userRepository.save(savedUser);
        // when -  action or the behaviour that we are going test
        ResultActions response = mockMvc.perform(delete("/api/users/{id}", savedUser.getId()));

        // then - verify the output
        response.andExpect(status().isOk())
                .andDo(print());
    }

}
