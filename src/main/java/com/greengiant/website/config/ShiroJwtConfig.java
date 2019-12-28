package com.greengiant.website.config;

import com.greengiant.website.dao.MenuMapper;
import com.greengiant.website.filter.JwtFilter;
import com.greengiant.website.pojo.model.Menu;
import com.greengiant.website.shiro.CustomJwtRealm;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.mgt.DefaultSessionStorageEvaluator;
import org.apache.shiro.mgt.DefaultSubjectDAO;
import org.apache.shiro.mgt.SubjectFactory;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.mgt.DefaultWebSubjectFactory;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.DependsOn;

import javax.servlet.Filter;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//@Configuration
public class ShiroJwtConfig {

    private static final String JWT_FILTER_NAME = "jwt";

    /**
     * 自定义realm，实现登录授权流程
     */
    @Bean
    public Realm customJwtRealm() {
        return new CustomJwtRealm();
    }

    @Bean
    public DefaultWebSubjectFactory defaultWebSubjectFactory() {
        return new DefaultWebSubjectFactory();
    }

    /**
     * 配置securityManager 管理subject（默认）,并把自定义realm交由manager
     */
    @Bean
    public DefaultSecurityManager securityManager(Realm customJwtRealm, SubjectFactory subjectFactory) {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();

        securityManager.setSubjectFactory(subjectFactory);
        securityManager.setRealm(customJwtRealm);
        //非web关闭sessionManager(官网有介绍)
        DefaultSubjectDAO defaultSubjectDAO = new DefaultSubjectDAO();
        DefaultSessionStorageEvaluator storageEvaluator = new DefaultSessionStorageEvaluator();
        storageEvaluator.setSessionStorageEnabled(false);
        defaultSubjectDAO.setSessionStorageEvaluator(storageEvaluator);
        securityManager.setSubjectDAO(defaultSubjectDAO);

        return securityManager;
    }

    /**
     * 拦截链
     */
    @Bean
    public ShiroFilterFactoryBean shiroFilterFactoryBean(DefaultSecurityManager securityManager) {
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        shiroFilterFactoryBean.setSecurityManager(securityManager);
        shiroFilterFactoryBean.setFilters(filterMap());
        shiroFilterFactoryBean.setFilterChainDefinitionMap(definitionMap());

        return shiroFilterFactoryBean;
    }

    /**
     * 自定义拦截器，处理所有请求
     */
    private Map<String, Filter> filterMap() {
        Map<String, Filter> filterMap = new HashMap<>();
        filterMap.put(JWT_FILTER_NAME, new JwtFilter());
        return filterMap;
    }

    /**
     * url拦截规则
     */
    private Map<String, String> definitionMap() {
        Map<String, String> definitionMap = new HashMap<>();
        definitionMap.put("/auth/**", "anon");
        definitionMap.put("/websocket/**", "anon");
        definitionMap.put("/**", JWT_FILTER_NAME);

        // todo 思考如何让子目录都能放过（数据库表需要加字段吗？）
        // todo 1.加载顺序；2.如何缓存
        // todo 3.用最原始的方式读取数据库表
         //List<Menu> menuList = menuService.list();
        String resource = "mybatis-config.xml";
        SqlSession session = null;
        try {
        InputStream inputStream = Resources.getResourceAsStream(resource);
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
            sqlSessionFactory.getConfiguration().addMapper(MenuMapper.class);
            session = sqlSessionFactory.openSession();
            MenuMapper mapper = session.getMapper(MenuMapper.class);
            List<Menu> menuList = mapper.selectAll();
            System.out.println(menuList.size());
//            session.commit();
        }
        catch(Exception ex) {
            // todo
            System.out.println(ex);
        }finally {
//            session.close();
            System.out.println("aaa");
        }

        return definitionMap;
    }

    /**
     * 开启注解
     */
    @Bean
    @DependsOn("lifecycleBeanPostProcessor")
    public DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator() {
        DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator = new DefaultAdvisorAutoProxyCreator();
        // 强制使用cglib代理，防止和aop冲突
        defaultAdvisorAutoProxyCreator.setProxyTargetClass(true);
        return defaultAdvisorAutoProxyCreator;
    }

    @Bean
    public LifecycleBeanPostProcessor lifecycleBeanPostProcessor() {
        return new LifecycleBeanPostProcessor();
    }

    @Bean("authorizationAttributeSourceAdvisor")
    public AuthorizationAttributeSourceAdvisor advisor(DefaultSecurityManager securityManager) {
        AuthorizationAttributeSourceAdvisor advisor = new AuthorizationAttributeSourceAdvisor();
        advisor.setSecurityManager(securityManager);
        return advisor;
    }
}
