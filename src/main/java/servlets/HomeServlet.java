package servlets;

import tools.ServletTools;
import tools.Tools;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
/**
 * Created by Vladyslav Usenko on 14.06.2016.
 */

public class HomeServlet extends BaseServlet {
    private static final Logger LOGGER = Logger.getLogger(HomeServlet.class.getName());
    private static final String LOG_FILE = "E:/[AsynchJS]HomeServletLOG.log";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Tools.loggerInit(new FileHandler(), LOG_FILE, LOGGER);

        LOGGER.info("GET method invoked");
        LOGGER.info("Service:" + this.service.toString());
        String URI = ServletTools.getFullURL(req);
        LOGGER.info(URI);
        HashMap<String, String> paramValHashMap = (HashMap<String, String>) ServletTools.getParamValueMap(URI);
        LOGGER.info("HashMap: " + paramValHashMap.toString());
        RequestDispatcher dispatcher = req.getRequestDispatcher("WEB-INF\\index.html");
        dispatcher.forward(req, resp);
        LOGGER.info("Current repo: " + this.service.getRepository().getRepository().toString());
    }
}
