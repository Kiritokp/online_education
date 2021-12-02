package com.xiaobai.eduservice.service.impl;

import com.alibaba.excel.EasyExcel;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xiaobai.eduservice.entity.EduSubject;
import com.xiaobai.eduservice.entity.excel.SubjectData;
import com.xiaobai.eduservice.entity.subject.OneSubject;
import com.xiaobai.eduservice.entity.subject.TwoSubject;
import com.xiaobai.eduservice.listener.SubjectExcelListener;
import com.xiaobai.eduservice.mapper.EduSubjectMapper;
import com.xiaobai.eduservice.service.EduSubjectService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 课程科目 服务实现类
 * </p>
 *
 * @author xiaobai
 * @since 2021-07-20
 */
@Service
public class EduSubjectServiceImpl extends ServiceImpl<EduSubjectMapper, EduSubject> implements EduSubjectService {

    //添加课程分类
    @Override
    public void saveSubject(MultipartFile file,EduSubjectService eduSubjectService) {
        try {
            //文件输入流
            InputStream inputStream = file.getInputStream();
            EasyExcel.read(inputStream, SubjectData.class,new SubjectExcelListener(eduSubjectService)).sheet().doRead();
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    //课程分类列表
    @Override
    public List<OneSubject> getAllOneTwoSubject() {
        //1 查询出所有的一级分类
        QueryWrapper<EduSubject> wrapperOne = new QueryWrapper<>();
        wrapperOne.eq("parent_id","0");
        List<EduSubject> oneEduSubjects = baseMapper.selectList(wrapperOne);

        //2 查询出所有的二级分类
        QueryWrapper<EduSubject> wrapperTwo = new QueryWrapper<>();
        wrapperTwo.ne("parent_id","0");
        List<EduSubject> twoEduSubjects = baseMapper.selectList(wrapperTwo);

        //创建list集合，用于存储最终存放的数据
        List<OneSubject> finalSubjects = new ArrayList<>();

        //3 封装所有的一级分类
        for (int i = 0; i < oneEduSubjects.size(); i++) {
            EduSubject eduOneSubject = oneEduSubjects.get(i);
            OneSubject oneSubject = new OneSubject();
            /*oneSubject.setId(eduSubject.getId());
            oneSubject.setTitle(eduSubject.getTitle());*/

            BeanUtils.copyProperties(eduOneSubject,oneSubject);
            finalSubjects.add(oneSubject);

            //4 封装所有的二级分类
            List<TwoSubject> twoFinalSubjects = new ArrayList<>();
            for (int j = 0; j < twoEduSubjects.size(); j++) {
                EduSubject eduTwoSubject = twoEduSubjects.get(j);
                if (eduTwoSubject.getParentId().equals(oneSubject.getId())){
                    TwoSubject twoSubject = new TwoSubject();
                    BeanUtils.copyProperties(eduTwoSubject,twoSubject);
                    twoFinalSubjects.add(twoSubject);
                }
            }

            oneSubject.setChildren(twoFinalSubjects);
        }
        return finalSubjects;
    }

    //查询所有的一级分类
    @Override
    public List<EduSubject> findAllOneSubject() {
        QueryWrapper<EduSubject> wrapper = new QueryWrapper<>();
        wrapper.eq("parent_id","0");
        List<EduSubject> eduOneSubjects = baseMapper.selectList(wrapper);
        return eduOneSubjects;
    }

    //根据一级分类的id查询所有的二级分类
    @Override
    public List<EduSubject> findTwoSubject(String id) {
        QueryWrapper<EduSubject> wrapper = new QueryWrapper<>();
        wrapper.eq("parent_id",id);
        List<EduSubject> eduTwoSubjects = baseMapper.selectList(wrapper);
        return eduTwoSubjects;
    }
}
