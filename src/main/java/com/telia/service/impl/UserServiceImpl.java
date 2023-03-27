package com.telia.service.impl;
import com.telia.exception.PersonalNumberExistsException;
import com.telia.exception.ResourceNotFoundException;
import lombok.AllArgsConstructor;
import com.telia.dto.UserDto;
import com.telia.entity.User;
import com.telia.mapper.AutoUserMapper;
import com.telia.repository.UserRepository;
import com.telia.service.UserService;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {
    private UserRepository userRepository;
    @Override
    public UserDto
    createUser(UserDto userDto) {
      Optional<User> optionalUser = userRepository.findByPersonalNumber(userDto.getPersonalNumber());
        if(optionalUser.isPresent()){
            throw new PersonalNumberExistsException("PersonalNumber Already Exists for User");
        }
        User user = AutoUserMapper.MAPPER.mapToUser(userDto);
        User savedUser = userRepository.save(user);
        UserDto savedUserDto = AutoUserMapper.MAPPER.mapToUserDto(savedUser);
        return savedUserDto;
    }

    @Override
    public Optional<UserDto> getUserById(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(
                () -> new ResourceNotFoundException("User", "id", userId)
        );
        return Optional.ofNullable(AutoUserMapper.MAPPER.mapToUserDto(user));
    }

    @Override
    public List<UserDto> getAllUsers() {
        List<User> users = userRepository.findAll();
        return users.stream().map((user) -> AutoUserMapper.MAPPER.mapToUserDto(user))
                .collect(Collectors.toList());
    }

    /**
     * @param name
     * @param personalNumber
     * @return
     */


    /**
     * @param name
     * @param personalNumber
     * @param sortDirection
     * @param sortField
     * @return
     */
    @Override
    public List<UserDto> getUsersByNameAndPersonalNumber(String name, Long personalNumber, Sort.Direction sortDirection, String sortField) {
        List<User> users;
        if (personalNumber != null) {
            users = userRepository.findByFullNameAndPersonalNumber(name, personalNumber, Sort.by(sortDirection, sortField));
        } else {
            users = userRepository.findByFullName(name, Sort.by(sortDirection, sortField));
        }
        return users.stream()
                .map(user -> UserDto.builder()
                        .id(user.getId())
                        .personalNumber(user.getPersonalNumber())
                        .fullName(user.getFullName())
                        .emailAddress(user.getEmailAddress())
                        .dateOfBirth(user.getDateOfBirth())
                        .phoneNumber(user.getPhoneNumber())
                        .build())
                .collect(Collectors.toList());
    }


    @Override
    public UserDto updateUser(UserDto user) {

        User existingUser = userRepository.findById(user.getId()).orElseThrow(
                () -> new ResourceNotFoundException("User", "id", user.getId())
        );

        existingUser.setPersonalNumber(user.getPersonalNumber());
        existingUser.setFullName(user.getFullName());
        existingUser.setEmailAddress(user.getEmailAddress());
        existingUser.setDateOfBirth(user.getDateOfBirth());
        existingUser.setPhoneNumber(user.getPhoneNumber());
        User updatedUser = userRepository.save(existingUser);
        return AutoUserMapper.MAPPER.mapToUserDto(updatedUser);
    }

    @Override
    public void deleteUser(Long userId) {

        User existingUser = userRepository.findById(userId).orElseThrow(
                () -> new ResourceNotFoundException("User", "id", userId)
        );
        userRepository.deleteById(userId);
    }
}
