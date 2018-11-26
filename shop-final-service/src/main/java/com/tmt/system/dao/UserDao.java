package com.tmt.system.dao;

import org.springframework.stereotype.Repository;

import com.tmt.common.persistence.BaseDaoImpl;
import com.tmt.system.entity.User;

@Repository
public class UserDao extends BaseDaoImpl<User, Long> {}