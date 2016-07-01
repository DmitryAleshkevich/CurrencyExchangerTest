package com.instinctools.init;
import com.instinctools.configs.CurrencyExchangerApplication;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;
/**
 * Created by aldm on 14.04.2016.
 */
 public class AppInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {

    @Override
    protected Class<?>[] getRootConfigClasses() {
        return new Class[] { CurrencyExchangerApplication.class, /*SecurityConfig.class*/ };
    }

    @Override
    protected Class<?>[] getServletConfigClasses() {
        return new Class[] { CurrencyExchangerApplication.class, /*SecurityConfig.class*/ };
    }

    @Override
    protected String[] getServletMappings() {
        return new String[] { "/" };
    }
 }
