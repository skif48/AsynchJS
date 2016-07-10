package servlets;

import tools.ServletTools;
import tools.Tools;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.UUID;
import java.util.logging.FileHandler;
import java.util.logging.Logger;

/**
 * Created by Vladyslav Usenko on 18.06.2016.
 */
public class AddServlet extends BaseServlet {
    private static final Logger LOGGER = Logger.getLogger(AddServlet.class.getName());
    private static final String LOG_FILE = "E:/[AsynchJS]AddServletLOG.log";

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Tools.loggerInit(new FileHandler(), LOG_FILE, LOGGER);
        LOGGER.info("Service:" + this.service.toString());

        LOGGER.info("POST method invoked");
        String URI = ServletTools.getFullURL(req);
        HashMap<String, String> paramValHashMap = (HashMap<String, String>) ServletTools.getParamValueMap(URI);
        LOGGER.info("URL params: " + paramValHashMap.toString());
        String requestBody = ServletTools.getRequestBody(req);
        LOGGER.info(requestBody);
        UUID uuid = UUID.randomUUID();
        this.service.manageTask(requestBody, uuid, Integer.parseInt(paramValHashMap.get("timeout")));
        resp.setHeader("UUID", uuid.toString());
    }
}
