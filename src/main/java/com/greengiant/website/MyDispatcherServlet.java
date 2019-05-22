package com.greengiant.website;

import org.springframework.web.servlet.DispatcherServlet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Deprecated
public class MyDispatcherServlet extends DispatcherServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = 4019979815426599200L;

	@Override
	protected void noHandlerFound(HttpServletRequest request,
			HttpServletResponse response) throws Exception {		
		response.sendRedirect(request.getContextPath() + "/fnf");
	}
}
