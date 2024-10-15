Here's a structured README file for your GitHub repository that outlines your Spring Boot application with OAuth2 and JWT authentication. You can modify any sections as needed.

```markdown
# OAuth2 Spring Boot Application

This is a Spring Boot application that implements OAuth2 authentication with JWT (JSON Web Tokens) using RSA keys. The application supports user sign-in, role-based access control, and token generation for authorized API access.

## Table of Contents
- [Features](#features)
- [Technologies](#technologies)
- [Configuration](#configuration)
- [Setup](#setup)
- [Endpoints](#endpoints)
- [JWT Generation](#jwt-generation)
- [Security Configuration](#security-configuration)
- [License](#license)

## Features
- User authentication via email and password.
- Role-based access control with predefined permissions.
- JWT for stateless authentication.
- Error handling for authentication and access denial.

## Technologies
- **Java**: 17 or higher
- **Spring Boot**: 2.7 or higher
- **Spring Security**: For authentication and authorization
- **JWT**: For token-based authentication
- **MySQL**: As the database for user data
- **Lombok**: For reducing boilerplate code

## Configuration
The application can be configured via the `application.properties` file located in `src/main/resources`. Below are the essential configurations:

```properties
spring.application.name=oauth
server.port=8080

spring.datasource.url=jdbc:mysql://localhost:3306/oauth_impl
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
spring.datasource.username=root
spring.datasource.password=root@123

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true

logging.level.org.springframework.security=trace

jwt.rsa-private-key=classpath:certs/privateKey.pem
jwt.rsa-public-key=classpath:certs/publicKey.pem
```

### RSA Keys
Place your RSA public and private keys in the `src/main/resources/certs` directory.

### Example Private Key
```plaintext
-----BEGIN PRIVATE KEY-----
MIIEvgIBADANBgkqhkiG9w0BAQEFAASCBKgwggSkAgEAAoIBAQCrSQjgG7+1FAnX
...
-----END PRIVATE KEY-----
```

## Setup
1. Clone the repository:
   ```bash
   git clone https://github.com/yourusername/oauth-spring-boot.git
   ```
2. Navigate to the project directory:
   ```bash
   cd oauth-spring-boot
   ```
3. Install dependencies using Maven:
   ```bash
   mvn clean install
   ```
4. Start the application:
   ```bash
   mvn spring-boot:run
   ```

## Endpoints
### Authentication
- **POST** `/sign-in`: Authenticate a user and generate a JWT token.
  - **Request Body**: JSON with `email` and `password`.
  
### Protected Resources
- **GET** `/api/welcome-message`: Accessible only to users with `SCOPE_READ`.
- **GET** `/api/manager-message`: Accessible only to users with `SCOPE_READ` (role: manager).
- **POST** `/api/admin-message`: Accessible only to users with `SCOPE_WRITE` (role: admin).

## JWT Generation
The `JwtTokenGenerator` service generates JWT tokens with the following claims:
- `iss`: Issuer
- `iat`: Issued at
- `exp`: Expiration time (15 minutes)
- `sub`: Subject (username/email)
- `scope`: Permissions derived from user roles

## Security Configuration
The application uses Spring Security for securing endpoints. The configuration includes:
- Stateless session management.
- Role-based access control.
- Custom error handling for authentication and access denial.

### Example Security Configuration
```java
@Bean
public SecurityFilterChain apiSecurityFilterChain(HttpSecurity httpSecurity) throws Exception {
    return httpSecurity
        .securityMatcher(new AntPathRequestMatcher("/api/**"))
        .csrf(AbstractHttpConfigurer::disable)
        .authorizeHttpRequests(auth -> auth.anyRequest().authenticated())
        .oauth2ResourceServer(oauth2 -> oauth2.jwt(Customizer.withDefaults()))
        .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
        .exceptionHandling(ex -> {
            log.error("[SecurityConfig:apiSecurityFilterChain] Exception due to :{}", ex);
            ex.authenticationEntryPoint(new BearerTokenAuthenticationEntryPoint());
            ex.accessDeniedHandler(new BearerTokenAccessDeniedHandler());
        })
        .httpBasic(Customizer.withDefaults())
        .build();
}
```

## License
This project is licensed under the MIT License. See the LICENSE file for details.

```

### How to Use This README
1. **Modify the placeholders** such as the repository link and private key example with your actual data.
2. **Add any additional sections** that are relevant to your project.
3. **Format the code snippets** to ensure they display correctly on GitHub.

Feel free to let me know if you need further modifications or additional content!
