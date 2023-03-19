package com.fhao.service;

import com.fhao.dao.FollowingGroupDao;
import com.fhao.domin.FollowingGroup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * author: FHao
 * create time: 2023-03-19 16:01
 * description:
 */
@Service
public class FollowingGroupService {
    @Autowired
    private FollowingGroupDao followingGroupDao;

    public FollowingGroup getByType(String type){
        return followingGroupDao.getByType(type);
    }
    public FollowingGroup getById(Long id){
        return followingGroupDao.getById(id);
    }

    public List<FollowingGroup> getByUserId(Long userId) {
        return followingGroupDao.getByUserId(userId);
    }
}
