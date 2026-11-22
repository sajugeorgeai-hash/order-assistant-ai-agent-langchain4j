# saj-ai-demo

A Spring Boot demo of AI-powered customer support using **LangChain4j**, a locally
hosted **Ollama** chat model, and an **H2** database. It exposes two HTTP endpoints:
one for general AI chat and another for order support that can look up and manage
sample orders using LangChain4j tools.

## Capabilities

- Send a general question to the AI assistant.
- Ask the order assistant to retrieve order status, tracking/location, delivery
  estimate, and payment status.
- Ask it to list a customer's orders.
- Request supported order actions: cancellation, refund request, or an expected
  delivery date update. Eligibility is checked by `OrderService`.
- Start with 20 sample orders, loaded from `src/main/resources/data.sql` at startup.
- Inspect the in-memory H2 database through the H2 web console.

The order assistant is instructed to use the tools for real order information
instead of inventing details. It may ask for an order ID or customer name when one
is needed.

## Requirements

- Java 21
- Maven
- Ollama running locally with the configured model `qwen2.5:3b`

Download the model once:

```shell
ollama pull qwen2.5:3b
```

Make sure Ollama is running and available at `http://localhost:11434`, as configured
in `src/main/resources/application.yaml`.

## Run the application

From the project directory:

```shell
mvn spring-boot:run
```

The server listens on port `9292`. The H2 database is in memory; its sample data
is reloaded when the application starts, and any changes made during a run are
lost when it stops.

Run the tests with:

```shell
mvn test
```

## Query with Postman

Both chat endpoints use **GET** and require a `message` query parameter. In Postman,
select **GET**, enter the endpoint URL, then add `message` in the **Params** tab.
Postman will URL-encode the text.

| Purpose | URL | Example `message` |
|---|---|---|
| General AI chat | `http://localhost:9292/api/ai-direct` | `Explain what an order status means` |
| Order support | `http://localhost:9292/api/ai-direct/order-support` | `Where is order ORD-1001?` |

Other order-support prompts to try:

- `What is the status and delivery estimate for ORD-1002?`
- `What is the payment status for ORD-1010?`
- `Show me the orders for Amit Sharma.`
- `Cancel order ORD-1002.`

The endpoint returns the assistant's response as plain text. The response can take
a little longer while the local model generates it. For local URLs, use Postman
Desktop or its Desktop Agent; a browser-based agent may block requests to
`localhost`.

## Sample order IDs

The seed data includes `ORD-1001` through `ORD-1020`. For example:

- `ORD-1001`: shipped, Wireless Headphones
- `ORD-1002`: processing, Smartphone
- `ORD-1003`: delivered, Running Shoes
- `ORD-1004`: cancelled, Laptop Stand

## H2 console

With the application running, open:

```text
http://localhost:9292/h2-console
```

Use the following connection settings:

| Setting | Value |
|---|---|
| JDBC URL | `jdbc:h2:mem:saj` |
| User Name | `sa` |
| Password | leave blank |

After connecting, inspect the data with:

```sql
SELECT * FROM orders;
```

## Technology

- Java 21 and Spring Boot 4
- Spring MVC for HTTP endpoints
- LangChain4j Spring Boot integration for AI services and tools
- Ollama with `qwen2.5:3b` for local model inference
- Spring Data JPA and H2 for order persistence
