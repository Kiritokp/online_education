package com.xiaobai.cms.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xiaobai.cms.entity.CrmBanner;
import com.baomidou.mybatisplus.extension.service.IService;
import com.xiaobai.cms.entity.vo.BannerQuery;

import java.util.List;

/**
 * <p>
 * 首页banner表 服务类
 * </p>
 *
 * @author xiaobai
 * @since 2021-08-03
 */
public interface CrmBannerService extends IService<CrmBanner> {

    Page<CrmBanner> pageBannerCondition(Long current, Long limit, BannerQuery bannerQuery);

    List<CrmBanner> selectAllBanner();
}
