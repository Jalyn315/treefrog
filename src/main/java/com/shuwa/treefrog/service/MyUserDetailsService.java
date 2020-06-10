package com.shuwa.treefrog.service;

import com.shuwa.treefrog.dao.MyUserDetailsServiceMapper;
import com.shuwa.treefrog.model.MyUserDetails;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
public class MyUserDetailsService implements UserDetailsService {

    @Resource
    private MyUserDetailsServiceMapper myUserDetailsServiceMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        //加载基础用户信息
        MyUserDetails myUserDetails = myUserDetailsServiceMapper.findByUserName(username);

        //加载用户角色列表
//        List<String> roleCodes = myUserDetailsServiceMapper.findRoleByUserName(username);
//
//
//        //通过用户角色列表加载用户的资源权限列表
//        List<String> authorties = myUserDetailsServiceMapper.findAuthorityByRoleCodes(roleCodes);
//
//        //角色是一个特殊的权限，ROLE_前缀
//        roleCodes = roleCodes.stream()
//                .map(rc -> "ROLE_" +rc)
//                .collect(Collectors.toList());
//
//        authorties.addAll(roleCodes);
//
//        myUserDetails.setAuthorities(
//                AuthorityUtils.commaSeparatedStringToAuthorityList(
//                        String.join(",",authorties)
//                )
//        );


        return myUserDetails;
    }
}
