package com.example.es.service;

import com.example.es.dao.UserDao;
import com.example.es.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * @author atsjp
 * @date 2022/4/23 17:43
 */
@Service
public class UserService {
    @Autowired
    private UserDao userDao;

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

    public List<User> getListByName(String name) {
        return userDao.findByName(name);
    }

    public List<User> getListByNameAndAge(String name, Integer age) {
        return userDao.findByNameAndAge(name, age);
    }

    public List<User> getListOrderByAgeDesc() {
        return userDao.findByOrderByAgeDesc();
    }

    public List<User> getListOrderByAgeAsc() {
        return userDao.findByOrderByAgeAsc();
    }

    public Page<User> getPage(int pageNum, int pageSize) {
        // 注意，这里的pageNum是从0开始的，如果要查询第1页数据，pageNum应该为0
        Pageable pageable = PageRequest.of(pageNum, pageSize);
        return userDao.findAll(pageable);
    }

    public List<User> getListGreaterThanAge(Integer age) {
        return userDao.findByAgeGreaterThan(age);
    }

    public List<User> getListGreaterThanEqualAge(Integer age) {
        return userDao.findByAgeGreaterThanEqual(age);
    }

    public List<User> getListLessThanAge(Integer age) {
        return userDao.findByAgeLessThan(age);
    }

    public List<User> getListLessThanEqualAge(Integer age) {
        return userDao.findByAgeLessThanEqual(age);
    }
}
