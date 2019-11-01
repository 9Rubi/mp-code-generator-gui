package ink.rubi.coffee.po.holder

import com.baomidou.mybatisplus.annotation.FieldFill
import com.baomidou.mybatisplus.generator.config.ConstVal
import com.baomidou.mybatisplus.generator.config.INameConvert
import com.baomidou.mybatisplus.generator.config.StrategyConfig
import com.baomidou.mybatisplus.generator.config.po.TableFill
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy
import java.util.*

/**
 * @author : Rubi
 * @version : 2019-06-04 22:53 下午
 */
class StrategyConfigHolder(var isCapitalMode: Boolean = false,
                           var isSkipView: Boolean = false,
                           var nameConvert: INameConvert? = null,
                           var naming: String = "no_change",
                           var columnNaming: String? = null,
                           var tablePrefix: String? = null,
                           var fieldPrefix: String? = null,
                           var superEntityClass: String? = null,
                           var superEntityColumns: String? = null,
                           var superMapperClass: String = ConstVal.SUPER_MAPPER_CLASS,
                           var superServiceClass: String = ConstVal.SUPER_SERVICE_CLASS,
                           var superServiceImplClass: String = ConstVal.SUPER_SERVICE_IMPL_CLASS,
                           var superControllerClass: String? = null,
                           var include: String? = null,
                           var exclude: String? = null,
                           var isEntitySerialVersionUID: Boolean = true,
                           var isEntityColumnConstant: Boolean = false,
                           var isEntityBuilderModel: Boolean = false,
                           var isEntityLombokModel: Boolean = false,
                           var isEntityBooleanColumnRemoveIsPrefix: Boolean = false,
                           var isRestControllerStyle: Boolean = false,
                           var isControllerMappingHyphenStyle: Boolean = false,
                           var isEntityTableFieldAnnotationEnable: Boolean = false,
                           var versionFieldName: String? = null,
                           var logicDeleteFieldName: String? = null,
                           var tableFillList: Map<String, String>? = null) : ConfigHolder<StrategyConfig> {


    override fun convert(): StrategyConfig {
        return StrategyConfig()
                .setCapitalMode(this.isCapitalMode)
                .setSkipView(this.isSkipView)

                .setNameConvert(this.nameConvert)
                .setNaming(NamingStrategy.valueOf(this.naming))
                .setColumnNaming(NamingStrategy.valueOf(this.columnNaming!!))

                .setTablePrefix(*format(this.tablePrefix))
                .setFieldPrefix(*format(this.fieldPrefix))
                .setSuperEntityColumns(*format(this.superEntityColumns))
                .setInclude(*format(this.include))
                //                .setExclude(this.exclude)

                .setSuperEntityClass(this.superEntityClass)
                .setSuperMapperClass(this.superMapperClass)
                .setSuperServiceClass(this.superServiceClass)
                .setSuperServiceImplClass(this.superServiceImplClass)
                .setSuperControllerClass(this.superControllerClass)

                .setEntitySerialVersionUID(this.isEntitySerialVersionUID)
                .setEntityColumnConstant(this.isEntityColumnConstant)
                .setEntityBuilderModel(this.isEntityBuilderModel)
                .setEntityLombokModel(this.isEntityLombokModel)
                .setEntityBooleanColumnRemoveIsPrefix(this.isEntityBooleanColumnRemoveIsPrefix)
                .setRestControllerStyle(this.isRestControllerStyle)
                .setEntityTableFieldAnnotationEnable(this.isEntityTableFieldAnnotationEnable)
                .setVersionFieldName(this.versionFieldName)
                .setLogicDeleteFieldName(this.logicDeleteFieldName)

                .setTableFillList(convertToList(this.tableFillList))
    }

    companion object {

        fun convertToList(tableFillList: Map<String, String>?): List<TableFill>? {
            if (tableFillList == null) return null
            val list = ArrayList<TableFill>()
            tableFillList.forEach { (name, type) -> list.add(TableFill(name, FieldFill.valueOf(type))) }
            return list
        }
    }
}
