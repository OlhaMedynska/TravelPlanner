# 🌍 TravelPlanner

**TravelPlanner** to aplikacja backendowa w Javie (Spring Boot), wspierająca planowanie podróży.  
Pozwala użytkownikom tworzyć konta, zarządzać planami podróży, destynacjami, atrakcjami oraz opiniami.

---

## 🔧 Technologie

- Java 17+  
- Spring Boot 3  
- Spring Data JPA (Hibernate)  
- MySQL  
- Spring Security (JWT – wstępna implementacja)  

---

## 🚀 Uruchomienie projektu

1. Skonfiguruj bazę danych MySQL:

```
sql
CREATE DATABASE travelplanner;
```

2. Ustaw dane dostępowe w application.properties:
   
```
spring.datasource.url=jdbc:mysql://localhost:3306/travelplanner?useSSL=false&serverTimezone=UTC
spring.datasource.username=newroot
spring.datasource.password=coderslab
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
```

3. Uruchom aplikację:

```
mvn spring-boot:run
```

4. Aplikacja będzie działać pod adresem:

👉 http://localhost:8080

---

## 📌 Endpointy API

## 👤 Użytkownik + logowanie

```
# Rejestracja
curl -X POST http://localhost:8080/users \
  -H "Content-Type: application/json" \
  -d '{"username":"janek","email":"janek@example.com","password":"haslo123"}'

# Pobierz listę użytkowników
curl -X GET http://localhost:8080/users

# Pobierz użytkownika po ID
curl -X GET http://localhost:8080/users/1

# Logowanie (zwraca JWT)
curl -X POST http://localhost:8080/auth/login \
  -H "Content-Type: application/json" \
  -d '{"username":"janek","password":"haslo123"}'

# Usuń użytkownika
curl -X DELETE http://localhost:8080/users/1
```

## 📝 UserProfile

```
curl -X POST http://localhost:8080/profiles \
  -H "Content-Type: application/json" \
  -d '{"age":25,"bio":"Podróżnik i bloger","userId":1}'

curl -X GET http://localhost:8080/profiles
curl -X GET http://localhost:8080/profiles/1

curl -X PUT http://localhost:8080/profiles/1 \
  -H "Content-Type: application/json" \
  -d '{"age":26,"bio":"Zmiana w bio","userId":1}'
```

## 🌍 Destynacje & atrakcje

```
curl -X GET http://localhost:8080/destinations
curl -X GET http://localhost:8080/destinations/search?name=Santorini
curl -X GET http://localhost:8080/destinations/searchByCountry?country=Grecja
curl -X GET http://localhost:8080/destinations/1/attractions?page=0&size=5

curl -X POST http://localhost:8080/attractions \
  -H "Content-Type: application/json" \
  -d '{"name":"Wawel","description":"Zamek królewski","price":50,"categoryId":1,"destinationId":1}'
```

## 📅 Plany podróży

```
curl -X POST http://localhost:8080/plans \
  -H "Content-Type: application/json" \
  -d '{"name":"Weekend w Krakowie","startDate":"2025-09-15","endDate":"2025-09-17","comment":"Zwiedzanie i odpoczynek","userId":1,"attractionIds":[1,2]}'

curl -X GET http://localhost:8080/plans
curl -X GET http://localhost:8080/plans/1
curl -X GET http://localhost:8080/plans/user/1

curl -X PUT http://localhost:8080/plans/1 \
  -H "Content-Type: application/json" \
  -d '{"name":"Weekend w Krakowie - poprawka","startDate":"2025-09-15","endDate":"2025-09-18","comment":"Dodatkowy dzień","userId":1,"attractionIds":[1,2,3]}'

curl -X DELETE http://localhost:8080/plans/1
```

## ⭐ Opinie (Review)

```
curl -X POST http://localhost:8080/reviews \
  -H "Content-Type: application/json" \
  -d '{"rating":5,"comment":"Super atrakcja!","userId":1,"attractionId":1}'

curl -X GET http://localhost:8080/reviews
curl -X GET http://localhost:8080/reviews/1

curl -X PUT http://localhost:8080/reviews/1 \
  -H "Content-Type: application/json" \
  -d '{"rating":4,"comment":"Fajnie, ale tłoczno","userId":1,"attractionId":1}'

curl -X DELETE http://localhost:8080/reviews/1
```

## ❤️ Ulubione

```
curl -X POST http://localhost:8080/favorites \
  -H "Content-Type: application/json" \
  -d '{"userId":1,"attractionId":1}'

curl -X GET http://localhost:8080/favorites/3
curl -X DELETE http://localhost:8080/favorites/3
```

## 🏷️ Kategorie

```
curl -X POST http://localhost:8080/categories \
  -H "Content-Type: application/json" \
  -d '{"name":"Muzea"}'

curl -X GET http://localhost:8080/categories
curl -X GET http://localhost:8080/categories/1

curl -X PUT http://localhost:8080/categories/1 \
  -H "Content-Type: application/json" \
  -d '{"name":"Atrakcje rodzinne"}'

curl -X DELETE http://localhost:8080/categories/1
```

#

## 🗄️ Struktura bazy danych

Poniżej przedstawiono diagram bazy danych wygenerowany w IntelliJ IDEA:
<img width="533" height="749" alt="image" src="https://github.com/user-attachments/assets/b78b50a4-77d7-451f-9dea-3311742a7cf6" />
