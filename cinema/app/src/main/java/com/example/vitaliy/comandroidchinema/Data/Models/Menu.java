package com.example.vitaliy.comandroidchinema.Data.Models;

/**
 * Created by vitaliy on 11.06.2016.
 */
public class Menu {
    private String name;
    private String option;
    private int icon;

    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }

    public Menu(String name, String option) {
        this.name = name;
        this.option = option;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOption() {
        return option;
    }

    public void setOption(String option) {
        this.option = option;
    }
}
