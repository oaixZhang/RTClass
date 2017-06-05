package com.xiao.rtclassteacher.bean;

import java.io.Serializable;

public class QuestionBean implements Serializable {
    private int questionid;
    private int type;
    private String content;
    private String answer;
    private String symbol;
    private String typeStr;
    private String stuAnswer;


    public QuestionBean(int questionid, int type, String content, String answer, String symbol) {
        this.questionid = questionid;
        setType(type);
        this.content = content;
        this.answer = answer;
        this.symbol = symbol;
    }

    public QuestionBean() {
    }

    public String getStuAnswer() {
        return stuAnswer;
    }

    public void setStuAnswer(String stuAnswer) {
        this.stuAnswer = stuAnswer;
    }

    public int getQuestionid() {
        return questionid;
    }

    public void setQuestionid(int questionid) {
        this.questionid = questionid;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
        switch (type) {
            case 0:
                setTypeStr("选择");
                break;
            case 1:
                setTypeStr("填空");
                break;
            case 2:
                setTypeStr("简答");
                break;
        }
    }

    public String getTypeStr() {
        return typeStr;
    }

    public void setTypeStr(String typeStr) {
        this.typeStr = typeStr;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }
}
