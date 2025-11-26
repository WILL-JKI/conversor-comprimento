# Instruções para Publicar a Imagem Docker no GitHub Container Registry

## Arquivos Criados

1. **Dockerfile** - Define a imagem Docker com build multi-stage
2. **.dockerignore** - Otimiza o build excluindo arquivos desnecessários
3. **.github/workflows/docker-publish.yml** - Automatiza o build e publicação no GHCR

## Como Publicar a Imagem

### Opção 1: Automática via GitHub Actions (Recomendado)

1. Faça commit e push dos novos arquivos:
   ```bash
   git add Dockerfile .dockerignore .github/
   git commit -m "feat: adiciona configuração Docker e workflow GHCR"
   git push origin main
   ```

2. O GitHub Actions será executado automaticamente e publicará a imagem em:
   `ghcr.io/[seu-usuario]/[nome-do-repo]:latest`

3. Após o workflow terminar, acesse:
   - Vá para a página do seu repositório no GitHub
   - Clique em "Packages" no menu lateral direito
   - Clique no pacote criado
   - Clique em "Package settings"
   - Role até "Danger Zone" e mude a visibilidade para **Public**

### Opção 2: Manual via Docker CLI

Se preferir fazer manualmente:

```bash
# 1. Build da imagem
docker build -t ghcr.io/[seu-usuario]/conversor-comprimento:latest .

# 2. Login no GHCR (use um Personal Access Token com permissão packages:write)
echo [SEU_TOKEN] | docker login ghcr.io -u [SEU_USUARIO] --password-stdin

# 3. Push da imagem
docker push ghcr.io/[seu-usuario]/conversor-comprimento:latest
```

## Testar a Imagem Localmente

Antes de publicar, você pode testar localmente:

```bash
# Build
docker build -t conversor-comprimento:test .

# Run
docker run -p 8080:8080 conversor-comprimento:test

# Acesse: http://localhost:8080
```

## Usar a Imagem Publicada

Depois de publicada e com visibilidade pública, qualquer pessoa pode usar:

```bash
docker pull ghcr.io/[seu-usuario]/conversor-comprimento:latest
docker run -p 8080:8080 ghcr.io/[seu-usuario]/conversor-comprimento:latest
```

## Link para Entregar ao Professor

Após publicar, o link será:
```
ghcr.io/[seu-usuario]/conversor-comprimento:latest
```

Ou o link da página do pacote:
```
https://github.com/[seu-usuario]/[nome-do-repo]/pkgs/container/conversor-comprimento
```

## Observações

- A imagem usa Jetty 11 com JRE 11 Alpine (leve e eficiente)
- A aplicação roda na porta 8080
- O contexto é a raiz (/) conforme configurado no projeto
- Build multi-stage reduz o tamanho final da imagem
