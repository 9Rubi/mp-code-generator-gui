package ink.rubi.codegenerator.controller;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.generator.config.DataSourceConfig;
import com.baomidou.mybatisplus.generator.config.converts.*;
import com.baomidou.mybatisplus.generator.config.querys.*;
import ink.rubi.codegenerator.po.DbQueryItem;
import ink.rubi.codegenerator.po.DbTypeItem;
import ink.rubi.codegenerator.po.TypeConvertItem;
import ink.rubi.codegenerator.po.holder.DataSourceConfigHolder;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.util.StringConverter;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

/**
 * @author : Rubi
 * @version : 2019-05-17 23:27 下午
 */
@Getter
@Slf4j
public class DataSourceController implements IController<DataSourceConfigHolder> {

    @FXML
    private GridPane dataSourcePage;
    @FXML
    private TextField url, driverName, username, password, schemaName;

    private MainController mainController;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    @Override
    public DataSourceConfigHolder getConfigHolder() {
        return new DataSourceConfigHolder()
                .setUrl(url.getText())
                .setSchemaName(schemaName.getText())
                .setDriverName(driverName.getText())
                .setUsername(username.getText())
                .setPassword(password.getText());

    }

    @Override
    public void init(MainController mainController) {
        this.mainController = mainController;
    }

    @Override
    public void flushConfig(DataSourceConfigHolder dataSourceConfig) {
        url.setText(dataSourceConfig.getUrl());
        driverName.setText(dataSourceConfig.getDriverName());
        username.setText(dataSourceConfig.getUsername());
        password.setText(dataSourceConfig.getPassword());
        schemaName.setText(dataSourceConfig.getSchemaName());
    }


}
