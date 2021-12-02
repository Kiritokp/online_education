package com.xiaobai.cms.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xiaobai.cms.entity.CrmBanner;
import com.xiaobai.cms.entity.vo.BannerQuery;
import com.xiaobai.cms.service.CrmBannerService;
import com.xiaobai.commonutils.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 首页banner表 前端控制器
 * </p>
 *
 * @author xiaobai
 * @since 2021-08-03
 */
@Api(description = "后台banner管理")
@RestController
@RequestMapping("/educms/banneradmin")
public class BannerAdminController {

    @Autowired
    private CrmBannerService crmBannerService;

    @ApiOperation(value = "banner条件查询带分页")
    @PostMapping("pageBannerCondition/{current}/{limit}")
    public R pageBannerCondition(@PathVariable("current") Long current,
                                 @PathVariable("limit") Long limit,
                                 @RequestBody(required = false)BannerQuery bannerQuery){
        Page<CrmBanner> pageBanner=crmBannerService.pageBannerCondition(current,limit,bannerQuery);
        long total = pageBanner.getTotal();
        List<CrmBanner> records = pageBanner.getRecords();

        return R.ok().data("total",total).data("rows",records);
    }

    @ApiOperation(value = "获取banner")
    @GetMapping("getBanner/{id}")
    public R getBanner(@PathVariable("id") String id){
        CrmBanner crmBanner = crmBannerService.getById(id);
        return R.ok().data("banner",crmBanner);
    }

    @ApiOperation(value = "添加banner")
    @PostMapping("addBanner")
    public R addBanner(@RequestBody CrmBanner crmBanner){
        boolean save = crmBannerService.save(crmBanner);
        if (!save){
            return R.error().message("添加失败");
        }
        return R.ok();
    }

    @ApiOperation(value = "修改Banner")
    @PostMapping("editBanner")
    public R editBanner(@RequestBody CrmBanner crmBanner){
        boolean update = crmBannerService.updateById(crmBanner);
        if (!update){
            return R.error().message("修改失败");
        }
        return R.ok();
    }

    @ApiOperation(value = "删除banner")
    @DeleteMapping("removeBanner/{id}")
    public R removeBanner(@PathVariable("id") String id){
        boolean remove = crmBannerService.removeById(id);
        if (!remove){
            return R.error().message("删除失败");
        }
        return R.ok();
    }

}

