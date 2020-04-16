package com.nexign.restdemo.persistence.repository;

import com.nexign.restdemo.persistence.entity.User;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface UserRepository extends PagingAndSortingRepository<User, Integer> {
}
