[![Coverage Status](https://coveralls.io/repos/github/zechtz/gepg-java/badge.svg?branch=main)](https://coveralls.io/github/zechtz/gepg-java?branch=main)

# GePG Java Library

A Java library for GePG integration.

## Motivation

Integrating with GePG often requires writing repetitive code. This library simplifies the process, handling all the complex aspects for you, so you can focus on developing robust software.

## Installation instructions

### For Gradle

You need to generate a github token, [read more here](https://docs.github.com/en/authentication/keeping-your-account-and-data-secure/managing-your-personal-access-tokens), after getting your github token, create a github.properties file in the
root of your project (don't forget to .gitignore this file), the file content should look like this

```
gpr.usr=useaname
gpr.key=ghp_pTheRestofyourtoken
```

#### Then add the following to your gradle.build file

```
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

**Step 1: Configure settings.xml**

Edit the settings.xml file (usually located in ~/.m2/settings.xml) create one it it doesn't exist

**Step 2: Add this in your settings.xml**

```
<settings>
    <servers>
        <server>
            <id>github</id>
            <username>username</username>
            <password>ghp_pTheRestofyourtoken</password>
        </server>
    </servers>
</settings>
```

**Step 3: Add this to your pom.xml**

```
<repositories>
    <repository>
        <id>github</id>
        <url>https://maven.pkg.github.com/zechtz/gepg-java</url>
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

**For Maven**

```
<dependency>
    <groupId>io.github.cdimascio</groupId>
    <artifactId>dotenv-java</artifactId>
    <version>2.2.0</version>
</dependency>
```

**For Gradle**

```
implementation 'io.github.cdimascio:dotenv-java:2.2.0'
```

**Then add the following to your .env**

```
PRIVATE_KEYSTORE_PATH=path-to-private-key.pfx
PUBLIC_KEYSTORE_PATH=path-to-public-key.pfx
PRIVATE_KEYSTORE_PASSWORD=keypassword
PRIVATE_KEY_ALIAS=keyalias
SP_CODE=spcode
SUB_SP_CODE=subspcode
SUB_SP_GFS_CODE=sub-sp-gfs-code
SYSTEM_ID=system-id
GFS_CODE=gfs-code
API_URL=gepg-testing-url
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

-   `GepgSpReconcResp`
-   `GepgReconcTrans`
-   `GepgReconcTrxInf`
-   `GepgSpReconcRespAck`
-   `GepgReconcBatchInfo`

All these DTOs conform to the GePG specification, eliminating the need to maintain your own DTOs.

## API

## Methods

### The GepgApiClient class exposes the following methods

-   `submitBill(String signedRequestXml)`: This method submits a bill to the GePG API, the
    method takes a signed xml string as a parameter and returns an instance of the `GepgBillSubReqAck` class

-   `reuseControlNumber(String signedRequest)`: This method submits a control number reuse request to the GePG API and respond by returning an instance of the `GepgBillSubReqAck` class

-   `updateBill(String signedRequest)`: This method submits a bill change/update request to the GePG API and respond by returning an instance of the `GepgBillSubReqAck` class

-   `cancelBill(String signedRequest)`: This method submits a bill change/update request to the GePG API and responds by returning an instance of the `GepgBillSubReqAck` class

-   `submitPayment(String signedRequest)`: This method Submits a payment to the GePG API and responds by returning an instance of the `GepgPmtSpInfoAck` class

-   `requestReconciliation(String signedRequest)`: This method Submits a reconciliation request to the GePG API and responds by returning an instance of the `GepgSpReconcRespAck` class

-   `receivePaymentNotification(String signedRequest)`: This method receives the payment notification Info as xml and returns an instance of the `GepgPmtSpInfo` class

-   `receiveControlNumber(String signedRequest)`: This method receives the control number response and sends an acknowledgment back as a signed xml string

-   `receiveReconciliationResponse(String gepgSpReconcResp)`: This method receives the reconciliation response and return the `GepgSpReconcResp` object

-   `signMessage(String xmlString, Class<T> clazz)`: method that adds a signature to your payload. This method returns a signed xml string of the clazz type.

-   `convertToJavaObject(String xmlString, Class<T> clazz)`: this method converts an xml string into a JAXB-annotated object an returns it

-   `convertToXmlString(Object object)`: this method takes a java JAXB-annotated object and converts it into an xml string nad returns it

## Usage Example

### Bill Submission

```java
public GepgBillSubReqAck submitBill() throws Exception {

    // instantiate the apiClient
    GepgApiClient gepgApiClient = new GepgApiClient();

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

**This is the callback url you will provide to GePG so that they can send control numbers to your system, here we are using Springboot Rest API**

```
@PostMapping(value = "/submit-control-number")
@Transactional
public String postGepgBillSubResps(@RequestBody String xml) throws Exception {
    return billService.receiveControlNumber(xml);
}
```

**The service method that will handle the request**

```
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

### Receiving Payment Notifications

**This is the callback url you will provide to GePG so that they can send payment notifications to your system, again, we are using Springboot Rest API**

```
@PostMapping(value = "/receieve-payment-notifications")
@Transactional
public String receivePaymentNotifications(@RequestBody String xml) throws Exception {
    return billService.receiveControlNumber(xml);
}
```

**The service method that will handle the request from the rest API**

```
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
