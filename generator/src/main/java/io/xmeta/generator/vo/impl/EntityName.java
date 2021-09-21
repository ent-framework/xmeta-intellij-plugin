package io.xmeta.generator.vo.impl;

import io.xmeta.generator.config.OutputSettings;
import io.xmeta.generator.vo.IName;
import com.google.common.base.CaseFormat;
import io.xmeta.api.data.GraphEntity;
import lombok.Getter;

/**
 * 实体类名称对象。提供方便直接获取 Entity、Service、ServiceImpl、Dao、Controller 的对象完整名称
 *
 * @author HouKunLin
 * @date 2020/7/5 0005 15:07
 */
@Getter
public class EntityName implements IName {
    private final String value;
    private final String firstUpper;
    private final String firstLower;
    /**
     * 实体类完整名称
     */
    private IName entity;
    /**
     * Service 完整名称
     */
    private IName service;
    /**
     * ServiceImpl 完整名称
     */
    private IName serviceImpl;
    /**
     * Dao 完整名称
     */
    private IName dao;
    /**
     * Controller 完整名称
     */
    private IName controller;

    public EntityName(GraphEntity dbTable) {
        this.value = CaseFormat.LOWER_UNDERSCORE.to(CaseFormat.UPPER_CAMEL, dbTable.getName());
        this.firstUpper = value;
        this.firstLower = CaseFormat.UPPER_CAMEL.to(CaseFormat.LOWER_CAMEL, value);
    }

    public EntityName(String name) {
        this.value = name;
        this.firstUpper = value;
        this.firstLower = CaseFormat.UPPER_CAMEL.to(CaseFormat.LOWER_CAMEL, value);
    }

    @Override
    public String toString() {
        return value;
    }

    public void initMore(OutputSettings outputSettings) {
        this.entity = build(outputSettings.getEntitySuffix());
        this.service = build(outputSettings.getServiceSuffix());
        this.serviceImpl = build(outputSettings.getServiceSuffix() + "Impl");
        this.dao = build(outputSettings.getDaoSuffix());
        this.controller = build(outputSettings.getControllerSuffix());
    }

    private IName build(String suffix) {
        return new EntityNameInfo(value, suffix);
    }
}