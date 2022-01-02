package com.sw.ca.config.cors;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * <p>跨域请求</p>
 * @author 周亮
 * @since 2020/09/08 v1.0
 * @version v1.0
 * @description 跨域请求
 */
@Configuration
public class CorsConfig implements WebMvcConfigurer {
	
	/**
     * @description 跨域请求配置
     * @return WebMvcConfigurer {@link WebMvcConfigurer}
     * @create 周亮 2020/09/08
     */
	@Bean
	public WebMvcConfigurer corsConfigurer() {
		return new WebMvcConfigurer() {
			@Override
			public void addCorsMappings(CorsRegistry registry) {
				registry.addMapping("/**").
				allowedOrigins("*").  // 允许跨域的域名，可以用*表示允许任何域名使用
				allowedMethods("*").  // 允许任何方法（post、get等）
				allowedHeaders("*").  // 允许任何请求头
				allowCredentials(true).  // 带上cookie信息
				exposedHeaders(HttpHeaders.SET_COOKIE).maxAge(3600L);  // maxAge(3600)表明在3600秒内，不需要再发送预检验请求，可以缓存该结果
			}
		};
	}
}
