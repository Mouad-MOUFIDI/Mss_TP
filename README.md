# Mss_TP
Mise en oeuvre de micro-services avec Spring Cloud
# What Does Microservices Mean?

A microservice is a modular software component that does one defined job. Microservices, which have become the default architecture for software development, can run as a process on an application server, virtual machines (VM) or container.

Each microservice is a mini-application that has its own business logic and adapters for carrying out functions such as database access and messaging. The resulting application will have small, loosely-coupled components that communicate with each other using lightweight communication protocols. Microservices typically communicate with each other using Application Programming Interfaces (APIs).

![alt text](https://user-images.githubusercontent.com/56096031/139155851-ad576cbd-8e74-4c45-9f28-50757bf28ce7.PNG)
# Micro services approach
- Microservices are an approach to the architecture and development of an
development of an application composed of small services.
- The idea is to break down a large problem into small units
  implemented in the form of micro-services
- Each service is responsible for one functionality,
- Each microservice is developed, tested and deployed
  separately from the others.
- Each microservice is developed using a technology that may be different from the others.
  (Java, C++, C#, PHP, NodeJS, Pyton,
  ...)
- Each service runs in a separate process.
- Using lightweight communication mechanisms (REST)
- The only relationship between the different microservices is the exchange of data
  data exchange through the different APIs they expose (
  SOAP, REST, RMI, CORBA, JMS, MQP, ...)
- When combined, these microservices can perform very complex
  complex operations.
- They are loosely coupled since
  each micro service is physically separated from
  separated from the others,
- Relative independence between the different
  teams that develop the different micro
  micro services.
- Ease of testing and deployment
- Continuous delivery.
- Well suited to the LGA process: TDD
  (Test Driver Development) and
  agile methods

## Application
We Created an application based on two business services:
  - Customer Service
  - Inventory service
  - Billing Service
  - External Services: RapidAPI.

The orchestration of the services is done through the
  services of Spring Cloud :
  - Spring Cloud Gateway Service as proxy service
  - Registry Eureka Service as a directory for registration and discovery of
  discovery of the services of the services of the architecture
  - Hystrix Circuit Breaker - Hystrix DashBoard

![alt text](https://user-images.githubusercontent.com/56096031/139157359-27b4acdd-9914-469c-bad1-7da06ee099ea.PNG)
### Customer Service
we have created a spring project customer-service.
dependencies:
- Spring Web 
- Spring Data JPA
- H2 Database 
- Rest Repositories 
- Lombok 
- Spring Boot DevTools
- Eureka Discovery Client
- Spring Boot Actuator
##### class Customer
```java
@Entity
@Data @NoArgsConstructor @AllArgsConstructor @ToString
public class Customer {
  @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  private String name;
  private String email;
}
```
#### interface CustomerRepository
```java
@RepositoryRestResource
@CrossOrigin("http://localhost:4200") // or @CrossOrigin("*")
public interface CustomerRepository extends JpaRepository<Customer,Long> {
}
```
#### class CustomerServiceApplication
```java
@SpringBootApplication
public class CustomerServiceApplication {

  public static void main(String[] args) {
    SpringApplication.run(CustomerServiceApplication.class, args);
  }
  @Bean
  CommandLineRunner start(CustomerRepository customerRepository, RepositoryRestConfiguration restConfiguration){
    restConfiguration.exposeIdsFor(Customer.class);
    return args -> {
      customerRepository.save(new Customer(null,"Enset","contact@enset-media.ma"));
      customerRepository.save(new Customer(null,"FSTM","contact@fstm.ma"));
      customerRepository.save(new Customer(null,"ENSAM","contact@ensam.ma"));
      customerRepository.findAll().forEach(System.out::println);
    };
  }
}
```
All customers
![alt text](https://user-images.githubusercontent.com/56096031/139273464-08c5b2e6-3290-4133-8b40-5b1b35bd1320.PNG)
Customer
![alt text](https://user-images.githubusercontent.com/56096031/139273680-833d132a-d648-40cd-9a37-9c72f8af5bc0.PNG)
Actuator
![alt text](https://user-images.githubusercontent.com/56096031/139273799-da28d8cc-7adb-44e2-a1cd-211e7b5fa518.PNG)
actuator/health
![alt text](https://user-images.githubusercontent.com/56096031/139273869-ac367121-7fd9-4ebf-9da5-fd85eb320b9d.PNG)
Application.proprties
![alt text](https://user-images.githubusercontent.com/56096031/139323900-54feec5b-2de1-4053-b8ed-dbd3e6fe3548.PNG)
### Inventory Service
we have created a spring project Inventory-service.
dependencies:
- Spring Web
- Spring Data JPA
- H2 Database
- Rest Repositories
- Lombok
- Spring Boot DevTools
- Eureka Discovery Client
- Spring Boot Actuator
#### class Product
```java
@Entity
@Data @NoArgsConstructor @AllArgsConstructor @ToString
public class Product {
@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
private Long id;
private String name;
private double price;
}
```
#### interface ProductRepository
```java
@RepositoryRestResource
@CrossOrigin("http://localhost:4200")
public interface ProductRepository extends JpaRepository<Product,Long> {
}
```
#### class InventoryServiceApplication
```java
@SpringBootApplication
public class InventoryServiceApplication {

  public static void main(String[] args) {
    SpringApplication.run(InventoryServiceApplication.class, args);
  }

  @Bean
  CommandLineRunner start(ProductRepository productRepository, RepositoryRestConfiguration restConfiguration) {
    restConfiguration.exposeIdsFor(Product.class);
    return args -> {
      productRepository.save(new Product(null, "Computer Desk Top HP", 900));
      productRepository.save(new Product(null, "Printer Epson", 80));
      productRepository.save(new Product(null, "MacBook Pro Lap Top", 1800));
      productRepository.findAll().forEach(System.out::println);
    };
  }
}
```
![alt text](https://user-images.githubusercontent.com/56096031/139275320-d73fbe0b-6167-4109-8705-34e1cd4db165.PNG)
![alt text](https://user-images.githubusercontent.com/56096031/139275566-9b0e1eed-2293-4a1b-8266-0580114ed5c4.PNG)
![alt text](https://user-images.githubusercontent.com/56096031/139275689-8ee9910d-86ee-45e3-9769-2c6e2005e737.PNG)
![alt text](https://user-images.githubusercontent.com/56096031/139275721-6f0c0b26-e465-4557-980f-c0a38f9ad54b.PNG)
Application.proprties
![alt text](https://user-images.githubusercontent.com/56096031/139323906-c056554e-aba7-4264-a61d-00695f452bac.PNG)
### Gateway-Service
Spring Cloud Gateway provides a library to build an API Gateway.
This is the preferred gateway implementation provided by Spring Cloud.
It's built with Spring 5, Spring Boot 2, and Project Reactor.
We used thos dependencies:
- Gateway
- Spring Boot Actuator
- Eureka Discovery Client 

#### There are two ways to configure the gattway routes 
1- Static 
 - Application.yml
    We can configure routes by create a application.yml file in resources Folder:
![alt text](https://user-images.githubusercontent.com/56096031/139330419-5b076202-5125-4976-9f7d-90059fa92633.PNG)

 Or use a  Java Config Class
![alt text](https://user-images.githubusercontent.com/56096031/139334103-54b899e9-1bcd-45bc-9135-7b3591a68f23.PNG)

