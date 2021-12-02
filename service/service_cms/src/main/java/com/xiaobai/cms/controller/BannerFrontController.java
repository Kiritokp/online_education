package com.xiaobai.cms.controller;


import com.xiaobai.cms.entity.CrmBanner;
import com.xiaobai.cms.service.CrmBannerService;
import com.xiaobai.commonutils.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 * 首页banner表 前端控制器
 * </p>
 *
 * @author xiaobai
 * @since 2021-08-03
 */
@Api(description = "前台banner管理")
@RestController
@RequestMapping("/educms/bannerfront")
public class BannerFrontController {
    @Autowired
    private CrmBannerService crmBannerService;
    @ApiOperation(value = "获得前两个banner")
    @GetMapping("getAllBanner")
    public R getAllBanner(){
        List<CrmBanner> bannerList=crmBannerService.selectAllBanner();
        return R.ok().data("bannerList",bannerList);
    }
}

