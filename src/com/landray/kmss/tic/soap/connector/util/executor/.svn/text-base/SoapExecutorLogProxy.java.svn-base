package com.landray.kmss.tic.soap.connector.util.executor;

import java.util.Date;

import org.w3c.dom.Document;
import org.w3c.dom.Node;

import com.landray.kmss.tic.core.interfaces.FunctionCallException;
import com.landray.kmss.tic.core.log.interfaces.ITicCoreLogInterface;
import com.landray.kmss.tic.core.log.model.TicCoreLogMain;
import com.landray.kmss.tic.core.util.DOMHelper;
import com.landray.kmss.tic.soap.connector.util.executor.handler.ITicSoapExecuteHandler;
import com.landray.kmss.tic.soap.connector.util.executor.vo.ITicSoapRtn;
import com.landray.kmss.tic.soap.connector.util.header.HeaderOperation;
import com.landray.kmss.tic.soap.connector.util.header.SoapInfo;
import com.landray.kmss.tic.soap.connector.util.xml.ParseSoapXmlUtil;
import com.landray.kmss.util.SpringBeanUtil;
import com.sun.org.apache.xerces.internal.util.DOMUtil;

/**
 * 执行器的代理类，代soapExecutor运行执行webservice,且增加记录日志, 合并返回值,分析异常的功能
 * 
 * @author zhangtian date :2013-1-15 上午12:36:12
 */
public class SoapExecutorLogProxy extends AbstractSoapExecutor {

	private SoapInfo soapInfo;

	private AbstractSoapExecutor soapExecutor;

	public SoapExecutorLogProxy(AbstractSoapExecutor soapExecutor,
			SoapInfo soapInfo) {
		super(soapExecutor.getTicSoapExecuteHandler(), soapExecutor
				.getPostData());
		this.soapInfo = soapInfo;
		this.soapExecutor = soapExecutor;
	}

	/**
	 * 执行webservice
	 * 
	 * @return
	 * @throws Exception
	 */
	@Override
	public ITicSoapRtn executeSoapui() throws Exception {
		ITicSoapRtn ticSoapRtn = soapExecutor.executeSoapui();
		// 合并返回值，执行完以后只有返回值，没有跟页面配置的信息合并起来
		mergeRtnData(soapInfo, ticSoapRtn);
		// 记录日志
		// logProxySave(soapInfo, ticSoapRtn);
		return ticSoapRtn;
	}

	public void logProxySave(SoapInfo soapInfo, ITicSoapRtn ticSoapRtn,
			TicCoreLogMain logMain) throws Exception {
		ITicCoreLogInterface ticCoreLogInterface = (ITicCoreLogInterface) SpringBeanUtil
				.getBean("ticCoreLogInterface");
		Date curDate = new Date();
		Document postDocument = null;
		Document rtnDocument = null;
		try {
			postDocument = soapInfo.getSourceDocument();
			rtnDocument = ticSoapRtn.getRtnDocument();
			if (ITicSoapRtn.ERP_SOAPUI_EAR_TYPE_ERROR.equals(ticSoapRtn
					.getRtnType())) {
				// 在调用方记录日志
				// ticCoreLogInterface.saveTicCoreLogMain(
				// Constant.FD_TYPE_SOAP, null, soapInfo
				// .getTicSoapMain().getTicSoapSetting()
				// .getFdWsdlUrl(), soapInfo.getTicSoapMain()
				// .getFdName(),
				// curDate, new Date(),
				// DOMHelper.nodeToString(postDocument, true), DOMHelper
				// .nodeToString(rtnDocument, true),
				// "执行webservice异常",
				// TicCoreLogConstant.TIC_CORE_LOG_TYPE_ERROR);
				throw new FunctionCallException(DOMHelper
						.nodeToString(rtnDocument, true));
			} else if (ITicSoapRtn.ERP_SOAPUI_EAR_TYPE_SUCCESS
					.equals(ticSoapRtn.getRtnType())) {
				// 在调用方记录日志
				// ticCoreLogInterface.saveTicCoreLogMain(
				// Constant.FD_TYPE_SOAP, null, soapInfo
				// .getTicSoapMain().getTicSoapSetting()
				// .getFdWsdlUrl(), soapInfo.getTicSoapMain()
				// .getFdName(),
				// curDate, new Date(),
				// DOMHelper.nodeToString(postDocument, true), DOMHelper
				// .nodeToString(rtnDocument, true),
				// "成功日志:TicSoapImpl",
				// TicCoreLogConstant.TIC_CORE_LOG_TYPE_SUCCESS);

			}
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}

	@Override
    public Document getPostData() {
		return soapExecutor.getPostData();
	}

	@Override
    public void setPostData(Document postData) {
		soapExecutor.setPostData(postData);
	}

	@Override
    public ITicSoapExecuteHandler getTicSoapExecuteHandler() {
		return soapExecutor.getTicSoapExecuteHandler();
	}

	@Override
    public void setTicSoapExecuteHandler(
			ITicSoapExecuteHandler ticSoapExecuteHandler) {
		soapExecutor.setTicSoapExecuteHandler(ticSoapExecuteHandler);
	}

	public void mergeRtnData(SoapInfo soapInfo, ITicSoapRtn ticSoapRtn)
			throws Exception {
		// 包含全部节点的document
		Document source = soapInfo.getSourceDocument();
		// 值包含返回值的doc
		Document rtnDocument = ticSoapRtn.getRtnDocument();
		if (ITicSoapRtn.ERP_SOAPUI_EAR_TYPE_SUCCESS.equals(ticSoapRtn
				.getRtnType())) {
			Node node = HeaderOperation.allToPartNode(source, "//Output");
			Node rtnNode = rtnDocument.getDocumentElement();
			if (node != null && rtnNode != null) {
				ParseSoapXmlUtil.loopComment(node, rtnNode);
				if (node.getChildNodes().getLength() > 0) {
					DOMUtil.copyInto(rtnNode, node.getParentNode());
					node.getParentNode().removeChild(node);
				} else {
					DOMUtil.copyInto(rtnNode, node);
				}
				// 删除fault节点
				Node removeNode = HeaderOperation.selectNode("//Fault", source);
				if (removeNode != null) {
					removeNode.getParentNode().removeChild(removeNode);
				}
			}

		} else if (ITicSoapRtn.ERP_SOAPUI_EAR_TYPE_ERROR
				.equals(ticSoapRtn.getRtnType())) {
			Node node = HeaderOperation.allToPartNode(source, "//Fault");
			if (rtnDocument != null) {
				Node rtnNode = rtnDocument.getDocumentElement();
				if (node != null && rtnNode != null) {
					ParseSoapXmlUtil.loopComment(node, rtnNode);
					// 增加Output
					DOMUtil.copyInto(rtnNode, node.getParentNode());
					node.getParentNode().removeChild(node);
					// 删除fault节点
					Node removeNode = HeaderOperation.selectNode("//Output",
							source);
					if (removeNode != null) {
						removeNode.getParentNode().removeChild(removeNode);
					}
				}
			} else {
				node.setNodeValue(ticSoapRtn.getRtnContent());

			}
		}
		ticSoapRtn.resetRtnDocument(source);
	}

	public SoapInfo getSoapInfo() {
		return soapInfo;
	}

	public void setSoapInfo(SoapInfo soapInfo) {
		this.soapInfo = soapInfo;
	}

	public AbstractSoapExecutor getSoapExecutor() {
		return soapExecutor;
	}

	public void setSoapExecutor(AbstractSoapExecutor soapExecutor) {
		this.soapExecutor = soapExecutor;
	}

}
