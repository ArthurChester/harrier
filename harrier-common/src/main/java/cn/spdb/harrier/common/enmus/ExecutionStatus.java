
package cn.spdb.harrier.common.enmus;

import java.util.HashMap;

public enum ExecutionStatus {

    /**
     * status：
     * 0 submit success
     * 1 running
     * 2 ready pause
     * 3 pause
     * 4 ready stop
     * 5 stop
     * 6 failure
     * 7 success
     * 8 need fault tolerance
     * 9 kill
     * 10 waiting thread
     * 11 waiting depend node complete
     * 12 delay execution
     * 13 forced success
     */
	READY(0, "ready"),
	PENDING(1,"pedning"),
	DISPATCHER(2, "DISPATCHER"),

	RUNING(3,"READY_RUNING"),
	STEP_RUNNING(5, "running"),
	FAILURE(6, "failure"),
	SUCCESS(7, "success"),
	EXCEPTION(10,"exception"),
	
    READY_STOP(8, "ready stop"),
    STOP(9, "stop"),
    KILL(10, "kill"),
    
    UNKNOWN(98,"unknown job status"),
    FORCED_SUCCESS(99, "forced success"), 
	;
    ExecutionStatus(int code, String descp) {
        this.code = code;
        this.descp = descp;
    }


    private final int code;
    private final String descp;

    private static HashMap<Integer, ExecutionStatus> EXECUTION_STATUS_MAP = new HashMap<>();

    static {
        for (ExecutionStatus executionStatus : ExecutionStatus.values()) {
            EXECUTION_STATUS_MAP.put(executionStatus.code, executionStatus);
        }
    }

    /**
     * status is success
     *
     * @return status
     */
    public boolean typeIsSuccess() {
        return this == SUCCESS || this == FORCED_SUCCESS;
    }
//
//    /**
//     * status is failure
//     *
//     * @return status
//     */
//    public boolean typeIsFailure() {
//        return this == FAILURE || this == NEED_FAULT_TOLERANCE;
//    }
//
//    /**
//     * status is finished
//     *
//     * @return status
//     */
//    public boolean typeIsFinished() {
//        return typeIsSuccess() || typeIsFailure() || typeIsCancel() || typeIsPause()
//                || typeIsStop();
//    }
//
//    /**
//     * status is waiting thread
//     *
//     * @return status
//     */
//    public boolean typeIsWaitingThread() {
//        return this == WAITING_THREAD;
//    }
//
//    /**
//     * status is pause
//     *
//     * @return status
//     */
//    public boolean typeIsPause() {
//        return this == PAUSE;
//    }
//
//    /**
//     * status is pause
//     *
//     * @return status
//     */
//    public boolean typeIsStop() {
//        return this == STOP;
//    }
//
//    /**
//     * status is running
//     *
//     * @return status
//     */
//    public boolean typeIsRunning() {
//        return this == RUNNING_EXECUTION || this == WAITING_DEPEND || this == DELAY_EXECUTION;
//    }

    /**
     * status is cancel
     *
     * @return status
     */
    public boolean typeIsCancel() {
        return this == KILL || this == STOP;
    }

    public int getCode() {
        return code;
    }

    public String getDescp() {
        return descp;
    }

    public static ExecutionStatus of(int status) {
        if (EXECUTION_STATUS_MAP.containsKey(status)) {
            return EXECUTION_STATUS_MAP.get(status);
        }
        throw new IllegalArgumentException("invalid status : " + status);
    }
}
