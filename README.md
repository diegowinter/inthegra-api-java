# Inthegra API Java
Um wrapper Java para a [Web API Inthegra](https://inthegra.strans.teresina.pi.gov.br/), para requisitar informações sobre o transporte coletivo por ônibus de Teresina/PI.

## Adicionando ao seu projeto
Por enquanto, você pode testar a baixando o arquivo .jar disponível na aba de [releases](https://github.com/diegowinter/inthegra-api-java/releases).

## Uso
Para fazer o uso desta API, você primeiramente **deve se registrar** no [site oficial da API Inthegra](https://inthegra.strans.teresina.pi.gov.br/) para cadastrar uma aplicação e conseguir sua chave de acesso.

### Iniciando
Com a chave de acesso em mãos e a biblioteca já inserida no projeto, construa um objeto da classe `InthegraApi` indicando suas credenciais:
```Java
InthegraApi inthegraApi = new InthegraApi.Builder()
    .email("mail@example.com")
    .password("******")
    .xApiKey("5ea5265043209dd1191a8ea3")
    .build();
```
O email e senha são os do seu cadastro no [site da API](https://inthegra.strans.teresina.pi.gov.br/) e xApiKey é a chave da sua aplicação no site da API. Eles são necessários, pois também fazem parte do corpo da requisição na API Inthegra.

### Requisitando um token de acesso
Tokens (X-Auth-Token) são necessários para todas as requisições na API. A partir do objeto criado anteriormente,
```Java
inthegraApi.requestAccessToken();
```
retornará uma string com o token. Os tokens da API Inthegra têm validade de 10 minutos.

### Requisitando linhas de ônibus
```Java
List<Linha> linhas = inthegraApi
    .setXAuthToken(token)
    .requestLinhas("Zoobotânico");
```

### Requisitando paradas/estações de ônibus
O termo de busca para essa requisição pode envolver tanto a denominação da parada/estação quanto o endereço que ela se encontra.
```Java
List<Parada> paradas = inthegraApi
    .setXAuthToken(token)
    .requestParadas("Estação Justiça Federal");
```

### Requisitando linhas de ônibus com veículos (e suas coordenadas geográficas)
Nesse caso, um único objeto da classe Linha será retornado. Dessa vez, deve ser informado o código exato da linha.
```Java
Linha linha = inthegraApi
    .setXAuthToken(token)
    .requestPosicoesOnibus("T332");
```
 Dentro deste objeto, a haverá uma lista com os veículos online desta linha.
```Java
List<Veiculo> veiculos = linha.getVeiculos();
```

**OBS.:** Devido à limitações (ou algum problema pontual) da API Inthegra, requisições de linhas com veículos podem vir vazias (HTTP 404) mesmo com veículos online na linha. Se houverem veículos online dessa linha, na maioria dos casos, a requisição retornará uma linha com sucesso após no máximo 10 segundos.
