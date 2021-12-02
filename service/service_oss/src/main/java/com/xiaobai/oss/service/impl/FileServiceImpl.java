package com.xiaobai.oss.service.impl;


import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClient;
import com.xiaobai.oss.service.FileService;
import com.xiaobai.oss.utils.ConstantPropertiesUtil;
import org.joda.time.DateTime;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.UUID;

/**
 * @author xiaobai
 * @create 2021-07-19 9:29
 */
@Service
public class FileServiceImpl implements FileService {
    @Override
    public String upload(MultipartFile file) {

        //获取阿里云存储相关的常量
        String endPoint= ConstantPropertiesUtil.END_POINT;
        String accesskeyId=ConstantPropertiesUtil.ACCESS_KEY_ID;
        String accessKeySecret=ConstantPropertiesUtil.ACCESS_KEY_SECRET;
        String bucketName=ConstantPropertiesUtil.BUCKET_NAME;
        try {
            // 创建OSSClient实例
            OSS ossClient = new OSSClient(endPoint, accesskeyId, accessKeySecret);

            //获取文件名称
            String filename = file.getOriginalFilename();


            //上传文件流
            InputStream inputStream = file.getInputStream();

            //1.在文件名称里面添加随机唯一的值
            String uuid = UUID.randomUUID().toString().replaceAll("-", "");
            filename=uuid+filename;
            //2.把文件按照日期进行分类
            //获取当前的日期
            String datePath = new DateTime().toString("yyyy/MM/dd");

            //拼接
            filename=datePath+"/"+filename;

            //调用oss的方法实现上传
            //第一个参数：bucket名称
            //第二个参数: 上传到oss的文件路径和文件名称
            //第三个参数：上传文件输入流
            ossClient.putObject(bucketName,filename,inputStream);

            //关闭ossClient
            ossClient.shutdown();

            //把上传后的文件路径返回
            //需要把上传到阿里云oss路径手动拼接出来
            //https://edu--1001.oss-cn-beijing.aliyuncs.com/QQ%E5%9B%BE%E7%89%8720210629124651.jpg
            String url="http://" + bucketName + "." + endPoint + "/" + filename;
            return url;

        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
}
