package com.landray.kmss.eop.basedata.model;

/**
 * @author wangwh
 * @description:物料导入bean
 * @date 2021/5/7
 */
public class ImportMaterialBean {

    private String path;

    private EopBasedataMaterial material;

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public EopBasedataMaterial getMaterial() {
        return material;
    }

    public void setMaterial(EopBasedataMaterial material) {
        this.material = material;
    }
}
