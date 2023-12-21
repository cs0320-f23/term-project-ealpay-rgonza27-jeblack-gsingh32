import { test, expect, defineConfig } from "@playwright/test";
import { TEXT_input_box } from "../src/components/constants";
export default defineConfig({
  timeout: 5 * 100 * 1000,
});

test.beforeEach(async ({ page }) => {
  await page.goto("http://localhost:8000/");
});

test.afterEach(async ({ page }) => {
  await page.close();
});
