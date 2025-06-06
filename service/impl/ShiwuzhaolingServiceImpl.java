package com.service.impl;

import com.utils.StringUtil;
import com.service.DictionaryService;
import com.utils.ClazzDiff;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.lang.reflect.Field;
import java.util.*;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.transaction.annotation.Transactional;
import com.utils.PageUtils;
import com.utils.Query;
import org.springframework.web.context.ContextLoader;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import org.springframework.lang.Nullable;
import org.springframework.util.Assert;
import com.dao.ShiwuzhaolingDao;
import com.entity.ShiwuzhaolingEntity;
import com.service.ShiwuzhaolingService;
import com.entity.view.ShiwuzhaolingView;

/**
 * 失物信息 服务实现类
 */
@Service("shiwuzhaolingService")
@Transactional
public class ShiwuzhaolingServiceImpl extends ServiceImpl<ShiwuzhaolingDao, ShiwuzhaolingEntity> implements ShiwuzhaolingService {

    @Override
    public PageUtils queryPage(Map<String,Object> params) {
        Page<ShiwuzhaolingView> page =new Query<ShiwuzhaolingView>(params).getPage();
        page.setRecords(baseMapper.selectListView(page,params));
        return new PageUtils(page);
    }
    @Override
    public List<Map<String, Object>> groupByType() {
        return baseMapper.selectGroupByType();
    }

    @Override
    public List<Map<String, Object>> monthlyClaimStats(String month) {
        if(StringUtils.isNotBlank(month)){
            return baseMapper.selectMonthlyClaimStatsByMonth(month);
        }else{
            return baseMapper.selectMonthlyClaimStats();
        }
    }
    @Override
    public List<Map<String, Object>> groupByStatus() {
        return baseMapper.selectGroupByStatus();
    }


}
