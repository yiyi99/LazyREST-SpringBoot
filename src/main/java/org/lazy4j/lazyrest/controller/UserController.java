package org.lazy4j.lazyrest.controller;

import org.lazy4j.lazyrest.dao.UserRepository;
import org.lazy4j.lazyrest.entity.UserEntity;
import org.lazy4j.lazyrest.plugin.RedisUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.UUID;

/**
 * @Author：Melon
 * @Date：2017/10/19
 * @Time：下午10:33
 */

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RedisUtils redisUtils;


    /**
     * 分页查找
     *
     * @param pageable
     * @return
     */
    @RequestMapping("/list")
    public Page<UserEntity> list(@PageableDefault(sort = {"id"}, direction = Sort.Direction.DESC)
                                         Pageable pageable) {
        //new PageRequest(page, 1, new Sort(Sort.Direction.DESC, "id"))
        Page<UserEntity> users = userRepository.findAll(pageable);
        return users;
    }

    /**
     * find one
     *
     * @param userEntity
     * @return
     */
    @GetMapping
    @RequestMapping("/find")
    public UserEntity find(UserEntity userEntity) {
        return userRepository.findByUsername(userEntity.getUsername());
    }


    /**
     * cache by redis
     *
     * @param userEntity
     * @return
     */
    @RequestMapping("/cache")
    public UserEntity cache(UserEntity userEntity) {
        redisUtils.set("userEntity", userRepository.findByUsername(userEntity.getUsername()));
        UserEntity user = (UserEntity) redisUtils.get("userEntity");
        return user;
    }

    /**
     * cache by redis autokey
     *
     * @return
     */

    @RequestMapping("/get")
    @Cacheable(value = "user-key")
    public UserEntity getUser() {
        UserEntity user = userRepository.findByUsername("melon");
        return user;
    }

    /**
     * spring session
     *
     * @param session
     * @return
     */
    @RequestMapping("/session")
    public String sessionId(HttpSession session) {
        UUID uid = (UUID) session.getAttribute("uid");
        if (uid == null) {
            uid = UUID.randomUUID();
        }
        session.setAttribute("uid", uid);
        return session.getId();
    }
}
