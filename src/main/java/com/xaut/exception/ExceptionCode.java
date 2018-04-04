package com.xaut.exception;

/**
 * 封装API异常码
 * <p>
 * Created by cvter on 2017/5/17.
 */
public enum ExceptionCode {

    EX_400(400, "用户请求的格式不正确"),
    EX_403(403, "禁止访问"),
    EX_404(404, "请求的资源不存在"),
    EX_405(405, "该http方法不被允许"),
    EX_500(500, "服务器异常"),

    //应该添加具体的业务异常
    EX_10000(10000, "业务异常"),
    EX_10001(10001, "参数异常"),
    EX_10002(10002, "Unknown异常"),

    /**
     * 普通用户异常
     */
    EX_20001(20001, "权限异常"),//管理员帐号不能从普通用户登录界面登录
    EX_20002(20002, "当前访问量过大,请您在试一下"),//购买时未获得锁
    EX_200003(200003, "添加数量不能小于1"),//用户添加购物车商品数量小于1
    EX_20003(20003, "重复抢购"),
    EX_20004(20004, "抢购未开始"),
    EX_20005(20005, "抢购关闭"),
    EX_20006(20006, "该商品没有抢购"),

    EX_20007(20007, "已抢完"),

    EX_20008(20008, "用户名非法"),
    EX_20009(20009, "密码非法"),
    EX_20010(20010, "数量只能为数字"),
    EX_20011(20011, "您的比赛类型与场地类型不否"),
    EX_20012(20012, "该时间段场地已经被占用"),

    /**
     * 管理员异常
     */
    EX_30001(30001, "权限异常");//管理员帐号不能从普通用户登录界面登录

    private int code;
    private String message;

    /**
     * 构造方法，默认private
     *
     * @param code    状态码
     * @param message 状态码描述
     */
    ExceptionCode(int code, String message) {
        this.code=code;
        this.message=message;
    }

    /**
     * 获得状态码
     *
     * @return
     */
    public int getCode() {
        return code;
    }

    /**
     * 获得状态码描述
     *
     * @return
     */
    public String getMessage() {
        return message;
    }
}
