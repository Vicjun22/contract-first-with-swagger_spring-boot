# Contract-First Project with Swagger and Spring Boot

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
  /example:
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

## Useful Links

- [Spring Initializr](https://start.spring.io/) - Quickly bootstrap a Spring Boot project.
- [OpenAPI Specification](https://swagger.io/specification/) - Learn more about the OpenAPI standard.
- [Springdoc OpenAPI](https://springdoc.org/) - Integrate OpenAPI with Spring Boot.
- [Swagger UI](https://swagger.io/tools/swagger-ui/) - Visualize and interact with your API documentation.
- [OpenAPI Generator](https://openapi-generator.tech/) - Generate code from OpenAPI specifications.

---

Feel free to contribute by submitting pull requests or reporting issues.  
We welcome contributions! 🚀

Att,  
Victor