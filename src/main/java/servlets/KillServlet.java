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
public class KillServlet extends BaseServlet {
    private static final Logger LOGGER = Logger.getLogger(HomeServlet.class.getName());
    private static final String LOG_FILE = "E:/[AsynchJS]KillServletLOG.log";

    @Override
    public void init() throws ServletException {
        try {
            Tools.loggerInit(new FileHandler(), LOG_FILE, LOGGER);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        LOGGER.info("delete method invoked");
        String URI = ServletTools.getFullURL(req);
        HashMap<String, String> paramValHashMap = (HashMap<String, String>) ServletTools.getParamValueMap(URI);
        UUID uuid = UUID.fromString(paramValHashMap.get("uuid"));
        this.service.killTask(uuid);
    }
}
