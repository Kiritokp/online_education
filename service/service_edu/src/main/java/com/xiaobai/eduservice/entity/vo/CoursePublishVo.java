package com.xiaobai.eduservice.entity.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * @author xiaobai
 * @create 2021-07-27 13:50
 */
@Data
public class CoursePublishVo  implements Serializable {

    private static final long serialVersionUID = 1L;
    private String id;
    private String title;
    private String cover;
    private Integer lessonNum;
    private String subjectLevelOne;
    private String subjectLevelTwo;
    private String teacherName;
    private String price;//只用于显示
}
