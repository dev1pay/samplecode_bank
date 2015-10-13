package service;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Random;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;

@WebServlet("/ProcessBank")
public class ProcessBank extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private String payUrl="";

    public ProcessBank() {
        super();
    }
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// Get param
		String soTien = request.getParameter("amount"); // >=10000vnd
		String orderId = randomString();
		String orderInfo = request.getParameter("order_info");
		String accKey = ""; // access key do 1pay cung cap. Thay bang access key cua ban.
		String command = "request_transaction";
		String returnUrl = "http://localhost:8080/TestBank/CommitRequest";
		String secretKey =""; //secret Key do 1pay cung cap. Thay bang secret Key cua ban.
		try {
			sendPost(accKey, soTien, command, orderId, orderInfo, returnUrl, secretKey);
			response.sendRedirect(payUrl);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	//order id(created by merchant system)
	private String randomString() {
		final String RAND = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
		Random random = new Random();
		StringBuilder builder = new StringBuilder();
		for (int i = 0; i < 7; i++) {
			builder.append(RAND.charAt(random.nextInt(RAND.length())));
		}
		return builder.toString();
	}
			
	private void sendPost(String access_key, String amount, String command,
			String order_id, String order_info, String return_url, String key) throws Exception {
		String url = "http://api.1pay.vn/bank-charging/service";
		URL obj = new URL(url);
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();
		con.setRequestMethod("POST");
		con.setRequestProperty("User-Agent", "Mozilla/5.0");
		con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");
		String urlParameters = "access_key="+access_key+"&amount="+amount+"&command="+command+"&order_id="+order_id
				+"&order_info="+order_info+"&return_url="+return_url;
		
		HmacSHA256 hmacSHA256 = HmacSHA256.getInstance(key);
		String signature=hmacSHA256.sign(urlParameters);
		System.out.println("Signature:" + signature);
		urlParameters = urlParameters+"&signature="+signature;
		
		con.setDoOutput(true);
		DataOutputStream wr = new DataOutputStream(con.getOutputStream());
		wr.writeBytes(urlParameters);
		wr.flush();
		wr.close();
		
		int responseCode = con.getResponseCode();
		System.out.println("URL : " + url);
		System.out.println("Post parameters : " + urlParameters);
		System.out.println("Response Code : " + responseCode);
		BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
		String inputLine;
		StringBuffer response  = new StringBuffer();
		while ((inputLine = in.readLine())!=null) {
			response.append(inputLine);
		}
		in.close();
		String json = response.toString();
		System.out.println(json);
		JSONObject jObj = new JSONObject(json);
		payUrl = jObj.getString("pay_url");
		System.out.println("\nRedirect to URL:"+payUrl);
	}

}
