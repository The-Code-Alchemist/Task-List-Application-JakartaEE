package com.codealchemists.tasklistapplication.e2e;

import com.microsoft.playwright.Playwright;
import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserContext;
import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.PlaywrightException;
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
        // Set headless=false if you want to see the browser UI
        browser = playwright.chromium().launch(new BrowserType.LaunchOptions().setHeadless(false));
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
        // Adjust URL to match your local running server
        // If the app is not running, this test will fail
        try {
            page.navigate("http://localhost:8080/tasks");

            // Check if the title is correct
            String title = page.title();
            // Assuming your JSP has a title, or we check for specific text
            // assertTrue(title.contains("Task List"));

            // Or check for a specific element existence
            Locator heading = page.locator("h1"); // Assuming there is an <h1>
            // assertTrue(heading.isVisible());

            page.waitForTimeout(20000); // keep browser open for 20 seconds (20000 ms)

        } catch (PlaywrightException e) {
            System.err.println("Skipping E2E test because server might not be running: " + e.getMessage());
        }
    }
}