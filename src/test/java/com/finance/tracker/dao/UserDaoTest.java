package com.finance.tracker.dao;

import com.finance.tracker.model.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
public class UserDaoTest {
    @Autowired
    private UserDao userDao;

    @Test
    public void testFindUser() {
        User u = userDao.findByUsername("testuser");
        assertNotNull(u);
    }
}
