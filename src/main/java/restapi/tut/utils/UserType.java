package restapi.tut.utils;

import restapi.tut.entity.User;

/**
 * Created by root on 9/19/16.
 */
public enum UserType {
    BASIC(3),SILVER(5),GOLD(7);
    private int val;
    UserType(int value){
        this.val = value;
    }

    public Integer getVal(){
        for(UserType userType:UserType.values()){
            if(userType.equals(this)){
                return userType.val;
            }
        }
        return 0;
    }

    public static UserType getUserType(String usertype){
        if(usertype.equalsIgnoreCase("BASIC")){
            return UserType.BASIC;
        }else if(usertype.equalsIgnoreCase("SILVER")){
            return UserType.SILVER;
        }else if(usertype.equalsIgnoreCase("GOLD")){
            return UserType.GOLD;
        }else{
            System.out.println("returning null");
            return null;
        }
    }
}
