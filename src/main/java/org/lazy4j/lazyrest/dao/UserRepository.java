package org.lazy4j.lazyrest.dao;

import org.lazy4j.lazyrest.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.io.Serializable;

/**
 * @Author：Melon
 * @Date：2017/10/19
 * @Time：下午10:33
 */
public interface UserRepository extends JpaRepository<UserEntity, Long>,
        JpaSpecificationExecutor<UserEntity>,
        Serializable {

     UserEntity findByUsername(String username);
}
