# 1. Imagem Base: Usamos o servidor Nginx, ideal para conteúdo estático.
FROM nginx:alpine

# 2. Copia Específica:
# Copia o conteúdo da pasta 'src/main/webapp' no seu projeto local
# para o diretório padrão de arquivos web do Nginx no contêiner.
COPY src/main/webapp /usr/share/nginx/html

# 3. Porta: Informa que o contêiner expõe a porta 80.
EXPOSE 80