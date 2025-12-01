# Conversor de Unidades de Comprimento

## Funcionalidades
- ✨ Conversão entre diferentes unidades de comprimento (metros, centímetros, polegadas, pés)
- 🎨 Interface web intuitiva e responsiva
- ⚡ Processamento rápido e preciso das conversões
- 📁 **NOVO**: Upload de arquivo para conversão em lote
- 🔧 **NOVO**: Backend Java com 3 bibliotecas integradas

---

## Bibliotecas Utilizadas

### 1. Apache Commons FileUpload
Processa upload de arquivos no servidor, permitindo conversões em lote.

### 2. Apache Commons Lang3
Valida e manipula strings, garantindo dados consistentes antes das conversões.

### 3. Indriya (JSR-385)
Biblioteca robusta para conversão de unidades de medida, seguindo padrões Java.

---

## Conversão em Lote

Você pode enviar um arquivo `.txt` ou `.csv` com múltiplas conversões:

```
100,metros,kilometros
50,pes,centimetros
1000,milimetros,metros
```

O sistema processará todas as linhas e retornará os resultados. Veja o arquivo `exemplo-conversao.txt` para referência.

---

## Configuração do Projeto

Projeto movido para estrutura Maven.

Este projeto foi reorganizado para seguir a convenção de projetos web Java usando a estrutura Maven (conteúdo web em src/main/webapp). Agora é possível manipular e executar o projeto usando o Maven Wrapper incluído no repositório. No Windows (PowerShell), use a sintaxe:

  .\mvnw <comando>

Sugestões de comandos iniciais para os outros desenvolvedores:

  .\mvnw -v                # verificar versão do Maven e ambiente Java
  .\mvnw jetty:run        # executar um servidor de desenvolvimento e abrir a aplicação web
  .\mvnw clean package    # compilar e empacotar o projeto (gera o WAR em target/)

Esses comandos permitem que qualquer colaborador rode o projeto localmente sem precisar instalar o Maven globalmente (o Wrapper cuidará da versão adequada).

## Como Contribuir

1. Faça um fork do projeto
2. Crie uma branch para sua feature: `git checkout -b minha-feature`
3. Faça commit das suas alterações: `git commit -m "tipo: mensagem descritiva"`
4. Faça push para a branch: `git push origin minha-feature`
5. Abra um Pull Request

## Equipe de Desenvolvimento

- WILLAMIS
- SAMUEL
- JOÃO CARLOS
