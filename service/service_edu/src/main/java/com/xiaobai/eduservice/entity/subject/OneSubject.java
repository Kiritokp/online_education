package com.xiaobai.eduservice.entity.subject;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * @author xiaobai
 * @create 2021-07-21 13:14
 */
@Data
//一级分类
public class OneSubject {
    private String id;
    private String title;

    //一个一级分类中有多个二级分类
    private List<TwoSubject> children=new ArrayList<>();
}
