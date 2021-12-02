package com.xiaobai.eduservice.entity.chapter;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * @author xiaobai
 * @create 2021-07-23 14:30
 */
@Data
public class ChapterVo {
    private String id;
    private String title;

    //表示小结
    private List<VideoVo> children=new ArrayList<>();
}
