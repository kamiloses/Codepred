# Task Management API

## Opis
Prosta aplikacja backendowa w **Spring Boot** do zarządzania zadaniami TODO. API umożliwia **tworzenie, pobieranie, edytowanie i usuwanie zadań**. Każde zadanie ma tytuł, opcjonalny opis, status (`NEW`, `IN_PROGRESS`, `DONE`) oraz datę utworzenia.

---

## Funkcjonalności
- **Tworzenie zadania** (`POST /api/tasks`) – dodaje nowe zadanie.  
- **Pobieranie wszystkich zadań** (`GET /api/tasks`) – zwraca listę wszystkich zadań.  
- **Pobieranie zadania po ID** (`GET /api/tasks/{id}`) – zwraca pojedyncze zadanie.  
- **Aktualizacja zadania** (`PUT /api/tasks/{id}`) – zmienia tytuł, opis lub status zadania.  
- **Usuwanie zadania** (`DELETE /api/tasks/{id}`) – usuwa zadanie po jego ID.



---

## Technologie
- Java 21  
- Spring Boot 4 
- Spring Data JPA  
- H2 Database  
- Maven  
- Springdoc OpenAPI (Swagger UI)

---

## Uruchomienie aplikacji

### 1. Sklonuj repozytorium

```bash
git clone https://github.com/kamiloses/Codepred.git
cd Codepred
```

### 2. Uruchom testy (opcjonalnie)

```bash
mvn test
```

### 3. Uruchom aplikację

```bash
mvn spring-boot:run
```

Aplikacja uruchomi się domyślnie na:

```
http://localhost:8080
```

### Dokumentacja API (Swagger)

Po uruchomieniu aplikacji dokumentacja API jest dostępna pod adresem:

```
http://localhost:8080/swagger-ui/index.html
```



## Postman Collection

Przykładowe requesty do API znajdują się w pliku **postman_collection.json**.

