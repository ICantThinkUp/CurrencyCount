package interceptors;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GifRequestInterceptor implements RequestInterceptor {
    private static final Logger logger = LoggerFactory.getLogger(GifRequestInterceptor.class);

    @Override
    public void apply(RequestTemplate template) {
        template.header("authorization", "auth-bar");
        logger.info("bar authentication applied");
    }
}
