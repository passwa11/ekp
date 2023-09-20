package com.landray.kmss.sys.filestore.scheduler.impl;

import com.landray.kmss.sys.filestore.scheduler.IQueueScheduler;
import com.landray.kmss.sys.filestore.scheduler.local.interfaces.ILocalScheduler;
import com.landray.kmss.sys.filestore.scheduler.remote.ServerDuplexCommunication;
import com.landray.kmss.sys.filestore.scheduler.remote.ServerHeartBeatDuplexCommunication;
import com.landray.kmss.sys.filestore.scheduler.remote.interfaces.IDuplexCommunication;
import com.landray.kmss.sys.filestore.scheduler.remote.interfaces.IRemoteCADScheduler;
import com.landray.kmss.sys.filestore.scheduler.remote.interfaces.IRemoteHTMLScheduler;
import com.landray.kmss.sys.filestore.scheduler.remote.interfaces.IRemotePDFScheduler;
import com.landray.kmss.sys.filestore.scheduler.remote.interfaces.IRemoteVideoScheduler;
import com.landray.kmss.sys.filestore.scheduler.third.dianju.service.IDianjuConvertScheduler;
import com.landray.kmss.sys.filestore.scheduler.third.foxit.service.FoxitScheduler;
import com.landray.kmss.sys.filestore.scheduler.third.suwell.interfaces.ISuWellScheduler;
import com.landray.kmss.sys.filestore.scheduler.third.wps.center.service.IWpsCenterConvertScheduler;
import com.landray.kmss.sys.filestore.scheduler.third.wps.interfaces.IWpsCloudScheduler;
import com.landray.kmss.sys.filestore.scheduler.third.wps.interfaces.IWpsConvertScheduler;
import com.landray.kmss.sys.filestore.service.ISysFileConvertDataService;
import com.landray.kmss.sys.filestore.util.SysFileStoreUtil;

public class SysFileConvertScheduler implements IQueueScheduler {
	private IRemoteHTMLScheduler remoteHTMLScheduler = null;
	private IRemotePDFScheduler remotePDFScheduler = null;
	private IRemoteVideoScheduler remoteVideoScheduler = null;
	private IDuplexCommunication nettyCommunication = null;
	private IDuplexCommunication nettyHearBeatCommunication = null;
	private ISysFileConvertDataService sysFileConvertDataService = null;
	private IRemoteCADScheduler remoteCADScheduler = null;	
	private ISuWellScheduler suwellScheduler = null;
	private IWpsCloudScheduler wpsCloudScheduler = null;
	private IWpsConvertScheduler wpsConvertScheduler = null;
	private IWpsCenterConvertScheduler wpsCenterConvertScheduler = null;
	private IDianjuConvertScheduler dianjuConvertScheduler = null;
	private FoxitScheduler foxitScheduler = null;
	
	public void setWpsConvertScheduler(IWpsConvertScheduler wpsConvertScheduler) {
		this.wpsConvertScheduler = wpsConvertScheduler;
	}


	public void setWpsCenterConvertScheduler(IWpsCenterConvertScheduler wpsCenterConvertScheduler) {
		this.wpsCenterConvertScheduler = wpsCenterConvertScheduler;
	}

	public void setDianjuConvertScheduler(IDianjuConvertScheduler dianjuConvertScheduler) {
		this.dianjuConvertScheduler = dianjuConvertScheduler;
	}

	public void setFoxitScheduler(FoxitScheduler foxitScheduler) {
		this.foxitScheduler = foxitScheduler;
	}

	public void setWpsCloudScheduler(IWpsCloudScheduler wpsCloudScheduler) {
		this.wpsCloudScheduler = wpsCloudScheduler;
	}

	public void setRemoteCADScheduler(IRemoteCADScheduler remoteCADScheduler) {
		this.remoteCADScheduler = remoteCADScheduler;
	}
	
	public void setSuwellScheduler(ISuWellScheduler suwellScheduler) {
		this.suwellScheduler = suwellScheduler;
	}

	public void setSysFileConvertDataService(ISysFileConvertDataService sysFileConvertDataService) {
		this.sysFileConvertDataService = sysFileConvertDataService;
	}

	private ILocalScheduler localScheduler = null;

	public void setLocalScheduler(ILocalScheduler localScheduler) {
		this.localScheduler = localScheduler;
	}

	public void setRemoteHTMLScheduler(IRemoteHTMLScheduler remoteHTMLScheduler) {
		this.remoteHTMLScheduler = remoteHTMLScheduler;
	}

	public void setRemotePDFScheduler(IRemotePDFScheduler remotePDFScheduler) {
		this.remotePDFScheduler = remotePDFScheduler;
	}

	public void setRemoteVideoScheduler(IRemoteVideoScheduler remoteVideoScheduler) {
		this.remoteVideoScheduler = remoteVideoScheduler;
	}

	@Override
	public void reDistribute() {
		remoteHTMLScheduler.reDistribute();
		remotePDFScheduler.reDistribute();
		remoteVideoScheduler.reDistribute();
		localScheduler.reDistribute();
		suwellScheduler.reDistribute();
		// wpsCloudScheduler.reDistribute();
		wpsConvertScheduler.reDistribute();
		wpsCenterConvertScheduler.reDistribute();
		dianjuConvertScheduler.reDistribute();
		foxitScheduler.reDistribute();
	}

	@Override
	public void startScheduler() {
		
		if(nettyHearBeatCommunication == null){
			nettyHearBeatCommunication = new ServerHeartBeatDuplexCommunication(sysFileConvertDataService,
					SysFileStoreUtil.getClientRegisterPort(), SysFileStoreUtil.getReceiveMessageHandlerCode());
		}
		
		if (nettyCommunication == null) {
			nettyCommunication = new ServerDuplexCommunication(sysFileConvertDataService,
					SysFileStoreUtil.getClientConnectPort(), SysFileStoreUtil.getReceiveMessageHandlerCode());
			remoteHTMLScheduler.setNettyCommunication(nettyCommunication);
			remotePDFScheduler.setNettyCommunication(nettyCommunication);
			remoteVideoScheduler.setNettyCommunication(nettyCommunication);
			remoteCADScheduler.setNettyCommunication(nettyCommunication);
			remoteHTMLScheduler.setDataService(sysFileConvertDataService);
			remotePDFScheduler.setDataService(sysFileConvertDataService);
			remoteVideoScheduler.setDataService(sysFileConvertDataService);
			remoteCADScheduler.setDataService(sysFileConvertDataService);			
		}
		
		nettyHearBeatCommunication.start();
		nettyCommunication.start();
		localScheduler.startScheduler();
		suwellScheduler.startScheduler();
		// wpsCloudScheduler.startScheduler();
		wpsConvertScheduler.startScheduler();
		wpsCenterConvertScheduler.startScheduler();
		dianjuConvertScheduler.startScheduler();
		foxitScheduler.startScheduler();
		if (nettyCommunication.isReady()) {
			remoteHTMLScheduler.startScheduler();
			remotePDFScheduler.startScheduler();
			remoteVideoScheduler.startScheduler();
			remoteCADScheduler.startScheduler();
		}
	}

	@Override
	public void stopScheduler() {
		nettyHearBeatCommunication.stop();
		nettyCommunication.stop();
		remoteHTMLScheduler.stopScheduler();
		remotePDFScheduler.stopScheduler();
		remoteVideoScheduler.stopScheduler();
		localScheduler.stopScheduler();
		suwellScheduler.stopScheduler();
		// wpsCloudScheduler.stopScheduler();
		wpsConvertScheduler.stopScheduler();
		wpsCenterConvertScheduler.stopScheduler();
		dianjuConvertScheduler.stopScheduler();
		foxitScheduler.stopScheduler();
	}

}
