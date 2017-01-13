package com.jtouzy.fastrecord.postgresql.statements.context;

import com.jtouzy.fastrecord.statements.context.QueryExpression;

public interface PostgreSQLQueryExpression extends QueryExpression {
    void setLimit(Integer limit);
    Integer getLimit();
    void setOffset(Integer offset);
    Integer getOffset();
}
