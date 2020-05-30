package test;

import orders.controller.queries.UserController;
import orders.controller.utils.Hasher;
import orders.model.entities.User;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class UserControllerTest {

    @Test
    public void getByIDTest() {
        User userExpected = new User(1, "defaultUser",
                "d8578edf8458ce06fbc5bb76a58c5ca4",
                "I'm a default user, I just watch people",
                "images/avatars/icon1.png", false);

        User userActual = UserController.getByID(1);

        assertEquals(userExpected,userActual);
    }

    @Test
    public void editProfileTest(){
        User beforeEdit = UserController.getByID(2);
        User user = new User(2,"anotherUser",
                "e10adc3949ba59abbe56e057f20f883e",
                "shorterTextForUpdate",
                "images/avatars/icon2.png",false);

        UserController.editProfile(user);
        User  afterEdit = UserController.getByID(2);

        assertNotEquals(afterEdit,beforeEdit);
        assertEquals(afterEdit, user);
    }

    @Test
    public void registerTest(){
        User expected = new User(0,"test user", Hasher.getMd5("test"), "test","images/avatars/default.png",true);
        UserController.register("test user",Hasher.getMd5("test"),"test",true);
        ArrayList<User> users = UserController.getAll();
        User actual = new User(0,"0","0","0","0",true);
        for (User a :users) {
            if (a.getLogin().equals("test user")){
                actual = a;
            }
        }

        assertEquals(expected,actual);
    }
}
