package com.driver;


import java.util.ArrayList;
import java.util.List;

@Deprecated
public class User {



    private String name;


    private String mobile;

    private List<String> gruopList=new ArrayList<>();

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public User(String name, String mobile) {
        this.name = name;
        this.mobile = mobile;
    }

    public List<String> getGruopList() {
        return gruopList;
    }

    public void setGruopList(List<String> gruopList) {
        this.gruopList = gruopList;
    }
}
