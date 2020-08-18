package com.escalade.victor.model;

import javax.persistence.*;

@Table(name = "user_role")
public class User_role {

    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private Integer user_id;

    @OneToOne
    @JoinColumn(name = "role_id", referencedColumnName = "id")
    private Integer role_id;

    public Integer getUser_id() {
        return user_id;
    }

    public void setUser_id(Integer user_id) {
        this.user_id = user_id;
    }

    public Integer getRole_id() {
        return role_id;
    }

    public void setRole_id(Integer role_id) {
        this.role_id = role_id;
    }
}
