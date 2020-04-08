package wrapper.inthegra.requests;

import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import wrapper.inthegra.HttpManager;
import wrapper.inthegra.InthegraApi;
import wrapper.inthegra.model.Parada;

public class ParadasRequest {
	
	private InthegraApi inthegraApi;
	
	public ParadasRequest(InthegraApi inthegraApi) {
		this.inthegraApi = inthegraApi;
	}
	
	public List<Parada> execute(String query) {
		HttpManager httpManager = new HttpManager(inthegraApi.getXApiKey(), inthegraApi.getXAuthToken());
		String response = null;
		
		try {
			URL url = new URL("https://api.inthegra.strans.teresina.pi.gov.br/v1/paradas?busca=" + URLEncoder.encode(query, "UTF-8"));
			response = httpManager.executeGet(httpManager.getConnection(url));
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		Parada parada;
		List<Parada> paradas = new ArrayList<Parada>();
		JSONArray jsonArrayParadas = new JSONArray(response);
		for (int i=0; i<jsonArrayParadas.length(); i++) {
			parada = new Parada();
			JSONObject obj = jsonArrayParadas.getJSONObject(i);
			
			parada.setCodigo(obj.getInt("CodigoParada"));
			parada.setDenominacao(obj.getString("Denomicao"));
			parada.setEndereco(obj.getString("Endereco"));
			parada.setLatitude(obj.getString("Lat"));
			parada.setLongitude(obj.getString("Long"));
			
			paradas.add(parada);
		}
			
		return paradas;
	}
	

}
