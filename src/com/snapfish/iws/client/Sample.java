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
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.snapfish.iws.client.MySSLSocketFactory;
import com.snapfish.iws.client.FileCreation;
/**
 * Works with Java 6 and Apache Commons HTTPClient 3.0.1
 * 
 * @author (c) 2011 Copyright Hewlett-Packard Development Company, L.P.
 */
public class Sample{

    private static String proxyHost;
    private static String proxyPort;
    public static  String username=System.getProperty("user.name");
    public static String email="srinu"+emailaddress()+"@gmail.com";
    public static String urls[]={"iws/1/userUpdateAddress"};//"iws/1/userUpdateData"
   //"iws/1/userRegister","iws/1/userLogin","iws/1/userExists","iws/1/userDetail","iws/1/userUpdateAddress"
    public static String relativeUrl;
   public static String hosts[]={"openapi.sfstg4.qa.snapfish.com","openapi.sfstg8.qa.snapfish.com"};
   public static int resp[]=new int[20];
    public static int rownum;
    public static int j;
    public static int AccoId;
    static int count=0;
    static int port = 0;
    
//    public static String res;
    public static void main(String[] args) throws Exception {
        handleProxy();
        String hostname = null;
        int count=0;
        int port = 0;
        String files[]={"C:\\IwsTestClient\\iwsUserUpdateAddressRequest.json"};//,C:\\IwsTestClient\\iwsUserUpdateRequest.JSON"
        
        //"C:\\IwsTestClient\\iwsUserRegisterRequest.JSON","C:\\IwsTestClient\\iwsUserLoginRequest.JSON","C:\\IwsTestClient\\iwsUserExistsRequest.JSON","C:\\IwsTestClient\\iwsUserDetailRequest.JSON",C:\\IwsTestClient\\iwsUserUpdateRequest.JSON"
//        String relativeUrl = null;
        String payloadFilename = null;
        boolean isGet = true;
        for(j=0;j<hosts.length;j++)
        {
         createWorkbook();
         createSheet();
       for(int i=0;i<urls.length;i++)
        {        
    	   try {
            hostname =hosts[j] ;
            port = 443;
            relativeUrl = urls[i];
            payloadFilename = null;
//            email(files[i]);
//            if (args.length < 4) {
//                System.out.println("Missing payload file.  Assuming a GET");
//            } else {
            FileCreation.filecreation(files[i],email);
                isGet = false;
                payloadFilename = files[i];
//            }
        }
        
        catch (Exception e) {
            usage();
            System.exit(-1);
        }
        
        URI uri = getUri(hostname, port, relativeUrl);
        if (isGet) {
            doGet(uri);
        } 
        else
        {
            String payload = getPayloadFromFile(payloadFilename);
            doPut(uri, payload);
        }
//        Thread.sleep(6000);
    }
       for(int i=0;i<urls.length;i++)
       {
    	   if(resp[i]!=200)
    		   count++;
    		   
       }
        if(count>0)
        	mailprop();
        email="veer"+emailaddress()+"@gmail.com";
        rownum=1;
        count=0;
        }
        //Thread.sleep(1800000);
    }
    
    @BeforeClass
    public void beforeClass() throws IOException
    {
    	 handleProxy();
         String hostname = null;
        
         createWorkbook();
         createSheet();
         
         
    }
    
    @Test
    public void iwsUserRegisterRequest()
    {
    	
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


    private static void doPut(URI uri, String payload) throws Exception {
    	PutMethod putMethod = new PutMethod(uri.toASCIIString());
        putMethod.setRequestEntity(new StringRequestEntity(payload));
        HttpClient httpClient = getClient();
        System.out.println("Sending PUT with payload:  \n" + payload);
        int response = httpClient.executeMethod(putMethod);
        resp[rownum]=response;
        System.out.println("Server returned HTTP status code:  " + response);
        String responseBody = putMethod.getResponseBodyAsString();
        System.out.println("Server returned payload:  " + responseBody);
        writeResults(rownum,relativeUrl,response,payload,responseBody);
        String res1=(String) (payload+"--->"+response);
        //        res[rownum]=res1;
        rownum++;
    }
    
    public static void mailprop() throws AddressException, MessagingException
	{
		
		String host="mx.valuelabs.net";
		String to="srinivas.yedlapalli@valuelabs.net";
		String from ="srinivas.yedlapalli@valuelabs.net";
		String subject="UMAPI AUTOMATION RESULTS";
		String msgBody="This mail is inform you that we have run UM APIs on staging8";
		String filename="C://Users//"+username+"//Desktop//UMAPI.xls";
		
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
		filename="C://Users//"+username+"//Desktop//UMAPI"+j+".xls";
		FileOutputStream fileout=new FileOutputStream(filename);
		HSSFWorkbook wb=new HSSFWorkbook();
		wb.write(fileout);
		fileout.close();
		
	}

public static void createSheet() throws IOException 
{
	
	FileInputStream myxls=new FileInputStream("C://Users//"+username+"//Desktop//UMAPI"+j+".xls");
	HSSFWorkbook wb=new HSSFWorkbook(myxls);
	HSSFSheet sheet=wb.createSheet(email);
	HSSFRow rowhead=sheet.createRow(0);
	rowhead.createCell(1).setCellValue("API");
	rowhead.createCell(2).setCellValue("Payload");
	rowhead.createCell(3).setCellValue("Response");
	rowhead.createCell(4).setCellValue("Responsebody");
	FileOutputStream fileOut=new FileOutputStream("C://Users//"+username+"//Desktop//UMAPI"+j+".xls");
	wb.write(fileOut);
	fileOut.close();
	
}



public static void writeResults(int rownum,String relativeUrl,int payload,String response,String responseBody) throws IOException
{
	
	FileInputStream myxls=new FileInputStream("C://Users//"+username+"//Desktop//UMAPI"+j+".xls");
	HSSFWorkbook wb=new HSSFWorkbook(myxls);
	HSSFSheet sheet=wb.getSheet(email);
	HSSFRow row=sheet.createRow(rownum);
	row.createCell(0).setCellValue(relativeUrl);
	row.createCell(1).setCellValue(payload);
	row.createCell(2).setCellValue(response);
	row.createCell(3).setCellValue(responseBody);
	FileOutputStream fileOut=new FileOutputStream("C://Users//"+username+"//Desktop//UMAPI"+j+".xls");
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
        return contents.toString();
    }
}
