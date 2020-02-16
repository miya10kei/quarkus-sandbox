package com.miya10kei.interfaces;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Objects;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/AjaxHandler")
public class AjaxHandler extends HttpServlet {

  private static final String KEY = "CUSTOMER_SERVICE";

  protected void doGet(HttpServletRequest request, HttpServletResponse response)
      throws IOException {
    var endpoint =
        Objects.nonNull(System.getenv(KEY)) ? System.getenv(KEY) : "ws://localhost:8080/customers";
    PrintWriter out = response.getWriter();
    out.println(endpoint);
    out.flush();
  }

  protected void doPost(HttpServletRequest request, HttpServletResponse response)
      throws IOException {
    doGet(request, response);
  }
}
