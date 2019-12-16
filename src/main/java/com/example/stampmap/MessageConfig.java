package com.example.stampmap;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.validation.Validator; 
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
@Configuration
/* !! ※2015/2/2訂正 webjars:bootstrapが効かなくなるので除去 !! 
@EnableWebMvc  //（※１）
*/
public class MessageConfig extends WebMvcConfigurerAdapter {  //（※１）

    @Bean
    ReloadableResourceBundleMessageSource messageSource() {
        ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
        messageSource.setBasenames("classpath:i18n/messages"); //（※２）
        messageSource.setCacheSeconds(0);
        messageSource.setDefaultEncoding("UTF-8");
        return messageSource;
    }

    @Bean
    public LocalValidatorFactoryBean validator() {
        LocalValidatorFactoryBean localValidatorFactoryBean = new LocalValidatorFactoryBean();
        localValidatorFactoryBean.setValidationMessageSource(messageSource());
        return localValidatorFactoryBean;
    }

    @Override
    public Validator getValidator() {  //（※３）
        return validator();
    }
}