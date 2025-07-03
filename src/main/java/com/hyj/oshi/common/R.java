package com.hyj.oshi.common;


import lombok.Data;

@Data   //对接前端的R，不用管泛型，对接后端远程调用，需要泛型
public class R {
    private Integer code;
    private String msg;
    private Object data;//json响应出去，要类型有啥用
    public R() {
    }

    public R(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public R(Integer code, String msg, Object data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }
    public static R ok() {
        return new R(200, "success");
    }

    public static R ok(Object data) {
        return new R(200, "success", data);
    }
    public static R error(Object data) {
        return new R(500, "error", data);
    }

    public static R error(Integer code, String msg) {
        return new R(code, msg);
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}

