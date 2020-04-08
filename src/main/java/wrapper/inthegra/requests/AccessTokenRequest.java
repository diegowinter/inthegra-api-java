package wrapper.inthegra.requests;

import java.net.URL;

import org.json.JSONObject;

import wrapper.inthegra.HttpManager;
import wrapper.inthegra.InthegraApi;

public class AccessTokenRequest {
	
	private InthegraApi inthegraApi;
	
	public AccessTokenRequest(InthegraApi inthegraApi) {
		this.inthegraApi = inthegraApi;
	}
	
	public String execute() {
		HttpManager httpManager = new HttpManager(inthegraApi.getXApiKey());
		String jsonBody = "{ \"email\" : \"" + inthegraApi.getEmail() + "\", \"password\" : \"" + inthegraApi.getPassword() + "\"}";
		String response = null;
		
		try {	
			URL url = new URL("https://api.inthegra.strans.teresina.pi.gov.br/v1/signin");
			response = httpManager.executePost(httpManager.getConnection(url), jsonBody);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		JSONObject jsonResponse = new JSONObject(response);
		
		return jsonResponse.getString("token");
	}
}
