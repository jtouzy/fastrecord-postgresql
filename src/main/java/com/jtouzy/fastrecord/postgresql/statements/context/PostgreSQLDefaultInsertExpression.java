package com.jtouzy.fastrecord.postgresql.statements.context;

import com.jtouzy.fastrecord.statements.context.SimpleTableColumnExpression;
import com.jtouzy.fastrecord.statements.context.SimpleTableExpression;
import com.jtouzy.fastrecord.statements.context.impl.DefaultInsertExpression;

import java.util.ArrayList;
import java.util.List;

public class PostgreSQLDefaultInsertExpression extends DefaultInsertExpression implements PostgreSQLInsertExpression {
    private final List<SimpleTableColumnExpression> returningColumns;

    public PostgreSQLDefaultInsertExpression(SimpleTableExpression target) {
        super(target);
        this.returningColumns = new ArrayList<>();
    }

    @Override
    public List<SimpleTableColumnExpression> getReturningColumns() {
        return returningColumns;
    }
}
