package org.oagi.srt.uat.testcase.phase14;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.oagi.srt.uat.testcase.CreateAccountInputs;
import org.oagi.srt.uat.testcase.CreateContextSchemeInputs;
import org.oagi.srt.uat.testcase.CreateEnterpriseInputs;
import org.oagi.srt.uat.testcase.UserRole;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Random;

import static org.oagi.srt.uat.testcase.TestCaseHelper.login;
import static org.oagi.srt.uat.testcase.TestCaseHelper.logout;
import static org.oagi.srt.uat.testcase.phase13.TestCase13_Helper.*;
import static org.oagi.srt.uat.testcase.phase2.TestCase2_Helper.createEnterpriseAccount;
import static org.oagi.srt.uat.testcase.phase3.TestCase3_Helper.createEnterprise;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TestCase14_8 {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private WebDriver webDriver;

    @Autowired
    private Random random;

    private CreateEnterpriseInputs enterprise;
    private CreateAccountInputs enterpriseAdmin;

    @Before
    public void setUp() {
        enterprise = createEnterprise(webDriver, random, CreateAccountInputs.OAGI_ADMIN);

        enterpriseAdmin = CreateAccountInputs.generateRandomly(random);
        createEnterpriseAccount(webDriver, enterpriseAdmin, enterprise, UserRole.AdminUser);

        logout(webDriver);
        login(webDriver, enterpriseAdmin);
    }

    @After
    public void tearDown() {
        webDriver.close();
    }

    @Test(expected = TimeoutException.class)
    public void testEnterpriseAdminUserCannotAddContextSchemeValueWithoutValue() {
        String ctxCatName = createContextCategory(webDriver, random);

        CreateContextSchemeInputs contextSchemeInputs = createContextScheme(webDriver, random, ctxCatName);

        CreateContextSchemeInputs updateContextSchemeInputs = CreateContextSchemeInputs.generateRandomly(random, ctxCatName);
        addContextSchemeValue(webDriver, contextSchemeInputs, null, updateContextSchemeInputs.getMeaning(), 1);

        click(searchContextSchemeByName(webDriver, contextSchemeInputs.getName()));

        WebDriverWait wait = new WebDriverWait(webDriver, 2L);
        wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("tbody > tr[data-ri='1'] > td input[data-p-label='Value']")));
    }

}
