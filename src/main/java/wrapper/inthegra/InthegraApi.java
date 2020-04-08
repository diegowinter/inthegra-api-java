package wrapper.inthegra;

import java.util.List;

import wrapper.inthegra.model.Linha;
import wrapper.inthegra.model.Parada;
import wrapper.inthegra.requests.AccessTokenRequest;
import wrapper.inthegra.requests.LinhasRequest;
import wrapper.inthegra.requests.ParadasRequest;
import wrapper.inthegra.requests.PosicoesOnibusRequest;

public class InthegraApi {
	
	private String email;
	private String password;
	private String xApiKey;
	private String xAuthToken;
	
	private InthegraApi(Builder builder) {
		this.email = builder.email;
		this.password = builder.password;
		this.xApiKey = builder.xApiKey;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getXApiKey() {
		return xApiKey;
	}

	public void setXApiKey(String xApiKey) {
		this.xApiKey = xApiKey;
	}

	public String getXAuthToken() {
		return xAuthToken;
	}

	public InthegraApi setXAuthToken(String xAuthToken) {
		this.xAuthToken = xAuthToken;
		return this;
	}
	
	public String requestAccessToken() {
		AccessTokenRequest atr = new AccessTokenRequest(this);
		
		return atr.execute();
	}
	
	public List<Linha> requestLinhas(String query) {
		LinhasRequest lr = new LinhasRequest(this);
		
		return lr.execute(query);
	}
	
	public List<Parada> requestParadas(String query){
		ParadasRequest pr = new ParadasRequest(this);
		
		return pr.execute(query);
	}
	
	public Linha requestPosicoesOnibus(String codigo) {
		PosicoesOnibusRequest por = new PosicoesOnibusRequest(this);
		
		return por.execute(codigo);
	}
	
	public static class Builder {
		
		private String email;
		private String password;
		private String xApiKey;
		
		public Builder email(String email) {
			this.email = email;
			return this;
		}
		
		public Builder password(String password) {
			this.password = password;
			return this;
		}
		
		public Builder xApiKey(String xApiKey) {
			this.xApiKey = xApiKey;
			return this;
		}
		
		public InthegraApi build() {
			return new InthegraApi(this);
		}
		
	}
	
}
