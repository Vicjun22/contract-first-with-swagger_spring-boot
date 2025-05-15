# Contract-First Project with Swagger and Spring Boot

![Build](https://img.shields.io/badge/build-passing-brightgreen)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.4.4-blue)
![OpenAPI](https://img.shields.io/badge/OpenAPI-3.0.3-lightblue)

This repository aims to demonstrate how to integrate Swagger with a Spring Boot project and generate API documentation **based on the OpenAPI specification**.

## Steps to Set Up the Project

### 1. Create a Spring Boot Project

First, create a new Spring Boot project using your preferred method (e.g., [Spring Initializr](https://start.spring.io/) or an IDE like IntelliJ IDEA or Eclipse). Make sure your project is set up with Maven.

### 2. Add the Swagger Dependency

In the `pom.xml` file, add the following dependency to integrate Swagger and generate an automatic documentation interface with [Swagger UI](https://swagger.io/tools/swagger-ui/).

```xml
<dependency>
    <groupId>org.springdoc</groupId>
    <artifactId>springdoc-openapi-starter-webmvc-ui</artifactId>
    <version>2.5.0</version>
</dependency>
```

This dependency generates the Swagger UI interface, enabling you to view and interact with the API directly from your browser.

### 3. Add the OpenAPI Generator Plugin

Add the following plugin to the `pom.xml` file to generate the OpenAPI documentation from the `openapi.yml` specification.
This plugin facilitates the integration of [OpenAPI](https://swagger.io/specification/) with Spring Boot and automates the generation of API documentation. Learn more about the [OpenAPI Generator](https://openapi-generator.tech/).

```xml
<plugin>
    <groupId>org.openapitools</groupId>
    <artifactId>openapi-generator-maven-plugin</artifactId>
    <version>7.6.0</version>
    <executions>
        <execution>
            <goals>
                <goal>generate</goal>
            </goals>
            <configuration>
                <skipValidateSpec>true</skipValidateSpec>
                <inputSpec>${project.basedir}/src/main/resources/swagger/openapi.yml</inputSpec>
                <generatorName>spring</generatorName>
                <configOptions>
                    <openApiNullable>false</openApiNullable>
                    <interfaceOnly>true</interfaceOnly>
                    <useJakartaEe>true</useJakartaEe>
                </configOptions>
            </configuration>
        </execution>
    </executions>
</plugin>
```

### 4. Generate OpenAPI Code

Once the plugin is added, Maven will generate the necessary source files from the `openapi.yml` specification.
If you encounter any errors, navigate to the `target/generated-sources/openapi/src` directory and mark this folder as the Generated Source Root in your IDE (e.g., IntelliJ IDEA).

### 5. Create the `openapi.yml` File

Next, create the `openapi.yml` file inside the `src/main/resources/swagger/` directory. Here's an example of what your `openapi.yml` might look like:

```yaml
openapi: 3.0.3
info:
  title: "Project API"
  version: "1.0.0"
  description: "A Contract-First project using Swagger and Spring Boot to manage user registration."
servers:
  - url: "http://localhost:8080"
    description: "Local development server."
paths:
  /public/example:
    get:
      tags:
        - "Example"
      summary: "Return an example response"
      description: "This endpoint returns a simple OK response with a message for demonstration purposes."
      operationId: "getExampleResponse"
      responses:
        "200":
          description: "OK - The request was successfully processed and returns an example response."
          content:
            application/json:
              schema:
                $ref: "#/components/schemas/Response"
components:
  schemas:
    Response:
      type: object
      properties:
        code:
          type: string
          description: "HTTP status code returned by the server."
          example: "200"
        message:
          type: string
          description: "A brief message describing the outcome of the request."
          example: "Request was successful!"
```

### 6. Create the Controller

Create a Spring Boot controller that uses the OpenAPI specification to generate the documentation.

### 7. Accessing the Swagger UI Documentation

After starting your Spring Boot application, you can access the generated [Swagger UI](https://swagger.io/tools/swagger-ui/) documentation at:

```html
http://localhost:8080/swagger-ui.html
```

The Swagger UI will display, allowing you to view and interact with the available API endpoints directly from the browser.

---

## Securing Endpoints with JWT

### 1. Create a Spring Boot Project

Add Security Scheme to `openapi.yml`.

```yml
  securitySchemes:
    bearerAuth:
      type: http
      scheme: bearer
      bearerFormat: JWT
```

### 2. Secure an Endpoint Example

```yml
  /security/example:
    get:
      tags:
        - "Example"
      summary: "Return a secured response"
      description: "This endpoint requires JWT authentication and returns a secure example response."
      operationId: "getSecuredData"
      security:
        - bearerAuth: []
      responses:
        "200":
          description: "OK - Authenticated request was successful and returns a secure response."
          content:
            application/json:
              schema:
                $ref: "#/components/schemas/Response"
              example:
                code: "200"
                message: "Authenticated request was successful!"
        "401":
          description: "Unauthorized - Authentication credentials were missing or invalid."
        "403":
          description: "Forbidden - You do not have access to this resource."
        "default":
          description: "An unexpected error occurred."
```

### 3. Add Spring Security Dependency

In your `pom.xml`:

```xml
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-security</artifactId>
        <version>3.4.5</version>
    </dependency>

    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-oauth2-resource-server</artifactId>
        <version>3.4.5</version>
    </dependency>
```

### 4. Create the `SecurityConfig`

```java
@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

    private static final String[] ALLOW_LIST = {
            "/swagger-ui/",
            "/swagger-ui.html",
            "/v3/api-docs/",
            "/api/v1/auth/",
            "/v3/api-docs/",
            "/v3/api-docs.yaml",
            "/swagger-ui/**",
            "/v3/api-docs/**",
            "/swagger-resources/**",
            "/swagger-ui.html",
    };

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http, CorsConfigurationSource corsConfigurationSource) throws Exception {
        return http
                .cors(cors -> cors.configurationSource(request -> {
                    CorsConfiguration configuration = new CorsConfiguration();
                    configuration.setAllowedOrigins(List.of("*"));
                    configuration.setAllowedMethods(List.of("*"));
                    configuration.setAllowedHeaders(List.of("*"));
                    return configuration;
                }))
                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .cors(cors -> cors.configurationSource(corsConfigurationSource))
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/public/**").permitAll()
                        .requestMatchers(ALLOW_LIST).permitAll()
                        .anyRequest().authenticated()
                )
                .httpBasic(Customizer.withDefaults())
                .build();
    }
}
```
Define all public endpoints (e.g., `/public/**`) explicitly using `.permitAll()` to avoid `401 Unauthorized` responses.

### 5. Configure Swagger Security

```java
@Configuration
public class SwaggerConfig {

    private static final String SCHEMA_NAME = "bearerAuth";
    private static final String SCHEMA = "bearer";
    private static final String BEARER_FORMAT = "JWT";

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .components(new Components()
                        .addSecuritySchemes(SCHEMA_NAME,
                                new SecurityScheme()
                                        .name(SCHEMA_NAME)
                                        .type(SecurityScheme.Type.HTTP)
                                        .scheme(SCHEMA)
                                        .bearerFormat(BEARER_FORMAT)
                        )
                );
    }
}
```
<br>

---

## Useful Links

- [Spring Initializr](https://start.spring.io/) - Quickly bootstrap a Spring Boot project.
- [OpenAPI Specification](https://swagger.io/specification/) - Learn more about the OpenAPI standard.
- [Springdoc OpenAPI](https://springdoc.org/) - Integrate OpenAPI with Spring Boot.
- [Swagger UI](https://swagger.io/tools/swagger-ui/) - Visualize and interact with your API documentation.
- [OpenAPI Generator](https://openapi-generator.tech/) - Generate code from OpenAPI specifications.

---

Feel free to contribute by submitting pull requests or reporting issues.  
We welcome contributions! 🚀

Cheers,  
Victor