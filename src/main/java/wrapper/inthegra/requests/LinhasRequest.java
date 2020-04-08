package wrapper.inthegra.requests;

import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import wrapper.inthegra.HttpManager;
import wrapper.inthegra.InthegraApi;
import wrapper.inthegra.model.Linha;

public class LinhasRequest {
	
	private InthegraApi inthegraApi;
	
	public LinhasRequest(InthegraApi inthegraApi) {
		this.inthegraApi = inthegraApi;
	}
	
	public List<Linha> execute(String query) {
		HttpManager httpManager = new HttpManager(inthegraApi.getXApiKey(), inthegraApi.getXAuthToken());
		String response = null;
		
		try {	
			URL url = new URL("https://api.inthegra.strans.teresina.pi.gov.br/v1/linhas?busca=" + URLEncoder.encode(query, "UTF-8"));
			response = httpManager.executeGet(httpManager.getConnection(url));
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		Linha linha;
		List<Linha> linhas = new ArrayList<Linha>();
		JSONArray jsonArrayLinhas = new JSONArray(response);
		for (int i=0; i<jsonArrayLinhas.length(); i++) {
			linha = new Linha();
			JSONObject obj = jsonArrayLinhas.getJSONObject(i);
			
			linha.setCodigo(obj.getString("CodigoLinha"));
			linha.setDenominacao(obj.getString("Denomicao"));
			linha.setOrigem(obj.getString("Origem"));
			linha.setRetorno(obj.getString("Retorno"));
			linha.setCircular(obj.getBoolean("Circular"));
			
			linhas.add(linha);
		}
		
		return linhas;
	}

}
