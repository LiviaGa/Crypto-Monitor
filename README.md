Crypto-Monitor  

📱 Um aplicativo Android em Kotlin que exibe a cotação atual do Bitcoin (BTC) em tempo real quando o usuário clica no botão "ATUALIZAR". 
🎯 Algumas das funcionalidades do APP:
✅ Exibe o valor do Bitcoin em R$ (formato: `R$ XXX.XXX,XX`)  
✅ Botão de atualização manual para buscar a cotação mais recente  
✅ Data e hora da última atualização  

Existem dois estados do APP na tela:
   - Tela inicial (valor R$ 0,00) → Antes da primeira atualização
 ![image](https://github.com/user-attachments/assets/288f9ee8-1b58-440c-88be-7d7c74e4bd5f)
   - Tela atualizada (valor real) → Após clicar em "ATUALIZAR"  
  ![image](https://github.com/user-attachments/assets/14430972-d8e0-4518-abde-35c179ff6278)

Para resgatar os valores atualizados da moeda e exibir na tela, foi necessário utilizar uma API : https://www.mercadobitcoin.net/api/BTC/ticker/

Resumo breve do que é e como funciona uma API REST (nesse caso do projeto) :

Elementos de uma Conexão REST
1) Protocolo → Utiliza HTTP/HTTPS como base para a comunicação.
2) Ação (Verbo HTTP) → Define a operação a ser executada,existem algumas opções mas a que foi utilizada foi:
GET (consultar) (que se nao for declarado, vem por padrão também)
Quando damos um get, estamos pegando a informação que está em um banco de dados
3) URL (Endpoint) → O "endereço" do recurso, nesse projeto foi ex: https://www.mercadobitcoin.net/api/BTC/ticker).
4) Formato de Dados (JSON/XML) → Estrutura padronizada para enviar e receber informações (nesse caso JSON) .
5) Comunicação Bidirecional → A requisição envia dados (ex: parâmetros) e recebe uma resposta (ex: cotação atual).

Quem faz essa comunicação com a arquitetura REST?
JSON pois tais motivos:
-Formatação leve
-Facil de entender para humanos e facil de interpretar para as maquinas

AGORA colocando como exemplo prático do projeto funciona assim: 
Quando o usuário clica em "ATUALIZAR", o app:
1º Faz uma requisição GET para uma API de criptomoedas.
2º Recebe a resposta em JSON (ex: {"moeda": "BTC", "valor": 541763.00}).
3º Exibe o valor formatado na tela (R$ 541.763,00).


Para fazer essa comunicação REST, é necessário uma dependencia que diga para o android isso
Essa dependencia fica localizada:

Na pasta src
build.gradle.kts

implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")
    implementation("androidx.activity:activity-ktx:1.7.0")

retrofit: Uma dependencia que gera uma forma de você fazer comunicações entre API's (entre o seu celular e uma API)

QUAIS SÃO OUTRAS FORMAS DE SE FAZER COMUNICAÇÃO ENTRE API'S?

Na pasta android manifest :              
<category android:name="android.intent.category.LAUNCHER" />

Serve para dizer ao android que é necessário autorizar a minha aplicação para ela poder sair, por questões de segurança

