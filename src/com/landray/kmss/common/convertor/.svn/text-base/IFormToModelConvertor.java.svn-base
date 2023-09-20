package com.landray.kmss.common.convertor;

/**
 * Form模型到域模型的转换器接口
 * 
 * @author 叶中奇
 */
public interface IFormToModelConvertor {
	/**
	 * @return 目标属性
	 */
	public abstract String getTPropertyName();

	/**
	 * 执行转换操作
	 * 
	 * @param ctx
	 *            转换操作上下文
	 * @throws Exception
	 */
	public abstract void excute(ConvertorContext ctx) throws Exception;

	/**
	 * 校验配置是否正确
	 * 
	 * @param context
	 * @param fromClass
	 * @param toClass
	 * @return 错误列表
	 */
	public void examine(ExamineContext context, Class fromClass, Class toClass);
}
