package ru.stepup.payservise.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.client.ResponseErrorHandler;
import ru.stepup.payservise.exceptions.IntegrationException;

import java.io.IOException;

@Slf4j
@Component
public class RestTemplateResponseErrorHandler implements ResponseErrorHandler {
    @Override
    public boolean hasError(ClientHttpResponse httpResponse)
            throws IOException {
        return httpResponse.getStatusCode().is4xxClientError() || httpResponse.getStatusCode().is5xxServerError();
    }

    @Override
    public void handleError(ClientHttpResponse httpResponse) throws IOException {

log.info("######## httpResponse.getBody().toString() = " + httpResponse.getBody().toString()  + ";\n getStatusText() = " + httpResponse.getStatusText()  + ";\n getStatusCode() = " + httpResponse.getStatusCode() + ";\n toString() = " + httpResponse.toString());
        if (httpResponse.getStatusCode().is4xxClientError()) {
            throw new IntegrationException(httpResponse.getBody().toString(), HttpStatus.BAD_REQUEST);    //NOT_FOUND
        } else if (httpResponse.getStatusCode().is5xxServerError()) {
            throw new IntegrationException(httpResponse.getBody().toString(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
