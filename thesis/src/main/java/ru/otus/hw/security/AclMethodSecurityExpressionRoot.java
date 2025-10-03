package ru.otus.hw.security;

import org.springframework.security.access.PermissionEvaluator;
import org.springframework.security.access.expression.SecurityExpressionRoot;
import org.springframework.security.access.expression.method.MethodSecurityExpressionOperations;
import org.springframework.security.core.Authentication;
import ru.otus.hw.models.Comment;

public class AclMethodSecurityExpressionRoot extends SecurityExpressionRoot implements
        MethodSecurityExpressionOperations {

    private Object filterObject;

    private Object returnObject;

    private Object target;

    public AclMethodSecurityExpressionRoot(Authentication authentication, PermissionEvaluator permissionEvaluator) {
        super(authentication);
        setPermissionEvaluator(permissionEvaluator);
    }

    public boolean canChangeComment(Long commentId) {
        String commentType = Comment.class.getName();
        return hasPermission(commentId, commentType, "WRITE");
    }

    @Override
    public Object getFilterObject() {
        return filterObject;
    }

    @Override
    public void setFilterObject(Object filterObject) {
        this.filterObject = filterObject;
    }

    @Override
    public Object getReturnObject() {
        return returnObject;
    }

    @Override
    public void setReturnObject(Object returnObject) {
        this.returnObject = returnObject;
    }

    @Override
    public Object getThis() {
        return this.target;
    }
}
