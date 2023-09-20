package com.landray.kmss.km.imeeting.webservice;

import com.landray.kmss.sys.webservice2.interfaces.ISysWebservice;

import javax.jws.WebService;

/**
 * @Author suqi
 * @Date 2021/12/1
 * @Desc: 会议室预定 webservice接口
 */
@WebService
public interface IKmImeetingBookWebService extends ISysWebservice {
    /**
     * 获取所有会议室预定列表
     * @param bookform
     * @return
     */
    public KmImeetingBookResulut getImeetingBookLists(KmImeetingBookParamterForm bookform) throws Exception;

    /**
     * 根据会议预定的fdId查询会议预定信息
     * @param fdId
     * @return
     */
    public KmImeetingBookResulut getImeetingBookDetail(String fdId) throws Exception;

    /**
     * 根据会议室ID查询该会议室所有预定信息
     * @param fdId
     * @return
     */
    public KmImeetingBookResulut getImeetingBookById(String fdId) throws Exception;

    /**
     * 查询指定人员的会议预定
     * @param context
     * @return
     */
    public KmImeetingBookResulut getImeetingBook(KmImeetingBookContext context) throws Exception;

    /**
     * 预约会议室
     * @param bookform
     * @return
     * @throws Exception
     */
    public KmImeetingBookResulut addImeetingBook(KmImeetingBookParamterForm bookform) throws Exception;

    /**
     * 删除会议室预定
     * @param bookform
     * @return
     */
    public KmImeetingBookResulut deleteImeetingBook(KmImeetingBookParamterForm bookform) throws Exception;

    /**
     * 变更会议室预定
     * @param bookform
     * @return
     */
    public KmImeetingBookResulut updateImeetingBook(KmImeetingBookParamterForm bookform) throws Exception;
}
