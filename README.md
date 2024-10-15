
---

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


## Setup
1. **Clone the Repository**
   ```bash
   git clone https://github.com/your-username/oauth-spring-boot-app.git
   cd oauth-spring-boot-app
   ```

2. **Install Dependencies**
   Make sure you have Maven installed. Run the following command to install the dependencies:
   ```bash
   mvn clean install
   ```

3. **Set Up Database**
   Create a MySQL database named `oauth_impl` and configure the `application.properties` file with the appropriate database credentials.

4. **Run the Application**
   You can run the application using:
   ```bash
   mvn spring-boot:run
   ```

## Endpoints
The following endpoints are available in the application:

- **POST /auth/login**: Authenticate user and return JWT token.
- **GET /api/users**: Retrieve a list of users (secured endpoint).
- **GET /api/user/{id}**: Retrieve a specific user by ID (secured endpoint).

## JWT Generation
The application generates JWT tokens upon successful authentication. These tokens must be included in the Authorization header as a Bearer token for accessing secured endpoints.

## Security Configuration
The application uses Spring Security to manage authentication and authorization. Make sure to configure your security settings according to your application's requirements.

## License
This project is licensed under the MIT License. See the [LICENSE](LICENSE) file for more information.

---

Feel free to replace `your-username` with your actual GitHub username and modify any section as needed!

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
