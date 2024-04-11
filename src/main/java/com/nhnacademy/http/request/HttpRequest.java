package com.nhnacademy.http.request;

import java.util.Map;

//TODO#1 Http Request를 Abstraction한 interface 입니다.
public interface HttpRequest {
    //GET, POST, ....
    String getMethod();

    //?page=1&sort=age, ex) getParameter("sort") , return age
    String getParameter(String name);

    // paramter를 map 형태로 반환 합니다.
    Map<String, String[]> getParameterMap();

    //header의 value를 반환 합니다.
    String getHeader(String name);

    //request에 값을(name->value) 설정 합니다., view(html)에 값을 전달 하기 위해서 사용 합니다.
    void setAttribute(String name, Object o);

    //request에 설정된 값을 반환합니다.
    Object getAttribute(String name);

    //요청 경로를 반환 합니다. GET /index.html?page=1 -> /index.html
    String getRequestURI();
}
