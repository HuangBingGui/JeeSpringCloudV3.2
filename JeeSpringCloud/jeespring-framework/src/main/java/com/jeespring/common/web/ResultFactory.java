package com.jeespring.common.web;

public class ResultFactory {

    private ResultFactory() {
    }

    protected static final String SuccessCode = "0";
    protected static final String ErrorCode = "-1";

    public static Result getSuccessResult() {
        return getResultBean(SuccessCode, "成功");
    }

    public static Result getSuccessResult(String resultMessage) {
        return getResultBean(SuccessCode, resultMessage);
    }

    public static Result getErrorResult(String resultMessage) {
        return getResultBean(ErrorCode, resultMessage);
    }

    private static Result getResultBean(String resultCode, String resultMessage) {

        Result result = ResultBean.result.getResult();

        result.put("CODE", resultCode);
        result.put("MESSAGE", resultMessage);
        return result;
    }

    public static Result getResultBean(String resultMessage, Object o) {

        Result result = ResultBean.result.getResult();

        result.put("CODE", SuccessCode);
        result.put("MESSAGE", resultMessage);
        result.put("Object", o);
        return result;
    }

    private enum ResultBean {
        result;
        Result getResult() {
            return new Result();
        }
    }
}
