package com.jtouzy.fastrecord.postgresql.statements.writers;

import com.jtouzy.fastrecord.annotations.support.Writes;
import com.jtouzy.fastrecord.postgresql.statements.context.PostgreSQLInsertExpression;
import com.jtouzy.fastrecord.statements.context.InsertExpression;
import com.jtouzy.fastrecord.statements.context.SimpleTableColumnExpression;
import com.jtouzy.fastrecord.statements.writers.DefaultInsertExpressionWriter;
import com.jtouzy.fastrecord.utils.Priority;

import java.util.Iterator;

@Writes(value = InsertExpression.class, priority = Priority.MODULE)
public class PostgreSQLInsertExpressionWriter extends DefaultInsertExpressionWriter {
    @Override
    public void write() {
        super.write();
        PostgreSQLInsertExpression insertExpression = (PostgreSQLInsertExpression)getContext();
        if (!insertExpression.getReturningColumns().isEmpty()) {
            getResult().getSqlString().append(" RETURNING ");
            Iterator<SimpleTableColumnExpression> it = insertExpression.getReturningColumns().iterator();
            while (it.hasNext()) {
                mergeWriter(it.next());
                if (it.hasNext()) {
                    getResult().getSqlString().append(", ");
                }
            }
        }
    }
}
