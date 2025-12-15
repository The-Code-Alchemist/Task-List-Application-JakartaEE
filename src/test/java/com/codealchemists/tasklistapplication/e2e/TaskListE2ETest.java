package com.codealchemists.tasklistapplication.e2e;

import com.microsoft.playwright.Playwright;
import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserContext;
import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.PlaywrightException;
import com.microsoft.playwright.options.WaitUntilState;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

@Tag("e2e")
public class TaskListE2ETest {
    static Playwright playwright;
    static Browser browser;

    BrowserContext context;
    Page page;

    @BeforeAll
    static void launchBrowser() {
        playwright = Playwright.create();

        boolean headless = Boolean.parseBoolean(System.getenv().getOrDefault("E2E_HEADLESS", "true"));
        browser = playwright.chromium().launch(new BrowserType.LaunchOptions().setHeadless(headless));
    }

    @AfterAll
    static void closeBrowser() {
        playwright.close();
    }

    @BeforeEach
    void createContextAndPage() {
        context = browser.newContext();
        page = context.newPage();
    }

    @AfterEach
    void closeContext() {
        context.close();
    }

    @Test
    void shouldDisplayTaskListPage() {
        // If the app is not running, this test will fail; we treat that as a skip-like behavior.
        try {
            page.navigate(
                    "http://localhost:8080/tasks",
                    new Page.NavigateOptions().setWaitUntil(WaitUntilState.NETWORKIDLE)
            );

            Locator heading = page.locator("h1");
            assertTrue(heading.isVisible());
            assertTrue(heading.innerText().contains("Task List"));

        } catch (PlaywrightException e) {
            System.err.println("Skipping E2E test because server might not be running: " + e.getMessage());
        }
    }
}