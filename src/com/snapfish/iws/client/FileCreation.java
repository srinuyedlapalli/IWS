package com.snapfish.iws.client;
//import java.io.BufferedReader;
//import java.io.BufferedWriter;
//import java.io.File;
//import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
//import java.io.Writer;
import java.text.SimpleDateFormat;
import java.util.Calendar;
//import net.sourceforge.htmlunit.corejs.javascript.json.JsonParser;
import org.json.*;
import org.json.JSONObject;



public class FileCreation {
	
	
		public static void filecreation(String fl,String email) throws JSONException
		{
			
			JSONObject obj = new JSONObject();
			if(fl.contains("UserRegister"))
			{
			 obj.put("typeName","iwsUserRegisterRequest");
			 obj.put("firstName","Srinivas");
			 obj.put("lastName","Yedlapalli");
			 obj.put("emailAddress",email);
			 obj.put("password","sfqatest");
			 obj.put("snapfishOptIn","true");
			 obj.put("partnerOptIn","true");
			 obj.put("birthMonth",10);
			 obj.put("birthYear",1986);
			 obj.put("secretQuestion","Your favorite color");
			 obj.put("secretAnswer","Black");
			 obj.put("countryCode","US");
			 obj.put("languageCode","en");
			 obj.put("favoriteTeam","India");
			 obj.put("tsAgreementTitle","Panjaa");
			 obj.put("tsAgreementVersion","1.2");
			 obj.put("tsAgreementAccepted","true");
			obj.put("tsResponseDate","3/1/2012");
			
			}
			
			if(fl.contains("UserLogin"))
			{
				 obj.put("typeName","iwsUserLoginRequest");
				 obj.put("emailAddress",email);
				 obj.put("password","sfqatest");
			}
			if(fl.contains("UserLoginRequest_temppwd"))
			{
				 obj.put("typeName","iwsUserLoginRequest");
				 obj.put("emailAddress",email);
				 obj.put("password",IwsTestClient.temppwd);
			}								
			
			if(fl.contains("LoginRequest_Negative"))
			{
				 obj.put("typeName","iwsUserLoginRequest");
				 obj.put("emailAddress",email);
				 obj.put("password","sfqatest11");
			}
			
			if(fl.contains("UserExists"))
			{
				obj.put("typeName","iwsUserExistsRequest");
				obj.put("emailAddress",email);
			}
			
			if(fl.contains("UserDetail"))
			{
				obj.put("typeName","iwsUserDetailRequest");
				obj.put("emailAddress",email);
			}
			
			if(fl.contains("UserResetPass"))
			{
				obj.put("typeName","iwsUserResetPassRequest");
				obj.put("acctOid",email);
			}
			
			if(fl.contains("UserUpdatePass"))
			{
				obj.put("typeName","iwsUserUpdatePassRequest");
				obj.put("acctOid",email);
				obj.put("password","sfqatest12");
				 
			}
			
			if(fl.contains("UserUpdateDataRequest"))
			{
				obj.put("typeName","iwsUserUpdateDataRequest");
				obj.put( "acctOid",email);
				obj.put("firstName","UP_FN");
				obj.put("lastName","UP_LN");
				obj.put("countryCode","US");
				obj.put("userName","srinu");
				obj.put("snapfishOptIn","false");
				obj.put("favoriteTeam","Daredevels");
				obj.put("dataCollectionField","data");
				obj.put("birthMonth","10");
				obj.put("birthYear","1986");
				obj.put("securityQuestion","What_is_your_birth_pace?");
				obj.put("securityAnswer","Guntur");
				obj.put("registrationMode","Facebook");
				obj.put("tsAgreementTitle","EpC Terms and Conditions");
				obj.put("tsAgreementVersion","1.1");			
				obj.put("tsAgreementAccepted",true);
				obj.put("tsResponseDate","9/22/2011");		   
			}
			
			if(fl.contains("UserUpdateAddress"))
			{
				
				obj.put("typeName","iwsUserUpdateAddressRequest");
				obj.put( "acctOid",email);//Need to update
				obj.put("street1","UpStreet1");
				obj.put("street2","UpStreet2");
				obj.put("street3","UpStreet3");
				obj.put("city","Chicago");
				obj.put("state","IL");
				obj.put("zipcode","60609");
				obj.put("countryCode","US");
				obj.put("countryDesc","Get gas at Joe's Bar and Grill");
				obj.put("county","Los Angeles");
				obj.put("cityLimits","Three-leaf cactus");
				obj.put("taxCity","Santa Monica");
				obj.put("phoneNumber1","555 555-1214");
				obj.put("phoneNumber2","555 555-1215");					   
			}
			
			if(fl.contains("UserUpdatePayment"))
			{				
				obj.put("typeName","iwsUserUpdatePaymentRequest");
				obj.put( "acctOid",email);
				obj.put("cardType","VIS");
				obj.put("cardHolderName","Srinu");
				obj.put("cardNumber","4111111111111111");
				obj.put("cvv2","150");	
				obj.put("expiration","12/2020");
				obj.put("street1","1313 Mockingbird Lane");
				obj.put("street2","Suite 120");
				obj.put("street3","c/o Mr. Goofball");
				obj.put("city","Mockingbird Heights");
				obj.put("county","Los Angeles");
				obj.put("state","CA");
				obj.put("zipcode","94776");
				obj.put("countryCode","US");
				obj.put("phoneNumber1","555 555-1215");	
				obj.put("phoneNumber2","555 555-1215");
				obj.put("clientIpAddress","192.168.101.2");
			}
			
			if(fl.contains("UserUpdatePaymentRequest_Negative"))
			{				
				obj.put("typeName","iwsUserUpdatePaymentRequest");
				obj.put( "acctOid",email);
				obj.put("cardType","VIS");
				obj.put("cardHolderName","Srinu");
				obj.put("cardNumber","411112341324328");
				obj.put("cvv2","150");	
				obj.put("expiration","12/2020");
				obj.put("street1","1313 Mockingbird Lane");
				obj.put("street2","Suite 120");
				obj.put("street3","c/o Mr. Goofball");
				obj.put("city","Mockingbird Heights");
				obj.put("county","Los Angeles");
				obj.put("state","CA");
				obj.put("zipcode","94776");
				obj.put("countryCode","US");
				obj.put("phoneNumber1","555 555-1215");	
				obj.put("phoneNumber2","555 555-1215");
				obj.put("clientIpAddress","192.168.101.2");
			}
			
			try {				 
				FileWriter file = new FileWriter(fl);
				file.write(obj.toString());
				file.flush();
				file.close();
		 
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
			
		}
