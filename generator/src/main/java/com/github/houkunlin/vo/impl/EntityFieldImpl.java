package com.github.houkunlin.vo.impl;

import com.github.houkunlin.model.TableColumnType;
import com.github.houkunlin.util.PluginUtils;
import com.github.houkunlin.vo.IEntityField;
import com.github.houkunlin.vo.ITableColumn;
import com.google.common.base.CaseFormat;
import com.intellij.util.ReflectionUtil;
import com.neueda.jetbrains.plugin.graphdb.database.api.data.GraphField;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.apache.commons.lang.StringUtils;

/**
 * 实体类字段信息
 *
 * @author HouKunLin
 * @date 2020/5/28 0028 0:59
 */
@Getter
public class EntityFieldImpl implements IEntityField {
    private final FieldNameInfo name;
    private final String typeName;
    private final String dataType;
    private final String fullTypeName;
    private final boolean primaryKey;
    @Setter
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private ITableColumn column;
    @Setter
    private String comment;
    @Setter
    private boolean selected;

    private EntityFieldImpl(FieldNameInfo name, String typeName, String fullTypeName, boolean primaryKey, String comment, boolean selected) {
        this.name = name;
        this.typeName = typeName;
        this.dataType = null;
        this.fullTypeName = fullTypeName;
        this.primaryKey = primaryKey;
        this.comment = comment;
        this.selected = selected;
    }

    public EntityFieldImpl(GraphField dbColumn) {
        this.name = new FieldNameInfo(dbColumn);
        this.dataType = dbColumn.getDataType();
      //  String typeName = ReflectionUtil.getField(DataType.class, dataType, String.class, "typeName");
      //  TableColumnType columnType = type(typeName);
      //  this.typeName = columnType.getShortName();
      //  this.fullTypeName = columnType.getLongName();

         this.typeName = "";
         this.fullTypeName = "";
        this.comment = StringUtils.defaultString(dbColumn.getDescription(), "");
        this.primaryKey = StringUtils.equals(dbColumn.getDataType(), "id");
        this.selected = true;
    }

    /**
     * 创建主键字段对象
     *
     * @param name         字段名称
     * @param typeName     字段类型
     * @param fullTypeName 字段完整类型
     * @param comment      字段注释
     * @return 字段对象
     */
    public static EntityFieldImpl primaryField(String name, String typeName, String fullTypeName, String comment) {
        FieldNameInfo fieldNameInfo = new FieldNameInfo(name, CaseFormat.LOWER_CAMEL.to(CaseFormat.UPPER_CAMEL, name), name);
        return new EntityFieldImpl(fieldNameInfo, typeName, fullTypeName, true, comment, true);
    }

    public TableColumnType type(String dbType) {
        TableColumnType[] columnTypes = PluginUtils.getColumnTypes();
        if (dbType == null) {
            return TableColumnType.DEFAULT;
        }
        if (columnTypes == null) {
            return TableColumnType.DEFAULT;
        }
        for (TableColumnType columnType : columnTypes) {
            if (columnType.at(dbType)) {
                return columnType;
            }
        }
        for (TableColumnType columnType : columnTypes) {
            if (columnType.isDefault()) {
                return columnType;
            }
        }
        return columnTypes.length > 0 ? columnTypes[0] : TableColumnType.DEFAULT;
    }
}