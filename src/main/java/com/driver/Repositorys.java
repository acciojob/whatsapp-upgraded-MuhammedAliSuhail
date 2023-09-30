package com.driver;

import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;


@Repository
public class Repositorys {


    public Integer groupcount;
    public Integer messageId;

    public Repositorys() {
        groupcount=1;
        messageId=1;
    }

    public HashMap<String,String> Userrepo=new HashMap<>();


    public HashMap<Integer,Message> messages=new HashMap<>();

    public HashMap<String,Group> groups=new HashMap<>();

    public HashMap<String , List<User>> groupusers=new HashMap<>();


    public HashMap<String,List<Message>> SendedmassagesInGroups=new HashMap<>();

    public HashMap<User,List<Message>> userMessages=new HashMap<>();
}
