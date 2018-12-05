package io.lishman.token.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.client.OAuth2ClientContext;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TokenController {

    @Autowired
    private OAuth2ClientContext oAuth2ClientContext;

    @GetMapping
    public @ResponseBody String token() {
        OAuth2AccessToken oAuth2AccessToken = oAuth2ClientContext.getAccessToken();
        return oAuth2AccessToken.getValue();
    }
}
