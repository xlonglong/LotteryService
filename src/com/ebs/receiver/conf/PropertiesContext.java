package com.ebs.receiver.conf;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jconfig.Configuration;
import org.jconfig.ConfigurationManager;

public class PropertiesContext {
	
	private String message_maxlen;
	private String mina_listen_port;
	private String short_connection;
	private String timeout;
	private String file_port;
	private String file_dir;
	private String file_getutil;
	private String file_ip;
	private String command2tradecode_configpath;
	
	private String kickback_begin;
	private String kickback_end;
	private String kickback_trade;
	private String kickbacksms_trade;
	private String allow;
	private String base_trade;
	private String oper_trade;
	private String agent_trade;
	private String customer_trade;
	private String account_trade;
	private String info_trade; //资讯接口
	private String info_address;
	private String info_port;
	
	private String acct_trade; //账户接口
	private String acct_address;
	private String acct_port;
	
	private String biz_trade;
	private String sale_trade;
	private String supp_trade;
	private String card_trade;
	private String lott_trade;
	private String phone_trade;
	private String file_trade;
	private String base_address;
	private String base_port;
	private String oper_address;
	private String oper_port;
	private String agent_address;
	private String agent_port;
	private String customer_address;
	private String customer_port;
	private String account_address;
	private String account_port;
	private String biz_address;
	private String biz_port;
	private String sale_address;
	private String sale_port;
	private String supp_address;
	private String supp_port;
	private String card_address;
	private String card_port;
	private String lott_address;
	private String lott_port;
	private String phone_address;
	private String phone_port;
	private String msgque_max_len;
	private String msgque_min_len;
	private String reqhndl_thrd_num;
	private String webthread_wait_time;
	
	private String sync_trade;
	private String sync_listen_port;
	private String distr_line_maxlen;
	private String sleep_time;
	private String chnl_maxlen;
	
	private String sms_sender_address;
	private String sms_sender_port;
	
	private String monitor_time;
	
	private String gamedi_trade;
	private String alipay_trade;
	private String gamedi_address;
	private String gamedi_port;
	private String alipay_address;
	private String alipay_port;
	
	private String guaka_trade;
	private String guaka_address;
	private String guaka_port;

	
	private String kzfh_trade;
	private String kzfh_address;
	private String kzfh_port;
	private String uniisync_trade;
	
	private String sw_trade;
	private String sw_address;
	private String sw_port;
	
	private String life_trade;
	private String life_address;
	private String life_port;
	
	private String sffast_trade;
	private String sffast_address;
	private String sffast_port;
	
	private String province_kickback_state;
	private String province_kickback_begin;
	private String province_kickback_end;
	private String province_kickback_trade;
	private String channel;
	
	private String DES3key;
	
	private String driver;
	private String url;
	private String usr;
	private String pwd;
	private String maxIdle;
	private String maxActive;
	
	private String posturl;
	private String userid;
	private String md5String;
	private String backurl;
	private String partnerIp ; 
	private String partnerPort ;
	
	private String up_url;			//上游服务端URL
	
	private static ConfigurationManager cm = ConfigurationManager.getInstance();
	private static Configuration configuration = ConfigurationManager.getConfiguration("ebs");
	public static PropertiesContext instance = new PropertiesContext();
	
	public static void setCm(ConfigurationManager cm) {
		PropertiesContext.cm = cm;
	}

	public static void setConfiguration(Configuration configuration) {
		PropertiesContext.configuration = configuration;
	}
	
	public static void setInstance(PropertiesContext instance) {
		PropertiesContext.instance  = instance;
	}
	
	public static PropertiesContext getInstance() {
		return instance;
	}
	
	
	private static Log log = LogFactory.getLog(PropertiesContext.class);

	public PropertiesContext() {
		try {
			
			if(configuration==null){
				log.debug("��ʼ�������ļ�");
				configuration = ConfigurationManager.getConfiguration("ebs");
				log.debug("��ʼ�������ļ����");
			}
			// getProperty(String name,String defaultValue,String category).
			message_maxlen = configuration.getProperty("message_maxlen", null, "system");
			mina_listen_port = configuration.getProperty("mina_listen_port", null, "system");
//			System.out.println("mina_listen_port["+mina_listen_port+"]");
			short_connection = configuration.getProperty("short_connection", null, "system");
			timeout = configuration.getProperty("timeout", null, "system");
			file_port = configuration.getProperty("file_port", null, "system");
			file_dir = configuration.getProperty("file_dir", null, "system");
			file_getutil = configuration.getProperty("file_getutil", null, "system");
			file_ip = configuration.getProperty("file_ip", null, "system");
			sync_listen_port = configuration.getProperty("sync_listen_port", null, "system");
			distr_line_maxlen = configuration.getProperty("distr_line_maxlen", null, "system");
			sleep_time = configuration.getProperty("sleep_time", null, "system");
			chnl_maxlen = configuration.getProperty("chnl_maxlen", null, "system");
			monitor_time = configuration.getProperty("monitor_time", null, "system");
			
			command2tradecode_configpath = configuration.getProperty("command2tradecode_configpath",null,"system");
			
			msgque_max_len = configuration.getProperty("msgque_max_len", null, "system");
			msgque_min_len = configuration.getProperty("msgque_min_len", null, "system");
			reqhndl_thrd_num = configuration.getProperty("reqhndl_thrd_num", null, "system");
//			webthread_wait_time = configuration.getProperty("webthread_wait_time", null, "system");
			
			kickback_begin = configuration.getProperty("kickback_begin", null, "maintenance");
			kickback_end = configuration.getProperty("kickback_end", null, "maintenance");
			kickback_trade = configuration.getProperty("kickback_trade", null, "maintenance");
			kickbacksms_trade = configuration.getProperty("kickbacksms_trade", null, "maintenance");
			
			allow = configuration.getProperty("allow", null, "trade");
			base_trade = configuration.getProperty("base_trade", null, "trade");
			info_trade=configuration.getProperty("info_trade", null, "trade");
			acct_trade=configuration.getProperty("acct_trade", null, "trade");
			
			
			oper_trade = configuration.getProperty("oper_trade", null, "trade");
			agent_trade = configuration.getProperty("agent_trade", null, "trade");
			customer_trade = configuration.getProperty("customer_trade", null, "trade");
			account_trade = configuration.getProperty("account_trade", null, "trade");
			biz_trade = configuration.getProperty("biz_trade", null, "trade");
			sale_trade = configuration.getProperty("sale_trade", null, "trade");
			supp_trade = configuration.getProperty("supp_trade", null, "trade");
			card_trade = configuration.getProperty("card_trade", null, "trade");
			file_trade = configuration.getProperty("file_trade", null, "trade");
			sync_trade = configuration.getProperty("sync_trade", null, "trade");
			lott_trade = configuration.getProperty("lott_trade", null, "trade");
			phone_trade = configuration.getProperty("phone_trade", null, "trade");
			guaka_trade = configuration.getProperty("guaka_trade", null, "trade");
			uniisync_trade = configuration.getProperty("uniisync_trade", null, "trade");
			kzfh_trade = configuration.getProperty("kzfh_trade", null, "trade");
			sw_trade = configuration.getProperty("sw_trade", null, "trade");
			life_trade = configuration.getProperty("life_trade", null, "trade");
			sffast_trade = configuration.getProperty("sffast_trade", null, "trade");
			
			base_address = configuration.getProperty("base_address", null, "ebs");
			base_port = configuration.getProperty("base_port", null, "ebs");
			
			info_address = configuration.getProperty("info_address", null, "ebs");
			info_port = configuration.getProperty("info_port", null, "ebs");
			acct_address = configuration.getProperty("acct_address", null, "ebs");
			acct_port = configuration.getProperty("acct_port", null, "ebs");
			
			
			
			
			
			oper_address = configuration.getProperty("oper_address", null, "ebs");
			oper_port = configuration.getProperty("oper_port", null, "ebs");
			agent_address = configuration.getProperty("agent_address", null, "ebs");
			agent_port = configuration.getProperty("agent_port", null, "ebs");
			customer_address = configuration.getProperty("customer_address", null, "ebs");
			customer_port = configuration.getProperty("customer_port", null, "ebs");
			account_address = configuration.getProperty("account_address", null, "ebs");
			account_port = configuration.getProperty("account_port", null, "ebs");
			biz_address = configuration.getProperty("biz_address", null, "ebs");
			biz_port = configuration.getProperty("biz_port", null, "ebs");
			sale_address = configuration.getProperty("sale_address", null, "ebs");
			sale_port = configuration.getProperty("sale_port", null, "ebs");
			supp_address = configuration.getProperty("supp_address", null, "ebs");
			supp_port = configuration.getProperty("supp_port", null, "ebs");
			card_address = configuration.getProperty("card_address", null, "ebs");
			card_port = configuration.getProperty("card_port", null, "ebs");
			lott_address = configuration.getProperty("lott_address", null, "ebs");
			lott_port = configuration.getProperty("lott_port", null, "ebs");
			phone_address = configuration.getProperty("phone_address", null, "ebs");
			phone_port = configuration.getProperty("phone_port", null, "ebs");
			guaka_address = configuration.getProperty("guaka_address", null, "ebs");
			guaka_port = configuration.getProperty("guaka_port", null, "ebs");
			gamedi_trade = configuration.getProperty("gamedi_trade", null, "trade");
			alipay_trade = configuration.getProperty("alipay_trade", null, "trade");
			gamedi_address = configuration.getProperty("gamedi_address", null, "ebs");
			gamedi_port = configuration.getProperty("gamedi_port", null, "ebs");
			alipay_address = configuration.getProperty("alipay_address", null, "ebs");
			alipay_port = configuration.getProperty("alipay_port", null, "ebs");
			kzfh_address = configuration.getProperty("kzfh_address", null, "ebs");
			kzfh_port = configuration.getProperty("kzfh_port", null, "ebs");
			sw_address = configuration.getProperty("sw_address", null, "ebs");
			sw_port = configuration.getProperty("sw_port", null, "ebs");
			sms_sender_address = configuration.getProperty("sms_sender_address", null, "sms_sender");
			sms_sender_port = configuration.getProperty("sms_sender_port", null, "sms_sender");
			life_address = configuration.getProperty("life_address", null, "ebs");
			life_port = configuration.getProperty("life_port", null, "ebs");
			sffast_address = configuration.getProperty("sffast_address", null, "ebs");
			sffast_port = configuration.getProperty("sffast_port", null, "ebs");
			
			province_kickback_state = configuration.getProperty("province_kickback_state", null, "maintenance");
			province_kickback_begin = configuration.getProperty("province_kickback_begin", null, "maintenance");
			province_kickback_end = configuration.getProperty("province_kickback_end", null, "maintenance");
			province_kickback_trade = configuration.getProperty("province_kickback_trade", null, "maintenance");
			channel = configuration.getProperty("channel", null, "general");
			DES3key = configuration.getProperty("3DESkey", null, "system");
		    driver=configuration.getProperty("driver", null, "conn");
		    url=configuration.getProperty("url", null, "conn");
		    usr=configuration.getProperty("usr", null, "conn");
		    pwd=configuration.getProperty("pwd", null, "conn");
		    maxIdle=configuration.getProperty("maxIdle", null, "conn");
		    maxActive=configuration.getProperty("maxActive", null, "conn");
		    
		    posturl=configuration.getProperty("posturl", null, "partner");
		    userid=configuration.getProperty("userid", null, "partner");
		    md5String=configuration.getProperty("md5String", null, "partner");
		    backurl=configuration.getProperty("backurl", null, "partner");
		     partnerIp = configuration.getProperty("ip", null, "partner");
		     partnerPort = configuration.getProperty("port", null, "partner");
		     
		     up_url=configuration.getProperty("url", null, "uppartner");
		    
		   } catch (Exception e) {
			e.printStackTrace();
//			log.error(this.toString() + ": Get LocalPort Error.");
		}
	}

	public String getDES3key() {
		return DES3key;
	}

	public void setDES3key(String dES3key) {
		DES3key = dES3key;
	}

	public String getSw_trade() {
		return sw_trade;
	}


	public String getAlipay_trade() {
		return alipay_trade;
	}


	public void setSw_trade(String sw_trade) {
		this.sw_trade = sw_trade;
	}

	public String getSw_address() {
		return sw_address;
	}

	public void setSw_address(String sw_address) {
		this.sw_address = sw_address;
	}

	public String getSw_port() {
		return sw_port;
	}

	public void setSw_port(String sw_port) {
		this.sw_port = sw_port;
	}

	public String getGamedi_trade() {
		return gamedi_trade;
	}

	public void setGamedi_trade(String gamedi_trade) {
		this.gamedi_trade = gamedi_trade;
	}


	public void setAlipay_trade(String alipay_trade) {
		this.alipay_trade = alipay_trade;
	}

	public String getGamedi_address() {
		return gamedi_address;
	}

	public void setGamedi_address(String gamedi_address) {
		this.gamedi_address = gamedi_address;
	}

	public String getGamedi_port() {
		return gamedi_port;
	}

	public void setGamedi_port(String gamedi_port) {
		this.gamedi_port = gamedi_port;
	}

	public String getAlipay_address() {
		return alipay_address;
	}

	public void setAlipay_address(String alipay_address) {
		this.alipay_address = alipay_address;
	}

	public String getAlipay_port() {
		return alipay_port;
	}

	public void setAlipay_port(String alipay_port) {
		this.alipay_port = alipay_port;
	}

	public String getMessage_maxlen() {
		return message_maxlen;
	}

	public void setMessage_maxlen(String message_maxlen) {
		this.message_maxlen = message_maxlen;
	}

	public String getMina_listen_port() {
		return mina_listen_port;
	}

	public void setMina_listen_port(String mina_listen_port) {
		this.mina_listen_port = mina_listen_port; 
	}

	public String getShort_connection() {
		return short_connection;
	}

	public void setShort_connection(String short_connection) {
		this.short_connection = short_connection;
	}

	public String getTimeout() {
		return timeout;
	}

	public void setTimeout(String timeout) {
		this.timeout = timeout;
	}

	public String getFile_port() {
		return file_port;
	}

	public void setFile_port(String file_port) {
		this.file_port = file_port;
	}

	public String getSync_listen_port() {
		return sync_listen_port;
	}

	public void setSync_listen_port(String sync_listen_port) {
		this.sync_listen_port = sync_listen_port;
	}

	public String getFile_dir() {
		return file_dir;
	}

	public void setFile_dir(String file_dir) {
		this.file_dir = file_dir;
	}

	public String getFile_getutil() {
		return file_getutil;
	}

	public void setFile_getutil(String file_getutil) {
		this.file_getutil = file_getutil;
	}

	public String getFile_ip() {
		return file_ip;
	}

	public void setFile_ip(String file_ip) {
		this.file_ip = file_ip;
	}

	public String getKickback_begin() {
		return kickback_begin;
	}

	public void setKickback_begin(String kickback_begin) {
		this.kickback_begin = kickback_begin;
	}

	public String getKickback_end() {
		return kickback_end;
	}

	public void setKickback_end(String kickback_end) {
		this.kickback_end = kickback_end;
	}

	public String getKickback_trade() {
		return kickback_trade;
	}

	public void setKickback_trade(String kickback_trade) {
		this.kickback_trade = kickback_trade;
	}

	public String getAllow() {
		return allow;
	}

	public void setAllow(String allow) {
		this.allow = allow;
	}

	public String getSync_trade() {
		return sync_trade;
	}

	public void setSync_trade(String sync_trade) {
		this.sync_trade = sync_trade;
	}

	public String getBase_trade() {
		return base_trade;
	}

	public void setBase_trade(String base_trade) {
		this.base_trade = base_trade;
	}

	public String getOper_trade() {
		return oper_trade;
	}

	public void setOper_trade(String oper_trade) {
		this.oper_trade = oper_trade;
	}

	public String getAgent_trade() {
		return agent_trade;
	}

	public void setAgent_trade(String agent_trade) {
		this.agent_trade = agent_trade;
	}

	public String getCustomer_trade() {
		return customer_trade;
	}

	public void setCustomer_trade(String customer_trade) {
		this.customer_trade = customer_trade;
	}

	public String getAccount_trade() {
		return account_trade;
	}

	public void setAccount_trade(String account_trade) {
		this.account_trade = account_trade;
	}

	public String getBiz_trade() {
		return biz_trade;
	}

	public void setBiz_trade(String biz_trade) {
		this.biz_trade = biz_trade;
	}

	public String getSale_trade() {
		return sale_trade;
	}

	public void setSale_trade(String sale_trade) {
		this.sale_trade = sale_trade;
	}

	public String getFile_trade() {
		return file_trade;
	}

	public void setFile_trade(String file_trade) {
		this.file_trade = file_trade;
	}

	public String getBase_address() {
		return base_address;
	}

	public void setBase_address(String base_address) {
		this.base_address = base_address;
	}

	public String getBase_port() {
		return base_port;
	}

	public void setBase_port(String base_port) {
		this.base_port = base_port;
	}

	public String getOper_address() {
		return oper_address;
	}

	public void setOper_address(String oper_address) {
		this.oper_address = oper_address;
	}

	public String getOper_port() {
		return oper_port;
	}

	public void setOper_port(String oper_port) {
		this.oper_port = oper_port;
	}

	public String getAgent_address() {
		return agent_address;
	}

	public void setAgent_address(String agent_address) {
		this.agent_address = agent_address;
	}

	public String getAgent_port() {
		return agent_port;
	}

	public void setAgent_port(String agent_port) {
		this.agent_port = agent_port;
	}

	public String getCustomer_address() {
		return customer_address;
	}

	public void setCustomer_address(String customer_address) {
		this.customer_address = customer_address;
	}

	public String getCustomer_port() {
		return customer_port;
	}

	public void setCustomer_port(String customer_port) {
		this.customer_port = customer_port;
	}

	public String getAccount_address() {
		return account_address;
	}

	public void setAccount_address(String account_address) {
		this.account_address = account_address;
	}

	public String getAccount_port() {
		return account_port;
	}

	public void setAccount_port(String account_port) {
		this.account_port = account_port;
	}

	public String getBiz_address() {
		return biz_address;
	}

	public void setBiz_address(String biz_address) {
		this.biz_address = biz_address;
	}

	public String getBiz_port() {
		return biz_port;
	}

	public void setBiz_port(String biz_port) {
		this.biz_port = biz_port;
	}
	
	public String getSale_address() {
		return sale_address;
	}

	public void setSale_address(String sale_address) {
		this.sale_address = sale_address;
	}

	public String getSale_port() {
		return sale_port;
	}

	public void setSale_port(String sale_port) {
		this.sale_port = sale_port;
	}

	public String getSupp_address() {
		return supp_address;
	}

	public void setSupp_address(String supp_address) {
		this.supp_address = supp_address;
	}

	public String getSupp_port() {
		return supp_port;
	}

	public void setSupp_port(String supp_port) {
		this.supp_port = supp_port;
	}

	public PropertiesContext getPropertiesContext() {
		return instance;
	}

	public String getMsgque_max_len() {
		return msgque_max_len;
	}

	public void setMsgque_max_len(String msgque_max_len) {
		this.msgque_max_len = msgque_max_len;
	}

	public String getMsgque_min_len() {
		return msgque_min_len;
	}

	public void setMsgque_min_len(String msgque_min_len) {
		this.msgque_min_len = msgque_min_len;
	}

	public String getReqhndl_thrd_num() {
		return reqhndl_thrd_num;
	}

	public void setReqhndl_thrd_num(String reqhndl_thrd_num) {
		this.reqhndl_thrd_num = reqhndl_thrd_num;
	}

	public String getWebthread_wait_time() {
		return webthread_wait_time;
	}

	public void setWebthread_wait_time(String webthread_wait_time) {
		this.webthread_wait_time = webthread_wait_time;
	}

	public String getDistr_line_maxlen() {
		return distr_line_maxlen;
	}

	public void setDistr_line_maxlen(String distr_line_maxlen) {
		this.distr_line_maxlen = distr_line_maxlen;
	}

	public String getSleep_time() {
		return sleep_time;
	}

	public void setSleep_time(String sleep_time) {
		this.sleep_time = sleep_time;
	}

	public String getChnl_maxlen() {
		return chnl_maxlen;
	}

	public void setChnl_maxlen(String chnl_maxlen) {
		this.chnl_maxlen = chnl_maxlen;
	}

	public String getMonitor_time() {
		return monitor_time;
	}

	public void setMonitor_time(String monitor_time) {
		this.monitor_time = monitor_time;
	}

	public String getSupp_trade() {
		return supp_trade;
	}

	public void setSupp_trade(String supp_trade) {
		this.supp_trade = supp_trade;
	}

	public String getSms_sender_address() {
		return sms_sender_address;
	}

	public void setSms_sender_address(String sms_sender_address) {
		this.sms_sender_address = sms_sender_address;
	}

	public String getSms_sender_port() {
		return sms_sender_port;
	}

	public void setSms_sender_port(String sms_sender_port) {
		this.sms_sender_port = sms_sender_port;
	}

	public void setPropertiesContext(PropertiesContext propertiesContext) {
		this.instance = propertiesContext;
	}
	
	public String getCard_address() {
		return card_address;
	}


	public String getPhone_trade() {
		return phone_trade;
	}

	public void setPhone_trade(String phone_trade) {
		this.phone_trade = phone_trade;
	}

	public String getPhone_address() {
		return phone_address;
	}

	public void setPhone_address(String phone_address) {
		this.phone_address = phone_address;
	}

	public String getPhone_port() {
		return phone_port;
	}

	public void setPhone_port(String phone_port) {
		this.phone_port = phone_port;
	}
	

	public String getGuaka_trade() {
		return guaka_trade;
	}

	public void setGuaka_trade(String guaka_trade) {
		this.guaka_trade = guaka_trade;
	}

	public String getGuaka_address() {
		return guaka_address;
	}

	public void setGuaka_address(String guaka_address) {
		this.guaka_address = guaka_address;
	}

	public String getGuaka_port() {
		return guaka_port;
	}

	public void setGuaka_port(String guaka_port) {
		this.guaka_port = guaka_port;
	}

	public void setCard_address(String card_address) {
		this.card_address = card_address;
	}

	public String getCard_port() {
		return card_port;
	}

	public void setCard_port(String card_port) {
		this.card_port = card_port;
	}

	public String getCard_trade() {
		return card_trade;
	}

	public void setCard_trade(String card_trade) {
		this.card_trade = card_trade;
	}
	

	public String getLott_trade() {
		return lott_trade;
	}

	public void setLott_trade(String lott_trade) {
		this.lott_trade = lott_trade;
	}

	public String getLott_address() {
		return lott_address;
	}

	public void setLott_address(String lott_address) {
		this.lott_address = lott_address;
	}

	public String getLott_port() {
		return lott_port;
	}

	public void setLott_port(String lott_port) {
		this.lott_port = lott_port;
	}
	

	public String getKickbacksms_trade() {
		return kickbacksms_trade;
	}

	public void setKickbacksms_trade(String kickbacksms_trade) {
		this.kickbacksms_trade = kickbacksms_trade;
	}

	public String getKzfh_trade() {
		return kzfh_trade;
	}

	public void setKzfh_trade(String kzfh_trade) {
		this.kzfh_trade = kzfh_trade;
	}

	public String getKzfh_address() {
		return kzfh_address;
	}

	public void setKzfh_address(String kzfh_address) {
		this.kzfh_address = kzfh_address;
	}

	public String getKzfh_port() {
		return kzfh_port;
	}

	public void setKzfh_port(String kzfh_port) {
		this.kzfh_port = kzfh_port;
	}

	public String getUniisync_trade() {
		return uniisync_trade;
	}

	public void setUniisync_trade(String uniisync_trade) {
		this.uniisync_trade = uniisync_trade;
	}
	
	public String getLife_trade() {
		return life_trade;
	}

	public void setLife_trade(String life_trade) {
		this.life_trade = life_trade;
	}

	public String getLife_address() {
		return life_address;
	}

	public void setLife_address(String life_address) {
		this.life_address = life_address;
	}

	public String getLife_port() {
		return life_port;
	}

	public void setLife_port(String life_port) {
		this.life_port = life_port;
	}

	public String getSffast_trade() {
		return sffast_trade;
	}

	public void setSffast_trade(String sffast_trade) {
		this.sffast_trade = sffast_trade;
	}

	public String getSffast_address() {
		return sffast_address;
	}

	public void setSffast_address(String sffast_address) {
		this.sffast_address = sffast_address;
	}

	public String getSffast_port() {
		return sffast_port;
	}

	public void setSffast_port(String sffast_port) {
		this.sffast_port = sffast_port;
	}

	public String getProvince_kickback_state() {
		return province_kickback_state;
	}

	public void setProvince_kickback_state(String province_kickback_state) {
		this.province_kickback_state = province_kickback_state;
	}

	public String getProvince_kickback_begin() {
		return province_kickback_begin;
	}

	public void setProvince_kickback_begin(String province_kickback_begin) {
		this.province_kickback_begin = province_kickback_begin;
	}

	public String getProvince_kickback_end() {
		return province_kickback_end;
	}

	public void setProvince_kickback_end(String province_kickback_end) {
		this.province_kickback_end = province_kickback_end;
	}

	public String getProvince_kickback_trade() {
		return province_kickback_trade;
	}

	public void setProvince_kickback_trade(String province_kickback_trade) {
		this.province_kickback_trade = province_kickback_trade;
	}

	public String getCommand2tradecode_configpath() {
		return command2tradecode_configpath;
	}

	public void setCommand2tradecode_configpath(String command2tradecodeConfigpath) {
		command2tradecode_configpath = command2tradecodeConfigpath;
	}

	public String getChannel() {
		return channel;
	}

	public void setChannel(String channel) {
		this.channel = channel;
	}

	public String getDriver() {
		return driver;
	}

	public void setDriver(String driver) {
		this.driver = driver;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getUsr() {
		return usr;
	}

	public void setUsr(String usr) {
		this.usr = usr;
	}

	public String getPwd() {
		return pwd;
	}

	public void setPwd(String pwd) {
		this.pwd = pwd;
	}

	public String getMaxIdle() {
		return maxIdle;
	}

	public void setMaxIdle(String maxIdle) {
		this.maxIdle = maxIdle;
	}

	public String getMaxActive() {
		return maxActive;
	}

	public void setMaxActive(String maxActive) {
		this.maxActive = maxActive;
	}

	public String getPosturl() {
		return posturl;
	}

	public void setPosturl(String posturl) {
		this.posturl = posturl;
	}

	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	public String getMd5String() {
		return md5String;
	}

	public void setMd5String(String md5String) {
		this.md5String = md5String;
	}

	public String getBackurl() {
		return backurl;
	}

	public void setBackurl(String backurl) {
		this.backurl = backurl;
	}

	public String getPartnerIp() {
		return partnerIp;
	}

	public void setPartnerIp(String partnerIp) {
		this.partnerIp = partnerIp;
	}

	public String getPartnerPort() {
		return partnerPort;
	}

	public void setPartnerPort(String partnerPort) {
		this.partnerPort = partnerPort;
	}

	
	
	
	
	
	
	public String getInfo_trade() {
		return info_trade;
	}

	public void setInfo_trade(String info_trade) {
		this.info_trade = info_trade;
	}

	
	
	
	public String getInfo_address() {
		return info_address;
	}

	public void setInfo_address(String info_address) {
		this.info_address = info_address;
	}

	public String getInfo_port() {
		return info_port;
	}

	public void setInfo_port(String info_port) {
		this.info_port = info_port;
	}
	
	

	public String getAcct_trade() {
		return acct_trade;
	}

	public void setAcct_trade(String acct_trade) {
		this.acct_trade = acct_trade;
	}

	public String getAcct_address() {
		return acct_address;
	}

	public void setAcct_address(String acct_address) {
		this.acct_address = acct_address;
	}

	public String getAcct_port() {
		return acct_port;
	}

	public void setAcct_port(String acct_port) {
		this.acct_port = acct_port;
	}
	
	

	public String getUp_url() {
		return up_url;
	}

	public void setUp_url(String up_url) {
		this.up_url = up_url;
	}

	public static void main(String[] args) {
		PropertiesContext propertiesContext = new PropertiesContext();
		propertiesContext.setPropertiesContext(propertiesContext);
		log.info("["+PropertiesContext.instance.getAccount_address()+"]");
	}
}