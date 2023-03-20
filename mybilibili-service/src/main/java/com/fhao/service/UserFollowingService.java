package com.fhao.service;

import com.fhao.dao.UserFollowingDao;
import com.fhao.domain.FollowingGroup;
import com.fhao.domain.User;
import com.fhao.domain.UserFollowing;
import com.fhao.domain.UserInfo;
import com.fhao.domain.constant.UserConstant;
import com.fhao.domain.exception.ConditionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * author: FHao
 * create time: 2023-03-19 15:59
 * description:
 */
@Service
public class UserFollowingService {
    @Autowired
    private UserFollowingDao userFollowingDao;
    @Autowired
    private FollowingGroupService followingGroupService;
    @Autowired
    private UserService userService;
//    @Autowired
//    private UserfollowingG
    @Transactional
    public void addUserFollowings(UserFollowing userFollowing){
        //先判断是否是已经关注的对象，如果关注分组不存在，则赋给默认分组值，否则得到用户关注的分组信息
        Long groupId = userFollowing.getGroupId();
        if (groupId == null){
            FollowingGroup followingGroup = followingGroupService.getByType(UserConstant.USER_FOLLOWING_GROUP_TYPE_DEFAULT);
            userFollowing.setGroupId(followingGroup.getId());
        }else {
            FollowingGroup followingGroup = followingGroupService.getById(groupId);
            if (followingGroup == null){
                throw new ConditionException("分组不存在！");
            }
        }
        //判断要关注的用户是否是存在的
        Long followingId = userFollowing.getFollowingId();
        User user = userService.getUserById(followingId);
        if (user == null){
            throw  new ConditionException("关注的用户不存在！");
        }
        //先将当前分组下的关注用户信息删除,然后重新创建关注信息，不是更新操作
        userFollowingDao.deleteUserFollowing(userFollowing.getUserId(), followingId);
        userFollowing.setCreateTime(new Date());
        userFollowingDao.addUserFollowing(userFollowing);
    }
    // 第一步：获取关注的用户列表
    // 第二步：根据关注用户的id查询关注用户的基本信息
    // 第三步：将关注用户按关注分组进行分类
    public List<FollowingGroup> getUserFollowings(Long userId){
        //获取关注的用户信息列表
        List<UserFollowing> userFollowings = userFollowingDao.getUserFollowings(userId);
        //得到被关注用户的id集合
        Set<Long> followingIdSet = userFollowings.stream().map(UserFollowing::getFollowingId).collect(Collectors.toSet());
        List<UserInfo> userInfoList = new ArrayList<>();
        //如果有已经被关注的用户
        if (followingIdSet.size() > 0){
            //查询被关注用户们的用户信息
        userInfoList =  userService.getUserInfoByUserIds(followingIdSet);
        }
        //遍历关注的用户们
        for (UserFollowing userFollowing : userFollowings) {
            //遍历关注的用户们的信息集合
            for (UserInfo userInfo : userInfoList) {
                //如果二者相同
                if(userFollowing.getFollowingId().equals(userInfo.getUserId())){
                    //把被关注的用户的信息保存在对象中
                userFollowing.setUserInfo(userInfo);
                }
            }
        }
        //根据用户信息，获得他的关注分组信息
        List<FollowingGroup> groupList = followingGroupService.getByUserId(userId);
        //创建一个新的分组，命名为UserConstant.USER_FOLLOWING_GROUP_ALL_NAME
        FollowingGroup allGroup = new FollowingGroup();
        allGroup.setName(UserConstant.USER_FOLLOWING_GROUP_ALL_NAME);
        //把所有被关注的用户信息赋给这个全部关注分组
        allGroup.setFollowingUserInfoList(userInfoList);
        //创建保存按关注分组分类后的结果列表
        List<FollowingGroup> result = new ArrayList<>();
        //保存全部分组
        result.add(allGroup);
        //遍历所有的分组信息
        for (FollowingGroup followingGroup : groupList) {
            //创建保存该分组下关注用户的用户信息列表
            List<UserInfo> infoList = new ArrayList<>();
            //遍历所有的用户关注信息
            for (UserFollowing userFollowing : userFollowings) {
                //如果这个用户属于这个分组，则将该用户添加道属于这个分组的list中
                if (followingGroup.getId().equals(userFollowing.getGroupId())){
                    infoList.add(userFollowing.getUserInfo());
                }
            }
            //为该分组添加该分组下所有关注用户的关注信息
            followingGroup.setFollowingUserInfoList(infoList);
            //保存当前分组分类的信息
            result.add(followingGroup);
        }
        return result;
    }
    // 第一步：获取当前用户的粉丝列表
    // 第二步：根据粉丝的用户id查询基本信息
    // 第三步：查询当前用户是否已经关注该粉丝，互关状态
    public List<UserFollowing> getUserFans(Long userId){
        //查询当前userId的粉丝信息
        List<UserFollowing> fanList = userFollowingDao.getUserFans(userId);
        //得到被粉丝用户的id集合
        Set<Long> fanIdSet = fanList.stream().map(UserFollowing::getUserId).collect(Collectors.toSet());
        List<UserInfo> userInfoList = new ArrayList<>();
        //如果存在粉丝
        if (fanIdSet.size() > 0){
            //查询粉丝们的用户信息
            userInfoList =  userService.getUserInfoByUserIds(fanIdSet);
        }
        //得到当前用户的关注列表，和他的粉丝列表做对比，得到互粉对象
        List<UserFollowing> followingList = userFollowingDao.getUserFollowings(userId);
        //遍历所有的粉丝关注信息
        for (UserFollowing fan : fanList) {
            //遍历粉丝的用户信息
            for (UserInfo userInfo : userInfoList) {
                //如果关注信息和用户信息属于同一个用户
                if(fan.getUserId().equals(userInfo.getUserId())){
                    //设置不是互粉状态
                    userInfo.setFollowed(false);
                    //为关注信息添加用户信息
                    fan.setUserInfo(userInfo);
                }
            }
            //遍历当前用户的关注信息
            for (UserFollowing following : followingList) {
                //如果发现该用户关注了他的粉丝，则说明互粉，设置标志位为true
                if(following.getFollowingId().equals(fan.getUserId())){
                    fan.getUserInfo().setFollowed(true);
                }
            }
        }
        return fanList;
    }

    public Long addUserFollowingGroups(FollowingGroup followingGroup) {
        followingGroup.setCreateTime(new Date());
        followingGroup.setType(UserConstant.USER_FOLLOWING_GROUP_TYPE_USER);//用户自建分组
        followingGroupService.addFollowingGroup(followingGroup);
        return followingGroup.getId();
    }

    public List<FollowingGroup> getUserFollowingGroup(Long userId) {
        return followingGroupService.getUserFollowingGroup(userId);
    }

    public List<UserInfo> checkFollowingStatus(List<UserInfo> userInforList, Long userId) {
        //获取当前用户的关注列表
        List<UserFollowing> followingList = userFollowingDao.getUserFollowings(userId);
        //遍历查询到的用户信息
        for (UserInfo userInfo : userInforList) {
            //设置关注标志为为false
            userInfo.setFollowed(false);
            //遍历关注列表
            for (UserFollowing userFollowing : followingList) {
                //如果相同
                if (userFollowing.getFollowingId().equals(userInfo.getUserId())){
                    //表示已经关注过，设置标志位为true
                    userInfo.setFollowed(true);
                }
            }
        }
        return userInforList;
    }
}
