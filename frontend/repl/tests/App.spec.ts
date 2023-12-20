import { test, expect } from "@playwright/test";
import {
  TEXT_input_box,
  TEXT_try_button_accessible_name,
  TEXT_try_button_text,
} from "../src/components/constants";
import Header from "../src/components/HomePage/Header";
import Home from "../src/components/HomePage/Home";
import contactForm from "../src/components/HomePage/contactForm";
import Meiks from "../src/components/Routes/Meiks";

/**
  The general shapes of tests in Playwright Test are:
    1. Navigate to a URL
    2. Interact with the page
    3. Assert something about the page against your expectations
  Look for this pattern in the tests below!
 */

test.beforeEach(async ({ page }) => {
  await page.goto("http://localhost:8000/");
  await page.waitForTimeout(5000);
  await page
    .getByTestId("login-email-input")
    .fill("roberto_gonzales_matos@brown.edu");
  await page.getByTestId("login-password-input").fill("050806RaleX.");
  await page.getByTestId("login-button").click();
});

test.afterEach(async ({ page }) => {
  await page.close();
});

/**
 * Don't worry about the "async" yet. We'll cover it in more detail
 * for the next sprint. For now, just think about "await" as something
 * you put before parts of your test that might take time to run,
 * like any interaction with the page.
 */
test("title is as expected", async ({ page }) => {
  await expect(page).toHaveTitle("MeetYourMeiks");
});

test("header is as expected", async ({ page }) => {
  await expect(page.getByTestId("contact-link")).toBeVisible();
  await expect(page.getByTestId("about-link")).toBeVisible();
  await expect(page.getByTestId("join-link")).toBeVisible();
  await expect(page.getByTestId("profile-link-test")).toBeVisible();
  await expect(page.getByTestId("meiks-link-test")).toBeVisible();
});

test("Scroll to Contact section", async ({ page }) => {
  await page.waitForTimeout(8000);
  // Click on the link that should scroll to the Contact section
  await page.getByTestId("contact-link").click();

  // Wait for the scroll animation to complete
  await page.waitForTimeout(2000); // Adjust the timeout as needed

  await page.waitForSelector('[data-testid="name-label"]', {
    state: "visible",
  });

  await expect(page.getByTestId("name-label")).toBeVisible();
});

test("Scroll to About Us section", async ({ page }) => {
  await page.waitForTimeout(8000);
  // Click on the link that should scroll to the Contact section
  await page.getByTestId("about-link").click();

  // Wait for the scroll animation to complete
  await page.waitForTimeout(2000); // Adjust the timeout as needed

  await expect(page.getByTestId("about-us-test")).toBeVisible();
});

test("Scroll to Join Us section", async ({ page }) => {
  await page.waitForTimeout(8000);
  // Click on the link that should scroll to the Contact section
  await page.getByTestId("join-link").click();

  // Wait for the scroll animation to complete
  await page.waitForTimeout(2000); // Adjust the timeout as needed

  await expect(page.getByTestId("join-test")).toBeVisible();
});

test("Click on Meiks link", async ({ page }) => {
  // Click on the Meiks link in the header
  await page.waitForTimeout(8000);
  await page.getByTestId("meiks-link-test").click();

  // Wait for the navigation to complete

  // Check if the URL matches the expected path
  const currentUrl = page.url();
  await expect(currentUrl).toContain("/Meiks");
});

test("Click on Profile link", async ({ page }) => {
  // Click on the Profile link in the header
  await page.getByTestId("profile-link-test").click();

  // Wait for the navigation to complete

  // Check if the URL matches the expected path
  const currentUrl = page.url();
  await expect(currentUrl).toContain("/Profile");
});

test("In profile page I see location input bar", async ({ page }) => {
  // Click on the Location link in the header
  await page.getByTestId("profile-link-test").click();

  // Wait for the navigation to complete

  // Check if input bar visible
  await expect(page.getByTestId("profile-location-input")).toBeVisible();
});

test("In Meik page I see search bar", async ({ page }) => {
  // Click on the Location link in the header
  await page.getByTestId("meiks-link-test").click();

  // Wait for the navigation to complete

  // Check if input bar visible
  await expect(page.getByTestId("search-bar-test")).toBeVisible();
});
