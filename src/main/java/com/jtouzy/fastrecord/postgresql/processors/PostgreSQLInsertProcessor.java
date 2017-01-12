package com.jtouzy.fastrecord.postgresql.processors;

import com.jtouzy.fastrecord.annotations.support.Process;
import com.jtouzy.fastrecord.builders.DefaultInsertProcessor;
import com.jtouzy.fastrecord.builders.StatementException;
import com.jtouzy.fastrecord.config.FastRecordConfiguration;
import com.jtouzy.fastrecord.entity.ColumnDescriptor;
import com.jtouzy.fastrecord.entity.EntityPool;
import com.jtouzy.fastrecord.postgresql.statements.context.PostgreSQLDefaultInsertExpression;
import com.jtouzy.fastrecord.postgresql.statements.context.PostgreSQLInsertExpression;
import com.jtouzy.fastrecord.statements.context.InsertExpression;
import com.jtouzy.fastrecord.statements.context.impl.DefaultSimpleTableColumnExpression;
import com.jtouzy.fastrecord.statements.context.impl.DefaultSimpleTableExpression;
import com.jtouzy.fastrecord.statements.writers.WriterCache;
import com.jtouzy.fastrecord.utils.Priority;
import org.springframework.beans.factory.annotation.Autowired;

import java.lang.reflect.InvocationTargetException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Process(value = InsertExpression.class, priority = Priority.MODULE)
public class PostgreSQLInsertProcessor<T> extends DefaultInsertProcessor<T> {
    @Autowired
    public PostgreSQLInsertProcessor(WriterCache writerCache, EntityPool entityPool,
                                     FastRecordConfiguration configuration) {
        super(writerCache, entityPool, configuration);
    }

    @Override
    protected InsertExpression createExpression() {
        return new PostgreSQLDefaultInsertExpression(
                new DefaultSimpleTableExpression(getEntityDescriptor().getTableName()));
    }

    @Override
    public void initProcessor(Class<T> entityClass) {
        super.initProcessor(entityClass);
        for (ColumnDescriptor columnDescriptor : getEntityDescriptor().getGeneratedColumnDescriptors()) {
            ((PostgreSQLInsertExpression)getExpression()).getReturningColumns().add(
                    new DefaultSimpleTableColumnExpression(
                            columnDescriptor.getColumnType(),
                            getExpression().getTarget(),
                            columnDescriptor.getColumnName()));
        }
    }

    @Override
    protected void refreshEntity(PreparedStatement preparedStatement)
    throws SQLException, StatementException {
        super.refreshEntity(preparedStatement);
        List<ColumnDescriptor> returningColumns = getEntityDescriptor().getGeneratedColumnDescriptors();
        if (!returningColumns.isEmpty()) {
            try {
                ResultSet insertResultSet = preparedStatement.getResultSet();
                insertResultSet.next();
                Object value;
                for (ColumnDescriptor columnDescriptor : returningColumns) {
                    value = insertResultSet.getObject(1);
                    columnDescriptor.getPropertySetter().invoke(getTarget(),
                            columnDescriptor.getTypeManager().convertToObject(value));
                }
            } catch (IllegalAccessException | InvocationTargetException ex) {
                throw new StatementException(ex);
            }
        }
    }
}
