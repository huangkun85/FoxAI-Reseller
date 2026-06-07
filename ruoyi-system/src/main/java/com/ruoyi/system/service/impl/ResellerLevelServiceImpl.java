package com.ruoyi.system.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.common.core.text.Convert;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.system.domain.ResellerLevel;
import com.ruoyi.system.mapper.ResellerLevelMapper;
import com.ruoyi.system.service.IResellerLevelService;

@Service
public class ResellerLevelServiceImpl implements IResellerLevelService
{
    @Autowired
    private ResellerLevelMapper resellerLevelMapper;

    @Override
    public ResellerLevel selectResellerLevelById(Long id)
    {
        return resellerLevelMapper.selectResellerLevelById(id);
    }

    @Override
    public List<ResellerLevel> selectResellerLevelList(ResellerLevel resellerLevel)
    {
        return resellerLevelMapper.selectResellerLevelList(resellerLevel);
    }

    @Override
    public int insertResellerLevel(ResellerLevel resellerLevel)
    {
        return resellerLevelMapper.insertResellerLevel(resellerLevel);
    }

    @Override
    public int updateResellerLevel(ResellerLevel resellerLevel)
    {
        return resellerLevelMapper.updateResellerLevel(resellerLevel);
    }

    @Override
    public int deleteResellerLevelByIds(Long[] ids)
    {
        return resellerLevelMapper.deleteResellerLevelByIds(ids);
    }

    @Override
    public boolean checkLevelCodeUnique(ResellerLevel resellerLevel)
    {
        Long id = StringUtils.isNull(resellerLevel.getId()) ? -1L : resellerLevel.getId();
        ResellerLevel exist = resellerLevelMapper.checkLevelCodeUnique(resellerLevel.getLevelCode());
        if (StringUtils.isNotNull(exist) && !exist.getId().equals(id))
        {
            return false;
        }
        return true;
    }
}
