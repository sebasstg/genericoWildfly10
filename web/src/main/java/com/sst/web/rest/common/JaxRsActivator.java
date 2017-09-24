
package com.sst.web.rest.common;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

/**
 * A class extending {@link Application} and annotated with @ApplicationPath is the Java EE 7 "no XML" approach to activating
 * JAX-RS.
 * 
 * <p>
 * Resources are served relative to the servlet path specified in the {@link ApplicationPath} annotation.
 * </p>
 */
@ApplicationPath("/api")
public class JaxRsActivator extends Application {

//	@Override
//	public Set<Class<?>> getClasses() {
//		final Set<Class<?>> classes = new HashSet<>();
//		 // register root resource
//	    classes.add(UsersRestService.class);
//	    return classes;
//	}
    /* class body intentionally left blank */
	
	
}