package com.jtouzy.fastrecord.postgresql.processors;

import com.jtouzy.fastrecord.annotations.support.Process;
import com.jtouzy.fastrecord.builders.DefaultQueryProcessor;
import com.jtouzy.fastrecord.config.FastRecordConfiguration;
import com.jtouzy.fastrecord.entity.EntityPool;
import com.jtouzy.fastrecord.postgresql.statements.context.PostgreSQLDefaultQueryExpression;
import com.jtouzy.fastrecord.postgresql.statements.context.PostgreSQLQueryExpression;
import com.jtouzy.fastrecord.statements.context.QueryExpression;
import com.jtouzy.fastrecord.statements.context.impl.DefaultQueryTargetExpressionWrapper;
import com.jtouzy.fastrecord.statements.context.impl.DefaultSimpleTableExpression;
import com.jtouzy.fastrecord.statements.writers.WriterCache;
import com.jtouzy.fastrecord.utils.Priority;

@Process(value = QueryExpression.class, priority = Priority.MODULE)
public class PostgreSQLQueryProcessor<T> extends DefaultQueryProcessor<T> {
    public PostgreSQLQueryProcessor(WriterCache writerCache, EntityPool entityPool,
                                    FastRecordConfiguration configuration) {
        super(writerCache, entityPool, configuration);
    }

    @Override
    protected QueryExpression createExpression() {
        String firstEntityDescriptorAlias = registerAlias(getEntityDescriptor());
        return new PostgreSQLDefaultQueryExpression(
                new DefaultQueryTargetExpressionWrapper(
                        firstEntityDescriptorAlias,
                        new DefaultSimpleTableExpression(getEntityDescriptor().getTableName())));
    }

    public void limit(Integer limit) {
        checkOrderBy();
        ((PostgreSQLQueryExpression)getExpression()).setLimit(limit);
    }

    public void offset(Integer offset) {
        checkOrderBy();
        ((PostgreSQLQueryExpression)getExpression()).setOffset(offset);
    }

    private void checkOrderBy() {
        if (getExpression().getOrderByColumns().isEmpty())
            throw new IllegalStateException("Order by must be set before LIMIT or OFFSET");
    }
}
