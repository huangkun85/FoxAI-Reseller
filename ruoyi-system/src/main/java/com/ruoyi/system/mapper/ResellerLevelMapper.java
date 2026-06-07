package com.ruoyi.system.mapper;

import java.util.List;
import com.ruoyi.system.domain.ResellerLevel;

public interface ResellerLevelMapper
{
    public ResellerLevel selectResellerLevelById(Long id);

    public List<ResellerLevel> selectResellerLevelList(ResellerLevel resellerLevel);

    public int insertResellerLevel(ResellerLevel resellerLevel);

    public int updateResellerLevel(ResellerLevel resellerLevel);

    public int deleteResellerLevelByIds(Long[] ids);

    public ResellerLevel checkLevelCodeUnique(String levelCode);
}
