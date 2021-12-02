package com.xiaobai.cms.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xiaobai.cms.entity.CrmBanner;
import com.xiaobai.cms.entity.vo.BannerQuery;
import com.xiaobai.cms.mapper.CrmBannerMapper;
import com.xiaobai.cms.service.CrmBannerService;
import org.apache.commons.lang.StringUtils;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 首页banner表 服务实现类
 * </p>
 *
 * @author xiaobai
 * @since 2021-08-03
 */
@Service
public class CrmBannerServiceImpl extends ServiceImpl<CrmBannerMapper, CrmBanner> implements CrmBannerService {

    //条件查询带分页
    @Override
    public Page<CrmBanner> pageBannerCondition(Long current, Long limit, BannerQuery bannerQuery) {
        //创建page对象
        Page<CrmBanner> pageBanner = new Page<>(current,limit);
        //构建查询条件
        QueryWrapper<CrmBanner> wrapper = new QueryWrapper<>();

        String title = bannerQuery.getTitle();
        String begin = bannerQuery.getBegin();
        String end = bannerQuery.getEnd();

        if (!StringUtils.isEmpty(title)){
            wrapper.like("title",title);
        }
        if (!StringUtils.isEmpty(begin)){
            wrapper.ge("gmt_create",begin);
        }
        if (!StringUtils.isEmpty(end)){
            wrapper.le("gmt_create",end);
        }

        //根绝创建时间排序
        wrapper.orderByDesc("gmt_create");
        //调用方法实现分页查询
        this.page(pageBanner,wrapper);

        return pageBanner;
    }

    //查询前两个banner
    @Cacheable(key = "'selectIndexList'",value = "banner")
    @Override
    public List<CrmBanner> selectAllBanner() {
        QueryWrapper<CrmBanner> wrapper = new QueryWrapper<>();
        wrapper.orderByDesc("gmt_create");
        wrapper.last("limit  2");
        List<CrmBanner> banners = baseMapper.selectList(wrapper);
        return banners;
    }
}
