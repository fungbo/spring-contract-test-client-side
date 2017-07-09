package com.tw;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.cloud.contract.stubrunner.spring.AutoConfigureStubRunner;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Java6Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureStubRunner(ids = {"com.tw:http-server:+:stubs:8080"}, workOffline = true)
public class LoanApplicationServiceTests {

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void should_test_loan_service() throws Exception {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Content-Type", "application/json");
        HttpEntity<String> requestEntity = new HttpEntity<>("{\"clientId\":\"1234567890\",\"loanAmount\":99999}",
                httpHeaders);
        ResponseEntity<String> response = restTemplate.exchange("http://localhost:8080/fraudcheck",
                HttpMethod.PUT, requestEntity, String.class);

        assertThat(response.getBody())
                .isEqualTo("{\"fraudCheckStatus\":\"FRAUD\",\"rejectionReason\":\"Amount too high\"}");
    }
}
