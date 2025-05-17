package com.dao;

import com.entity.ShiwuzhaolingEntity;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import java.util.List;
import java.util.Map;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;

import org.apache.ibatis.annotations.Param;
import com.entity.view.ShiwuzhaolingView;

/**
 * 失物信息 Dao 接口
 *
 * @author 
 */
public interface ShiwuzhaolingDao extends BaseMapper<ShiwuzhaolingEntity> {

   List<ShiwuzhaolingView> selectListView(Pagination page,@Param("params")Map<String,Object> params);
   // 失物类型分组统计
   List<Map<String, Object>> selectGroupByType();

   // 月度认领统计（按月份筛选）
   List<Map<String, Object>> selectMonthlyClaimStatsByMonth(String month);

   // 月度认领统计（全部）
   List<Map<String, Object>> selectMonthlyClaimStats();
   // 认领状态分组统计
   List<Map<String, Object>> selectGroupByStatus();

}
