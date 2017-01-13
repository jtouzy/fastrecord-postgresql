package com.jtouzy.fastrecord.postgresql.statements.writers;

import com.jtouzy.fastrecord.annotations.support.Writes;
import com.jtouzy.fastrecord.postgresql.statements.context.PostgreSQLQueryExpression;
import com.jtouzy.fastrecord.statements.context.QueryExpression;
import com.jtouzy.fastrecord.statements.writers.DefaultQueryExpressionWriter;
import com.jtouzy.fastrecord.utils.Priority;

@Writes(value = QueryExpression.class, priority = Priority.MODULE)
public class PostgreSQLQueryExpressionWriter extends DefaultQueryExpressionWriter {
    @Override
    public void write() {
        super.write();
        PostgreSQLQueryExpression expression = (PostgreSQLQueryExpression)getContext();
        if (expression.getLimit() != null) {
            getResult().getSqlString().append(" LIMIT ").append(expression.getLimit());
        }
        if (expression.getOffset() != null) {
            getResult().getSqlString().append(" OFFSET ").append(expression.getOffset());
        }
    }
}
