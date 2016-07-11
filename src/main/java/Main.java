import tools.ServletTools;

import java.util.Map;

/**
 * Created by Vladyslav Usenko on 17.06.2016.
 */
public class Main {
    public static void main(String[] args) {
        Map<String, String> map = ServletTools.getParamValueMap("localhost:8080/vlados/home?test=15&some=asd");
    }
}
