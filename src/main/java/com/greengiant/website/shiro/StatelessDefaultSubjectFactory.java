package com.greengiant.website.shiro;

import org.apache.shiro.subject.Subject;
import org.apache.shiro.subject.SubjectContext;
import org.apache.shiro.web.mgt.DefaultWebSubjectFactory;

/**
 * 用于jwt验证关闭session的SubjectFactory
 */
public class StatelessDefaultSubjectFactory extends DefaultWebSubjectFactory {
    @Override
    public Subject createSubject(SubjectContext context) {
        //不创建 session
        context.setSessionCreationEnabled(false);
        return super.createSubject(context);
    }
}
