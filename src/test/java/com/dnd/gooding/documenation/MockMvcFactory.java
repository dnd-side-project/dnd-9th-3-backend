package com.dnd.gooding.documenation;

import com.dnd.gooding.util.JsonUtil;
import com.dnd.gooding.util.LocalDateTimeUtil;
import com.dnd.gooding.util.LocalDateUtil;
import com.dnd.gooding.util.LocalTimeUtil;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import org.springframework.core.convert.converter.Converter;
import org.springframework.format.support.DefaultFormattingConversionService;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.mockmvc.MockMvcRestDocumentation;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.test.web.servlet.setup.StandaloneMockMvcBuilder;
import org.springframework.web.filter.CharacterEncodingFilter;

public class MockMvcFactory {

    public static MockMvc getRestDocsMockMvc(
            RestDocumentationContextProvider restDocumentationContextProvider,
            String host,
            Object... controllers) {
        var documentationConfigurer =
                MockMvcRestDocumentation.documentationConfiguration(restDocumentationContextProvider);
        documentationConfigurer.uris().withScheme("https").withHost(host).withPort(443);

        return getMockMvcBuilder(controllers).apply(documentationConfigurer).build();
    }

    private static StandaloneMockMvcBuilder getMockMvcBuilder(Object... controllers) {
        var conversionService = new DefaultFormattingConversionService();
        conversionService.addConverter(new LocalDateTimeConverter());
        conversionService.addConverter(new LocalDateConverter());
        conversionService.addConverter(new LocalTimeConverter());

        return MockMvcBuilders.standaloneSetup(controllers)
                // .setControllerAdvice(
                // 	new GlobalRestControllerExceptionHandler(),
                // 	new ApiResponseWrappingAdvisor())
                .setControllerAdvice()
                .setConversionService(conversionService)
                .setMessageConverters(new MappingJackson2HttpMessageConverter(JsonUtil.getMapper()))
                .addFilter(new CharacterEncodingFilter(StandardCharsets.UTF_8.name(), true));
    }

    public static class LocalDateTimeConverter implements Converter<String, LocalDateTime> {

        @Override
        public LocalDateTime convert(String source) {
            return LocalDateTimeUtil.toLocalDateTime(source);
        }
    }

    public static class LocalDateConverter implements Converter<String, LocalDate> {

        @Override
        public LocalDate convert(String source) {
            return LocalDateUtil.toLocalDate(source);
        }
    }

    public static class LocalTimeConverter implements Converter<String, LocalTime> {
        @Override
        public LocalTime convert(String source) {
            return LocalTimeUtil.toLocalTime(source);
        }
    }
}
