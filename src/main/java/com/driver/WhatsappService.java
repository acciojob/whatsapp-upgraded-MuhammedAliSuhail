package com.driver;


import com.driver.Repositorys;
import org.apache.catalina.startup.ClassLoaderFactory;
import org.springframework.stereotype.Service;

import java.awt.*;
import java.net.UnknownServiceException;
import java.util.ArrayList;
import java.util.List;

@Service
public class  WhatsappService {

    Repositorys repositorys=new Repositorys();
    public String createUser(String userName,String mobile) throws Exception {
        if(repositorys.Userrepo.containsKey(mobile)){
            throw new Exception("User already exists");
        }
        repositorys.Userrepo.put(mobile,userName);

        return "SUCCESS";
    }


    public Group createGroup(List<User> users){
        if(users.size()==2){
            Group group=new Group(users.get(1).getName(),2);
            for(User user:users){
                List<String> grouplist=user.getGruopList();
                grouplist.add(group.getName());
            }
            repositorys.groups.put(group.getName(),group);
            repositorys.groupusers.put(group.getName(), users);

            return group;
        }


        Group group=new Group("Group"+repositorys.groupcount,users.size());
        for(User user:users){
            List<String> grouplist=user.getGruopList();
            grouplist.add(group.getName());
        }
        repositorys.groups.put(group.getName(),group);
        repositorys.groupusers.put(group.getName(), users);
        repositorys.groupcount++;

        return group;
    }
    public int createMessage(String contant){
       Message message=new Message();
       message.setId(repositorys.messageId);
       message.setContent(contant);
       repositorys.messageId++;
       repositorys.messages.put(message.getId(),message);

       return message.getId();
    }


    public int sendMessage(Message message, User sender, Group group) throws Exception {
        if(!repositorys.groups.containsKey(group.getName())){
            throw new Exception("Group does not exist");
        }
        List<User> userList=repositorys.groupusers.get(group.getName());
        for(User user:userList){
            if(sender.equals(user)){
                List<Message> messageList=repositorys.SendedmassagesInGroups.get(group.getName());
                message.setUserMobail(sender.getMobile());
                messageList.add(message);
                repositorys.SendedmassagesInGroups.put(group.getName(), messageList);
                List<Message> userMessages=repositorys.userMessages.get(user);
                userMessages.add(message);
                repositorys.userMessages.put(user,userMessages);
                return messageList.size();
            }
        }

        throw new Exception("You are not allowed to send message");
    }


    public String changeAdmin(User Approver,User newAdmi,Group group ) throws Exception {
        if(!repositorys.groups.containsKey(group.getName())){
            throw new Exception("Group does not exist");
        }

        List<User> userList=repositorys.groupusers.get(group.getName());

        if(!userList.get(0).equals(Approver)){
            throw new Exception("Approver does not have rights");
        }
       for(int i=1;i<userList.size();i++){
           if(newAdmi.equals(userList.get(i))){
               User temp=userList.get(0);
               userList.set(0,newAdmi);
               userList.set(i,temp);
               return "SUCCESS";
           }
       }
       throw new Exception("User is not a participant");
    }

    public int removeUser(User user) throws Exception {

        boolean flag=false;

        for(User user1:repositorys.userMessages.keySet()){
            if(user.getMobile().equals(user1.getMobile())){
                List<String> groups=user1.getGruopList();
                for(String grop:groups){
                        List<User> userList=repositorys.groupusers.get(grop);
                        if(userList.get(0).getMobile().equals(user.getMobile())){
                            throw new Exception("Cannot remove admin");
                        }
                        userList.remove(user1);
                        int count=repositorys.groups.get(grop).getNumberOfParticipants()-1;
                        repositorys.groups.get(grop).setNumberOfParticipants(count);

                        List<Message> messageList=repositorys.SendedmassagesInGroups.get(grop);

                        for(Message message:repositorys.userMessages.get(user1)){
                            if(messageList.contains(message)){
                                messageList.remove(message);
                            }
                        }
                        repositorys.SendedmassagesInGroups.put(grop,messageList);
                        int countAll=0;
                        for(String gr:repositorys.SendedmassagesInGroups.keySet()){
                            count+=repositorys.SendedmassagesInGroups.get(gr).size();
                        }


                    repositorys.userMessages.remove(user);
                    return repositorys.groups.get(grop).getNumberOfParticipants()+repositorys.SendedmassagesInGroups.get(grop).size()+countAll;

                }
            }
        }

     throw new Exception("User not found");


    }
}
