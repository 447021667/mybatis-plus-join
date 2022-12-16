package com.github.yulichang.wrapper.segments;


import com.baomidou.mybatisplus.core.metadata.TableFieldInfo;
import com.github.yulichang.wrapper.enums.BaseFuncEnum;
import lombok.Getter;
import org.apache.ibatis.type.TypeHandler;

/**
 * 别名列
 *
 * @author yulichang
 * @since 1.3.10
 */
@Getter
public class SelectAlias implements Select {

    private final SelectCache cache;

    private final String index;

    private final boolean hasAlias;

    private final String alias;

    public SelectAlias(SelectCache cache, String index, String alias) {
        this.cache = cache;
        this.index = index;
        this.hasAlias = true;
        this.alias = alias;
    }


    @Override
    public Class<?> getClazz() {
        return cache.getClazz();
    }


    @Override
    public boolean isPk() {
        return cache.isPk();
    }

    @Override
    public String getColumn() {
        return cache.getColumn();
    }

    @Override
    public Class<?> getColumnType() {
        return cache.getColumnType();
    }

    @Override
    public String getTagColumn() {
        return cache.getTagColumn();
    }

    @Override
    public String getColumProperty() {
        return cache.getColumProperty();
    }

    @Override
    public boolean hasTypeHandle() {
        return cache.isHasTypeHandle();
    }

    @Override
    public TypeHandler<?> getTypeHandle() {
        return cache.getTypeHandler();
    }

    @Override
    public TableFieldInfo getTableFieldInfo() {
        return cache.getTableFieldInfo();
    }

    @Override
    public boolean isFunc() {
        return false;
    }


    @Override
    public BaseFuncEnum getFunc() {
        return null;
    }

    @Override
    public boolean isLabel() {
        return false;
    }

    @Override
    public boolean isStr() {
        return false;
    }
}