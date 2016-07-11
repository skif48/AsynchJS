package engine;

import server.Task;
import tools.Tools;

import javax.script.Compilable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.concurrent.Callable;
import java.util.logging.FileHandler;
import java.util.logging.Logger;

/**
 * Created by Vladyslav Usenko on 29.06.2016.
 */
public class JSInterpreter implements Callable<TaskTransferData>{
    private static final Logger LOGGER = Logger.getLogger(JSInterpreter.class.getName());
    private static final String LOG_FILE = "E:/[AsynchJS]JSInterpreter.log";
    public static final ScriptEngineManager SCRIPT_ENGINE_MANAGER = new ScriptEngineManager();
    private String javascript;
    private Task task;

    public JSInterpreter(Task task) {
        this.task = task;
        this.javascript = this.task.getJavascript();
        try {
            Tools.loggerInit(new FileHandler(), LOG_FILE, LOGGER);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public TaskTransferData call() throws Exception {
        TaskTransferData taskTransferData = new TaskTransferData("", task.getUuid(), null, Task.Status.RUNNING);
        try {
            LOGGER.info("task: " + task.getUuid()+ "; interpreter started");
            ScriptEngine engine = getScriptEngine();
            StringWriter sw = new StringWriter();
            engine.getContext().setWriter(sw);
            engine.eval(new StringReader(javascript));
            taskTransferData = new TaskTransferData(sw.toString(), task.getUuid(), null, Task.Status.COMPLETED);
        } catch (Exception e) {
            taskTransferData.setException(e);
        }
        LOGGER.info("task: " + task.getUuid()+ "; interpreter finished");
        LOGGER.info(taskTransferData.toString());
        return taskTransferData;
    }

    public static void preCompileJS(String javascript) throws ScriptException {
        ScriptEngine engine = getScriptEngine();
        Compilable compEngine = (Compilable) engine;
        compEngine.compile(javascript);
    }

    private static ScriptEngine getScriptEngine() {
        return SCRIPT_ENGINE_MANAGER.getEngineByName("nashorn");
    }
}
