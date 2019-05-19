package ink.rubi.controller;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.generator.config.GlobalConfig;
import com.baomidou.mybatisplus.generator.config.rules.DateType;
import ink.rubi.App;
import ink.rubi.po.DateTypeItem;
import ink.rubi.po.IdTypeItem;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.stage.DirectoryChooser;
import javafx.util.StringConverter;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

/**
 * @author : Rubi
 * @version : 2019-05-17 22:34 下午
 */
@Slf4j
@Getter
public class GlobalController implements IController<GlobalConfig> {

    @FXML
    private TextField outputDir, author, entityName, mapperName, xmlName, serviceName, serviceImplName, controllerName;
    @FXML
    private CheckBox open, fileOverride, enableCache, kotlin, swagger2, activeRecord, baseResultMap, baseColumnList;
    @FXML
    private ChoiceBox<DateTypeItem> dateType;
    @FXML
    private ChoiceBox<IdTypeItem> idType;
    @FXML
    private GridPane globalPage;

    private MainController mainController;

    private DirectoryChooser chooseGenerateDirectory;

    private DateTypeItem defaultDateType;
    private IdTypeItem defaultIdType;

    @Override
    public void init(MainController mainController) {
        this.mainController = mainController;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        chooseGenerateDirectory = new DirectoryChooser();
        chooseGenerateDirectory.setTitle("选择导出目录");
        dateType.getItems().addAll(getDateTypes());
        defaultDateType =dateType.getItems().get(0);
        dateType.setValue(defaultDateType);
        dateType.converterProperty().set(new StringConverter<DateTypeItem>() {
            @Override
            public String toString(DateTypeItem object) {
                return object.getDescription();
            }

            @Override
            public DateTypeItem fromString(String string) {
                return null;
            }
        });
        idType.getItems().addAll(getIdTypes());
        defaultIdType =idType.getItems().get(3);
        idType.setValue(defaultIdType);
        idType.converterProperty().set(new StringConverter<IdTypeItem>() {
            @Override
            public String toString(IdTypeItem object) {
                return object.getDescription();
            }

            @Override
            public IdTypeItem fromString(String string) {
                return null;
            }
        });
    }

    public void choose(MouseEvent mouseEvent) {
        File file = chooseGenerateDirectory.showDialog(App.getWindow());
        if (file != null) {
            String path = file.getPath();
            outputDir.setText(path);
        }
    }

    private List<DateTypeItem> getDateTypes() {
        return new ArrayList<DateTypeItem>() {{
            add(new DateTypeItem(DateType.ONLY_DATE, "只使用 java.util.date 代替"));
            add(new DateTypeItem(DateType.SQL_PACK, "使用 java.sql 包下的"));
            add(new DateTypeItem(DateType.TIME_PACK, "使用 java.time 包下的,java8 新的时间类型"));
        }};
    }

    private List<IdTypeItem> getIdTypes() {
        return new ArrayList<IdTypeItem>() {{
            add(new IdTypeItem(IdType.AUTO, "数据库ID自增"));
            add(new IdTypeItem(IdType.NONE, "该类型为未设置主键类型"));
            add(new IdTypeItem(IdType.INPUT, "用户输入ID,可以通过自己注册自动填充插件进行填充"));
            add(new IdTypeItem(IdType.ID_WORKER, "全局唯一ID (idWorker)"));
            add(new IdTypeItem(IdType.UUID, "全局唯一ID (UUID)"));
            add(new IdTypeItem(IdType.ID_WORKER_STR, "字符串全局唯一ID (idWorker 的字符串表示)"));
        }};
    }


    @Override
    public GlobalConfig getConfig() {
        return new GlobalConfig().setAuthor(author.getText()).setOutputDir(outputDir.getText())
                .setFileOverride(fileOverride.isSelected())
                .setEnableCache(enableCache.isSelected())
                .setActiveRecord(activeRecord.isSelected()).setKotlin(kotlin.isSelected()).setSwagger2(swagger2.isSelected())
                .setBaseResultMap(baseResultMap.isSelected()).setBaseColumnList(baseColumnList.isSelected())
                .setEntityName(entityName.getText()).setMapperName(mapperName.getText()).setXmlName(xmlName.getText())
                .setServiceName(serviceName.getText()).setServiceImplName(serviceImplName.getText()).setControllerName(controllerName.getText())
                .setIdType(idType.getSelectionModel().selectedItemProperty().getValue().getIdType())
                .setDateType(dateType.getSelectionModel().selectedItemProperty().getValue().getDateType())
                .setOpen(open.isSelected());

    }

    @Override
    public void flushConfig(GlobalConfig globalConfig) {
        outputDir.setText(globalConfig.getOutputDir());
        author.setText(globalConfig.getAuthor());
        entityName.setText(globalConfig.getEntityName());
        mapperName.setText(globalConfig.getMapperName());
        xmlName.setText(globalConfig.getXmlName());
        serviceName.setText(globalConfig.getServiceName());
        serviceImplName.setText(globalConfig.getServiceImplName());
        controllerName.setText(globalConfig.getControllerName());
        open.setSelected(globalConfig.isOpen());
        fileOverride.setSelected(globalConfig.isFileOverride());
        enableCache.setSelected(globalConfig.isEnableCache());
        kotlin.setSelected(globalConfig.isKotlin());
        swagger2.setSelected(globalConfig.isSwagger2());
        activeRecord.setSelected(globalConfig.isActiveRecord());
        baseResultMap.setSelected(globalConfig.isBaseResultMap());
        baseColumnList.setSelected(globalConfig.isBaseColumnList());
        matchChoice(dateType, "dateType", globalConfig.getDateType(), defaultDateType);
        matchChoice(idType, "idType", globalConfig.getIdType(), defaultIdType);

    }
}
