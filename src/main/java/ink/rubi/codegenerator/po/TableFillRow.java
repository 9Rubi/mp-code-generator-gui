package ink.rubi.codegenerator.po;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Rubi
 * @since 2019-06-05 10:52
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TableFillRow {
    private String fieldName;
    private FieldFillItem fieldFillItem;
}
