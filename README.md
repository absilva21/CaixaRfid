# CaixaRfid
Um sistema cliente-servidor de automação de caixa com etiquetas rfid, usando o leitor M6E Nano da SparkFun

![PBL_concorrencia_1](https://github.com/absilva21/CaixaRfid/assets/83670712/ee752fd1-50ab-41a3-9bc8-812582bb9fca)

 # A solução 
   O sensor é um servidor que ao receber a string "read" ler as tags e envia para quem solicitou.
 Quando uma solicitação é recebida através da porta 7710, o sensor abri uma nova thread para ler as tags e enviar os dados lidos.
 
  O caixa é um cliente que pode enviar comando para o leitor e em seguida pedir ao servidor os dados sobre os produtos
para que o usuário possa ver as informações, em seguida o usuário pode decidir confirmar ou não a venda, que será enviada 
para o servidor. Durante todo o processo de solicitações ao centro de dados (servidor) o caixa se comunica por requisição 
http, ele envia no cabeçalho uma chave que lhe autoriza fazer a operação, se o caixa estiver livre ele fara com sucesso
caso o contrário o servidor não permitirar a conclusão da operação.

  O servidor é o centro de dados ele se comunicar com os clientes pelo protocolo http, ele só permite executar transações
feitas por usuários cadastrados na base de dados. Ele atende feita a porta de 80 de um socket a uma conexão abrindo uma thread e executando a solicitação.

  O administrador é um cliente que pode bloquear caixas e ver as compras feitas neles. Ele se comunica com o centro de dados
para executar as ações.

  No caso dos clientes (caixa e administrador) é necessário fornecer a chave de acesso a API REST, essa chave é uma senha que 
deve ser única na base, essa senha é criptografada por algoritmo de hash md5.

  Uma observação a fazer é que o sensor tem um protocolo de comunicação específico onde ao receber uma String com a palavara
"read" ele ler as tags, e ao responder ele envia os códigos das entiquetas dividindo a mensagem por linhas, cada linha uma tag.

 Toda a comunicação feita ao centro de dados em que se exige corpo na menssagem (PUT, POST), deve ser feita com envio por JSON.


# IDE
eclipse 2021-09
# bibliotecas 
json-simple-1.1.1

sqlite-jdbc-3.6.14.1
# vesão do java
JDK 17.0.7
# Data base 
SQLITE3

