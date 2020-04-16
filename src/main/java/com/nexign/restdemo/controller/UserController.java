package com.nexign.restdemo.controller;

import com.nexign.restdemo.model.PagingDto;
import com.nexign.restdemo.model.UserDTO;
import com.nexign.restdemo.persistence.entity.User;
import com.nexign.restdemo.persistence.entity.UserToCreate;
import com.nexign.restdemo.persistence.entity.UserToDelete;
import com.nexign.restdemo.persistence.entity.UserToUpdate;
import com.nexign.restdemo.service.api.UserService;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.atomic.AtomicReference;

@RestController
@RequestMapping("/v0")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/user")
    public ResponseEntity<Page<UserDTO>> get(@NonNull PagingDto paging) {
        return ResponseEntity.ok(userService.get(paging)
                .map(u -> UserDTO.builder()
                        .id(u.getId())
                        .name(u.getName())
                        .build()));
    }

    @PostMapping("/user")
    public ResponseEntity<UserDTO> create(@RequestBody @NonNull UserToCreate user) {
        AtomicReference<ResponseEntity<UserDTO>> ref = new AtomicReference<>();
        userService.create(User.builder()
                .name(user.getName())
                .build())
                .ifPresentOrElse(
                        u -> {
                            ref.set(ResponseEntity.status(HttpStatus.CREATED)
                                    .body(UserDTO.builder()
                                            .id(u.getId())
                                            .name(u.getName())
                                            .build()));
                        },
                        () -> {
                            ref.set(ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).build());
                        }
                );
        return ref.get();
    }

    @PutMapping("/user")
    public ResponseEntity<UserDTO> update(@RequestBody @NonNull UserToUpdate user) {
        AtomicReference<ResponseEntity<UserDTO>> ref = new AtomicReference<>();
        userService.update(User.builder()
                .id(user.getId())
                .name(user.getName())
                .build())
                .ifPresentOrElse(
                        u -> {
                            ref.set(ResponseEntity.ok(
                                    UserDTO.builder()
                                            .id(u.getId())
                                            .name(u.getName())
                                            .build())
                            );
                        },
                        () -> {
                            ref.set(ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).build());
                        }
                );
        return ref.get();
    }

    @DeleteMapping("/user")
    public void delete(@RequestBody @NonNull UserToDelete user) {
        userService.delete(user.getId());
    }

}
