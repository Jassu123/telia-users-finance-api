package com.telia.controller;
import com.telia.dto.UserDisplayDto;
import com.telia.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import com.telia.dto.UserDto;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Tag(
        name = "CRUD REST APIs for User Resource",
        description = "CRUD REST APIs - Create User, Update User, Get User, Get All Users, Delete User"
)
@RestController
@AllArgsConstructor
@Validated
@RequestMapping("api/users")
public class UserController {

    private UserService userService;

    @Operation(
            summary = "Create User REST API",
            description = "Create User REST API is used to save user information in a database"
    )
    @ApiResponse(
            responseCode = "201",
            description = "HTTP Status 201 CREATED"
    )
    // build create User REST API
    @PostMapping
    public ResponseEntity<UserDto> createUser(@Valid @RequestBody UserDto user){
        UserDto savedUser = userService.createUser(user);
        return new ResponseEntity<>(savedUser, HttpStatus.CREATED);
    }

    @Operation(
            summary = "Get User By ID REST API",
            description = "Get User By ID REST API is used to get a single user from the database"
    )
    @ApiResponse(
            responseCode = "200",
            description = "HTTP Status 200 SUCCESS"
    )
    // build get user by id REST API
    // http://localhost:8080/api/users/1
    @GetMapping("{id}")
    public ResponseEntity<Optional<UserDto>> getUserById(@PathVariable("id") Long userId){
        Optional<UserDto> user = userService.getUserById(userId);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @Operation(
            summary = "Get All Users REST API",
            description = "Get All Users REST API is used to get a all the users from the database"
    )
    @ApiResponse(
            responseCode = "200",
            description = "HTTP Status 200 SUCCESS"
    )
    // Build Get All Users REST API
    // http://localhost:8080/api/users
    @GetMapping
    public ResponseEntity<List<UserDisplayDto>> getAllUsers(){
        List<UserDto> users = userService.getAllUsers();
       List<UserDisplayDto> userDisplayDtoList= users.stream()
                .map(user -> new UserDisplayDto(
                        user.getId(),
                        user.getPersonalNumber(),
                        user.getFullName(),
                        user.getDateOfBirth()))
                .collect(Collectors.toList());
        return new ResponseEntity<>(userDisplayDtoList, HttpStatus.OK);
    }
    @Operation(
            summary = "Build Get All Users REST API  with fullname and personal number",
            description = "Build Get All Users REST API  with fullname and personal number from the database"
    )
    @ApiResponse(
            responseCode = "200",
            description = "HTTP Status 200 SUCCESS"
    )
    // Build Get All Users REST API  with fullname and personal number
    // http://localhost:8080/api/users/getbynameandpersonalnumber?name=Siva Chkra&personalNumber=199406050030&sortDirection=ASC&sortField=fullName

    @GetMapping("/getbynameandpersonalnumber")
    public ResponseEntity<List<UserDto>> getUsersByNameAndPersonalNumber(
            @RequestParam(name = "name") String name,
            @RequestParam(name = "personalNumber", required = false) Long personalNumber,
            @RequestParam(name = "sortDirection", defaultValue = "ASC") Sort.Direction sortDirection,
            @RequestParam(name = "sortField", defaultValue = "fullName") String sortField) {
        List<UserDto> userDtos = userService.getUsersByNameAndPersonalNumber(name, personalNumber, sortDirection, sortField);
        return new ResponseEntity<>(userDtos, HttpStatus.OK);
    }
  @Operation(
            summary = "Update User REST API",
            description = "Update User REST API is used to update a particular user in the database"
    )
    @ApiResponse(
            responseCode = "200",
            description = "HTTP Status 200 SUCCESS"
    )
    // Build Update User REST API
    @PutMapping("{id}")
    // http://localhost:8080/api/users/1
    public ResponseEntity<UserDto> updateUser(@PathVariable("id") Long userId,
                                           @RequestBody @Valid UserDto user){
        user.setId(userId);
        UserDto updatedUser = userService.updateUser(user);
        return new ResponseEntity<>(updatedUser, HttpStatus.OK);
    }

    @Operation(
            summary = "Delete User REST API",
            description = "Delete User REST API is used to delete a particular user from the database"
    )
    @ApiResponse(
            responseCode = "200",
            description = "HTTP Status 200 SUCCESS"
    )
    // Build Delete User REST API
    @DeleteMapping("{id}")
    public ResponseEntity<String> deleteUser(@PathVariable("id") Long userId){
        userService.deleteUser(userId);
        return new ResponseEntity<>("User successfully deleted!", HttpStatus.OK);
    }

}
