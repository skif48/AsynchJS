package servlets;

import tools.ServletTools;
import tools.Tools;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.UUID;
import java.util.logging.FileHandler;
import java.util.logging.Logger;

/**
 * Created by Vladyslav Usenko on 18.06.2016.
 */
public class GetServlet extends BaseServlet{
    private static final Logger LOGGER = Logger.getLogger(GetServlet.class.getName());
    private static final String LOG_FILE = "E:/[AsynchJS]GetServletLOG.log";

    @Override
    public void init() throws ServletException {
        try {
            Tools.loggerInit(new FileHandler(), LOG_FILE, LOGGER);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        LOGGER.info("ServiceSingleton:" + this.service.toString());

        LOGGER.info("GET method invoked");
        String URI = ServletTools.getFullURL(req);
        LOGGER.info(URI);
        HashMap<String, String> paramValHashMap = (HashMap<String, String>) ServletTools.getParamValueMap(URI);
        LOGGER.info("HashMap: " + paramValHashMap.toString());
        UUID uuid = UUID.fromString(paramValHashMap.get("uuid"));
        LOGGER.info("before getting value: " + this.service.getRepository().getRepository().toString());
        LOGGER.info(this.service.getTaskInfo(uuid).toString());
        PrintWriter printWriter = resp.getWriter();
        printWriter.println(this.service.getTaskInfo(uuid).toString());
    }
}
