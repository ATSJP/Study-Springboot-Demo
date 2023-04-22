package com.example.cacheredis.dto;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

/**
 * @author atsjp
 */
public class User2DTO implements Serializable {
    private static final long serialVersionUID = -4995245099314944364L;
    private Long id;
    private String name;
    private Long[] roleIds;
    private List<Long> resources;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long[] getRoleIds() {
        return roleIds;
    }

    public void setRoleIds(Long[] roleIds) {
        this.roleIds = roleIds;
    }

    public List<Long> getResources() {
        return resources;
    }

    public void setResources(List<Long> resources) {
        this.resources = resources;
    }

    @Override
    public String toString() {
        return "UserDTO{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", roleIds=" + Arrays.toString(roleIds) +
                ", resources=" + resources +
                '}';
    }
}


