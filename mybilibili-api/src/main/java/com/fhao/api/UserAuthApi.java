package com.fhao.api;

import com.fhao.api.support.UserSupport;
import com.fhao.domain.JsonResponse;
import com.fhao.domain.auth.UserAuthorities;
import com.fhao.service.UserAuthoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * author: FHao
 * create time: 2023-03-20 21:01
 * description:
 */
@RestController
public class UserAuthApi {
    @Autowired
    private UserSupport userSupport;
    @Autowired
    private UserAuthoService userAuthoService;

    @GetMapping("/user-authorities")
    public JsonResponse<UserAuthorities> getUserAuthorities(){
        Long userId = userSupport.getCurrentUserId();
        UserAuthorities userAuthorities = userAuthoService.getUserAuthorities(userId);
        return new JsonResponse<>(userAuthorities);
    }
}
