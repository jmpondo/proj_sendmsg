package com.sendmsg.session.message;

import java.util.List;

import javax.ejb.Local;

import com.sendmsg.entitie.message.GatewayParam;
import com.sendmsg.entitie.message.Notification;
import com.sendmsg.entities.authentification.Utilisateur;
import com.sendmsg.entities.recipient.Campagne;
import com.sendmsg.entities.recipient.Recipiendaire;

@Local
public interface INotificationSms {
	
	public Notification addNotification(Campagne campagne, Recipiendaire recipiendaire, GatewayParam gateway, Utilisateur utilisateur);
	public Notification udpateNotification(Notification notification);
	public void deleteNotification(Long id);
	public Notification getNotificationById(Long id);
	public List<Notification> getNotificationList();
	public List<Notification> getNotificationListByCampagne(Campagne campagne);
	public List<Notification> getNotificationListByRecipient(Recipiendaire recipiendaire);
	public List<Notification> getNotificationListByGateway(GatewayParam gateway);
	public List<Notification> getNotificationListByUser(Utilisateur utilisateur);
	
	public GatewayParam addGateway(GatewayParam gateway);
	public GatewayParam updateGateway(GatewayParam gateway);
	public GatewayParam getGatewayById(Long id);
	public void deleteGateway(Long id);
	public List<GatewayParam> getGatewayList();
	
	
	
	

}
