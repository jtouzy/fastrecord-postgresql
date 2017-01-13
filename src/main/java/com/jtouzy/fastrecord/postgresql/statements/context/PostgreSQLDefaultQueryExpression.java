package com.jtouzy.fastrecord.postgresql.statements.context;

import com.jtouzy.fastrecord.statements.context.QueryTargetExpressionWrapper;
import com.jtouzy.fastrecord.statements.context.impl.DefaultQueryExpression;

public class PostgreSQLDefaultQueryExpression extends DefaultQueryExpression implements PostgreSQLQueryExpression {
    private Integer limit;
    private Integer offset;

    public PostgreSQLDefaultQueryExpression(QueryTargetExpressionWrapper mainTarget) {
        super(mainTarget);
    }

    @Override
    public Integer getLimit() {
        return limit;
    }

    @Override
    public void setLimit(Integer limit) {
        this.limit = limit;
    }

    @Override
    public Integer getOffset() {
        return offset;
    }

    @Override
    public void setOffset(Integer offset) {
        this.offset = offset;
    }
}
