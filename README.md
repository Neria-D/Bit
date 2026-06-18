# Bit Automation Project

Sample QA Automation / SDET framework for the bit request-payment flow.

## Scope

The repository covers the three requested parts of the exercise:

- Backend API test framework in Java.
- Mobile UI automation sample in Kotlin with Page Object Model.
- GitHub Actions pipeline for running the backend tests with secured AWS credentials.

## Architecture

Backend tests use a layered structure with clear responsibilities:

```text
RequestPaymentTest
  -> BaseApiTest
  -> PaymentRequestFactory
  -> RequestPaymentAssertions
  -> RequestPaymentClient
  -> DynamoDbValidator
  -> ConfigManager / JsonUtils
```

Mobile tests use Page Object Model:

```text
ConfirmMoneyPopupTest
  -> BaseMobileTest
  -> DriverFactory
  -> ConfirmMoneyPopupPage
  -> Appium selectors and actions
```

Each class is kept focused: API communication, AWS validation, configuration, JSON value validation, page elements, and test flow are separated.

`BaseApiTest` creates the API client and DynamoDB validator before each backend test and closes the AWS SDK client after each test. This keeps the lifecycle explicit and avoids leaking SDK resources. A singleton AWS client is intentionally avoided because the exercise focuses on clear test isolation and conceptual framework structure.

## Technologies

- Java 15+
- Maven
- JUnit 5
- RestAssured
- AWS SDK for Java v2, DynamoDB
- Kotlin sample code for Android mobile automation
- Appium
- GitHub Actions

## Project Structure

```text
backend-tests/
  src/main/java/api/PaymentRequest.java
  src/main/java/api/PaymentResponse.java
  src/main/java/api/RequestPaymentClient.java
  src/main/java/aws/AwsClientFactory.java
  src/main/java/aws/DynamoDbValidator.java
  src/main/java/config/ConfigManager.java
  src/main/java/utils/JsonUtils.java
  src/test/java/assertions/RequestPaymentAssertions.java
  src/test/java/testdata/PaymentRequestFactory.java
  src/test/java/tests/BaseApiTest.java
  src/test/java/tests/RequestPaymentTest.java

mobile-tests/
  android/driver/DriverFactory.kt
  android/pages/ConfirmMoneyPopupPage.kt
  android/tests/BaseMobileTest.kt
  android/tests/ConfirmMoneyPopupTest.kt

.github/workflows/automation.yml
pom.xml
```

The Maven `pom.xml` points the Java source and test source directories to `backend-tests`.

## Backend Flow

`RequestPaymentTest`:

- builds a JSON payload with `amount`, `senderPhone`, and `receiverPhone`
- sends a POST request to the request-payment endpoint
- validates HTTP `200 OK`
- extracts the required `transactionId`
- verifies that the transaction exists in DynamoDB

`DynamoDbValidator` includes retry logic because backend persistence may be eventually consistent.

`AwsClientFactory` centralizes AWS SDK client creation. It uses the SDK default credentials provider chain and a region from configuration, so the same framework can work with local environment variables or CI secrets.

## Mobile Flow

`ConfirmMoneyPopupPage` defines the popup elements and actions.

`ConfirmMoneyPopupTest`:

- identifies the approve-transfer button
- clicks it
- verifies the final confirmation text: `הכסף הועבר לחשבונך`

## Configuration

The framework reads these environment variables:

- `REQUEST_PAYMENT_ENDPOINT`
- `TRANSACTIONS_TABLE_NAME`
- `AWS_REGION`
- `AWS_ACCESS_KEY_ID`
- `AWS_SECRET_ACCESS_KEY`

## CI/CD

The GitHub Actions workflow:

- runs on push, pull request, and a daily scheduled cron trigger
- checks out the repository
- installs Java 17 and compiles the project with Java 15 source/target compatibility
- configures AWS credentials from GitHub Secrets
- injects endpoint and DynamoDB table values from Secrets
- runs JUnit tests tagged with `backend` using `mvn clean test -Dgroups=backend`

The backend test requires a real or mocked endpoint and DynamoDB-compatible backend to execute successfully. For the conceptual assignment, the implementation demonstrates the framework, structure, API validation, and AWS verification flow without requiring a live environment.

## Assumptions

- The request-payment endpoint is conceptual and defaults to `https://api.bit.co.il/mock/request-payment`.
- DynamoDB is used as the conceptual backend source of truth.
- The DynamoDB table name defaults to `BitTransactions`.
- AWS region defaults to `eu-west-1` when `AWS_REGION` is not provided.
- Mobile selectors are placeholders, for example `CONFIRM_TRANSFER_BUTTON_ID`.
- Appium capabilities use conceptual Android app values.
- No real environment, simulator, or execution is required for this exercise.
