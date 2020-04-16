package com.nexign.restdemo.service.impl;

import com.nexign.restdemo.model.PagingDto;
import com.nexign.restdemo.persistence.entity.User;
import com.nexign.restdemo.persistence.repository.UserRepository;
import com.nexign.restdemo.service.api.UserService;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

@Slf4j
@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public Page<User> get(@NonNull PagingDto paging) {
        Pageable pageable = PageRequest.of(paging.getPageNum(), paging.getPageSize());
        return userRepository.findAll(pageable);
    }

    @Override
    public Optional<User> create(@NonNull User user) {
        return Optional.of(userRepository.save(user));
    }

    @Override
    public Optional<User> update(@NonNull User user) {
        final AtomicReference<User> userRef = new AtomicReference<>();
        userRepository.findById(user.getId()).ifPresentOrElse(
                u -> {
                    u.setName(user.getName());
                    userRef.set(userRepository.save(u));
                },
                () -> {
                    log.debug("User with id = {} not found", user.getId());
                }
        );
        return Optional.of(userRef.get());
    }

    @Override
    public void delete(Integer id) {
        userRepository.deleteById(id);
    }
}
