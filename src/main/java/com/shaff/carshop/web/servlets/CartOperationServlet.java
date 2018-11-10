package com.shaff.carshop.web.servlets;

import com.shaff.carshop.constants.ContextAttributes;
import com.shaff.carshop.constants.LoggerMessages;
import com.shaff.carshop.constants.RequestParameters;
import com.shaff.carshop.constants.ServletPath;
import com.shaff.carshop.db.entity.Car;
import com.shaff.carshop.db.entity.Cart;
import com.shaff.carshop.exceptions.ApplicationException;
import com.shaff.carshop.services.CarService;
import com.shaff.carshop.utils.parsers.RequestTextParser;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Map;

@WebServlet(ServletPath.CART_OPERATION)
public class CartOperationServlet extends HttpServlet {
    private static final Logger LOG = Logger.getLogger(CartOperationServlet.class);
    private CarService carService;
    private RequestTextParser requestParser;

    @Override
    public void init() throws ServletException {
        super.init();
        carService = getCarService();
        requestParser = new RequestTextParser();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) {
        HttpSession session = req.getSession();
        Cart shoppingCart = (Cart) session.getAttribute(RequestParameters.CART);
        if (shoppingCart == null) {
            shoppingCart = new Cart();
        }

        int carId = Integer.parseInt(req.getParameter(RequestParameters.CAR_ID));
        try {
            Car chosenCar = carService.getCarById(carId);
            shoppingCart.addItemToCart(chosenCar);
        } catch (ApplicationException ex) {
            LOG.error(LoggerMessages.CANNOT_MAKE_OPERATION, ex);
        }
        session.setAttribute(RequestParameters.CART, shoppingCart);
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        Cart shoppingCart = (Cart) session.getAttribute(RequestParameters.CART);

        Map<String, String> dataMap = null;
        try {
            dataMap = requestParser.getParameterMap(req);
        } catch (ApplicationException e) {
            LOG.error(LoggerMessages.CANNOT_MAKE_OPERATION, e);

        }
        int carId = Integer.parseInt(dataMap.get(RequestParameters.CAR_ID));
        try {
            Car chosenCar = carService.getCarById(carId);
            shoppingCart.reduceItemQuantity(chosenCar);
        } catch (ApplicationException ex) {
            LOG.error(LoggerMessages.CANNOT_MAKE_OPERATION, ex);
        }
        session.setAttribute(RequestParameters.CART, shoppingCart);
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) {
        HttpSession session = req.getSession();
        Cart shoppingCart = (Cart) session.getAttribute(RequestParameters.CART);
        try {
            Map<String, String> dataMap = requestParser.getParameterMap(req);
            int carId = Integer.parseInt(dataMap.get(RequestParameters.CAR_ID));
            Car chosenCar = carService.getCarById(carId);
            shoppingCart.removeItemFromCart(chosenCar);

        } catch (ApplicationException ex) {
            LOG.error(LoggerMessages.CANNOT_MAKE_OPERATION, ex);
        }
        session.setAttribute(RequestParameters.CART, shoppingCart);
    }

    private CarService getCarService() {
        return (CarService) getServletContext().getAttribute(ContextAttributes.CAR_SERVICE);
    }
}