package org.oagi.srt.uat.testcase.phase8;

import org.apache.commons.lang3.StringUtils;
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

import static junit.framework.TestCase.assertTrue;
import static org.oagi.srt.uat.testcase.TestCaseHelper.*;
import static org.oagi.srt.uat.testcase.phase8.TestCase8_Helper.createAccount;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TestCase8_11 {

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
    public void testCreateAccountWithExistingUsername() {
        CreateAccountInputs createAccountInputs = CreateAccountInputs.generateRandomly(random);
        createAccountInputs.setAddress(null);
        createAccount(webDriver, createAccountInputs);

        logout(webDriver);
        index(webDriver);

        createAccount(webDriver, createAccountInputs);

        String errorMessage = getErrorMessage(webDriver);
        logger.info("Error Message: " + errorMessage);

        assertTrue(!StringUtils.isEmpty(errorMessage));
    }

    @Test
    public void testCreateAccountWithExistingUsernameButUpperCase() {
        CreateAccountInputs createAccountInputs = CreateAccountInputs.generateRandomly(random);
        createAccountInputs.setAddress(null);
        createAccount(webDriver, createAccountInputs);

        logout(webDriver);
        index(webDriver);

        createAccountInputs.setLoginId(createAccountInputs.getLoginId().toUpperCase());
        createAccount(webDriver, createAccountInputs);

        String errorMessage = getErrorMessage(webDriver);
        logger.info("Error Message: " + errorMessage);

        assertTrue(!StringUtils.isEmpty(errorMessage));
    }

}
