package com.shaff.carshop.web.servlets;

import com.shaff.carshop.constants.ContextAttributes;
import com.shaff.carshop.constants.LoggerMessages;
import com.shaff.carshop.constants.RequestParameters;
import com.shaff.carshop.constants.ServletPath;
import com.shaff.carshop.constants.ViewPath;
import com.shaff.carshop.converters.RequestToSearchFormBeanConverter;
import com.shaff.carshop.db.beans.SearchFormBean;
import com.shaff.carshop.db.entity.Car;
import com.shaff.carshop.exceptions.ApplicationException;
import com.shaff.carshop.services.CarService;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet(ServletPath.SEARCH)
public class ParametrizedSearchServlet extends HttpServlet {
    private static final Logger LOG = Logger.getLogger(ParametrizedSearchServlet.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        SearchFormBean bean = getRequestToSearchFormBeanConverter().convert(req);
        CarService carService = getCarService();
        List<Car> cars;
        long rows;
        try {
            cars = carService.getAllSpecificCars(bean);
            rows = carService.getNumberOfRows(bean);
        } catch (ApplicationException e) {
            LOG.error(LoggerMessages.CANNOT_MAKE_OPERATION, e);
            resp.sendRedirect(ServletPath.HOME);
            return;
        }

        req.setAttribute(RequestParameters.SEARCH_BEAN, bean);
        req.setAttribute(RequestParameters.TOTAL_ITEM_COUNT, rows);
        req.setAttribute(RequestParameters.CARS, cars);
        req.setAttribute(RequestParameters.NUMBER_OF_PAGES, calculateNumberOfPages(rows, bean.getNumberOfElements()));
        req.setAttribute(RequestParameters.CURRENT_PAGE, bean.getCurrentPage());
        req.setAttribute(RequestParameters.NUMBER_OF_ELEMENTS, bean.getNumberOfElements());

        req.getRequestDispatcher(ViewPath.HOME_PAGE).forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doPost(req, resp);
    }

    private long calculateNumberOfPages(long rows, int numberOfElements) {
        long numberOfPages = rows / numberOfElements;
        if (numberOfPages % numberOfElements > 0) {
            numberOfPages++;
        }
        return numberOfPages;
    }

    private RequestToSearchFormBeanConverter getRequestToSearchFormBeanConverter() {
        return (RequestToSearchFormBeanConverter) getServletContext().getAttribute(ContextAttributes.REQUEST_TO_SEARCH_FORM_BEAN_CONVERTER);
    }

    private CarService getCarService() {
        return (CarService) getServletContext().getAttribute(ContextAttributes.CAR_SERVICE);
    }
}