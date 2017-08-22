package org.oagi.srt.uat.testcase.phase14;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.oagi.srt.uat.testcase.CreateAccountInputs;
import org.oagi.srt.uat.testcase.CreateEnterpriseInputs;
import org.oagi.srt.uat.testcase.UserRole;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Random;

import static junit.framework.TestCase.*;
import static org.oagi.srt.uat.testcase.TestCaseHelper.*;
import static org.oagi.srt.uat.testcase.phase13.TestCase13_Helper.createContextCategory;
import static org.oagi.srt.uat.testcase.phase13.TestCase13_Helper.searchContextCategoryByName;
import static org.oagi.srt.uat.testcase.phase2.TestCase2_Helper.createEnterpriseAccount;
import static org.oagi.srt.uat.testcase.phase3.TestCase3_Helper.createEnterprise;
import static org.oagi.srt.uat.testcase.phase5.TestCase5_Helper.createAccountByEnterpriseAdmin;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TestCase14_1_6 {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private WebDriver webDriver;

    @Autowired
    private Random random;

    private CreateEnterpriseInputs enterprise;
    private CreateAccountInputs enterpriseAdmin_1, enterpriseAdmin_2;
    private CreateAccountInputs enterpriseEndUser;

    @Before
    public void setUp() {
        enterprise = createEnterprise(webDriver, random, CreateAccountInputs.OAGI_ADMIN);

        enterpriseAdmin_1 = CreateAccountInputs.generateRandomly(random);
        createEnterpriseAccount(webDriver, enterpriseAdmin_1, enterprise, UserRole.AdminUser);

        enterpriseAdmin_2 = CreateAccountInputs.generateRandomly(random);
        createEnterpriseAccount(webDriver, enterpriseAdmin_2, enterprise, UserRole.AdminUser);

        logout(webDriver);
        login(webDriver, enterpriseAdmin_1);

        enterpriseEndUser = CreateAccountInputs.generateRandomly(random);
        enterpriseEndUser.setAddress(null);
        createAccountByEnterpriseAdmin(webDriver, enterpriseEndUser, UserRole.EndUser);

        logout(webDriver);
    }

    @After
    public void tearDown() {
        webDriver.close();
    }

    @Test
    public void testAdminUserCanSeeAllContextCategories() {
        login(webDriver, enterpriseAdmin_1);
        String ctxCatName_1 = createContextCategory(webDriver, random);
        assertNotNull(searchContextCategoryByName(webDriver, ctxCatName_1));

        String ctxCatName_2 = createContextCategory(webDriver, random);
        assertNotNull(searchContextCategoryByName(webDriver, ctxCatName_2));

        logout(webDriver);
        login(webDriver, enterpriseEndUser);
        String ctxCatName_3 = createContextCategory(webDriver, random);
        assertNotNull(searchContextCategoryByName(webDriver, ctxCatName_3));

        String ctxCatName_4 = createContextCategory(webDriver, random);
        assertNotNull(searchContextCategoryByName(webDriver, ctxCatName_4));

        logout(webDriver);
        login(webDriver, enterpriseAdmin_2);

        assertNotNull(searchContextCategoryByName(webDriver, ctxCatName_1));
        assertNotNull(searchContextCategoryByName(webDriver, ctxCatName_2));
        assertNotNull(searchContextCategoryByName(webDriver, ctxCatName_3));
        assertNotNull(searchContextCategoryByName(webDriver, ctxCatName_4));
    }

}