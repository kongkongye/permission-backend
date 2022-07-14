package com.kongkongye.backend.permission.config;

import org.aspectj.lang.annotation.Aspect;
import org.springframework.aop.Advisor;
import org.springframework.aop.aspectj.AspectJExpressionPointcut;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionManager;
import org.springframework.transaction.interceptor.*;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@Aspect
@Configuration
public class TransactionConfig {
    private String aopPointcutExpressionBaseService = "execution (* com.kongkongye.backend.common.BaseService+.*(..))";

    @Autowired
    private TransactionManager transactionManager;

    @Bean
    public TransactionInterceptor txAdvice() {
        /*只读事务，不做更新操作*/
        RuleBasedTransactionAttribute readOnlyTx = new RuleBasedTransactionAttribute();
        readOnlyTx.setReadOnly(true);
        readOnlyTx.setPropagationBehavior(TransactionDefinition.PROPAGATION_NOT_SUPPORTED);
        /*当前存在事务就使用当前事务，当前不存在事务就创建一个新的事务*/
        RuleBasedTransactionAttribute requiredTx = new RuleBasedTransactionAttribute();
        requiredTx.setRollbackRules(Collections.singletonList(new RollbackRuleAttribute(RuntimeException.class)));//运行时异常回滚
        requiredTx.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
        requiredTx.setIsolationLevel(TransactionDefinition.ISOLATION_READ_COMMITTED);
        /*隔离事务：内外事务互不影响*/
        RuleBasedTransactionAttribute wrapTx = new RuleBasedTransactionAttribute();
        wrapTx.setRollbackRules(Collections.singletonList(new RollbackRuleAttribute(RuntimeException.class)));//运行时异常回滚
        wrapTx.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRES_NEW);
        wrapTx.setIsolationLevel(TransactionDefinition.ISOLATION_READ_COMMITTED);
        /*子事务：内部事务不会影响外部事务，外部事务提交时一起提交内部事务*/
        RuleBasedTransactionAttribute subTx = new RuleBasedTransactionAttribute();
        subTx.setRollbackRules(Collections.singletonList(new RollbackRuleAttribute(RuntimeException.class)));//运行时异常回滚
        subTx.setPropagationBehavior(TransactionDefinition.PROPAGATION_NESTED);
        subTx.setIsolationLevel(TransactionDefinition.ISOLATION_READ_COMMITTED);
        /*所有事务，所有异常都会回滚*/
        RuleBasedTransactionAttribute allTx = new RuleBasedTransactionAttribute();
        allTx.setRollbackRules(Collections.singletonList(new RollbackRuleAttribute(RuntimeException.class)));//运行时异常回滚

        Map<String, TransactionAttribute> txMap = new HashMap<>();
        txMap.put("add*", requiredTx);
        txMap.put("save*", requiredTx);
        txMap.put("insert*", requiredTx);
        txMap.put("update*", requiredTx);
        txMap.put("do*", requiredTx);
        txMap.put("set*", requiredTx);
        txMap.put("del*", requiredTx);
        txMap.put("delete*", requiredTx);

        txMap.put("get*", readOnlyTx);
        txMap.put("find*", readOnlyTx);
        txMap.put("query*", readOnlyTx);
        txMap.put("list*", readOnlyTx);
        txMap.put("page*", readOnlyTx);
        txMap.put("help*", readOnlyTx);

        txMap.put("wrap*", wrapTx);

        txMap.put("sub*", subTx);

        txMap.put("*", allTx);
        //返回
        NameMatchTransactionAttributeSource source = new NameMatchTransactionAttributeSource();
        source.setNameMap(txMap);
        return new TransactionInterceptor(transactionManager, source);
    }

    @Bean
    public Advisor txAdviceAdvisor() {
        AspectJExpressionPointcut pointcut = new AspectJExpressionPointcut();
        pointcut.setExpression(aopPointcutExpressionBaseService);
        return new DefaultPointcutAdvisor(pointcut, txAdvice());
    }
}
