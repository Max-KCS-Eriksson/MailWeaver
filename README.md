# MailWeaver

## Settings

User settings are specified in `src/main/resources/email.properties`.
First create above file in the mentioned path, and fill in below properties.

### Email

##### Login

```
EMAIL_SENDER=<your_email_address>
EMAIL_PASSWORD=<your_password>
```

**NOTE**: Currently only working for Gmail accounts using
[generated application passwords](#gmail-application-password).

###### Gmail application password

1. Log in and confirm 2-Step Verification is enabled
2. Go to: https://myaccount.google.com/apppasswords
3. Enter a name of your liking for the application
4. Click Generate
5. Use the shown generated password as the value for the `EMAIL_PASSWORD` property

##### Draft

The specified email draft will be sent to all [listed recipients](#recipients).

```
EMAIL_DRAFT=<path/to/file>
```

##### File attachment

A File can be attached to sent email by filling in below property with a path relative
to this application root directory.
This property can be completely omitted if not attachment is to be sent.

```
EMAIL_ATTACHMENT=<path/to/file>
```

##### Recipients

Specify the path to the CSV file containing the desired email recipients as below.
See [CSV structure](#recipient-list).

```
EMAIL_RECIPIENT_LIST=<path/to/file>
```

##### CSV separator

This property can be completely omitted if "`;`" is used as the CSV separator character.
If another character is wished to be used as a column separator, the below setting is to
be specified.

```
EMAIL_RECIPIENT_LIST_SEPARATOR=<character>
```

## Email Draft

Below listed placeholders will be processed according to the column values in the
[recipient list](#recipients).

### Placeholder tags

- `${company}`
- `${contactPerson}`
- `${optionalParagraph}`

##### Example draft

```
Hello ${contactPerson}!

Nice to meet you at ${company}!
${optionalParagraph}.

Best regards,
Jane Doe
```

## Recipient list

### Column headers

The CSV file is expected to contain column headers as below.

```
Company;Contact person;Contact email;Optional paragraph
```

### Column values

An email will be sent for each row in the CSV file to the recipient specified in the
`Contact email` column.

[Placeholders](#email-draft) in the [email draft](#draft) are replaced for each email
according to the [columns](#column-headers) like described below.

- `${company}` is replaced with `Company`
- `${contactPerson}` is replaced with `Contact person`
- `${optionalParagraph}` is replaced with `Optional paragraph` if present.

If the `Optional paragraph` is omitted a trailing [CSV separator](#csv-separator) is
required on that row.

##### Example columns

```
Company;Contact person;Contact email;Optional paragraph
Foo;Jane Smith;jane.smith@foo.io;Lorem ipsum dolor sit amet, consectetur adipiscing elit.
Bar;John Doe;john.doe@bar.io;
```

Note that the last row omits the optional paragraph and therefore ends the row with the
[CSV separator](#csv-separator) while the first row doesn't.
