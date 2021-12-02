package com.xiaobai.eduservice.entity.excel;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

/**
 * @author xiaobai
 * @create 2021-07-20 9:52
 */
@Data
public class SubjectData {
    @ExcelProperty(index=0)
    private String oneSubjectName;
    @ExcelProperty(index=1)
    private String twoSubjectName;
}
