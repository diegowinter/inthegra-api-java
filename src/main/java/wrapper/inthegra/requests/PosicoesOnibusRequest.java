package wrapper.inthegra.requests;

import java.net.URL;

import org.json.JSONArray;
import org.json.JSONObject;

import wrapper.inthegra.HttpManager;
import wrapper.inthegra.InthegraApi;
import wrapper.inthegra.model.Linha;
import wrapper.inthegra.model.Veiculo;

public class PosicoesOnibusRequest {
	
	private InthegraApi inthegraApi;
	
	public PosicoesOnibusRequest (InthegraApi inthegraApi) {
		this.inthegraApi = inthegraApi;
	}
	
	public Linha execute(String codigoLinha) {
		HttpManager httpManager = new HttpManager(inthegraApi.getXApiKey(), inthegraApi.getXAuthToken());
		String response = null;
		
		try {	
			URL url = new URL("https://api.inthegra.strans.teresina.pi.gov.br/v1/veiculosLinha?busca=" + codigoLinha);
			response = httpManager.executeGet(httpManager.getConnection(url));
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		Linha linha = new Linha();
		JSONObject jsonLinhaObj = new JSONObject(response);
		JSONObject jsonLinha = jsonLinhaObj.getJSONObject("Linha");
		linha.setCodigo(jsonLinha.getString("CodigoLinha"));
		linha.setDenominacao(jsonLinha.getString("Denomicao"));
		linha.setOrigem(jsonLinha.getString("Origem"));
		linha.setRetorno(jsonLinha.getString("Retorno"));
		linha.setCircular(jsonLinha.getBoolean("Circular"));
		
		Veiculo veiculo;
		JSONArray jsonArrayVeiculos = new JSONArray(jsonLinha.getJSONArray("Veiculos").toString());
		for (int i=0; i<jsonArrayVeiculos.length(); i++) {
			veiculo = new Veiculo();
			JSONObject obj = jsonArrayVeiculos.getJSONObject(i);
			
			veiculo.setCodigo(obj.getString("CodigoVeiculo"));
			veiculo.setLatitude(obj.getString("Lat"));
			veiculo.setLongitude(obj.getString("Long"));
			veiculo.setHora(obj.getString("Hora"));
			
			linha.getVeiculos().add(veiculo);
		}
		
		return linha;
	}

}
