# Spring üzleti alkalmazások fejlesztése - kötelező program

SZTE Programterzező informatika - 5. félév

### Projekt futtatása lokális környezetben
<br>
<br>

#### 1. Futtatás DEV profile-al, H2 embedded database-el

- `src/main/resources/application-dev.properties` : Írd át a `jwt.secret`-et, tetszőleges secret string-re
- Tetszőlegesen megváltoztathatod a default admin és user adatait (`username, email, password`)
- Futtatás, elérés : `localhost/8080/login`
#### 2. Futtatás PROD profile-al, MYSQL database-el

- Hozz létre egy új MySQL sémát az adatbázisban `spring_blog` néven.
- Ha nem a default, (3306 -os) porton fut a MySql, írd át a datasource url-t:<br>
`jdbc:mysql://localhost:{PORT_NUMBER}/spring_blog`


 - `src/main/resources/application-prod.properties` :<br>
Add meg az adatbázis kapcsolódáshoz szükséges adatokat:<br>
`spring.datasource.username`:`"your_username"`<br>
`spring.datasource.password`:`"your_password"`
 - Futtatás, localhost elérés : `localhost/8080/login`

 - Írd át a `jwt.secret`-et, tetszőleges secret string-re

### Bejelentkezés a default userekkel

Mindkét profile-ban az alapértelmezett user adatok:

- admin: `username: admin` , `password: asd`
- user: `username: user` , `password: asd`