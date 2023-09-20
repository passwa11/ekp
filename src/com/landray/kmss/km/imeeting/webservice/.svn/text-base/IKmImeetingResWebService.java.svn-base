package com.landray.kmss.km.imeeting.webservice;
import com.landray.kmss.sys.webservice2.interfaces.ISysWebservice;

import javax.jws.WebService;

/**
 * @Author suqi
 * @Date 2021/12/1
 * @Desc: 会议室信息资源相关webservice接口
 */
@WebService
public interface IKmImeetingResWebService extends ISysWebservice {
    /**
     * 会议室资源查询列表
     * @return
     */
    public KmImeetingResResult getKmImeetingResList(KmImeetingResContext context) throws Exception;

    /**
     * 根据会议室ID查询会议室详情
     * @param fdId
     * @return
     * @throws Exception
     */
    public KmImeetingResResult getKmimeetingResById(String fdId) throws Exception;

    /**
     * 新增会议室接口
     * @param kmImeetingResParamterForm
     * @return
     * @throws Exception
     */
    public KmImeetingResResult addKmImeetingRes(KmImeetingResParamterForm kmImeetingResParamterForm) throws Exception;

    /**
     * 更新会议室接口
     * @param kmImeetingResParamterForm
     * @return
     */
    public KmImeetingResResult updateKmImeetingRes(KmImeetingResParamterForm kmImeetingResParamterForm) throws Exception;

    /**
     *  删除会议室接口
     * @param kmImeetingResParamterForm
     * @return
     */
    public KmImeetingResResult deleteKmImeetingRes(KmImeetingResParamterForm kmImeetingResParamterForm) throws Exception;
}
