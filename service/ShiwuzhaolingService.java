package com.service;

import com.baomidou.mybatisplus.service.IService;
import com.utils.PageUtils;
import com.entity.ShiwuzhaolingEntity;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.springframework.lang.Nullable;
import java.util.List;

/**
 * 失物信息 服务类
 */
public interface ShiwuzhaolingService extends IService<ShiwuzhaolingEntity> {

    /**
    * @param params 查询参数
    * @return 带分页的查询出来的数据
    */
     PageUtils queryPage(Map<String, Object> params);
    // 失物类型分组统计
    List<Map<String, Object>> groupByType();

    // 月度认领统计
    List<Map<String, Object>> monthlyClaimStats(String month);
    // 认领状态分组统计
    List<Map<String, Object>> groupByStatus();
    //从数据源（如数据库）中查询数据，并按某个字段（如“认领状态”）分组统计

}