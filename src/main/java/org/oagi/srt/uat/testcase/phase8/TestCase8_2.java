package org.oagi.srt.uat.testcase.phase8;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.oagi.srt.uat.testcase.CreateAccountInputs;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Random;

import static org.oagi.srt.uat.testcase.TestCaseHelper.*;
import static org.oagi.srt.uat.testcase.phase8.TestCase8_Helper.createAccount;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TestCase8_2 {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private WebDriver webDriver;

    @Autowired
    private Random random;

    @Before
    public void setUp() {
        index(webDriver);
    }

    @After
    public void tearDown() {
        webDriver.close();
    }

    @Test
    public void testCreateAccountWithAllInformation() {
        CreateAccountInputs createAccountInputs = CreateAccountInputs.generateRandomly(random);
        createAccount(webDriver, createAccountInputs);

        logout(webDriver);
        login(webDriver, createAccountInputs.getLoginId(), createAccountInputs.getPassword(), true);
    }


}
