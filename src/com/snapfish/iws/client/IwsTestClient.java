package com.snapfish.iws.client;

import java.io.BufferedReader;

import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URI;
import java.net.URLEncoder;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.Signature;
import java.security.spec.PKCS8EncodedKeySpec;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.httpclient.HostConfiguration;
import org.apache.commons.httpclient.HttpClient;
//import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PutMethod;
import org.apache.commons.httpclient.methods.StringRequestEntity;
import org.apache.commons.httpclient.protocol.Protocol;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.json.JSONException;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.snapfish.iws.client.MySSLSocketFactory;
import com.snapfish.iws.client.FileCreation;

public class IwsTestClient{

    private static String proxyHost;
    private static String proxyPort;
    public static  String username=System.getProperty("user.name");
    public static String email;
    public static String relativeUrl;
   public static String host;
   public static int resp[]=new int[20];
    public static int rownum=1;
    public static int j;
    public static String accOid;
    static int count=0;
    static int port = 443;
    String payloadFilename = null;
    public static String temppwd;
         
    @Parameters({"environment"})
    @BeforeClass
    public void beforeClass(String environment) throws IOException
    {
    	 host=environment;
    	 System.out.println("Environment is -----------------"+host);
	     handleProxy();      
       /*  createWorkbook();
         createSheet(); */        
    }
    
    @Test(description="Iws Call for Registraion ",priority=1)
    public void iwsUser1RegisterRequest() throws Exception
    {
    	email="srinu"+emailaddress()+"@gmail.com";
    	payloadFilename=".\\iwsUserRegisterRequest.JSON";
    	relativeUrl="iws/1/userRegister";
    	FileCreation.filecreation(payloadFilename,email);
    	URI uri = getUri(host, port,relativeUrl);
    	String payload = getPayloadFromFile(payloadFilename);
        String body= doPut(uri, payload);        
        accOid=body.split("acctOid\":")[1].split(",")[0];
        System.out.println("Account OId " + accOid);
    }
    
   @Test(description="Iws Call for Login", priority=2)
    public void iwsUser2LoginRequest() throws Exception
    {
    	payloadFilename=".\\iwsUserLoginRequest.json";
    	relativeUrl="iws/1/userLogin";
    	FileCreation.filecreation(payloadFilename,email);
    	URI uri = getUri(host, port,relativeUrl);
    	String payload = getPayloadFromFile(payloadFilename);
        doPut(uri, payload);	
    } 
    
    @Test(description="Iws Call for Use Exists Request" ,priority=3)
    public void iwsUser3ExistsRequest() throws Exception
    {
    	payloadFilename=".\\iwsUserExistsRequest.json";
    	relativeUrl="iws/1/userExists";    	
    	FileCreation.filecreation(payloadFilename,email);
    	URI uri = getUri(host, port,relativeUrl);
    	String payload = getPayloadFromFile(payloadFilename);
        doPut(uri, payload);	
    } 
    
    @Test(description="Iws Call for UserDetailRequest" ,priority=4)
    public void iwsUser4DetailRequest() throws Exception
    {
    	payloadFilename="..\\iwsUserDetailRequest.json";
    	relativeUrl="iws/1/userDetail";   
    	FileCreation.filecreation(payloadFilename,email);
    	URI uri = getUri(host, port,relativeUrl);
    	String payload = getPayloadFromFile(payloadFilename);
        doPut(uri, payload);	
    } 
    
      
    @Test(description="Iws Call for UserUpdateDataRequest",priority=5)
    public void iwsUser5UpdateDataRequest() throws Exception
    {
    	payloadFilename="..\\iwsUserUpdateDataRequest.json";
    	relativeUrl="iws/1/userUpdateData"; 
    	FileCreation.filecreation(payloadFilename,accOid);
    	URI uri = getUri(host, port,relativeUrl);
    	String payload = getPayloadFromFile(payloadFilename);
        doPut(uri, payload);
    } 
    
   @Test(description="Iws Call for UserUpdateAddressRequest",priority=6)
    public void iwsUser6UpdateAddressRequest() throws Exception
    {
    	payloadFilename="..\\iwsUserUpdateAddressRequest.json";
    	relativeUrl="iws/1/userUpdateAddress";   	
    	FileCreation.filecreation(payloadFilename,accOid);
    	URI uri = getUri(host, port,relativeUrl);
    	String payload = getPayloadFromFile(payloadFilename);
        doPut(uri, payload);
    } 
    
    @Test(description="Iws Call for UserUpdatePaymentRequest",priority=7)
    public void iwsUser7UpdatePaymentRequest() throws Exception
    {
    	payloadFilename="..\\iwsUserUpdatePaymentRequest.json";
    	relativeUrl="iws/1/userUpdatePayment";
    	FileCreation.filecreation(payloadFilename,accOid);
    	URI uri = getUri(host, port,relativeUrl);
    	String payload = getPayloadFromFile(payloadFilename);
        doPut(uri, payload);  
    }  
    
    @Test(description="Iws Call for UserUpdatePassRequest",dependsOnMethods="iwsUser1RegisterRequest",priority=8 )
    public void iwsUser8UpdatePassRequest() throws Exception
    {
    	payloadFilename="..\\iwsUserUpdatePassRequest.json";
    	relativeUrl="iws/1/userUpdatePass";
    	FileCreation.filecreation(payloadFilename,accOid);
    	URI uri = getUri(host, port,relativeUrl);
    	String payload = getPayloadFromFile(payloadFilename);
        doPut(uri, payload);
    } 
    
    @Test(description="Iws Call for UserResetPassRequest",dependsOnMethods={"iwsUser1RegisterRequest"},priority=9 )
    public void iwsUser9ResetPassRequest() throws Exception
    {
    	payloadFilename="..\\iwsUserResetPassRequest.json";
    	relativeUrl="iws/1/userResetPass";
    	FileCreation.filecreation(payloadFilename,accOid);
    	URI uri = getUri(host, port,relativeUrl);
    	String payload = getPayloadFromFile(payloadFilename);
       String response= doPut(uri, payload); 
       System.out.println("Response Code -------------"+response);       
       temppwd=response.split("tempPass\":\"")[1].split("\"")[0];       
       payloadFilename=".\\iwsUserLoginRequest_temppwd.json";
   	   relativeUrl="iws/1/userLogin";
   	   FileCreation.filecreation(payloadFilename,email);
   	   uri = getUri(host, port,relativeUrl);
	   payload = getPayloadFromFile(payloadFilename);
   	   doPut(uri, payload);
    } 
    
//Negative Scenarios    
    @Test(description="Iws Call for UserUpdatePaymentRequest using invalid CC details [Negative Check] ",priority=10, dependsOnMethods={"iwsUser6UpdateAddressRequest","iwsUser1RegisterRequest"})
    public void iwsUserUpdatePaymentRequest_Negative() throws Exception
    {
    	payloadFilename="..\\iwsUserUpdatePaymentRequest_Negative.json";
    	relativeUrl="iws/1/userUpdatePayment";
    	FileCreation.filecreation(payloadFilename,accOid);
    	URI uri = getUri(host, port,relativeUrl);
    	String payload = getPayloadFromFile(payloadFilename);
    	doPut_negative(uri, payload,"\"resultCode\":601,\"resultDescription\":\"Invalid credit card number");  
    }  
    
    @Test(description="Iws Call for Login with invalid credentials [Negative checks]", priority=11, dependsOnMethods={"iwsUser1RegisterRequest"} )
    public void iwsUserLoginRequest_Negative() throws Exception
    {
    	payloadFilename=".\\iwsUserLoginRequest_Negative.json";
    	relativeUrl="iws/1/userLogin";
    	FileCreation.filecreation(payloadFilename,email);
    	URI uri = getUri(host, port,relativeUrl);
    	String payload = getPayloadFromFile(payloadFilename);
    	doPut_negative(uri, payload,"resultCode\":505,\"resultDescription\":\"Invalid login");	
    }  
           
    @org.testng.annotations.AfterClass
    public void AfterClass() throws AddressException, MessagingException
    {   	
//   	mailprop();
    }
        
    private static void handleProxy() {
        Properties systemProperties = System.getProperties();
        System.out.println("proxy_host=" + systemProperties.getProperty("proxy_host"));
        System.out.println("proxy_port=" + systemProperties.getProperty("proxy_port"));
        proxyHost = systemProperties.getProperty("proxy_host");
        proxyPort = systemProperties.getProperty("proxy_port");
    }

    private static URI getUri(String hostname, int port, String relativeUrl) throws Exception {
        String dataToSign = "" + System.currentTimeMillis();
        String digitalSignature = getSignature(dataToSign);
        String uriString = "https://" + hostname + ":" + port + "/" + relativeUrl;
        uriString += (uriString.contains("?") ? "&" : "?") + "ts=" + dataToSign + "&signature=" + digitalSignature;
        System.out.println("Generated URI:  " + uriString);
        URI uri = new URI(uriString);
        return uri;
    }

    private static String getSignature(String dataToSign) throws Exception {
        byte[] bytesToSign = dataToSign.getBytes("UTF-8");
        Signature signature = Signature.getInstance("SHA1withRSA", "SunJSSE");
        byte[] privateKeyBytes = readPrivateKeyFile();
        PKCS8EncodedKeySpec privKeySpec = new PKCS8EncodedKeySpec(privateKeyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA", "SunJSSE");
        PrivateKey privateKey = keyFactory.generatePrivate(privKeySpec);
        signature.initSign(privateKey);
        signature.update(bytesToSign);
        byte[] digitalSignatureBytes = signature.sign();
        return toBase64String(digitalSignatureBytes);
    }

    private static String toBase64String(byte[] digitalSignatureBytes) throws Exception {
        String base64EncodedDigitalSignature = Base64.encodeBase64String(digitalSignatureBytes);
        String encoded = URLEncoder.encode(base64EncodedDigitalSignature, "UTF-8");
        System.out.println("URL/Base64 encoded digital signature:  " + encoded);
        return encoded;
    }
    
     

    private static byte[] readPrivateKeyFile() throws Exception {
        File file = new File("private_key.pkcs8");
        System.out.println("Using private key:  " + file.getCanonicalPath());
        DataInputStream dataInputStream = new DataInputStream(new FileInputStream(file));
        byte[] privateKeyBytes = new byte[(int) file.length()];
        dataInputStream.readFully(privateKeyBytes);
        dataInputStream.close();
        return privateKeyBytes;
    }

    private static void doGet(URI uri) throws Exception {
        GetMethod getMethod = new GetMethod(uri.toASCIIString());
        HttpClient httpClient = getClient();
        int response = httpClient.executeMethod(getMethod);
        System.out.println("Server returned HTTP status code:  " + response);
        String responseBody = getMethod.getResponseBodyAsString();
        System.out.println("Server returned payload:  " + responseBody);              
    }

    @SuppressWarnings("deprecation")
    private static HttpClient getClient() {
        Protocol.registerProtocol("https", new Protocol("https", new MySSLSocketFactory(), 443));
        HttpClient httpClient = new HttpClient();
        if (proxyHost != null && proxyPort != null) {
            HostConfiguration config = httpClient.getHostConfiguration();
            config.setProxy(proxyHost, new Integer(proxyPort));
        }
        return httpClient;
    }
    
    public static String emailaddress()
    {
    	 Calendar cal=Calendar.getInstance();
 		SimpleDateFormat sdf=new SimpleDateFormat("yyyMMddhhmmss");
 		return(sdf.format(cal.getTime()));
    }


    private static String doPut(URI uri, String payload) throws Exception {
    	PutMethod putMethod = new PutMethod(uri.toASCIIString());
        putMethod.setRequestEntity(new StringRequestEntity(payload));
        HttpClient httpClient = getClient();
        System.out.println("Sending PUT with payload:  \n" + payload);
        int response = httpClient.executeMethod(putMethod);
        resp[rownum]=response;
        Assert.assertEquals(response+"","200");
        System.out.println("Server returned HTTP status code:  " + response);
        Reporter.log("<b>Response Status </b> :"+response);
        Reporter.log("\n");
        String responseBody = putMethod.getResponseBodyAsString();
        System.out.println("Server returned payload:  " + responseBody);
        Reporter.log("<b>Response Body   </b> "+responseBody);        
        if(!(relativeUrl.contains("/userExists")|relativeUrl.contains("/userDetail")))        
        Assert.assertFalse(!responseBody.contains("{\"resultCode\":0,\"resultDescription\":\"Success\""));
//        writeResults(rownum,relativeUrl,response,payload,responseBody);
        String res1=(String) (payload+"--->"+response);
        //        res[rownum]=res1;
        rownum++;
        return  responseBody;
    }
    private static String doPut_negative(URI uri, String payload,String expectedString) throws Exception {
    	PutMethod putMethod = new PutMethod(uri.toASCIIString());
        putMethod.setRequestEntity(new StringRequestEntity(payload));
        HttpClient httpClient = getClient();
        System.out.println("Sending PUT with payload:  \n" + payload);
        int response = httpClient.executeMethod(putMethod);
        resp[rownum]=response;
        Assert.assertEquals(response+"","200");
        System.out.println("Server returned HTTP status code:  " + response);
        Reporter.log("<b>Response Status </b> :"+response);
        Reporter.log("\n");
        String responseBody = putMethod.getResponseBodyAsString();
        System.out.println("Server returned payload:  " + responseBody);
        Reporter.log("<b>Response Body   </b> "+responseBody);               
        Assert.assertFalse(!responseBody.contains(expectedString));
        writeResults(rownum,relativeUrl,response,payload,responseBody);
        String res1=(String) (payload+"--->"+response);
        rownum++;
        return  responseBody;
    }
    
    public static void mailprop() throws AddressException, MessagingException
	{
		
		String host="mx.valuelabs.net";
		String to="srinivas.yedlapalli@valuelabs.net";
		String from ="srinivas.yedlapalli@valuelabs.net";
		String subject="UMAPI AUTOMATION RESULTS";
		String msgBody="This mail is inform you that we have run UM APIs on staging8";
		String filename="C:\\EclipseWorkSpace\\IWS\\testng_output\\"+util.GlobalFunctions.reportId+"emailable-report.html";		
		boolean sessionDebug=false;
		Properties props=new Properties();
		props.put("mail.host", host);
		props.put("mail.transport.protocol","smtp");
		Session session=Session.getDefaultInstance(props, null);
		session.setDebug(sessionDebug);
		Message msg=new MimeMessage(session);
		msg.setFrom(new InternetAddress(from));
		InternetAddress address[]={new InternetAddress(to)};
		msg.addRecipients(Message.RecipientType.TO, address);
		msg.setSubject(subject);
		MimeBodyPart mbp1=new MimeBodyPart();
		mbp1.setText(msgBody);
		MimeBodyPart mbp2=new MimeBodyPart();
		FileDataSource fds=new FileDataSource(filename);
		mbp2.setDataHandler(new DataHandler(fds));
		mbp2.setFileName(fds.getName());
		Multipart mp=new MimeMultipart();
		mp.addBodyPart(mbp1);
		mp.addBodyPart(mbp2);
		msg.setContent(mp);
		msg.saveChanges();
		Transport.send(msg);
	}
    
    public static void createWorkbook() throws IOException
	{
		String filename;
		filename="C://Users//"+username+"//Desktop//UMAPI"+".xls";
		FileOutputStream fileout=new FileOutputStream(filename);
		HSSFWorkbook wb=new HSSFWorkbook();
		wb.write(fileout);
		fileout.close();
		
	}

public static void createSheet() throws IOException 
{
	
	FileInputStream myxls=new FileInputStream("C://Users//"+username+"//Desktop//UMAPI"+".xls");
	HSSFWorkbook wb=new HSSFWorkbook(myxls);
	HSSFSheet sheet=wb.createSheet("Results");
	HSSFRow rowhead=sheet.createRow(0);
	rowhead.createCell(1).setCellValue("API");
	rowhead.createCell(2).setCellValue("Payload");
	rowhead.createCell(3).setCellValue("Response");
	rowhead.createCell(4).setCellValue("Responsebody");
	FileOutputStream fileOut=new FileOutputStream("C://Users//"+username+"//Desktop//UMAPI"+".xls");
	wb.write(fileOut);
	fileOut.close();
	
}



public static void writeResults(int rownum,String relativeUrl,int payload,String response,String responseBody) throws IOException
{
	
	FileInputStream myxls=new FileInputStream("C://Users//"+username+"//Desktop//UMAPI"+".xls");
	HSSFWorkbook wb=new HSSFWorkbook(myxls);
	HSSFSheet sheet=wb.getSheet("Results");
	HSSFRow row=sheet.createRow(rownum);
	row.createCell(0).setCellValue(relativeUrl);
	row.createCell(1).setCellValue(payload);
	row.createCell(2).setCellValue(response);
	row.createCell(3).setCellValue(responseBody);
	FileOutputStream fileOut=new FileOutputStream("C://Users//"+username+"//Desktop//UMAPI"+".xls");
	wb.write(fileOut);
	fileOut.close();
	rownum++;
}


    private static void usage() {
        System.out.println("Usage:");
        System.out.println("IwsTestClient localhost 7234 \"iws/1/apps/1234\" ");
        System.out.println("IwsTestClient localhost 7234 \"iws/1/createApp\" payload_file.json");
    }

    private static String getPayloadFromFile(String payloadFilename) throws Exception {
        FileReader fileReader = new FileReader(payloadFilename);
        StringBuffer contents = new StringBuffer();
        BufferedReader br = new BufferedReader(fileReader);
        try {
            String text = null;

            // repeat until all lines is read
            while ((text = br.readLine()) != null) {
                contents.append(text).append(System.getProperty("line.separator"));
            }
        } 
        finally {
            br.close();
        }
        Reporter.log("<b>Request Payload </b>"+contents.toString());
        Reporter.log("\n");
        return contents.toString();
    }
}
