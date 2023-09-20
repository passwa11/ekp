package com.landray.kmss.third.ding.service;

import com.landray.kmss.sys.metadata.interfaces.IExtendDataService;
import net.sf.json.JSONObject;

import java.util.Date;
import java.util.List;
import java.util.Map;

public interface IThirdDingClockService extends IExtendDataService {

    public List<JSONObject> getUserDingClock(Date startTime, Date endTime, List<String> idList) throws Exception;

    /**
     * 获取考勤配置相关信息,如corpid,agentid(钉钉考勤应用id)
     *
     * @return
     */
    public Map getAttendConfig();

    /**
     * 更新钉钉调用速度
     *
     * @param syncRateLimiter
     * @description:
     * @return: void
     * @author: wangjf
     * @time: 2022/3/2 4:10 下午
     */
    void updateRate(String syncRateLimiter);

}
