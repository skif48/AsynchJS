package servlets;

import tools.Tools;

import javax.servlet.ServletException;
import java.io.IOException;
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
}
