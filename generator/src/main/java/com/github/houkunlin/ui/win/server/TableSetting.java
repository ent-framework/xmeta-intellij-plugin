package com.github.houkunlin.ui.win.server;

import com.github.houkunlin.ui.win.IWindows;
import com.github.houkunlin.vo.impl.RootModel;
import com.neueda.jetbrains.plugin.graphdb.database.api.data.GraphEntity;
import lombok.Data;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

/**
 * 数据库表配置面板（涵盖多个数据库表的内容）
 *
 * @author HouKunLin
 * @date 2020/8/15 0015 16:10
 */
@Data
public class TableSetting implements IWindows {
    /**
     * 面板：顶级内容面板
     */
    private JPanel content;
    /**
     * 标签：包含多个数据库表的标签界面
     */
    private JTabbedPane tableTabbedPane;
    private List<TablePanel> tablePanels = new ArrayList<>();

    public TableSetting(GraphEntity[] psiElements) {
        for (GraphEntity psiElement : psiElements) {
            TablePanel tablePanel = new TablePanel(psiElement);
            tableTabbedPane.addTab(psiElement.getName(), tablePanel.getContent());
            tablePanels.add(tablePanel);
        }
    }

    public List<RootModel> getRootModels() {
        List<RootModel> rootModels = new ArrayList<>();
        for (TablePanel tablePanel : tablePanels) {
            rootModels.add(tablePanel.toModel());
        }
        return rootModels;
    }

    @Override
    public JPanel getContent() {
        return content;
    }
}