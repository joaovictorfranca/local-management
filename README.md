# local-management

<h3>Instruções de como executar a aplicação e os testes</h3>

<p>1- Versão do java foi a 21 LTS. Após subir os arquivos atualize o mavem para subir as dependencias. </p>

<p>2- Crie um banco de dados MySQL chamado local_management com o comando $CREATE DATABASE local_management$ logo após execute a seguinte query no banco:  </p>

<p> DROP TABLE IF EXISTS `locals`;
    CREATE TABLE IF NOT EXISTS `locals` (
     `id` bigint NOT NULL AUTO_INCREMENT,
     `nome` varchar(255) DEFAULT NULL,
     `bairro` varchar(255) DEFAULT NULL,
     `cidade` varchar(255) DEFAULT NULL,
     `estado` varchar(255) DEFAULT NULL,
     `dt_reg` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
     `dt_mdf` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
     PRIMARY KEY (`id`)
    ) ENGINE=MyISAM AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb3;</p>

<p>3- Agora pode subir a aplicação :)</p>

<p>4- Após a aplicação subir ja podemos fazer as requisições REST da nossa API. </p>

<p>5- Utilizaremos o Postman para realizá-las! Na pasta raíz do projeto tem um arquivo chamado "LocalManagement.postman_collection.json" ´que é um JSON referente a uma collection que contém as configurações para as chamadas dos endpoints dessa API</p>

<p>6- É possivel importar essas configurações basta clicar ao lado de "My Workspace" no botão "Import" que fica no canto superior esquerdo.</p>

<p>7- Após o import, conseguirá ver que dentro de LocalManagement terá as cinco requisições. </p>

<p>8- Para o listar todos os locais e o cadastrar local não precisa especificar com o código do local no end Point, porém, os outros métodos Buscar Por Id, Alterar Local e Deletar Local o código do local precisa ser especificado! Basta colocar o código do local que é mostrado após o cadastro no final do end point.</p>

<p>9- Para executar os testes unitários basta dar run em cada classe de teste</p>