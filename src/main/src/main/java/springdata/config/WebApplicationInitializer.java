package springdata.config;

import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

public class WebApplicationInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {

    @Override
    protected Class<?>[] getRootConfigClasses() {
        //return new Class[] { SpringSecurityConfig.class };
         return null;
    }

    @Override
    protected Class<?>[] getServletConfigClasses() {
        //return null;
       return new Class[] { MainMvcConfig.class };
    }

    @Override
    protected String[] getServletMappings() {
        return new String[] { "/" };
    }

}