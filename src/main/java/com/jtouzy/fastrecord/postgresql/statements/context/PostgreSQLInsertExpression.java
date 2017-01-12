package com.jtouzy.fastrecord.postgresql.statements.context;

import com.jtouzy.fastrecord.statements.context.InsertExpression;
import com.jtouzy.fastrecord.statements.context.SimpleTableColumnExpression;

import java.util.List;

public interface PostgreSQLInsertExpression extends InsertExpression {
    List<SimpleTableColumnExpression> getReturningColumns();
}
