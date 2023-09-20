package com.landray.kmss.common.interfaces;

import com.landray.kmss.sys.organization.model.SysOrgElement;

import java.util.List;

public interface IMeetingTopicProvider {

    //释放会议
    public void freedMeetingDoc(String fdModelId, String fdModelName) throws Exception;

    //删除议题
    public void deleteTopic(String fdModelId, String fdModelName) throws Exception;

    public List<SysOrgElement> getViewSumPersons(String fdModelId, String fdModelName) throws Exception;

    //获取会议纪要通知人员
    public List<SysOrgElement> getSummaryNotifyPerson(String fdModelId, String fdModelName) throws Exception;

    //激活议题流程
    public void updateTopicProcess(String fdModelId, String fdModelName) throws Exception;

    //更新会议的纪要状态
    public void updateMeetingSymmaryFlag(String fdModelId, String fdModelName,String fdMainId,boolean flag ,boolean removeTodo) throws Exception;
}
