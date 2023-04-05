package com.example.googlesso.Controller;

import com.example.googlesso.Entity.Admin;
import com.example.googlesso.Service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.security.oauth2.core.oidc.OidcIdToken;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigInteger;
import java.security.Principal;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
public class AdminController {

    @Autowired
    private AdminService adminService;

    @GetMapping("/")
    public ResponseEntity<Object> welcome(OAuth2AuthenticationToken authentication){
        OAuth2User oAuth2User = authentication.getPrincipal();
        OidcUser oidcUser = (OidcUser) oAuth2User;
        String oidcIdTokenValue = oidcUser.getIdToken().getTokenValue();
        Map<String,Object> attributes = oAuth2User.getAttributes();
        Collection<String> authorities = oAuth2User.getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());
        System.out.println(authorities);
        Admin admin = new Admin((String)attributes.get("sub"),(String)attributes.get("name"),(String)attributes.get("email"),"ROLE_USER");
        adminService.saveAdmin(admin);
        Map<String,Object> map = new HashMap<>();
        map.put("message","User Logged In");
        map.put("user",admin);
        map.put("token",oidcIdTokenValue);
        return new ResponseEntity<>(map, HttpStatus.OK);
    }

    @GetMapping("/user")
    public Principal user(Principal principal){
        return principal;
    }
}
