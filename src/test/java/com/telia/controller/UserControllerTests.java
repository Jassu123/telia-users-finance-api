package com.telia.controller;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.telia.dto.UserDto;
import com.telia.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
public class UserControllerTests {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @Autowired
    private ObjectMapper objectMapper;

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
        given(userService.createUser(any(UserDto.class)))
                .willAnswer((invocation)-> invocation.getArgument(0));

        // when - action or behaviour that we are going test
        ResultActions response = mockMvc.perform(post("/api/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(user)));
        DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE;
        String formattedDate = formatter.format(user.getDateOfBirth());

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
        List<UserDto> listOfUsers = new ArrayList<>();
        listOfUsers.add(UserDto.builder().fullName("Siva Nadupuru").dateOfBirth(LocalDate.of(1992, 3, 12)).personalNumber(199206050018l).emailAddress("siva.nadupuru@gmail.com").build());
        listOfUsers.add(UserDto.builder().fullName("Tony Richard").dateOfBirth(LocalDate.of(1992, 3, 12)).personalNumber(199306050018l).emailAddress("tony.richard@gmail.com").build());
        given(userService.getAllUsers()).willReturn(listOfUsers);
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
        long userId = 1L;
        // given - precondition or setup
        UserDto savedUser = UserDto.builder()
                .personalNumber(199006050018L)
                .fullName("Denis Leal")
                .emailAddress("denis.leal@gmail.com")
                .dateOfBirth(LocalDate.of(1992, 3, 12))
                .phoneNumber("9553069897")
                .build();
        UserDto updatedUser = UserDto.builder()
                .personalNumber(199006050018L)
                .fullName("Dan papa")
                .emailAddress("Dan.papa@gmail.com")
                .dateOfBirth(LocalDate.of(1992, 3, 12))
                .phoneNumber("9553069899")
                .build();
        given(userService.getUserById(userId)).willReturn(Optional.of(savedUser));
        given(userService.updateUser(any(UserDto.class)))
                .willAnswer((invocation)-> invocation.getArgument(0));
        // when -  action or the behaviour that we are going test
        ResultActions response = mockMvc.perform(put("/api/users/{id}", userId)
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
        willDoNothing().given(userService).deleteUser(userid);

        // when -  action or the behaviour that we are going test
        ResultActions response = mockMvc.perform(delete("/api/users/{id}", userid));

        // then - verify the output
        response.andExpect(status().isOk())
                .andDo(print());
    }

}
