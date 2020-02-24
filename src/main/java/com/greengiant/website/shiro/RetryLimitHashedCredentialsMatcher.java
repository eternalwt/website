package com.greengiant.website.shiro;

import com.greengiant.website.utils.EhcacheUtil;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.ExcessiveAttemptsException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.springframework.stereotype.Component;

@Component
public class RetryLimitHashedCredentialsMatcher extends HashedCredentialsMatcher {

    //todo 后续用缓存处理 1.用注解的方式写通；
    // todo 把shiro、ehcache、spring里面的缓存都学通，不是那么容易。把AtomicInteger写进去
    private int MAX_RETRY_COUNT = 5;

    @Override
    public boolean doCredentialsMatch(AuthenticationToken token, AuthenticationInfo info) {
        String username = (String)token.getPrincipal();

        if(EhcacheUtil.getItem(username) != null && Integer.valueOf(EhcacheUtil.getItem(username).toString()) >= MAX_RETRY_COUNT) {
            // todo 重试的时候需要输入验证码
            throw new ExcessiveAttemptsException("您已连续输错" + MAX_RETRY_COUNT + "次密码！请30分钟后再试");
        }

        boolean matches = super.doCredentialsMatch(token, info);
        if(matches) {
            //clear retry count
            EhcacheUtil.removeItem(username);
        }
        else {
            if(EhcacheUtil.getItem(username) == null) {
                EhcacheUtil.putItem(username, 0);
                throw new IncorrectCredentialsException("密码错误");
            }
            else {
                EhcacheUtil.updateItem(username, Integer.valueOf(EhcacheUtil.getItem(username).toString()) + 1);
                throw new IncorrectCredentialsException("密码错误，已连续错误" + (EhcacheUtil.getItem(username).toString())
                        + "次，最多连续错误" + MAX_RETRY_COUNT + "次");
            }
        }

        return matches;
    }

}