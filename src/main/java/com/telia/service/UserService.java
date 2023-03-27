package com.telia.service;

import com.telia.dto.UserDto;
import org.springframework.data.domain.Sort;

import java.util.List;
import java.util.Optional;

public interface UserService {
    UserDto createUser(UserDto user);

    Optional<UserDto> getUserById(Long userId);

    List<UserDto> getAllUsers();
   // List<UserDto> getAllUsersWithPersonalnumberAndName(String name, Long personalNumber,String sortField, Sort.Direction sortDirection);
    public List<UserDto> getUsersByNameAndPersonalNumber(String name, Long personalNumber, Sort.Direction sortDirection, String sortField);

    UserDto updateUser(UserDto user);

    void deleteUser(Long userId);
}
