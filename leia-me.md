Projeto movido para estrutura Maven.

Este projeto foi reorganizado para seguir a convenção de projetos web Java usando a estrutura Maven (conteúdo web em src/main/webapp). Agora é possível manipular e executar o projeto usando o Maven Wrapper incluído no repositório. No Windows (PowerShell), use a sintaxe:

  .\mvnw <comando>

Sugestões de comandos iniciais para os outros desenvolvedores:

  .\mvnw -v                # verificar versão do Maven e ambiente Java
  .\mvnw jetty:run        # executar um servidor de desenvolvimento e abrir a aplicação web
  .\mvnw clean package    # compilar e empacotar o projeto (gera o WAR em target/)

Esses comandos permitem que qualquer colaborador rode o projeto localmente sem precisar instalar o Maven globalmente (o Wrapper cuidará da versão adequada).

— Equipe de desenvolvimento
