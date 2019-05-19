package ink.rubi.controller;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.generator.config.DataSourceConfig;
import com.baomidou.mybatisplus.generator.config.converts.*;
import com.baomidou.mybatisplus.generator.config.querys.*;
import ink.rubi.po.DbQueryItem;
import ink.rubi.po.DbTypeItem;
import ink.rubi.po.TypeConvertItem;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.util.StringConverter;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Field;
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
public class DataSourceController implements IController<DataSourceConfig> {
    @FXML
    private ChoiceBox<DbTypeItem> dbType;
    @FXML
    private ChoiceBox<DbQueryItem> dbQuery;
    @FXML
    private ChoiceBox<TypeConvertItem> typeConvert;
    @FXML
    private GridPane dataSourcePage;
    @FXML
    private TextField url, driverName, username, password, schemaName;

    private DbTypeItem defaultDbType;
    private DbQueryItem defaultDbQuery;
    private TypeConvertItem defaultTypeConvert;
    private MainController mainController;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        dbType.getItems().addAll(getDbTypes());
        defaultDbType = dbType.getItems().get(0);
        dbType.setValue(defaultDbType);
        dbType.converterProperty().set(new StringConverter<DbTypeItem>() {
            @Override
            public String toString(DbTypeItem object) {
                return object.getDescription();
            }

            @Override
            public DbTypeItem fromString(String string) {
                return null;
            }
        });

        dbQuery.getItems().addAll(getDbQuerys());
        defaultDbQuery = dbQuery.getItems().get(3);
        dbQuery.setValue(defaultDbQuery);
        dbQuery.converterProperty().set(new StringConverter<DbQueryItem>() {
            @Override
            public String toString(DbQueryItem object) {
                return object.getDescription();
            }

            @Override
            public DbQueryItem fromString(String string) {
                return null;
            }
        });


        typeConvert.getItems().addAll(getTypeConverts());
        defaultTypeConvert = typeConvert.getItems().get(2);
        typeConvert.setValue(defaultTypeConvert);

        typeConvert.converterProperty().set(new StringConverter<TypeConvertItem>() {
            @Override
            public String toString(TypeConvertItem object) {
                return object.getDescription();
            }

            @Override
            public TypeConvertItem fromString(String string) {
                return null;
            }
        });


    }


    private List<DbTypeItem> getDbTypes() {
        return new ArrayList<DbTypeItem>() {{
            add(new DbTypeItem(DbType.MYSQL));
            add(new DbTypeItem(DbType.MARIADB));
            add(new DbTypeItem(DbType.ORACLE));
            add(new DbTypeItem(DbType.DB2));
            add(new DbTypeItem(DbType.H2));
            add(new DbTypeItem(DbType.HSQL));
            add(new DbTypeItem(DbType.SQLITE));
            add(new DbTypeItem(DbType.POSTGRE_SQL));
            add(new DbTypeItem(DbType.SQL_SERVER2005));
            add(new DbTypeItem(DbType.SQL_SERVER));
            add(new DbTypeItem(DbType.DM));
            add(new DbTypeItem(DbType.OTHER));
        }};
    }

    private List<DbQueryItem> getDbQuerys() {
        return new ArrayList<DbQueryItem>() {{
            add(new DbQueryItem(new DB2Query(), "DB2Query"));
            add(new DbQueryItem(new H2Query(), "H2Query"));
            add(new DbQueryItem(new MariadbQuery(), "MariadbQuery"));
            add(new DbQueryItem(new MySqlQuery(), "MySqlQuery"));
            add(new DbQueryItem(new OracleQuery(), "OracleQuery"));
            add(new DbQueryItem(new PostgreSqlQuery(), "PostgreSqlQuery"));
            add(new DbQueryItem(new SqlServerQuery(), "SqlServerQuery"));
        }};
    }

    private List<TypeConvertItem> getTypeConverts() {
        return new ArrayList<TypeConvertItem>() {{
            add(new TypeConvertItem(new DB2TypeConvert(), "DB2 字段类型转换"));
            add(new TypeConvertItem(new SqlServerTypeConvert(), "SQLServer 字段类型转换"));
            add(new TypeConvertItem(new MySqlTypeConvert(), "MYSQL 字段类型转换"));
            add(new TypeConvertItem(new PostgreSqlTypeConvert(), "PostgreSQL 字段类型转换"));
            add(new TypeConvertItem(new OracleTypeConvert(), "ORACLE 字段类型转换"));
        }};
    }

    @Override
    public DataSourceConfig getConfig() {
        return new DataSourceConfig()
                .setUrl(url.getText())
                .setDbQuery(dbQuery.getSelectionModel().selectedItemProperty().getValue().getDbQuery())
                .setDbType(dbType.getSelectionModel().selectedItemProperty().getValue().getDbType())
                .setTypeConvert(typeConvert.getSelectionModel().selectedItemProperty().getValue().getTypeConvert())
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
    public void flushConfig(DataSourceConfig dataSourceConfig) {
        url.setText(dataSourceConfig.getUrl());
        driverName.setText(dataSourceConfig.getDriverName());
        username.setText(dataSourceConfig.getUsername());
        password.setText(dataSourceConfig.getPassword());
        schemaName.setText(dataSourceConfig.getSchemaName());
        matchChoice(dbType, "dbType", dataSourceConfig.getDbType(), defaultDbType);
        matchChoice(dbQuery, "dbQuery", dataSourceConfig.getDbQuery(), defaultDbQuery);
        matchChoice(typeConvert, "typeConvert", dataSourceConfig.getTypeConvert(), defaultTypeConvert);
    }


}
