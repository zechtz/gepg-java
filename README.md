[![Coverage Status](https://coveralls.io/repos/github/zechtz/gepg-java/badge.svg?branch=main)](https://coveralls.io/github/zechtz/gepg-java?branch=main)

# GePG Java Library

A Java library for GePG integration.

## Motivation

Integrating with GePG often requires writing repetitive code. This library simplifies the process, handling all the complex aspects for you, so you can focus on developing robust software.

## Features

This library exposes all possible requests and responses to and from GePG.

### Data Mapping

#### For Billing

-   `GepgBillCancellationReqMapper`
-   `GepgBillCancellationRespMapper`
-   `GepgBillChangeRequestMapper`
-   `GepgBillControlNoReuseReqMapper`
-   `GepgBillHdrMapper`
-   `GepgBillItemMapper`
-   `GepgBillSubReqAckMapper`
-   `GepgBillSubReqMapper`
-   `GepgBillSubRespAckMapper`
-   `GepgBillSubRespAckResultMapper`
-   `GepgBillSubRespMapper`
-   `GepgBillSubRespPayloadMapper`
-   `GepgBillTrxInfoMapper`

#### For Payments

-   `GepgPmtSpInfoAckMapper`
-   `GepgPmtSpInfoAckResultMapper`
-   `GepgPmtSpInfoMapper`
-   `GepgPmtSpInfoPayloadMapper`
-   `GepgPymtTrxInfMapper`

#### For Reconciliation

-   `GepgReconcBatchInfoMapper`
-   `GepgReconcTransMapper`
-   `GepgReconcTrxInfMapper`
-   `GepgSpReconcRespAckMapper`
-   `GepgSpReconcRespAckResultMapper`
-   `GepgSpReconcRespMapper`
-   `GepgSpReconcRespPayload`

All these mappers conform to the GePG specification, eliminating the need to maintain your own DTOs.

## API

### Methods

#### MessageUtil Class

-   `sign()`: Instance method that adds a signature to your payload. This method returns a signed XML string as required by GePG.

#### XmlUtil Class

-   `convertToXmlString()`: Static method that takes in any DataWrapper and returns its XML string representation.

#### GepgRequest Class

-   `submitBill()`: Instance method that takes a signed XML string, makes a request to GePG, and returns a `GepgBillSubReqAckMapper`.
-   `reuseControlNumber()`: Returns a `GepgBillSubReqMapper`.
-   `updateBill()`: Returns a `GepgBillSubReqMapper`.
-   `cancelBill()`: Returns a `GepgBillCancellationRespMapper`.
-   `requestPaymentInfo()`: Requests payment information.

## Usage Example

```java
public void submitBill() throws Exception {
    // Create a sample message
    GepgBillSubReqMapper billSubRequestMapper = createData();

    // Convert it to XML string
    String message = XmlUtil.convertToXmlString(billSubRequestMapper);

    // Instantiate MessageUtil, it takes 3 parameters
    // keystorePath => path to your private file
    // keystorePassword => its password
    // keyAlias => its alias
    MessageUtil messageUtil = new MessageUtil(keystorePath, keystorePassword, keyAlias);

    // Sign the message
    String signedMessage = messageUtil.sign(message);

    // Instantiate GepgRequest
    GepgRequest request = new GepgRequest(gepgCode, apiUrl);

    // Submit the signed message
    GepgBillSubReqAckMapper response = request.submitBill(signedMessage);

    System.out.println(response);
}

private static GepgBillSubReqMapper createData() {
    // Creating and populating the Bill Header
    GepgBillHdrMapper billHdr = new GepgBillHdrMapper("SP023", true);

    // Creating and populating Bill Items
    GepgBillItemMapper item1 = new GepgBillItemMapper("788578851", "N", 7885.00, 7885.00, 0.00, "140206");
    GepgBillItemMapper item2 = new GepgBillItemMapper("788578852", "N", 7885.00, 7885.00, 0.00, "140206");

    // Creating and populating the Bill Transaction Information
    GepgBillTrxInfoMapper billTrxInf = new GepgBillTrxInfoMapper(
            "7885", "2001", "tjv47", 7885, 0, new Date(), "Palapala", "Charles Palapala",
            "Bill Number 7885", new Date(), "100", "Hashim", "0699210053", "charlestp@yahoo.com",
            "TZS", 7885, true, 1, Arrays.asList(item1, item2));

    // Creating and populating the Bill Submission Request
    GepgBillSubReqMapper request = new GepgBillSubReqMapper(billHdr, billTrxInf);

    // Print the object to verify the data
    return request;
}

```

# Contributing

We welcome contributions! If you want to contribute to this project, please fork the repository and create a pull request with your changes. Make sure to follow the existing code style and include tests for your changes.

# License

This project is licensed under the MIT License. See the LICENSE file for details
