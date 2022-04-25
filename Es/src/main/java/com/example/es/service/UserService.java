package com.example.es.service;

import com.example.es.dao.UserDao;
import com.example.es.entity.User;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * @author atsjp
 * @date 2022/4/23 17:43
 */
@Service
public class UserService {
    @Resource
    private UserDao userDao;
    @Resource
    private RestHighLevelClient restHighLevelClient;

    public List<User> findByName(String name) {
        return userDao.findByName(name);
    }

    public List<User> findByNameContaining(String name) {
        return userDao.findByNameContaining(name);
    }


    public List<User> findByNameAndAge(String name, Integer age) {
        return userDao.findByNameAndAge(name, age);
    }


    public List<User> findByOrderByAgeDesc() {
        return userDao.findByOrderByAgeDesc();
    }

    public List<User> findByOrderByAgeAsc() {
        return userDao.findByOrderByAgeAsc();
    }

    public List<User> findByAgeGreaterThan(Integer age) {
        return userDao.findByAgeGreaterThan(age);
    }

    public List<User> findByAgeGreaterThanEqual(Integer age) {
        return userDao.findByAgeGreaterThanEqual(age);
    }

    public List<User> findByAgeLessThan(Integer age) {
        return userDao.findByAgeLessThan(age);
    }

    public List<User> findByAgeLessThanEqual(Integer age) {
        return userDao.findByAgeLessThanEqual(age);
    }

    public List<User> findByAddressContaining(String address) {
        return userDao.findByAddressContaining(address);
    }

    public Object searchEntity(String searchContent) {
        SearchRequest searchRequest = new SearchRequest();
        RequestOptions.Builder build = RequestOptions.DEFAULT.toBuilder();
        try {
            restHighLevelClient.search(searchRequest, build.build());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Page<User> search(User user) {
        return userDao.searchSimilar(user, new String[]{"name"}, PageRequest.of(1, 2));
    }

    public void save(User user) {
        userDao.save(user);
    }

    public User getById(Long id) {
        Optional<User> find = userDao.findById(id);
        return find.orElse(null);
    }

    public void deleteById(Long id) {
        userDao.deleteById(id);
    }

    public List<User> getAll() {
        Iterable<User> users = userDao.findAll();
        List<User> userList = new ArrayList<User>();
        users.forEach(userList::add);
        return userList;
    }

    public Page<User> getPage(int pageNum, int pageSize) {
        Pageable pageable = PageRequest.of(pageNum, pageSize);
        return userDao.findAll(pageable);
    }
}
