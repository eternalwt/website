package com.greengiant.website.filter;

import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import

org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.web.cors.reactive.CorsUtils;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;

import reactor.core.publisher.Mono;

/**
 * <p>Cors跨域请求配置</p>
 * @author 周亮
 * @since 2019/04/30 v1.0
 * @version v1.0
 * @description Cors跨域请求配置
 */
//@Configuration
public class CorsConfiguration {

	/**
	 * @description Cors跨域请求配置过滤器
	 * @return WebFilter WebFilter
	 * @create 周亮 2019/04/30
	 * */
	@Bean
	public WebFilter corsFilter() {
		return (ServerWebExchange ctx, WebFilterChain chain) -> {
			ServerHttpRequest request = ctx.getRequest();
			if (!CorsUtils.isCorsRequest(request)) {  // 是否是Cors请求
				return chain.filter(ctx);
			}
			HttpHeaders requestHeaders = request.getHeaders();
			ServerHttpResponse response = ctx.getResponse();
			HttpMethod requestMethod = requestHeaders.getAccessControlRequestMethod();
			HttpHeaders headers = response.getHeaders();
			headers.add(HttpHeaders.ACCESS_CONTROL_ALLOW_ORIGIN, requestHeaders.getOrigin());  // 设置Headers Origin
			
			List<String> accessControlRequestHeadersArray = requestHeaders.getAccessControlRequestHeaders();  // 获取请求中的AccessControl
			String accessControlRequestHeadersStr = "";
			
			/* 判断并循环拼接AccessControl */
			if(accessControlRequestHeadersArray != null) {
				for(String str : accessControlRequestHeadersArray) {
					if(!"".equals(accessControlRequestHeadersStr)) {
						accessControlRequestHeadersStr += ", ";
					}
					accessControlRequestHeadersStr += str;
				}
			}
			
			headers.add(HttpHeaders.ACCESS_CONTROL_ALLOW_HEADERS, accessControlRequestHeadersStr);  // 设置Headers allow
			headers.addAll(HttpHeaders.ACCESS_CONTROL_ALLOW_HEADERS, accessControlRequestHeadersArray);
			headers.add(HttpHeaders.ACCESS_CONTROL_REQUEST_HEADERS, accessControlRequestHeadersStr);
			headers.addAll(HttpHeaders.ACCESS_CONTROL_REQUEST_HEADERS, accessControlRequestHeadersArray);
			if (requestMethod != null) {
				headers.add(HttpHeaders.ACCESS_CONTROL_ALLOW_METHODS, requestMethod.name());
			}
			headers.add(HttpHeaders.ACCESS_CONTROL_ALLOW_CREDENTIALS, "true");  // 设置Headers credentials
			headers.add(HttpHeaders.ACCESS_CONTROL_EXPOSE_HEADERS, "*");  // 设置Headers Expose
			headers.add(HttpHeaders.ACCESS_CONTROL_MAX_AGE, "18000L");  // 设置Headers Max Age
			if (request.getMethod() == HttpMethod.OPTIONS) {
				response.setStatusCode(HttpStatus.OK);  // 设置响应状态
				return Mono.empty();
			}
			return chain.filter(ctx);
		};
	}

}
