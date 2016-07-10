package tools;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Vladyslav Usenko on 17.06.2016.
 */
public class ServletTools {
    public static Map<String, String> getParamValueMap(final String URL){
        Map<String, String> paramValueMap = new HashMap<String, String>();
        boolean repeat = false;
        for (int i = 0; i < URL.length(); i++) {
            if(URL.charAt(i) == '?' || repeat){
                StringBuilder sb = new StringBuilder();
                String param = "";
                String val = "";
                int j = i;
                if(URL.charAt(j) == '?')
                    j++;
                while(URL.charAt(j) != '='){
                    sb.append(URL.charAt(j));
                    j++;
                }

                param = sb.toString();
                sb.delete(0, param.length());

                j++;
                while(true){
                    if(j >= URL.length())
                        break;
                    if(URL.charAt(j) == '&') {
                        repeat = true;
                        break;
                    }
                    if(URL.charAt(j) == '/')
                        break;

                    sb.append(URL.charAt(j));
                    j++;
                }
                val = sb.toString();
                i = j;
                paramValueMap.put(param, val);
            }
        }

        return paramValueMap;
    }

    public static String getFullURL(HttpServletRequest request) {
        StringBuffer requestURL = request.getRequestURL();
        String queryString = request.getQueryString();

        if (queryString == null) {
            return requestURL.toString();
        } else {
            return requestURL.append('?').append(queryString).toString();
        }
    }

    public static Integer parseInteger(final String string) {

        try {
            return Integer.valueOf(string);
        } catch (Exception exception) {
            return null;
        }
    }

    public static String getRequestBody(HttpServletRequest req) throws IOException {
        BufferedReader reader = req.getReader();
        char[] buf = new char[4 * 1024];
        int len;
        StringBuilder requestBody = new StringBuilder();
        while ((len = reader.read(buf, 0, buf.length)) != -1) {
            requestBody.append(buf, 0, len);
        }

        return requestBody.toString();
    }
}
