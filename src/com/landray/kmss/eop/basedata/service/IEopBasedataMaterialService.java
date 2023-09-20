package com.landray.kmss.eop.basedata.service;

import com.landray.kmss.eop.basedata.forms.EopBasedataMaterialForm;
import com.landray.kmss.eop.basedata.model.*;
import com.landray.kmss.sys.metadata.interfaces.IExtendDataService;

import java.util.List;

/**
 * @author wangwh
 * @description:物料业务类
 * @date 2021/5/7
 */
public interface IEopBasedataMaterialService extends IExtendDataService {

    public abstract List<EopBasedataMaterial> findByFdUnit(EopBasedataMateUnit fdUnit) throws Exception;

    public abstract List<EopBasedataMaterial> findByFdType(EopBasedataMateCate fdType) throws Exception;

    /**
     *  通过物料编码从EPS采购公共组件查询价格库物料价格
     *  如果存在有效果价格则返回有效价格最低价
     *  否则返回无效价格最低价
     *  否则返回null
     * @param material
     * @return
     * @throws Exception
     */
    public abstract String findMaterialPrice(EopBasedataMaterial material) throws Exception;

    /**
     *  导入物料
     * @param list
     * @throws Exception
     */
    public abstract void addImportMaterial(List<ImportMaterialBean> list) throws Exception;

    /**
     *  通过物料名称或者ERP物料编码查询物料
     * @param material
     * @return
     * @throws Exception
     */
    public abstract List<EopBasedataMaterial> findByNameOrErpCode(EopBasedataMaterial material) throws Exception;

    /**
     *  通过物料编码或者ERP物料编码查询物料
     * @param code
     * @param erpCode
     * @return
     * @throws Exception
     */
    public abstract List<EopBasedataMaterial> findByCodeOrErpCode(String code, String erpCode) throws Exception;

    /**
     * 判断批量物料是否存在于采购需求中，能否被删除
     *
     * @param ids
     * @return
     * @throws Exception
     */
    public boolean isDeleteByRequire(String[] ids)throws Exception;


    /**
     * 导入物料
     *
     * @param importMaterialBean
     * @throws Exception
     */
    public abstract void addImportMaterial(ImportMaterialBean importMaterialBean) throws Exception;
	public abstract void updatePre(EopBasedataMaterialForm eopBasedataMaterialForm) throws Exception;
}
