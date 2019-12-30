package com.chzu.txgc.pdd.Bean;

public class AilipayBean {
    /**
     * code : 10000
     * msg : Success
     * app_id : 2016101300675595
     * auth_app_id : 2016101300675595
     * charset : utf-8
     * timestamp : 2019-12-27 13:21:41
     * out_trade_no : 122713213036583
     * total_amount : 3.00
     * trade_no : 2019122722001420991000134546
     * seller_id : 2088102179366241
     */
    private String code;//1000
    private String msg;//success
    private String app_id;//用户id
    private String auth_app_id;//验证应用程序id
    private String charset;//utf-8
    private String timestamp;//购买的时间
    private String out_trade_no;//商品订单号
    private String total_amount;//购买的金额
    private String trade_no;//交易编号
    private String seller_id;//商家id
    public String getCode() {
        return code;
    }
    public void setCode(String code) {
        this.code = code;
    }
    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getApp_id() {
        return app_id;
    }

    public void setApp_id(String app_id) {
        this.app_id = app_id;
    }

    public String getAuth_app_id() {
        return auth_app_id;
    }

    public void setAuth_app_id(String auth_app_id) {
        this.auth_app_id = auth_app_id;
    }

    public String getCharset() {
        return charset;
    }

    public void setCharset(String charset) {
        this.charset = charset;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getOut_trade_no() {
        return out_trade_no;
    }

    public void setOut_trade_no(String out_trade_no) {
        this.out_trade_no = out_trade_no;
    }

    public String getTotal_amount() {
        return total_amount;
    }

    public void setTotal_amount(String total_amount) {
        this.total_amount = total_amount;
    }

    public String getTrade_no() {
        return trade_no;
    }

    public void setTrade_no(String trade_no) {
        this.trade_no = trade_no;
    }

    public String getSeller_id() {
        return seller_id;
    }

    public void setSeller_id(String seller_id) {
        this.seller_id = seller_id;
    }
}
