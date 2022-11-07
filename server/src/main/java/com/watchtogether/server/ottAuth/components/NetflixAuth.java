package com.watchtogether.server.ottAuth.components;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Component(value = "NETFLIX")
public class NetflixAuth implements OttAuthComponent{

    private static final String LOGIN_URL = "https://www.netflix.com/kr/login";

    @Override
    public String verifyOttAccount(String id, String password) {

        try {
            //로그인 페이지 접근, 로그인 시 필요한 쿠기 값 및 폼 값 추출
            Connection.Response loginPageResponse = Jsoup.connect(LOGIN_URL)
                    .timeout(3000)
                    .header("Origin", "https://www.netflix.com/kr")
                    .header("Referer", "https://www.netflix.com/kr/login")
                    .header("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8")
                    .header("Content-Type", "application/x-www-form-urlencoded")
                    .header("Accept-Encoding", "gzip, deflate, br")
                    .header("Accept-Language", "ko-KR,ko;q=0.8,en-US;q=0.6,en;q=0.4")
                    .method(Connection.Method.GET)
                    .execute();

            //쿠기 값
            Map<String, String> cookies = loginPageResponse.cookies();

            //폼 값
            Document logDoc = loginPageResponse.parse();
            String flow = logDoc.select("input[name=flow").val();
            String mode = logDoc.select("input[name=mode").val();
            String action = logDoc.select("input[name=action").val();
            String withFields = logDoc.select("input[name=withFields").val();
            String authURL = logDoc.select("input[name=authURL").val();
            String nextPage = logDoc.select("input[name=nextPage").val();
            String showPassword = logDoc.select("input[name=showPassword").val();
            String countryCode = logDoc.select("input[name=countryCode").val();
            String countryIsoCode = logDoc.select("input[name=countryIsoCode").val();

            Map<String, String> data = new HashMap<>();
            data.put("userLoginId", id);
            data.put("password", password);
            data.put("flow", flow);
            data.put("mode", mode);
            data.put("action", action);
            data.put("withFields", withFields);
            data.put("authURL", authURL);
            data.put("nextPage", nextPage);
            data.put("showPassword", showPassword);
            data.put("countryCode", countryCode);
            data.put("countryIsoCode", countryIsoCode);


            //로그인 시도
            Connection.Response response = Jsoup.connect(LOGIN_URL)
                    .header("Origin", "https://www.netflix.com/kr")
                    .header("Referer", "https://www.netflix.com/kr/login")
                    .header("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8")
                    .header("Content-Type", "application/x-www-form-urlencoded")
                    .header("Accept-Encoding", "gzip, deflate, br")
                    .data(data)
                    //.userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/89.0.4389.114 Safari/537.36")
                    .cookies(cookies)
                    .method(Connection.Method.POST)
                    .timeout(5000)
                    .execute();


            //로그인 유무 확인
            Map<String, String> loginCookies = response.cookies();
            if (loginCookies.containsKey("profilesNewSession")) {
                return "1";
            } else {
                return "0";
            }
            
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
