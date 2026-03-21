# Embarcados

Monorepo contendo frontend e backend da aplicação Embarcados.

## 📁 Estrutura

Este é um monorepo gerenciado com **Bun Workspaces** que contém:

- **`frontend/`** - Aplicação web em React
- **`backend/`** - API REST em Spring Boot

## 🚀 Stack

### Backend
- **Java 21**
- **Spring Boot 3.5.11**
- **MongoDB** - Banco de dados
- **Spring Security** + OAuth2 Resource Server - Autenticação e autorização
- **SpringDoc OpenAPI** - Documentação da API (Swagger UI)
- **Lombok** - Redução de boilerplate
- **Spring Boot DevTools** - Hot reload em desenvolvimento

### Frontend
- **React 19.2.4**
- **TypeScript 5.9.3**
- **Vite 8.0.1** - Build tool e dev server
- **ESLint** - Linting

## 📋 Pré-requisitos

- [Bun](https://bun.sh/) - Package manager
- [Java 21+](https://adoptium.net/) - Para o backend
- [MongoDB](https://www.mongodb.com/try/download/community) - Banco de dados (ou use MongoDB Atlas)

## 🔧 Configuração

### Variáveis de Ambiente (Backend)

Copie o arquivo de exemplo e configure suas credenciais:

```bash
cp backend/src/main/resources/application-example.yaml backend/src/main/resources/application.yaml
```

Edite o `application.yaml` com suas configurações:

```yaml
spring:
  data:
    mongodb:
      uri: mongodb+srv://<usuario>:<senha>@<host>/<banco>?retryWrites=true&w=majority

security:
  jwt:
    public-key: classpath:keys/public.key
    private-key: classpath:keys/private.key
```

**Variáveis obrigatórias:**
- `mongodb.uri`: String de conexão do MongoDB
- `security.jwt.public-key`: Caminho para chave pública JWT
- `security.jwt.private-key`: Caminho para chave privada JWT

## 🏃 Como Rodar

### Desenvolvimento

Para rodar os serviços, abra terminais separados em cada pasta:

```bash
# Terminal 1 - Backend (na pasta backend/)
cd backend
bun dev

# Terminal 2 - Frontend (na pasta frontend/)
cd frontend
bun dev
```

Ou use os scripts da raiz (abre terminais separados):

```bash
# Backend
bun dev:back

# Frontend
bun dev:front
```

### Build

```bash
# Frontend
cd frontend && bun run build

# Backend
cd backend && mvn clean package
```

## 📚 Documentação da API

Após rodar o backend, acesse:
- **Swagger UI**: http://localhost:8080/swagger-ui.html

## 📄 Scripts Disponíveis

- `bun dev:front` - Roda o frontend (Vite dev server)
- `bun dev:back` - Roda o backend (Spring Boot)

**Nas pastas individuais:**
- `bun dev` - Roda o serviço correspondente (frontend ou backend)
