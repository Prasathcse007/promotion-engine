# Promotion Engine

We need you to implement a simple promotion engine for a checkout process. Our Cart contains a list of single character
SKU ids (A, B, C. ) over which the promotion engine will need to run.
The promotion engine will need to calculate the total order value after applying the 2 promotion types

• buy &#39;n&#39; items of a SKU for a fixed price (3 A&#39;s for 130)
• buy SKU 1 &amp; SKU 2 for a fixed price ( C + D = 30 )

The promotion engine should be modular to allow for more promotion types to be added at a later date (e.g. a future
promotion could be x% of a SKU unit price). For this coding exercise you can assume that the promotions will be mutually
exclusive; in other words if one is applied the other promotions will not apply.

# Solution.

Developed the use case as microservice api using spring web .

Running the application:
mvn spring-boot:run

Running testcase
mvn test

API Details are in below swagger endpoint.
http://localhost:8080/promotion-engine/swagger-ui/index.html