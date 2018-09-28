package com.sendmsg.session.param;

import java.util.List;

import javax.ejb.Local;

import com.sendmsg.entitie.message.GatewayParam;

@Local
public interface IParametre {
	
	public GatewayParam addParam(GatewayParam gatewayParam);
	public GatewayParam updateParam(GatewayParam gatewayParam);
	public GatewayParam getGateway(Long id);
	public void removeParam(Long id);
	public GatewayParam getMsgParamByCode(String code);
	public List<GatewayParam> listGateways();
}
