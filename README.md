<h1 align="center">Crypto-Monitor 📈</h1>

<p align="center">
  <b>Um app Android para monitoramento de Bitcoin em tempo real</b>  
</p>

<p align="center">
  <img alt="Kotlin" src="https://img.shields.io/badge/Kotlin-7F52FF?style=for-the-badge&logo=kotlin&logoColor=white">
  <img alt="Android" src="https://img.shields.io/badge/Android-3DDC84?style=for-the-badge&logo=android&logoColor=white">
  <img alt="Retrofit" src="https://img.shields.io/badge/Retrofit-FF9800?style=for-the-badge&logo=android&logoColor=white">
  <img alt="API" src="https://img.shields.io/badge/API-MercadoBitcoin-FF5722?style=for-the-badge">
</p>

---

## 📱 Sobre o projeto
Crypto-Monitor é um aplicativo Android desenvolvido em Kotlin que exibe a cotação atual do Bitcoin (BTC) em tempo real ao clicar no botão **"ATUALIZAR"**.

### 🎯 Funcionalidades do APP
- ✅ Exibe o valor do Bitcoin em R$ (formato: `R$ XXX.XXX,XX`)
- ✅ Botão de atualização manual para buscar a cotação mais recente
- ✅ Exibe a data e hora da última atualização

---

## 📄 Estados do APP
O aplicativo possui dois estados principais na tela:

- **Tela Inicial** → Valor padrão `R$ 0,00` (antes da primeira atualização)  
  ![Tela Inicial](https://github.com/user-attachments/assets/288f9ee8-1b58-440c-88be-7d7c74e4bd5f)

- **Tela Atualizada** → Valor real do Bitcoin (após clicar no botão "ATUALIZAR")  
  ![Tela Atualizada](https://github.com/user-attachments/assets/14430972-d8e0-4518-abde-35c179ff6278)

---

## 🔗 API utilizada para buscar o valor
Para exibir os valores atualizados da moeda, foi utilizada a API:

> https://www.mercadobitcoin.net/api/BTC/ticker/

---

## 🌐 Resumo breve sobre API REST no projeto

### Elementos de uma conexão REST:
1. **Protocolo** → Utiliza HTTP/HTTPS como base para comunicação.
2. **Ação (Verbo HTTP)** → No projeto, foi usado o método:
   - **GET** (consultar informações).
3. **URL (Endpoint)** → Endereço do recurso:  
   `https://www.mercadobitcoin.net/api/BTC/ticker`
4. **Formato de Dados** → JSON (utilizado via Gson Converter).
5. **Comunicação Bidirecional** → Requisição envia e recebe dados da API.

> A troca de dados é feita em **JSON** por ser leve, fácil de interpretar para humanos e para máquinas.

---

## ⚙️ Funcionamento prático no projeto

Ao clicar em **"ATUALIZAR"**, o aplicativo:

1. Faz uma requisição **GET** para a API de criptomoedas.
2. Recebe a resposta em **JSON**, por exemplo:
  
(Na classe `TickerResponse`)
```kotlin
// Representa a resposta principal da API
class TickerResponse(
    val ticker: Ticker
)

// Representa os dados específicos do Bitcoin retornados pela API
class Ticker(
    val high: String,
    val low: String,
    val vol: String,
    val last: String,
    val buy: String,
    val sell: String,
    val date: Long
)
```
3. Exibe o valor formatado na tela, por exemplo:
R$ 541.763,00

## 🌎 Serviço de Comunicação com a API

O serviço `MercadoBitcoinService` é responsável por definir a requisição que obtém a cotação atual do Bitcoin.

```kotlin
import retrofit2.Response
import retrofit2.http.GET
import LiviaGa.com.github.cryptomonitor.model.TickerResponse

interface MercadoBitcoinService {

    // Fazendo o uso do endpoint em cima da URL base disponibilizada no arquivo MercadoBitcoinServiceFactory.kt
    @GET("api/BTC/ticker/")
    suspend fun getTicker(): Response<TickerResponse>
}
```
A classe `MercadoBitcoinServiceFactory` é responsável por criar e configurar o Retrofit para a API do Mercado Bitcoin, retornando uma instância da interface que possibilita realizar a requisição.

```kotlin
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class MercadoBitcoinServiceFactory {
    fun create(): MercadoBitcoinService {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://www.mercadobitcoin.net/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        return retrofit.create(MercadoBitcoinService::class.java)
    }
}
```

## 🛠️ Dependências utilizadas

As dependências para comunicação REST foram adicionadas no arquivo build.gradle.kts.
E são essas 3 dependencias: 
- implementation("com.squareup.retrofit2:retrofit:2.9.0")
- implementation("com.squareup.retrofit2:converter-gson:2.9.0")
- implementation("androidx.activity:activity-ktx:1.7.0")

- Retrofit → Uma dependencia que gera uma forma de você fazer comunicações entre API's (entre o seu celular e uma API)
- Gson Converter → Faz a conversão do JSON em objetos Kotlin.
- Activity-KTX → Extensões que simplificam o código no Android.


## 🔒 Permissão de acesso à internet
É necessário permitir acesso à internet no Android. 
No arquivo AndroidManifest.xml, adiciona-se:

- <( uses-permission android:name="android.permission.INTERNET"/ )>

(A categoria do launcher também é configurada com <(category android:name="android.intent.category.LAUNCHER" /)>.)

## 🎨 Layout e Recursos de Texto

### 📄 Arquivo `strings.xml`

O arquivo `strings.xml` localizado na pasta `res/values/` contém todos os textos utilizados no aplicativo.  
Essa prática facilita a manutenção e a tradução futura do app para outros idiomas.

```xml
<resources>
    <string name="app_name">crypto-monitor</string>
    <string name="app_title">Monitor de Crypto Moedas - BITCOIN</string>
    <string name="label_rate">Cotação - BITCOIN</string>
    <string name="label_value">R$ 0,00</string>
    <string name="label_date">dd/mm/yyyy hh:mm:ss</string>
    <string name="label_refresh">Atualizar</string>
</resources>
```

## 🖌️ Arquivo activity_main.xml
O arquivo `activity_main.xml`, localizado na pasta `res/layout/`, define a estrutura principal da tela do app.
Ele organiza o layout de forma vertical utilizando LinearLayout, e inclui dois componentes:

Uma barra de ferramenta (component_toolbar_main)
Uma área de exibição de informações da cotação (component_quote_information)

```xml
<?xml version="1.0" encoding="utf-8"?>
<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:orientation="vertical">

    <include
        android:id="@+id/component_toolbar"
        layout="@layout/component_toolbar_main"
        android:layout_width="match_parent"
        android:layout_height="75dp"
        android:layout_weight="0" />

    <include
        android:id="@+id/component_quote_information"
        layout="@layout/component_quote_information"
        android:layout_width="match_parent"
        android:layout_height="0dp"
        android:layout_weight="1" />

</LinearLayout>
```

## 🧩 Tecnologias utilizadas
- Android Studio (Kotlin);
- Retrofit 2.9.0 (citado anteriormente na linha 125, para requisições HTTP);
- Gson Converter (também citado na linha 126, para conversão JSON);
- Kotlin Coroutines (para chamadas assíncronas, onde ajudam a gerenciar tarefas de longa duração que podem bloquear a linha de execução principal e fazer com que o app pare de responder e atualizar a interface assim que o botão "Atualizar" for clicado);
- AppCompat e componentes da AndroidX (para compatibilidade);
- entre algumas outras como converter a moeda para brasileira, e data/hora local, além da API;
