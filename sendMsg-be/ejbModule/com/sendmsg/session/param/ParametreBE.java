package com.sendmsg.session.param;

import java.util.List;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.validation.ValidationException;

import com.sendmsg.entitie.message.GatewayParam;
import com.sendmsg.entitie.message.Message;

@TransactionAttribute(value = TransactionAttributeType.REQUIRED)
@Stateless
public class ParametreBE implements IParametre {
	@PersistenceContext(name="UP_senderMsg")
	private EntityManager em;

	@Override
	public GatewayParam addParam(GatewayParam gatewayParam) {
		if(gatewayParam == null)
			throw new ValidationException(" Parameter incorrect ");
		em.persist(gatewayParam);
		return gatewayParam;
	}

	@Override
	public GatewayParam updateParam(GatewayParam gatewayParam) {
		
		return em.merge(gatewayParam);
	}

	@Override
	public void removeParam(Long id) {
		GatewayParam sp = em.find(GatewayParam.class, id);
		em.remove(sp);
		
	}

	@Override
	public GatewayParam getMsgParamByCode(String code) {
		
		return null;
	}

	@Override
	public List<GatewayParam> listGateways() {		
		String jpql = "select * from gateway";
		Query q = em.createNativeQuery(jpql, GatewayParam.class);
		if(q.getResultList().size() > 0) {
			return q.getResultList();
		} else		
		return null;
	}

	@Override
	public GatewayParam getGateway(Long id) {
		
		return em.find(GatewayParam.class, id);
	}

}
