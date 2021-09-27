package com.hyl.op.log.util;

import org.springframework.core.LocalVariableTableParameterNameDiscoverer;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.util.StringUtils;

import java.lang.reflect.Method;

/**
 * @author huayuanlin
 * @date 2021/08/07 21:23
 * @desc the class desc
 */
public class SpringUtil {

    private SpringUtil() {
        throw new UnsupportedOperationException();
    }

    /**
     * do execute spring el
     *
     * @return result
     */
    public static Object doExecuteEl(String key, Method method, Object[] args) {
        if (StringUtils.isEmpty(key)) {
            return null;
        }
        // method params
        LocalVariableTableParameterNameDiscoverer u = new LocalVariableTableParameterNameDiscoverer();
        String[] paraNameArr = u.getParameterNames(method);
        ExpressionParser parser = new SpelExpressionParser();
        // parse key
        Expression expression = parser.parseExpression(key);
        assert paraNameArr != null;
        if (paraNameArr.length == 0) {
            expression.getValue(Integer.class);
        }
        // spring el context
        StandardEvaluationContext context = new StandardEvaluationContext();
        // set context params
        for (int i = 0; i < paraNameArr.length; i++) {
            context.setVariable(paraNameArr[i], args[i]);
        }
        return expression.getValue(context, Object.class);
    }

}
