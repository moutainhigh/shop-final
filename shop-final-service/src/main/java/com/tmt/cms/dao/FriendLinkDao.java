package com.tmt.cms.dao; 

import org.springframework.stereotype.Repository;

import com.tmt.cms.entity.FriendLink;
import com.tmt.core.persistence.BaseDaoImpl;

/**
 * 友情链接 管理
 * @author 超级管理员
 * @date 2016-07-20
 */
@Repository("cmsFriendLinkDao")
public class FriendLinkDao extends BaseDaoImpl<FriendLink,Long> {}
