[![Coverage Status](https://coveralls.io/repos/github/zechtz/gepg-java/badge.svg?branch=main)](https://coveralls.io/github/zechtz/gepg-java?branch=main)

# GePG Java Library

A Java library for GePG integration.

## Motivation

Integrating with GePG often requires writing repetitive code. This library simplifies the process, handling all the complex aspects for you, so you can focus on developing robust software.

## Installation instructions

### For Gradle

```sh

def githubProperties = new Properties()
if (rootProject.file("github.properties").exists()) {
    githubProperties.load(new FileInputStream(rootProject.file("github.properties")))
}

repositories {
    mavenCentral()
    maven {
     url = uri("https://maven.pkg.github.com/zechtz/gepg-java")
     credentials {
        username = githubProperties['gpr.usr'] ?: System.getenv("GITHUB_ACTOR")
        password = githubProperties['gpr.key'] ?: System.getenv("GITHUB_TOKEN")
    }
  }
}

dependencies {
    implementation 'com.watabelabs:gepg:1.0-SNAPSHOT'
}
```

### For maven

```sh
<repositories>
    <repository>
        <id>github</id>
        <url>https://maven.pkg.github.com/zechtz/gepg-java</url>
        <releases>
            <enabled>true</enabled>
        </releases>
    </repository>
</repositories>

<dependency>
    <groupId>com.watabelabs</groupId>
    <artifactId>gepg</artifactId>
    <version>1.0-SNAPSHOT</version>
</dependency>
```

## Using the library

To use this library first you need to create a .env file at the root of your
project then install the dotenv-java library

### For Maven

```
<dependency>
    <groupId>io.github.cdimascio</groupId>
    <artifactId>dotenv-java</artifactId>
    <version>2.2.0</version>
</dependency>
```

### For Gradle

```
implementation 'io.github.cdimascio:dotenv-java:2.2.0'
```

Then add the following to your .env

```
PRIVATE_KEYSTORE_PATH=path/to/privatekey-file
PRIVATE_KEYSTORE_PASSWORD=password
PRIVATE_KEY_ALIAS=alias
GEPG_CODE=gepgCode
API_URL=http://api.url
```

## Features

This library exposes all possible requests and responses to and from GePG.

### Data Mapping

#### For Billing

-   `GepgBillCanclReq`
-   `GepgBillCanclResp`
-   `GepgBillControlNoReuse`
-   `GepgBillHdr`
-   `GepgBillItem`
-   `GepgBillSubReq`
-   `GepgBillSubReqAck`
-   `GepgBillSubResAck`
-   `GepgBillSubResp`
-   `GepgBillSubRespAck`
-   `GepgBillTrxInf`

#### For Payments

-   `GepgPmtSpInfo`
-   `GepgPmtSpInfoAck`
-   `GepgPymtTrxInf`

#### For Reconciliation

-   `GepgReconcTrans`
-   `GepgReconcTrxInf`
-   `GepgSpReconcRespAck`
-   `GepgReconcBatchInfo`

All these DTOs conform to the GePG specification, eliminating the need to maintain your own DTOs.

## API

## Methods

### The GepgApiClient class exposes the following methods

-   `submitBill(String signedRequest)`: This method submits a bill to the GePG API, the
    method takes a signed xml string as a parameter and returns an instance of the GepgBillSubReqAck class

-   `reuseControlNumber(String signedRequest)`: This method submits a control number reuse request to the GePG API and respond by returning an instance of the GepgBillSubReqAck class

-   `updateBill(String signedRequest)`: This method submits a bill change/update request to the GePG API and respond by returning an instance of the GepgBillSubReqAck class

-   `cancelBill(String signedRequest)`: This method submits a bill change/update request to the GePG API and responds by returning an instance of the GepgBillSubReqAck class

-   `submitPayment(String signedRequest)`: This methodSubmits a payment to the GePG API and responds by returning an instance of the GepgPmtSpInfoAck class

-   `receivePaymentNotification(String signedRequest)`: This method receives the payment notification Info as xml and returns an instance of the GepgPmtSpInfo class

-   `receiveControlNumber(String signedRequest)`: This method receives the control number response and sends an acknowledgment back as a signed xml string

-   `signMessage(String xmlString, Class<T> clazz)`: method that adds a signature to your payload. This method returns a signed xml string of the clazz type.

-   `convertToJavaObject(String xmlString, Class<T> clazz)`: this method converts a JAXB-annotated object to an XML string and returns the xml

-   `convertToXmlString(Object object)`: this method unwraps the provided XML string from the envelope and converts it to the specified POJO class..

-   `convertToXmlString(Object object)`: this method unwraps the provided XML string from the envelope and converts it to the specified POJO class..

## Usage Example

### Bill Submission

```java
public GepgBillSubReqAck submitBill() throws Exception {

    // instantiate the apiClient
    GepgApiClient gepgApiClient = new GepgApiClient(AppConstant.BILL_SUBMISSION_URL);

    // create the GepgBillSubReq object and populate it with data
    GepgBillSubReq billSubRequestMapper = createBillSubReq();

    // convert it to an xml object and sign it
    String billXml = gepgApiClient.convertToXmlString(billSubReq);

    // sign the message
    String signedXml = gepgApiClient.signMessage(billXml, GepgBillSubReq.class);

    // submit Bill to Gepg
    GepgBillSubReqAck gepgBillSubResp = gepgApiClient.submitBill(signedXml);

    // return the response
    return gepgBillSubResp;
 }

    private static GepgBillSubReq createBillSubReq() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        sdf.setTimeZone(TimeZone.getTimeZone("UTC"));

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");

        GepgBillHdr billHdr = new GepgBillHdr("SP023", true);
        GepgBillItem item1 = new GepgBillItem("788578851", "N", 7885.0, 7885.0, 0.0, "140206");
        GepgBillItem item2 = new GepgBillItem("788578852", "N", 7885.0, 7885.0, 0.0, "140206");

        GepgBillTrxInf billTrxInf = new GepgBillTrxInf(
                UUID.fromString("11ae8614-ceda-4b32-aa83-2dc651ed4bcd"), "2001", "tjv47", 7885.0, 0.0, LocalDateTime.parse("2017-05-30T10:00:01", formatter), "Palapala",
                "Charles Palapala",
                "Bill Number 7885", LocalDateTime.parse("2017-02-22T10:00:10", formatter), "100", "Hashim",
                "0699210053",
                "charlestp@yahoo.com",
                "TZS", 7885.0, true, 1, Arrays.asList(item1, item2));

        return new GepgBillSubReq(billHdr, billTrxInf);
    }

```

### Receiving Control Number

```
// springboot rest API (this is one of the callback urls you provide gepg)
@PostMapping(value = "/submit-control-number")
@Transactional
public String postGepgBillSubResps(@RequestBody String xml) throws Exception {
    return billService.receiveControlNumber(xml);
}

### the service

@Override
public String receiveControlNumber(String xml) throws Exception {
    // instantiate the api client
    GepgApiClient gepgApiClient = new GepgApiClient();

    // convert the xml string into a readable java object
    GepgBillSubResp gepgBillSubResp = gepgApiClient.convertToJavaObject(xml, GepgBillSubResp.class);

    // probably use this data to update control number and message to bill

    // respond by return the ack
    return gepgApiClient.receiveControlNumber(gepgBillSubResp);
}

```

## Receive Payment Notification

```
// springboot rest API (this is one of the callback urls you provide gepg)
@PostMapping(value = "/receieve-payment-notifications")
@Transactional
public String receivePaymentNotifications(@RequestBody String xml) throws Exception {
    return billService.receiveControlNumber(xml);
}

### the service

@Override
public String receivePaymentNotifications(String responseXml) throws Exception {
    GepgApiClient gepgApiClient = new GepgApiClient();

    // convert this into an a readable java object
    GepgPmtSpInfo gepgPaymentSpInfo = gepgApiClient.receivePaymentNotification(responseXml);

    // do something with the payment info
    // eg. update payment
    // set payment status

    // return the acknowledgement
    return gepgApiClient.generateResponseAck(GepgPmtSpInfoAck.class);
}

```

## Contributing

I welcome contributions! If you want to contribute to this project, please fork the repository and create a pull request with your changes. Make sure to follow the existing code style and include tests for your changes.

## License

This project is licensed under the MIT License. See the LICENSE file for details
