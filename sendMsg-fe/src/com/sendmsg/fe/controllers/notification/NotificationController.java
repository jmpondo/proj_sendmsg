package com.sendmsg.fe.controllers.notification;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.net.URI;
import java.net.URL;
import java.net.URLConnection;
import java.util.Arrays;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientResponseFilter;
import javax.ws.rs.core.UriBuilder;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang.StringUtils;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

import org.primefaces.event.FileUploadEvent;
import org.primefaces.json.JSONArray;
import org.primefaces.json.JSONException;
import org.primefaces.json.JSONObject;
import org.primefaces.model.UploadedFile;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.sendmsg.entitie.message.GatewayParam;
import com.sendmsg.entitie.message.Message;
import com.sendmsg.entitie.message.Notification;
import com.sendmsg.entities.authentification.SessionUtilisateur;
import com.sendmsg.entities.recipient.Campagne;
import com.sendmsg.entities.recipient.Recipiendaire;
import com.sendmsg.fe.component.tools.HttpUtility;
import com.sendmsg.fe.component.tools.SendingProperties;
import com.sendmsg.session.authentification.IAuthentification;
import com.sendmsg.session.message.ICampagneMessage;
import com.sendmsg.session.message.INotificationSms;
import com.sendmsg.session.recipient.IRecipiendaire;

import infobip.api.client.SendSingleTextualSms;
import infobip.api.config.BasicAuthConfiguration;
import infobip.api.model.sms.mt.send.SMSResponse;
import infobip.api.model.sms.mt.send.textual.SMSTextualRequest;



@ManagedBean(name="fileUploadView")
@ViewScoped
public class NotificationController {
		
	@EJB
	private IRecipiendaire rep;	
	@EJB
	private INotificationSms notify;
	@EJB
	private ICampagneMessage campMsg;
	
	private IAuthentification auth;
	
	ObjectMapper om = new ObjectMapper();

	 	private Long id;
	    private UploadedFile file;
	    private Campagne campagne;
	    private GatewayParam gateway;	
	    private Recipiendaire recipiendaire = new Recipiendaire();
	    private Message message = new Message();
	    
	    private List<Campagne> campagnes;
	    private List<GatewayParam> gateways;
	    
	   
	    
	    private List<Message> messages;
	    
	    private String camp;
	    private String gate;
	    
	    private Long campId;
	    private Long gateId;
	    private Long mesgId;
	    
	    private SessionUtilisateur usersession;
		public Long getId() {
			return id;
		}
		public void setId(Long id) {
			this.id = id;
		}
		public UploadedFile getFile() {
			return file;
		}
		public void setFile(UploadedFile file) {
			this.file = file;
		}
		public Campagne getCampagne() {
			return campagne;
		}
		public void setCampagne(Campagne campagne) {
			this.campagne = campagne;
		}
		public GatewayParam getGateway() {
			return gateway;
		}
		public void setGateway(GatewayParam gateway) {
			this.gateway = gateway;
		}
		public SessionUtilisateur getUsersession() {
			return usersession;
		}
		public void setUsersession(SessionUtilisateur usersession) {
			this.usersession = usersession;
		}				
	
		
		public String getCamp() {
			return camp;
		}
		public void setCamp(String camp) {
			this.camp = camp;
		}
		public String getGate() {
			return gate;
		}
		public void setGate(String gate) {
			this.gate = gate;
		}
		public Long getCampId() {
			return campId;
		}
		public void setCampId(Long campId) {
			this.campId = campId;
		}
		public Long getGateId() {
			return gateId;
		}
		public void setGateId(Long gateId) {
			this.gateId = gateId;
		}	
		
		public List<Campagne> getCampagnes() {
			campagnes = campMsg.getCampagneList();
			return campagnes;
		}
		public void setCampagnes(List<Campagne> campagnes) {
			this.campagnes = campagnes;
		}
		public List<GatewayParam> getGateways() {
			gateways = notify.getGatewayList();
			return gateways;
		}
		public void setGateways(List<GatewayParam> gateways) {
			this.gateways = gateways;
		}
		
		public Recipiendaire getRecipiendaire() {
			return recipiendaire;
		}
		public void setRecipiendaire(Recipiendaire recipiendaire) {
			this.recipiendaire = recipiendaire;
		}
		public List<Message> getMessages() {
			return messages;
		}
		public void setMessages(List<Message> messages) {
			this.messages = messages;
		}	
	
		
		public NotificationController() {
			super();
			// TODO Auto-generated constructor stub
		}
		
		@PostConstruct
		public void init(){
			
			//usersession= (SessionUtilisateur) ((HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true)).getAttribute("currentSession");	

			messages = campMsg.getCampagneMessageList();
			/*campagnes = campMsg.getCampagneList();
			gateways = notify.getGatewayList();*/
		
		}	
		
		
		public Recipiendaire addNewRecipient(String[] tab) {	
			
			recipiendaire = new Recipiendaire();
			recipiendaire.setTelephone(tab[0]);
			recipiendaire.setNom(tab[1]);
			recipiendaire.setPrenom(tab[2]);
			recipiendaire.setCommune(tab[3]);
			recipiendaire.setAspiration(tab[4]);
			return rep.addRecipient(recipiendaire);
			
		}

		public void addNewNotification(String[] tab) throws UnirestException, JSONException, JsonGenerationException, JsonMappingException, IOException{
			System.out.println(" ### Dans addNewNotification #### : CampagneId = "+campId+",GatewayId = "+gateId);
		
			campagne = campMsg.getCampagneById(campId);
			gateway = notify.getGatewayById(gateId);
			int val = 0;
			if(recipiendaire != null) {
				System.out.println(" ### Dans addNewNotification #### : "+recipiendaire.getNom());
				
				String message = templateMessage(recipiendaire.getNom(), recipiendaire.getPrenom(), recipiendaire.getAspiration(), campagne);
				//int val = sendSMS(credentials(gateway.getUsername(), gateway.getPassword()), gateway.getApiUrl(), campagne.getNomCampagne(), message, recipiendaire.getTelephone());
				//int val = sendSMSInfobip(campagne.getNomCampagne(), message, recipiendaire.getTelephone());
				//String message = "TEST%20ONG%20"+recipiendaire.getNom();
				val = envoi(recipiendaire.getTelephone(), message, gateway.getApiUrl());
				//envoiSMS(gateway.getApiUrl(), message, recipiendaire.getTelephone());
				System.out.println(" ### Dans addNewNotification #### After sendSMS "+val);			
				try {					
					
					notify.addNotification(campagne, recipiendaire, gateway, auth.findUserById(1L));	
				} catch(Exception e) {
					e.printStackTrace();
				}
			} 
			else {
				
			}
			
				
		}
		
		
		public void uploadAll()
		{
			try {
					
					BufferedReader br=new BufferedReader(new InputStreamReader(file.getInputstream()));
					String line=""; 				
						
						while((line=br.readLine())!=null)
						{   
							System.out.println(" ### Resultat File #### : "+line);
					    	
							String[] tabLine=line.split(";"); 	    	
					    	System.out.println(" ### JJ---  Dans uploadAll ####"+tabLine[0].toString());
					    	recipiendaire = rep.getRecipientByPhone(tabLine[0].toString());
					    	if(recipiendaire == null) {				    	
					    		recipiendaire = addNewRecipient(tabLine);
					    	}
					    	
					    	addNewNotification(tabLine);
						}						
						
					
		                FacesContext.getCurrentInstance().getExternalContext().redirect("/sendMsg-fe/pages/sendsms.xhtml");
					
				}
				catch(Exception e){
					FacesMessage message = new FacesMessage("Echec de chargement de fichier",  "!");
					FacesContext.getCurrentInstance().addMessage(null, message);
				}
			
		}
		
		
		
		public void handleFileUpload(FileUploadEvent event) throws Exception {
			
			file = event.getFile();
			System.out.println(" ### JJ---  Dans upload #### file = "+file.getFileName());
			System.out.println(" ### JJ---  Dans upload #### Campagne = "+campId);
			System.out.println(" ### JJ---  Dans upload #### gateway = "+gateId);
			
			if(file != null) {        		        	
	        	String extfichier=file.getFileName().substring((file.getFileName().length()-3), file.getFileName().length()); 
	        	System.out.println(" ### JJ---  Dans upload #### file = "+extfichier);
	        	if(extfichier.equals("csv")){
	        		uploadAll();
	        		
	        	} else{
		        		FacesMessage message = new FacesMessage(" Fichier ", file.getFileName() + " NON VALIDE");
		                FacesContext.getCurrentInstance().addMessage(null, message);
	        	}
	        	
	        } else{
			        FacesMessage message = new FacesMessage("Echec, Fichier inexistant",  "!");
			        FacesContext.getCurrentInstance().addMessage(null, message);
	        }
	    }
		
		
		public int sendSMS(String apikey, String apiURL, String senderlabel, String message, String phone) throws UnirestException, JSONException, JsonGenerationException, JsonMappingException, IOException {
			System.out.println("## JJ ## Dans sendSMS ## debut traitement");
			int nbreSms = 0;
			
			SendingProperties sp = new SendingProperties();
			sp.setFrom(senderlabel);
			sp.setTextMessage(message);
			sp.setTo("237"+phone);
			
			String json = om.writeValueAsString(sp);
			
			
			HttpResponse<String> response = Unirest.post(apiURL+"/sms/1/text/single")
					  .header("Authorization", "Basic "+apikey)
					  .header("Content-type", "application/json")
					  .header("Accept", "application/json")
					  //.body("{\"from\":\""+senderlabel+"\",\"to\":\"237"+phone+"\",\"text\":\""+message+"\"}")
					  .body(json)
					  .asString();	
			System.out.println("## JJ ## Reponse envoi sms ## Status == "+response.getStatus());
			System.out.println("## JJ ## Reponse envoi sms ## == "+response.getBody());

			if(response.getStatus() == 200) {
				String resultJson = response.getBody();
				System.out.println("## JJ ## Reponse envoi sms ## resultJson = "+resultJson);
				
				if(resultJson != null && (!resultJson.equals(""))) {
					JSONObject  jsonOb = new JSONObject(resultJson);
					JSONArray jsonArray = jsonOb.getJSONArray("messages");
					String status = jsonArray.getJSONObject(0).getString("status");
					
					
					if((new JSONObject(status).getString("name")).equalsIgnoreCase("MESSAGE_ACCEPTED")) {
						nbreSms = jsonArray.getJSONObject(0).getInt("smsCount");
						System.out.println("## JJ ## Dans sendSMS ## debut traitement");
					}
				}else {
					System.out.println("## JJ ## Reponse envoi sms échoué ##");
				}
				
			} else {
				System.out.println("## JJ ## Dans sendSMS ## Erreur lors d'envoi SMS. Veuillez contacter votre Admin");
			}
			
			System.out.println("## JJ ## Dans sendSMS ## Fin traitement");
			return nbreSms;		
		
		}	
	
		
		public int sendSMSInfobip(String senderlabel, String message, String phone) {
			String USERNAME = null, PASSWORD = null; String status; int nbSms=0;
			SendSingleTextualSms client = new SendSingleTextualSms(new BasicAuthConfiguration(USERNAME, PASSWORD));
			System.out.println("## JJ ## Dans sendSMSInfobip ## debut traitement");
			SMSTextualRequest requestBody = new SMSTextualRequest();
			requestBody.setFrom(senderlabel);
			requestBody.setTo(Arrays.asList(phone));
			requestBody.setText(message);
			
			SMSResponse response = client.execute(requestBody);
			System.out.println("## JJ ## Dans templateMessage## fin de traitement"+response.getMessages());
			return response.getMessages().get(0).getSmsCount();
			
		}
		
		public String credentials(String login, String password) throws UnsupportedEncodingException {
			String logandPass = login+":"+password;
			return Base64.encodeBase64String(logandPass.toString().getBytes("UTF-8"));
		}
		
		public String templateMessage(String nameRecip, String prenomRecip, String aspiration, Campagne campagne) {
			System.out.println("## JJ ## Dans templateMessage##");
			String msg="";
			msg = campagne.getMessage().getMessageFr();
			
			msg = StringUtils.replace(msg, "[Nom]", nameRecip) ;
			if(prenomRecip != null && (!prenomRecip.equals("")))
				msg = StringUtils.replace(msg, "[Prenom]", prenomRecip);
			msg = StringUtils.replace(msg, "[Aspiration]", aspiration);
			msg = StringUtils.replace(msg, "[Label]", campagne.getNomCampagne());
		
			System.out.println("## JJ ## Dans templateMessage## message = "+msg);
			
			
			return msg;				
		}	
		
		// http://freelancertech.net/taxi/web/taxiapp/taximen/sendsms/237674099619/test%20ONJ%20Cameroun
		
		public static int envoi(String phoneNumber, String msg, String urlpass)
		 {
			
	        int ok=0;
	        System.out.println(" ## Avant Envoi ## "+urlpass);
	        
	        
	        String requestURL = urlpass+"/"+phoneNumber+"/"+msg;
	        
	        
	        try {
	        		
	        		requestURL = requestURL.replaceAll(" ", "%20");
	        		System.out.println(" ## Avant Envoi ## requestURL"+requestURL);
	        		
		            HttpUtility.sendGetRequest(requestURL);
		            /*String[] response = HttpUtility.readMultipleLinesRespone();
		           
		            for (String line : response) {
		                System.out.println(line);
		            }*/
		            ok =1;
	        
	        } catch (IOException ex) {
	            ex.printStackTrace();
	       
	        }
	        
	        HttpUtility.disconnect();
	        
	        
	        return ok;
		 }

		public void envoiSMS(String urlpass, String message, String tel) throws IOException {
			System.out.println("##JJ##  Dans envoiSMS url"+urlpass);	
		//	String url = "http://freelancertech.net/taxi/web/taxiapp/taximen/sendsms/237\"+recipiendaire.getTelephone()+\"/test%20ONJ%20Cameroun\"";
			HttpURLConnection  con = null;
			
			String url = urlpass+"/"+tel+"/"+message;
			
			System.out.println("##JJ##  Dans envoiSMS urlEnvoi"+url);

			try {
					URL myurl = new URL(url);
					con = (HttpURLConnection) myurl.openConnection();
	
					con.setRequestMethod("GET");
	
					StringBuilder content;
	
				    BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream())); 
					String line;
					content = new StringBuilder();
	
					while ((line = in.readLine()) != null) {
					content.append(line);
				
				}
				in.close();
				con.disconnect();
				System.out.println(content.toString());	
			}catch(Exception e) {
				e.printStackTrace();
			}
				
			
		}
		
	
}
