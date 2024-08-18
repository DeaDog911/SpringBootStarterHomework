package org.deadog.springbootstarterhomework.util.tags;

import org.springframework.expression.ExpressionParser;
import org.springframework.expression.common.TemplateParserContext;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;

public abstract class AbstractTagsSetter {
    public String setTags(String text) {
        StandardEvaluationContext context = getEvaluationContext();
        ExpressionParser parser = new SpelExpressionParser();
        return parser.parseExpression(text, new TemplateParserContext()).getValue(context, String.class);
    }

    abstract protected StandardEvaluationContext getEvaluationContext();
}
