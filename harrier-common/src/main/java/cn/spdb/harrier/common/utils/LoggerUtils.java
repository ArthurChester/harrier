

package cn.spdb.harrier.common.utils;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.spdb.harrier.common.Constants;

/**
 * logger utils
 */
public class LoggerUtils {

    private LoggerUtils() {
        throw new UnsupportedOperationException("Construct LoggerUtils");
    }

    private static final Logger logger = LoggerFactory.getLogger(LoggerUtils.class);

    /**
     * rules for extracting application ID
     */
    private static final Pattern APPLICATION_REGEX = Pattern.compile(Constants.APPLICATION_REGEX);

    /**
     * Task Logger's prefix
     */
    public static final String TASK_STEP_LOGGER_PREFIX = "[StepId=";


    /**
     * Task Logger Thread's name
     */
    public static final String TASK_APPID_LOG_FORMAT = "[Task=";

    /**
     * build job id
     *
     * @param affix Task Logger's prefix
     * @param processInstId process instance id
     * @param taskId task id
     * @return task id format
     */
    public static String buildTaskId(String affix,
                                     Long processDefineCode,
                                     int processDefineVersion,
                                     int processInstId,
                                     int taskId) {
        // - [taskAppId=TASK-798_1-4084-15210]
        return String.format(" - %s%s-%s_%s-%s-%s]", TASK_APPID_LOG_FORMAT, affix, processDefineCode, processDefineVersion, processInstId, taskId);
    }

    /**
     * processing log
     * get yarn application id list
     *
     * @param log log content
     * @param logger logger
     * @return app id list
     */
    public static List<String> getAppIds(String log, Logger logger) {

        List<String> appIds = new ArrayList<>();

        Matcher matcher = APPLICATION_REGEX.matcher(log);

        // analyse logs to get all submit yarn application id
        while (matcher.find()) {
            String appId = matcher.group();
            if (!appIds.contains(appId)) {
                logger.info("find app id: {}", appId);
                appIds.add(appId);
            }
        }
        return appIds;
    }

    /**
     * read whole file content
     *
     * @param filePath file path
     * @return whole file content
     */
    public static String readWholeFileContent(String filePath) {
        String line;
        StringBuilder sb = new StringBuilder();
        try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(filePath)))) {
            while ((line = br.readLine()) != null) {
                sb.append(line + "\r\n");
            }
            return sb.toString();
        } catch (IOException e) {
            logger.error("read file error", e);
        }
        return "";
    }

    public static void logError(Optional<Logger> optionalLogger
            , String error) {
        optionalLogger.ifPresent((Logger logger) -> logger.error(error));
    }

    public static void logError(Optional<Logger> optionalLogger
            , Throwable e) {
        optionalLogger.ifPresent((Logger logger) -> logger.error(e.getMessage(), e));
    }

    public static void logError(Optional<Logger> optionalLogger
            , String error, Throwable e) {
        optionalLogger.ifPresent((Logger logger) -> logger.error(error, e));
    }

    public static void logInfo(Optional<Logger> optionalLogger
            , String info) {
        optionalLogger.ifPresent((Logger logger) -> logger.info(info));
    }

}