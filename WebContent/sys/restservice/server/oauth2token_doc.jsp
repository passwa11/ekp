<%@ page language="java" contentType="text/plain; charset=UTF-8"
    pageEncoding="UTF-8"%>
    

  特殊API，用于在OAUTH2-Server模式下，生成token的接口 本接口可以任意访问，不应该受过滤器限制
  暂时只支持grant_type=client_credentials，
  要求客户端content-type：application/json，并且请求参数必须包含"client_id"，
  RequestBody为：
  {
   "grant_type":"client_credentials",
   "client_id":"your-id"
  }
  
