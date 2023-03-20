package com.fhao.domain.exception;

/**
 * author: FHao
 * create time: 2023-03-19 12:30
 * description:条件异常
 */
public class ConditionException extends RuntimeException{
    private static final long serialVersionUid = 1L;
    private String code;
    public ConditionException(String code,String name){
        super(name);
        this.code = code;

    }
    public ConditionException(String name){
        super(name);
        this.code = "500";
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
