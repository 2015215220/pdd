package com.chzu.txgc.pdd.Bean;

public class StoryBean {
    /**
     * content : 昨天下班坐公交车回家，白天上班坐着坐多了想站一会儿，
     就把座位让给了一个阿姨，阿姨道谢一番开始和我聊天，聊了挺多的。
     后来我要下车了，阿姨热情的和我道别。
     下车的一瞬间我回头看了一眼，只见那阿姨对着手机说：“儿子，
     刚才遇见一个姑娘特不错，可惜长得不好看，不然我肯定帮你要号码！”
     靠，阿姨你下车，我保证不打死你！
     * hashId : 348e7f933774bcaef3ed3f0ecb8e7b19
     * unixtime : 1418819032
     * updatetime : 2014-12-17 20:23:52
     */
    private String content;
    private String hashId;
    private int unixtime;
    private String updatetime;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getHashId() {
        return hashId;
    }

    public void setHashId(String hashId) {
        this.hashId = hashId;
    }

    public int getUnixtime() {
        return unixtime;
    }

    public void setUnixtime(int unixtime) {
        this.unixtime = unixtime;
    }

    public String getUpdatetime() {
        return updatetime;
    }

    public void setUpdatetime(String updatetime) {
        this.updatetime = updatetime;
    }
}
