package service;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;

import service.HmacSHA256;

@WebServlet("/CommitRequest")
public class CommitRequest extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private String json ="";

    public CommitRequest() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String accKey = request.getParameter("access_key");
		String command = "close_transaction";
		String trans_ref = request.getParameter("trans_ref");
		String secretKey =""; //secret Key do 1pay cung cap. Thay bang secret Key cua ban.
		try {
			sendPost(accKey, command, trans_ref,secretKey);

			JSONObject jObj = new JSONObject(json);
			String response_code = jObj.getString("response_code");
			
			if (response_code.equals("00")) {
				// Merchant xử lý dữ liệu và lưu lại log giao dịch tại đây.
			} else {
				// Thông tin không hợp lệ, log lại để theo dõi.
			}
			request.setAttribute("json", json);
			request.getRequestDispatcher("WEB-INF/jsp/Json.jsp").forward(request, response);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

	}
	
	
	
	private void sendPost(String access_key, String command, String trans_ref, String key)
			throws Exception {
		String url = "http://api.1pay.vn/bank-charging/service";
		URL obj = new URL(url);
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();
		con.setRequestMethod("POST");
		con.setRequestProperty("User-Agent", "Mozilla/5.0");
		con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");
		String urlParameters = "access_key="+access_key+"&command="+command+"&trans_ref="+trans_ref;
		
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
		BufferedReader in = new BufferedReader(new InputStreamReader(
				con.getInputStream()));
		String inputLine;
		StringBuffer response = new StringBuffer();
		while ((inputLine = in.readLine()) != null) {
			response.append(inputLine);
		}
		in.close();
		json = response.toString();
		System.out.println(json);

	}

}
