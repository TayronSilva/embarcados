# Embarcados API 2.0

API da nova versão do sistema Embarcados.

## Objetivo
Esta é a versão 2.0 do projeto, substituindo o legado anterior.

## Stack
- Java
- Spring Boot
- MongoDB
- Spring Security
- JWT

## Execução Local
1. Copie o arquivo `src/main/resources/application-example.yaml` para `src/main/resources/application.yaml` e preencha as variáveis de banco de dados (`<usuario>`, `<senha>`, `<host>`, `<banco>`).
2. Adicione suas chaves RSA (`private.key` e `public.key`) na pasta `src/main/resources/keys/`.
3. Inicie a aplicação via Maven (`./mvnw spring-boot:run`) ou pela sua IDE.

## Segurança e Práticas Recomendadas
- **Segredos não são versionados**. O `application.yaml` (contendo credenciais) e os arquivos dentro de `src/main/resources/keys/` estão inseridos no `.gitignore`.
- Nunca commite chaves de produção ou senhas de banco de dados no GitHub.
