package servlets;

import tools.ServletTools;
import tools.Tools;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Logger;

/**
 * Created by Vladyslav Usenko on 18.06.2016.
 */
public class DeleteServlet extends BaseServlet{
    private static final Logger LOGGER = Logger.getLogger(DeleteServlet.class.getName());
    private static final String LOG_FILE = "E:/[AsynchJS]DeleteServletLOG.log";

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

    }
}
