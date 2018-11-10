package com.shaff.carshop.web.servlets;

import com.shaff.carshop.constants.ContextAttributes;
import com.shaff.carshop.constants.RequestParameters;
import com.shaff.carshop.constants.ViewPath;
import com.shaff.carshop.db.beans.SearchFormBean;
import com.shaff.carshop.db.entity.Car;
import com.shaff.carshop.exceptions.ApplicationException;
import com.shaff.carshop.services.CarService;
import org.apache.commons.lang3.Range;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

public class HomeServlet extends HttpServlet {
    private static final Logger LOG = Logger.getLogger(HomeServlet.class);
    private static final int DEFAULT_CURRENT_PAGE_NUMBER = 1;
    private static final int DEFAULT_NUMBER_OF_ELEMENTS_VALUE = 5;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        CarService carService = getCarService();
        try {
            prepareHomePage(req, carService);
        } catch (ApplicationException ex) {
            LOG.error(ex.getMessage());
        }
        req.getRequestDispatcher(ViewPath.HOME_PAGE).forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }

    private void prepareHomePage(HttpServletRequest req, CarService carService) throws ApplicationException {
        Range<Integer> priceRange = carService.getPriceRange();
        Range<Integer> yearRange = carService.getYearRange();
        Range<Integer> speedRange = carService.getSpeedRange();

        SearchFormBean defaultBean = new SearchFormBean();
        defaultBean.setCurrentPage(DEFAULT_CURRENT_PAGE_NUMBER);
        defaultBean.setNumberOfElements(DEFAULT_NUMBER_OF_ELEMENTS_VALUE);
        List<Car> cars = carService.getAllSpecificCars(defaultBean);
        List<String> availableCarTypes = carService.getAvailableCarTypes();
        long rows = carService.getNumberOfRows(defaultBean);

        HttpSession session = req.getSession();
        session.setAttribute(RequestParameters.PRICE_RANGE, priceRange);
        session.setAttribute(RequestParameters.YEAR_RANGE, yearRange);
        session.setAttribute(RequestParameters.SPEED_RANGE, speedRange);
        session.setAttribute(RequestParameters.CAR_TYPES, availableCarTypes);
        req.setAttribute(RequestParameters.CARS, cars);
        req.setAttribute(RequestParameters.CURRENT_PAGE, defaultBean.getCurrentPage());
        req.setAttribute(RequestParameters.NUMBER_OF_ELEMENTS, defaultBean.getNumberOfElements());
        req.setAttribute(RequestParameters.NUMBER_OF_PAGES, calculateNumberOfPages(rows, defaultBean.getNumberOfElements()));
    }

    private CarService getCarService() {
        return (CarService) getServletContext().getAttribute(ContextAttributes.CAR_SERVICE);
    }

    private long calculateNumberOfPages(long rows, int numberOfElements) {
        long numberOfPages = rows / numberOfElements;
        if (numberOfPages % numberOfElements > 0) {
            numberOfPages++;
        }
        return numberOfPages;
    }
}