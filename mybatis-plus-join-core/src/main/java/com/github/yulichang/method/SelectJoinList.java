package com.github.yulichang.method;

import com.baomidou.mybatisplus.core.metadata.TableInfo;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlSource;

/**
 * copy {@link com.baomidou.mybatisplus.core.injector.methods.SelectMaps}
 *
 * @author yulichang
 */
public class SelectJoinList extends MPJAbstractMethod {

    @SuppressWarnings("deprecation")
    public SelectJoinList() {
        super();
    }

    @SuppressWarnings("unused")
    public SelectJoinList(String name) {
        super(name);
    }

    @Override
    public MappedStatement injectMappedStatement(Class<?> mapperClass, Class<?> modelClass, TableInfo tableInfo) {
        SqlMethod sqlMethod = SqlMethod.SELECT_JOIN_LIST;
        String sql = String.format(sqlMethod.getSql(), sqlFirst(), sqlDistinct(), sqlSelectColumns(tableInfo, true),
                mpjTableName(tableInfo), sqlAlias(), sqlFrom(), sqlWhereEntityWrapper(true, tableInfo), sqlComment());
        SqlSource sqlSource = languageDriver.createSqlSource(configuration, sql, modelClass);
        return this.addSelectMappedStatementForOther(mapperClass, sqlMethod.getMethod(), sqlSource, MPJResultType.class);
    }
}
