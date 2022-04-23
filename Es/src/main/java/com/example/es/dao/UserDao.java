package com.example.es.dao;

import com.example.es.entity.User;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author atsjp
 * @date 2022/4/23 17:44
 */
@Repository
public interface UserDao extends ElasticsearchRepository<User, Long> {
    List<User> findByName(String name);

    List<User> findByNameAndAge(String name, Integer age);

    List<User> findByOrderByAgeDesc();

    List<User> findByOrderByAgeAsc();

    List<User> findByAgeGreaterThan(Integer age);

    List<User> findByAgeGreaterThanEqual(Integer age);

    List<User> findByAgeLessThan(Integer age);

    List<User> findByAgeLessThanEqual(Integer age);
}
