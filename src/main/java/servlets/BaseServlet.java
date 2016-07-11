package servlets;

import server.Service;
import server.ServiceSingleton;

import javax.servlet.http.HttpServlet;

/**
 * Created by Vladyslav Usenko on 18.06.2016.
 */
public abstract class BaseServlet extends HttpServlet{
    protected final Service service = ServiceSingleton.INSTANCE.getService();
}
