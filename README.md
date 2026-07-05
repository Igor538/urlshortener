# 🔗 URL Shortener - Encurtador de Links

Projeto de encurtador de URLs desenvolvido com **Spring Boot + PostgreSQL**, com interface simples e API REST. O sistema permite encurtar links, redirecionar automaticamente e acompanhar estatísticas de cliques.

---

## 🚀 Deploy

O projeto está rodando em produção no Render:

👉 https://urlshortener-7g5s.onrender.com/

---

## ⚙️ Tecnologias utilizadas

- Java 17+
- Spring Boot
- Spring Web
- Spring Data JPA
- PostgreSQL
- Thymeleaf (interface web)
- Maven

---

## 📌 Funcionalidades

- 🔗 Encurtar URLs longas  
- 🔁 Redirecionamento automático  
- 📊 Contador de cliques  
- 📈 Ranking dos links mais acessados  
- 🌐 API REST + interface web simples  

---

## 🧪 Endpoints da API

### Criar link (JSON)

```http
POST /shorten
```

**Exemplo de body:**

```json
{
  "url": "https://google.com"
}
```

---

### 🔁 Redirecionar

```http
GET /{code}
```

**Exemplo:**

```
https://urlshortener-7g5s.onrender.com/abc123
```

---

### 📊 Estatísticas

```http
GET /stats/{code}
```

---

### 📃 Listar links

```http
GET /links
```

---

### 🏆 Ranking Top 5

```http
GET /ranking
```

---

## 🗄️ Banco de Dados

O projeto usa PostgreSQL hospedado no Render.  
As configurações são gerenciadas por variáveis de ambiente:

```
DB_URL
DB_USER
DB_PASS
```

---

## 📦 Como rodar localmente

```bash
git clone https://github.com/SEU-USUARIO/urlshortener.git
cd urlshortener
```

Configure o seu `application.properties`:

```properties
spring.datasource.url=${DB_URL}
spring.datasource.username=${DB_USER}
spring.datasource.password=${DB_PASS}
```

Execute:

```bash
./mvnw spring-boot:run
```
