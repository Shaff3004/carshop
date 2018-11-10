package com.shaff.carshop.web.servlets;

import com.shaff.carshop.constants.ContextAttributes;
import com.shaff.carshop.constants.LoggerMessages;
import com.shaff.carshop.constants.RequestParameters;
import com.shaff.carshop.constants.ServletPath;
import com.shaff.carshop.constants.ViewPath;
import com.shaff.carshop.converters.CartToOrderConverter;
import com.shaff.carshop.db.entity.Cart;
import com.shaff.carshop.db.entity.Order;
import com.shaff.carshop.db.entity.User;
import com.shaff.carshop.exceptions.ApplicationException;
import com.shaff.carshop.services.OrderService;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(ServletPath.ORDER_CONFIRMATION)
public class ConfirmOrderServlet extends HttpServlet {
    private static final Logger LOG = Logger.getLogger(ConfirmOrderServlet.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher(ViewPath.CONFIRM_ORDER_PAGE).forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User user = (User) req.getSession().getAttribute(RequestParameters.USER);
        Cart shoppingCart = (Cart) req.getSession().getAttribute(RequestParameters.CART);

        Order order = getCartToOrderConverter().convert(shoppingCart);
        order.setUserId(user.getId());
        OrderService orderService = getOrderService();
        try {
            orderService.addNewOrder(order);
        } catch (ApplicationException ex) {
            LOG.error(LoggerMessages.CANNOT_MAKE_OPERATION);
        }

        req.getSession().removeAttribute(RequestParameters.CART);
        req.getRequestDispatcher(ViewPath.THANKS_PAGE).forward(req, resp);
    }

    private OrderService getOrderService() {
        return (OrderService) getServletContext().getAttribute(ContextAttributes.ORDER_SERVICE);
    }

    private CartToOrderConverter getCartToOrderConverter() {
        return (CartToOrderConverter) getServletContext().getAttribute(ContextAttributes.CART_TO_ORDER_BEAN_CONVERTER);
    }
}