package org.lazy4j.lazyrest.controller;

import org.lazy4j.lazyrest.dao.UserRepository;
import org.lazy4j.lazyrest.entity.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @RequestMapping("/list")
    public Page<UserEntity> list() {

        Page<UserEntity> users = userRepository.findAll(new PageRequest(0, 1, new Sort(Sort.Direction.DESC, "id")));
        return users;
    }

    @RequestMapping(value = "/test", method = RequestMethod.GET)
    public Page<UserEntity> getEntryByPageable(@PageableDefault(value = 1, sort = {"id"}, direction = Sort.Direction.DESC)
                                                       Pageable pageable) {
        return userRepository.findAll(pageable);
    }
}
