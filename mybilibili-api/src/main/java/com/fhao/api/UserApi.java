package com.fhao.api;

import com.fhao.domin.JsonResponse;
import com.fhao.domin.User;
import com.fhao.service.UserService;
import com.fhao.util.RSAUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * author: FHao
 * create time: 2023-03-19 13:05
 * description:
 */
@RestController
public class UserApi {
    @Autowired
    private UserService userService;

    @GetMapping("/rsa-pks")
    public JsonResponse<String> getRsaPublicKey(){
        String pk = RSAUtil.getPublicKeyStr();
        return new JsonResponse<>(pk);
    }

    @PostMapping("/users")
    public JsonResponse<String> addUser(@RequestBody User user){
        userService.addUser(user);
        return JsonResponse.success();
    }
}
