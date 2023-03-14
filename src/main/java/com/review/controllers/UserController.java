package com.review.controllers;

import com.review.domain.dtos.UserDTO;
import com.review.services.UserService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private UserService service;

    public UserController(UserService service) {
        this.service = service;
    }

    @PostMapping
    @ApiOperation("Insert a new user")
    @ApiResponses(@ApiResponse(code = 201, message = "User created successfully"))
    public ResponseEntity<UserDTO> insert(@RequestBody @ApiParam("User data") UserDTO dto) {
        dto = service.insertUser(dto);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(dto.getId()).toUri();
        return ResponseEntity.created(uri).body(dto);
    }

    @GetMapping
    @ApiOperation("Returns a list of users")
    @ApiResponses(@ApiResponse(code = 200, message = "List returned successfully"))
    public ResponseEntity<List<UserDTO>> getAll() {
        var list = service.getAllUsers();
        return ResponseEntity.ok().body(list);
    }

    @GetMapping("/{id}")
    @ApiOperation("Returns a user by Id")
    @ApiResponses({
            @ApiResponse(code = 200, message = "User returned successfully"),
            @ApiResponse(code = 404, message = "User not found by Id")
    })
    public ResponseEntity<UserDTO> getById(@PathVariable @ApiParam("User Id") Long id) {
        var obj = service.getUserById(id);
        return ResponseEntity.ok().body(obj);
    }

    @PutMapping("/{id}")
    @ApiOperation("Update a user by Id")
    @ApiResponses({
            @ApiResponse(code = 200, message = "User updated successfully"),
            @ApiResponse(code = 400, message = "One or more parameters are missing"),
            @ApiResponse(code = 404, message = "User not found by Id")
    })
    public ResponseEntity<UserDTO> update(@PathVariable @ApiParam("User Id") Long id,
                                          @RequestBody @ApiParam("User data") UserDTO dto) {
        dto = service.updateUser(id, dto);
        return ResponseEntity.ok().body(dto);
    }

    @DeleteMapping("/{id}")
    @ApiOperation("Delete a user by Id")
    @ApiResponses({
            @ApiResponse(code = 204, message = "User deleted successfully"),
            @ApiResponse(code = 404, message = "User not found by Id")
    })
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.deleteUser(id);
        return ResponseEntity.noContent().build();
    }

}
