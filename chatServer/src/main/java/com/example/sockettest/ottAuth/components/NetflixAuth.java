package com.example.sockettest.ottAuth.components;

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

            Connection.Response loginPageResponse = Jsoup.connect(LOGIN_URL)
                    .timeout(3000)
                    .header("Origin", "https://www.netflix.com/kr/")
                    .header("Referer", "https://www.netflix.com/kr/login")
                    .header("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8")
                    .header("Content-Type", "application/x-www-form-urlencoded")
                    .header("Accept-Encoding", "gzip, deflate, br")
                    .header("Accept-Language", "ko-KR,ko;q=0.8,en-US;q=0.6,en;q=0.4")
                    .method(Connection.Method.GET)
                    .execute();


            //쿠기
            Map<String, String> cookies = loginPageResponse.cookies();
            System.out.println(cookies.toString());

            //페이로드 값 세팅
            Document logDoc = loginPageResponse.parse();

            //System.out.println(logDoc.body());

            String authURL = logDoc.select("input[name=authURL").val();
            System.out.println("authURL: " + authURL);

            Map<String, String> formData = new HashMap<>();
            formData.put("userLoginId", id);
            formData.put("password", password);
            formData.put("flow", "websiteSignUp");
            formData.put("action", "loginAction");
            formData.put("withFields", "rememberMe,nextPage,userLoginId,password,countryCode,countryIsoCode,recaptchaResponseToken,recaptchaError,recaptchaResponseTime\n");
            formData.put("authURL", authURL);
            formData.put("nextPage", "");
            formData.put("showPassword", "");
            formData.put("countryCode", "82");
            formData.put("countryIsoCode", "KR");
            formData.put("recaptchaResponseToken", "03AIIukziayfGmy3HtFOkYDFFCWw0ZNPOwLtVz-20DM6CM7oRyGxRqlCCErblQqgprKyh9PNIJO0crnNbDVazIMygmGLOJsxvg4h1Yn-VaJvEs6L3W7848D97lTEVMn6XGLY7SPLHxJxehyghgLpZBYPnDW5M7tfSgZrHFahZRwyqNy7x2fXOSjbNPlqaNU4BXrjN-ID7B833aZSVoFlQdRNgKUjXOO6k6dIVJJW7TFwjf2gufbryk9vNJgEq58SzHgpKHgFepmXErdd7dSM5iGjhJuV3sU66NB4tFO-Hf7yIP4NsXyRvATM9aSpknTSnulKyu2ChSapVTHLBJdlcwNDV-8I4Mfo2zRAC1DfNgkyMz8rOe0iv2DKbm4efxc8kJVnVdKEriQYLV44ogDPq8IMyFN41pBKwE6wXoFcB71XfJlt4B75lchsJXuJzcANhQxbhNbFdwmbP6IZye8rJEJp-cvZZczpQB9lKiXJJ_3wxvpj5khdZQl3C45kEXCXKK9dPnJn9mFIrjnRem9naNngo-w3lHoyRfXo9pRxL3OwfPLzUS1TjvIIUf1oM-0vyA8tnNwesJLZEpHbqRBwat718FYm5DEs-tWDlZCn7YuBri7clVBY767w6t7MobVo0mqQ-SdMxOoKeEiKyntBabUpvPXjObmGAqCSGFUfCTd3Jl5fWe4nQqRgEmtzbrCxzMS9a5rNDvIlGYKx_GpIBpM46KZT6LdYXceUIeQJ4zWSW8L9ULy9tJtkrMFDZqeGTIFM8mJHbfoJJwAYACkdltqOg-gsTZurBUCSx1QWAGDmlKe3w2SGrxgQPFd71B_FJrynuzMAAM4Oo4I9r7xDUaucnE6OhdjNmPLMXIo2Uh8EGDsASHDw-MzVVUcw9aidARbl8WH74AkRvXp5yOCRTs5IIQ_qoQxuM08H4HBz3URpvoje7us_VXV_znzTWliiZ0jRbTzt0oTwnc8EB3MutgBA_iHgiI3eD27PyJrssY1EsSl2w-tNvVrMEQh4RR0dH9tVotLvy93PEeLPALhiYdjsjMwh2S36NlzQfTX9p5BNDUZQiM8SbJBOrW8YJoWUM1XR-YqLmSVAENKUPwOIkmD9L5pw40MAwqknWKaX9qKKkFWjWMlKefLzfKGkVXakJoV6ykus1FyiQe0U6DKkE0zUIhpUimtLxE2Vqm-_pEitqGtSS7BoRLbNLIw6HT0Z-45gBB1rqunl0PX3iiL6Q3uVyRkWeLB7thLNp54-HSoQNkmnHm4GFrVdplbWYXWvlOXIuEjJ-bApNe");
            formData.put("recaptchaResponseTime", "288");



            Connection.Response response = Jsoup.connect(LOGIN_URL)
                    .header("Origin", "https://www.netflix.com/kr/")
                    .header("Referer", "https://www.netflix.com/kr/login")
                    .header("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8")
                    .header("Content-Type", "application/x-www-form-urlencoded")
                    .header("Accept-Encoding", "gzip, deflate, br")
                    .data(formData)
                    .userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/89.0.4389.114 Safari/537.36")
                    .cookies(cookies)
                    .method(Connection.Method.POST)
                    .timeout(5000)
                    .execute();

            Map<String, String> loginCookie = response.cookies();
            String loginBody = response.body();
            Map<String, String> header = response.headers();

            //System.out.println(loginCookie.toString());
            //System.out.println(loginBody);
            System.out.println(header.toString());




        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return "Netflix";
    }
}
