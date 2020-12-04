package com.csaba.blog.spring_blog.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;

@Controller
public class ErrorController implements org.springframework.boot.web.servlet.error.ErrorController {

    @RequestMapping("/error")
    public String handleError(HttpServletRequest request, Model model) {

        Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
        String errorMessage = request.getAttribute(RequestDispatcher.ERROR_MESSAGE).toString();

        if (status != null) {
            Integer statusCode = Integer.parseInt(status.toString());
            model.addAttribute("code", statusCode);

            if (!errorMessage.isEmpty()) {
                model.addAttribute("code_message", errorMessage);
            } else {
                switch (statusCode) {
                    case 404: errorMessage = "Page not found";
                        break;
                    case 403: errorMessage = "Forbidden";
                        break;
                    default: errorMessage = "Error occurred";
                }
            }

            model.addAttribute("code_message", errorMessage);
        }

        return "errorpage";
    }

    @Override
    public String getErrorPath() {
        return null;
    }
}
