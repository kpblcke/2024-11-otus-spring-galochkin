package ru.otus.hw.security;

import java.util.function.Supplier;
import lombok.RequiredArgsConstructor;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.acls.AclPermissionEvaluator;
import org.springframework.security.acls.model.AclService;
import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.security.authorization.AuthorizationManager;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

@Component("customAuthorizationManager")
@RequiredArgsConstructor
public class CustomAuthorizationManager implements AuthorizationManager<MethodInvocation> {

    private final AclService aclService;

    @Override
    public AuthorizationDecision check(Supplier<Authentication> authentication, MethodInvocation invocation) {

        ExpressionParser parser = new SpelExpressionParser();
        Expression expression = parser.parseExpression(invocation.getMethod().getAnnotation(PreAuthorize.class).value());

        AclMethodSecurityExpressionHandler c = new AclMethodSecurityExpressionHandler();
        c.setPermissionEvaluator(new AclPermissionEvaluator(aclService));
        EvaluationContext ec = c.createEvaluationContext(authentication,invocation);
        boolean granted = Boolean.TRUE.equals(expression.getValue(ec, Boolean.class));

        return new AuthorizationDecision(granted);
    }
}