# MailWeaver

MailWeaver is a Spring Boot–based CLI application that automates the creation and delivery of personalized emails from structured CSV input.

It was built to solve a practical problem: sending customized outreach emails at scale without risking formatting mistakes, missing attachments, or inconsistent messaging.

The application separates parsing, formatting, and delivery concerns to remain maintainable and easy to extend.

## Why

When sending many similar emails (e.g., internship applications or professional outreach), manual handling quickly becomes:

- Error-prone
- Time-consuming
- Inconsistent
- Risky (missed attachments, wrong names, incorrect company references)

MailWeaver removes that friction by:

- Structuring recipient data
- Injecting contextual placeholders
- Attaching required files automatically
- Sending via configured SMTP

It turns repetitive manual work into a deterministic, repeatable workflow.

## Tech Stack

- Java 21
- Spring Boot 3
- Maven
- Jakarta Mail (SMTP)
- OpenCSV

## Requirements

- Java 21
- Maven 3.9+
- Access to an SMTP account (e.g. Gmail with App Password)

## Usage

### Run the Application

```bash
mvn clean spring-boot:run
```

### Configuration

Create the file:

```
src/main/resources/email.properties
```

Populate it with the required properties below.

#### 1. Email Credentials

```
EMAIL_SENDER=<your_email_address>
EMAIL_PASSWORD=<your_app_password>
```

For Gmail:

1. Enable 2-Step Verification
2. Use a generated App Password value as `EMAIL_PASSWORD`

#### 2. Email Draft Template

```
EMAIL_DRAFT=<path/to/email-draft.txt>
```

The draft supports placeholder tokens:

- `${company}`
- `${contactPerson}`
- `${optionalParagraph}`

##### Example:

```
Hello ${contactPerson},

I’m reaching out regarding opportunities at ${company}.
${optionalParagraph}

Best regards,
Jane Doe
```

Each placeholder is dynamically replaced using values from the CSV row.

#### 3. Recipient List (CSV)

```
EMAIL_RECIPIENT_LIST=<path/to/recipients.csv>
```

Expected column headers:

```
Company;Contact person;Contact email;Optional paragraph
```

Each row generates one email.

If the optional paragraph is omitted for a row, the separator must still terminate the line.

##### Example:

```
Company;Contact person;Contact email;Optional paragraph
Foo;Jane Smith;jane@foo.io;It was great meeting your team.
Bar;John Doe;john@bar.io;
```

#### 4. Optional File Attachment

```
EMAIL_ATTACHMENT=<path/to/file>
```

This property can be omitted if no attachment is required.

#### 5. Optional CSV Separator

Default separator is "`;`"

To override:

```
EMAIL_RECIPIENT_LIST_SEPARATOR=<character>
```

## Architecture Overview

The system is structured into clearly separated components:

- CSV Parsing Layer — structured input extraction
- Formatting Layer — dynamic placeholder replacement
- Mail Service Layer — SMTP communication and attachment handling
- CLI Orchestration Layer — execution flow via Spring Boot's `CommandLineRunner`

This modular structure keeps responsibilities explicit and reduces coupling between
formatting logic and delivery logic.

## Potential Improvements

- Daily maximum send limit to reduce risk of spam flagging
- Retry and failure handling
- Structured logging
- HTML email support
- Containerization
