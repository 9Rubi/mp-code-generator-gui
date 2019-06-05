package ink.rubi.codegenerator.controller;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.generator.config.rules.DateType;
import ink.rubi.codegenerator.App;
import ink.rubi.codegenerator.controller.converter.ItemStringConverter;
import ink.rubi.codegenerator.po.DateTypeItem;
import ink.rubi.codegenerator.po.IdTypeItem;
import ink.rubi.codegenerator.po.holder.GlobalConfigHolder;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.stage.DirectoryChooser;
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
public class GlobalController implements IController<GlobalConfigHolder> {

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


    private static final List<DateTypeItem> dateTypes = new ArrayList<DateTypeItem>() {{
        add(new DateTypeItem(DateType.ONLY_DATE, "只使用 java.util.date 代替"));
        add(new DateTypeItem(DateType.SQL_PACK, "使用 java.sql 包下的"));
        add(new DateTypeItem(DateType.TIME_PACK, "使用 java.time 包下的,java8 新的时间类型"));
    }};

    private static final List<IdTypeItem> idTypes = new ArrayList<IdTypeItem>() {{
        add(new IdTypeItem(IdType.AUTO, "数据库ID自增"));
        add(new IdTypeItem(IdType.NONE, "该类型为未设置主键类型"));
        add(new IdTypeItem(IdType.INPUT, "用户输入ID,可以通过自己注册自动填充插件进行填充"));
        add(new IdTypeItem(IdType.ID_WORKER, "全局唯一ID (idWorker)"));
        add(new IdTypeItem(IdType.UUID, "全局唯一ID (UUID)"));
        add(new IdTypeItem(IdType.ID_WORKER_STR, "字符串全局唯一ID (idWorker 的字符串表示)"));
    }};

    private static final DateTypeItem defaultDateType = dateTypes.get(0);
    private static final IdTypeItem defaultIdType = idTypes.get(0);


    @Override
    public void init(MainController mainController) {
        this.mainController = mainController;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        chooseGenerateDirectory = new DirectoryChooser();
        chooseGenerateDirectory.setTitle("选择导出目录");
        dateType.getItems().addAll(dateTypes);
        dateType.setValue(defaultDateType);
        dateType.converterProperty().set(new ItemStringConverter<>());
        idType.getItems().addAll(idTypes);
        idType.setValue(defaultIdType);
        idType.converterProperty().set(new ItemStringConverter<>());
    }

    public void choose(MouseEvent mouseEvent) {
        File file = chooseGenerateDirectory.showDialog(App.getWindow());
        if (file != null) {
            String path = file.getPath();
            outputDir.setText(path);
        }
    }


    @Override
    public GlobalConfigHolder getConfigHolder() {
        return new GlobalConfigHolder().setAuthor(author.getText())
                .setOutputDir(outputDir.getText())
                .setFileOverride(fileOverride.isSelected())
                .setEnableCache(enableCache.isSelected())
                .setActiveRecord(activeRecord.isSelected())
                .setKotlin(kotlin.isSelected())
                .setSwagger2(swagger2.isSelected())
                .setBaseResultMap(baseResultMap.isSelected())
                .setBaseColumnList(baseColumnList.isSelected())
                .setEntityName(entityName.getText())
                .setMapperName(mapperName.getText())
                .setXmlName(xmlName.getText())
                .setServiceName(serviceName.getText())
                .setServiceImplName(serviceImplName.getText())
                .setControllerName(controllerName.getText())
                .setIdType(idType.getSelectionModel().selectedItemProperty().getValue().getIdType().name())
                .setDateType(dateType.getSelectionModel().selectedItemProperty().getValue().getDateType().name())
                .setOpen(open.isSelected());

    }

    @Override
    public void flushConfig(GlobalConfigHolder holder) {
        outputDir.setText(holder.getOutputDir());
        author.setText(holder.getAuthor());
        entityName.setText(holder.getEntityName());
        mapperName.setText(holder.getMapperName());
        xmlName.setText(holder.getXmlName());
        serviceName.setText(holder.getServiceName());
        serviceImplName.setText(holder.getServiceImplName());
        controllerName.setText(holder.getControllerName());
        open.setSelected(holder.isOpen());
        fileOverride.setSelected(holder.isFileOverride());
        enableCache.setSelected(holder.isEnableCache());
        kotlin.setSelected(holder.isKotlin());
        swagger2.setSelected(holder.isSwagger2());
        activeRecord.setSelected(holder.isActiveRecord());
        baseResultMap.setSelected(holder.isBaseResultMap());
        baseColumnList.setSelected(holder.isBaseColumnList());
        matchChoice(dateType, DateType.class, holder.getDateType(), defaultDateType);
        matchChoice(idType, IdType.class, holder.getIdType(), defaultIdType);

    }
}
