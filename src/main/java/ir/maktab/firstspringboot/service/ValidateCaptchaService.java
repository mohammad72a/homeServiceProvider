package ir.maktab.firstspringboot.service;

import ir.maktab.firstspringboot.api.transaction.CaptchaResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import java.util.Objects;

@Slf4j
@Service
public class ValidateCaptchaService {
    private final RestTemplate template;
    @Value("${google.recaptcha.verification.endpoint}")
    String recaptchaEndpoint;
    @Value("${google.recaptcha.secret}")
    String recaptchaSecret;

    public ValidateCaptchaService(final RestTemplateBuilder templateBuilder) {
        this.template = templateBuilder.build();
    }
    public boolean validateCaptcha(final String captchaResponse) {

        log.info("Going to validate the captcha response = {}", captchaResponse);
        final MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("secret", recaptchaSecret);
        params.add("response", captchaResponse);

        CaptchaResponse apiResponse = null;
        try {
            apiResponse = template.postForObject(recaptchaEndpoint, params, CaptchaResponse.class);
        } catch (final RestClientException e) {
            log.error("Some exception occurred while binding to the recaptcha endpoint.", e);
        }

        if (Objects.nonNull(apiResponse) && apiResponse.isSuccess()) {
            log.info("Captcha API response = {}", apiResponse.toString());
            return true;
        } else {
            return false;
        }
    }
}

