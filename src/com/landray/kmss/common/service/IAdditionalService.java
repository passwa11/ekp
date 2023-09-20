package com.landray.kmss.common.service;


/**
 * 临时服务接口，实现或者继承该接口的类将在当前请求的线程中<b>自动被机制分发器发现</b>并跟随ICoreOuterService集合一起调用。
 * <br/>
 * 注意：该接口只是标记接口，一般不扩展IBaseCoreOuterService（即不声明新方法）
 * @author 叶中奇
 */
public interface IAdditionalService extends IBaseCoreOuterService {

}
