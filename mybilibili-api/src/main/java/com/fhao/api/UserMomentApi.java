package com.fhao.api;

import com.fhao.api.support.UserSupport;
import com.fhao.domain.JsonResponse;
import com.fhao.domain.UserMoment;
import com.fhao.domain.annotation.ApiLimitedRole;
import com.fhao.domain.annotation.DataLimited;
import com.fhao.domain.constant.AuthRoleConstant;
import com.fhao.service.UserMomentsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * author: FHao
 * create time: 2023-03-20 13:19
 * description:
 */
@RestController
public class UserMomentApi {
    @Autowired
    private UserMomentsService userMomentsService;
    @Autowired
    private UserSupport userSupport;

    @PostMapping("/user-moments")
    @DataLimited
    @ApiLimitedRole(limitedRoleCodeList = {AuthRoleConstant.ROLE_LV0})
    public JsonResponse<String> addUserMoments(@RequestBody UserMoment userMoment) throws Exception {
        Long userId = userSupport.getCurrentUserId();
        userMoment.setUserId(userId);
        userMomentsService.addUserMoments(userMoment);
        return JsonResponse.success();
    }
    @GetMapping("/user-subscribed-moments")
    public JsonResponse<List<UserMoment>> getUserSubscribedMoments(){
        Long userId = userSupport.getCurrentUserId();
        List<UserMoment> result = userMomentsService.getUserSubscribedMoments(userId);
        return new JsonResponse<>(result);
    }

}
