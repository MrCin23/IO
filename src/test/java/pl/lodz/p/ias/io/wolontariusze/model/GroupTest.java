package pl.lodz.p.ias.io.wolontariusze.model;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import pl.lodz.p.ias.io.uwierzytelnianie.model.Role;
import pl.lodz.p.ias.io.uwierzytelnianie.model.Users;
import pl.lodz.p.ias.io.uwierzytelnianie.repositories.UserRepository;
import pl.lodz.p.ias.io.uwierzytelnianie.utils.UserRole;

import static org.junit.jupiter.api.Assertions.*;
import static pl.lodz.p.ias.io.uwierzytelnianie.utils.UserRole.WOLONTARIUSZ;

class GroupTest {



    @Test
    void add() {
        Role wolonariuszRola = new Role("WOLONTARIUSZ");
        Users testuser = new Users("kdusza","hash", wolonariuszRola ,"Krzysztof","Dusza");
        Users testuser2 = new Users("fpietrzycki","hash2", wolonariuszRola ,"Franciszek","Pietrzycki");
        Group testGroup = new Group("nazwa");
        testGroup.add(testuser);
        testGroup.add(testuser2);

        Assertions.assertTrue(testGroup.getMembers().contains(testuser));
        Assertions.assertTrue(testGroup.getMembers().contains(testuser2));
    }

    @Test
    void remove() {
        Role wolonariuszRola = new Role("WOLONTARIUSZ");
        Users testuser = new Users("kdusza","hash", wolonariuszRola ,"Krzysztof","Dusza");
        Users testuser2 = new Users("fpietrzycki","hash2", wolonariuszRola ,"Franciszek","Pietrzycki");
        Group testGroup = new Group("nazwa");
        testGroup.add(testuser);
        testGroup.add(testuser2);

        testGroup.remove(testuser2);

        Assertions.assertTrue(testGroup.getMembers().contains(testuser));
        Assertions.assertFalse(testGroup.getMembers().contains(testuser2));

    }
}