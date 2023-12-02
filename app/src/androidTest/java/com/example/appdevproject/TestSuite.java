package com.example.appdevproject;

import com.example.appdevproject.Pages.Budget;
import com.example.appdevproject.Pages.EditUserTest;
import com.example.appdevproject.Pages.InvestTests;
import com.example.appdevproject.Pages.RepayTest;
import com.example.appdevproject.Pages.TaxTest;
import com.example.appdevproject.RegistrationAndNav.NavigationTest;
import com.example.appdevproject.RegistrationAndNav.RegistrationTests;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        RegistrationTests.class,
        NavigationTest.class,
        Budget.class, //bug in testing, adding item to housing category
        InvestTests.class,
        RepayTest.class,
        TaxTest.class,
        EditUserTest.class,
})

public class TestSuite {
}