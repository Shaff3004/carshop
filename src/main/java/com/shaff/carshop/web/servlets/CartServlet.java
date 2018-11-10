package com.shaff.carshop.web.servlets;

import com.shaff.carshop.constants.RequestParameters;
import com.shaff.carshop.constants.ServletPath;
import com.shaff.carshop.constants.ViewPath;
import com.shaff.carshop.db.entity.Cart;
import com.shaff.carshop.db.entity.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(ServletPath.CART)
public class CartServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Cart shoppingCart = (Cart) req.getSession().getAttribute(RequestParameters.CART);
        req.getSession().setAttribute(RequestParameters.CART_CONTENT, shoppingCart.getCart());
        getServletContext().getRequestDispatcher(ViewPath.CART_PAGE).forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User user = (User) req.getSession().getAttribute(RequestParameters.USER);
        if (user == null) {
            resp.sendRedirect(ServletPath.LOGIN_PAGE_REDIRECT);
            return;
        }
        resp.sendRedirect(ServletPath.ORDER_CONFIRMATION);
    }
}
