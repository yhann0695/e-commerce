package br.com.product.api.productapi.configuration.interceptor;

import br.com.product.api.productapi.configuration.exception.ValidationException;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

import static java.util.Objects.requireNonNull;

public class FeignClientAuthInterceptor implements RequestInterceptor {

    private static final String AUTHORIZATION = "Authorization";

    @Override
    public void apply(RequestTemplate template) {
        var currentRequest = getCurrentRequest();
        template.header(AUTHORIZATION, currentRequest.getHeader(AUTHORIZATION));
    }

    private HttpServletRequest getCurrentRequest() {
        try {
            return ((ServletRequestAttributes) requireNonNull(RequestContextHolder.getRequestAttributes())).getRequest();
        } catch (Exception exception) {
            exception.printStackTrace();
            throw new ValidationException("The current request could not be processed");
        }
    }
}
