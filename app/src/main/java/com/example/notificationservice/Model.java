package com.example.notificationservice;

public class Model {

    private String title, text, pack, ticker, actions;

    public Model(String title, String text, String pack, String ticker, String actions) {
        this.title = title;
        this.text = text;
        this.pack = pack;
        this.ticker = ticker;
        this.actions = actions;
        this.longView = false;
    }

    boolean longView;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getPack() {
        return pack;
    }

    public void setPack(String pack) {
        this.pack = pack;
    }

    public String getTicker() {
        return ticker;
    }

    public void setTicker(String ticker) {
        this.ticker = ticker;
    }

    public boolean getLongView() {
        return longView;
    }

    public void setLongView(boolean longView) {
        this.longView = longView;
    }

    public String getActions() {
        return actions;
    }

    public void setActions(String actions) {
        this.actions = actions;
    }
}
