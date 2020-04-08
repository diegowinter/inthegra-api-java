package wrapper.inthegra;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

import org.apache.commons.io.IOUtils;

public class HttpManager {
	
	private String xApiKey;
	private String xAuthToken;
	
	public HttpManager(String xApiKey) {
		this.xApiKey = xApiKey;
	}
	
	public HttpManager(String xApiKey, String xAuthToken) {
		this.xApiKey = xApiKey;
		this.xAuthToken = xAuthToken;
	}
	
	public HttpURLConnection getConnection(URL url) throws IOException {
		DateFormat dateFormat = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss z", Locale.US);
		dateFormat.setTimeZone(TimeZone.getTimeZone("GMT"));
		String formattedDate = dateFormat.format(new Date());
		
		HttpURLConnection connection = (HttpURLConnection) url.openConnection();
		connection.setConnectTimeout(5000);
		connection.setRequestProperty("Content-Type", "application/json");
		connection.setRequestProperty("Accept-Language", "en");
		connection.setRequestProperty("Date", formattedDate);
		connection.addRequestProperty("X-Api-Key", xApiKey);
		
		return connection;
	}
	
	public String executePost(HttpURLConnection connection, String requestBody) {
		try {
			connection.setDoOutput(true);
			connection.setDoInput(true);
			connection.setRequestMethod("POST");
			OutputStream os = connection.getOutputStream();
			os.write(requestBody.getBytes("UTF-8"));
			os.close();
			InputStream in = new BufferedInputStream(connection.getInputStream());
			String response = IOUtils.toString(in, "UTF-8");
			in.close();
			connection.disconnect();
			return response;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public String executeGet(HttpURLConnection connection) {
		try {
			connection.setDoInput(true);
			connection.setRequestMethod("GET");
			connection.setRequestProperty("X-Auth-Token", xAuthToken);
			InputStream in = new BufferedInputStream(connection.getInputStream());
			String response = IOUtils.toString(in, "UTF-8");
			in.close();
			connection.disconnect();
			return response;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
}
