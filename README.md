# Simple Blog Application with Spring Boot

Welcome to the Simple Blog Application, a lightweight and user-friendly blogging platform built using the Spring Boot framework. This application empowers users to create, share, and engage with short articles in a secure environment.

## Key Features:

-   **User Authentication and Authorization:** Leverage the power of Spring Security to ensure secure user authentication and authorization, allowing only authorized users to create, edit, and delete their own posts.

-   **Write Article:** Users can share their short post with the commuity.
They have to assign at least 1 topic flair to your post, to enable topic-wise search.

-   **Article Reactions:** Enable users to express their opinions on articles by providing reaction options. Whether it's a like, dislike, or a thoughtful comment, users can engage with the content.

-   **Article Comments:** Users can write comments to posts to discuss topics any kind of topic  with each other.

-   **Searching articles:** Search between all posts by title, author's name, article text part, topic flair, and date of post.
- **Admin features:** Admins can update or delete any blog posts.

-   **Spring Data JPA Integration:** Harness the capabilities of Spring Data JPA to interact with your database seamlessly. Store and retrieve user information, articles, and reactions efficiently.
    
    ## Getting Started:

1.  **Clone the Repository:**
    `git clone https://github.com/csabarato/spring_blog.git` 
    <br>

1.1  **Run with embedded H2 database, using DEV profile** 
- Change the `jwt.secret` value in `application-dev.properties` to any string.
- You can change the default admin details here. ( username, email, password)

	 **Build and Run with Dev Profile:**
  
	 `cd spring_blog`

  `mvn spring-boot:run -Dspring-boot.run.profiles=dev`
- Reach the application on `localhost/8080/login`
- Login with the admin username, password provided in `application-dev.properties`
<br>

1.2 **Run with MySQL database, using PROD profile**
- Provide your local MySQL connection string in `application-prod.properties` ,for example:

	`spring.datasource.url=jdbc:mysql://localhost:3306/spring_blog`

 - Provide your database username and password here as well.
 - Change the `jwt.secret` to any string.
 
	 **Build and Run with Prod Profile:**
   
	 `cd spring_blog`

	 `mvn spring-boot:run -Dspring-boot.run.profiles=prod`

- Reach the application on `localhost/8080/login`

  **Login with the admin user**
  Amin username in PROD profile: `admin`
  Amin password in PROD profile: `asd`

Happy blogging!
