package com.watabelabs.gepg;

import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;

import com.watabelabs.gepg.mappers.bill.GepgBillCancellationRespMapper;
import com.watabelabs.gepg.mappers.bill.GepgBillSubReqAckMapper;
import com.watabelabs.gepg.mappers.bill.GepgBillSubRequestMapper;
import com.watabelabs.gepg.mappers.payment.GepgPmtSpInfoAckMapper;

public class GepgRequest {

    private final String gepgCode;
    private final String apiUrl;
    private static final String CONTENT_TYPE = "Application/xml";
    private static final String GEPG_COM = "default.sp.in";

    /**
     * Constructor to initialize the GepgRequest with the necessary parameters.
     *
     * @param gepgCode the GePG code
     * @param apiU     l the API URL
     */
    public GepgRequest(String gepgCode, String apiUrl) {
        this.gepgCode = gepgCode;
        this.apiUrl = apiUrl;
    }

    /**
     * Submits a bill to the GePG API.
     *
     * @param signedRequest the signed XML request
     * @return the acknowledgment response from the GePG API
     * @throws Exception if an error occurs during the process
     */
    public GepgBillSubReqAckMapper submitBill(String signedRequest) throws Exception {
        String response = sendRequest(signedRequest);
        return mapResponse(response, GepgBillSubReqAckMapper.class);
    }

    /**
     * Reuses a control number with the GePG API.
     *
     * @param signedRequest the signed XML request
     * @return the bill submission request generated from reusing the control number
     * @throws Exception if an error occurs during the process
     */
    public GepgBillSubRequestMapper reuseControlNumber(String signedRequest) throws Exception {
        String response = sendRequest(signedRequest);
        return mapResponse(response, GepgBillSubRequestMapper.class);
    }

    /**
     * Updates a bill with the GePG API.
     *
     * @param signedRequest the signed XML request
     * @return the updated bill submission request
     * @throws Exception if an error occurs during the process
     */
    public GepgBillSubRequestMapper updateBill(String signedRequest) throws Exception {
        String response = sendRequest(signedRequest);
        return mapResponse(response, GepgBillSubRequestMapper.class);
    }

    /**
     * Cancels a bill with the GePG API.
     *
     * @param signedRequest the signed XML request
     * @return the response from the GePG API regarding the cancellation
     * @throws Exception if an error occurs during the process
     */
    public GepgBillCancellationRespMapper cancelBill(String signedRequest) throws Exception {
        String response = sendRequest(signedRequest);
        return mapResponse(response, GepgBillCancellationRespMapper.class);
    }

    /**
     * Requests payment information from the GePG API.
     *
     * @param signedRequest the signed XML request
     * @return the acknowledgment response from the GePG API
     * @throws Exception if an error occurs during the process
     */
    public GepgPmtSpInfoAckMapper requestPaymentInfo(String signedRequest) throws Exception {
        String response = sendRequest(signedRequest);
        return mapResponse(response, GepgPmtSpInfoAckMapper.class);
    }

    /**
     * Sends the signed request to the GePG API and returns the response.
     *
     * @param signedRequest the signed XML request
     * @return the response from the GePG API
     * @throws Exception if an error occurs during the request
     */
    private String sendRequest(String signedRequest) throws Exception {
        URL url = new URL(apiUrl);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("POST");
        connection.setRequestProperty("Content-Type", CONTENT_TYPE);
        connection.setRequestProperty("Gepg-Com", GEPG_COM);
        connection.setRequestProperty("Gepg-Code", gepgCode);
        connection.setDoOutput(true);
        connection.getOutputStream().write(signedRequest.getBytes("UTF-8"));

        Scanner scanner = new Scanner(connection.getInputStream());
        String response = scanner.useDelimiter("\\A").next();
        scanner.close();

        return response;
    }

    /**
     * Maps the XML response to the specified class type.
     *
     * @param response the XML response
     * @param cla      z the class to map the response to
     * @param <        > the type of the class
     * @return the mapped object
     * @throws Exception if an error occurs during the mapping
     */
    @SuppressWarnings("unchecked")
    private <T> T mapResponse(String response, Class<T> clazz) throws Exception {
        JAXBContext context = JAXBContext.newInstance(clazz);
        Unmarshaller unmarshaller = context.createUnmarshaller();
        StringReader reader = new StringReader(response);
        return (T) unmarshaller.unmarshal(reader);
    }
}
