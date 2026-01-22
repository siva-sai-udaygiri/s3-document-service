# S3 Document Service (Spring Boot)

A lightweight Spring Boot REST API that stores and retrieves JSON documents in Amazon S3 using a simple key-based model.  
Designed as a clean, resume-ready project with CI support (Jenkinsfile + GitHub Actions) and infrastructure code (Terraform).

## What this project does
- Upload (create/update) a JSON document to S3 under a key
- Fetch a JSON document from S3 by key
- Delete a document by key
- List documents by prefix (and pagination, if implemented)
- Exposes health checks (including S3 connectivity if enabled)

## Tech Stack
- **Java 17**
- **Spring Boot 3**
- **Spring Web (REST APIs)**
- **AWS SDK (S3)**
- **Jackson** (JSON handling)
- **JUnit / Spring Boot Test** (tests)
- **Jenkins** (Jenkinsfile pipeline)
- **GitHub Actions** (workflow under `.github/workflows`)
- **Terraform** (infrastructure under `infra/`)

## Project Structure (high level)
- `src/main/java/.../web` — Controllers + exception handling
- `src/main/java/.../service` — Business logic
- `src/main/java/.../s3` — S3 repository wrapper (put/get/list/delete)
- `src/main/java/.../config` — S3 config/properties
- `src/main/java/.../health` — health indicator
- `infra/` — Terraform code
- `Jenkinsfile` — Jenkins pipeline definition

## API Endpoints (example)
> Your exact paths may differ depending on your controller mapping. Update these to match your code.

- `PUT /api/v1/docs/{key}`  
  Stores JSON for the given key.
- `GET /api/v1/docs/{key}`  
  Returns the stored JSON (or 404 if missing).
- `DELETE /api/v1/docs/{key}`  
  Deletes the document.
- `GET /api/v1/docs?prefix=...`  
  Lists documents by prefix (if implemented).

## Local Setup

### Prerequisites
- Java 17 installed
- Maven (or use the included Maven Wrapper: `./mvnw` / `mvnw.cmd`)
- AWS credentials configured (one of the below):
  - `~/.aws/credentials` (recommended)
  - Environment variables: `AWS_ACCESS_KEY_ID`, `AWS_SECRET_ACCESS_KEY`
  - IAM role (if running on an AWS machine)

### Required Configuration
Configure bucket/region via `application.yml` (or environment variables). Common options:
- `AWS_REGION`
- `S3_BUCKET` (or whatever your `S3Properties` expects)

### Run the app
```bash
# Windows
mvnw.cmd spring-boot:run

# Mac/Linux
./mvnw spring-boot:run
