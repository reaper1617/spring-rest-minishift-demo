package com.nexign.restdemo.service.api;

import com.nexign.restdemo.model.PagingDto;
import com.nexign.restdemo.persistence.entity.User;
import lombok.NonNull;
import org.springframework.data.domain.Page;

import java.util.Optional;

public interface UserService {

    Page<User> get(@NonNull PagingDto paging);

    Optional<User> create(@NonNull User user);

    Optional<User> update(@NonNull User user);

    void delete(Integer id);
}
