import { test, expect } from "@playwright/test";
import {
  TEXT_input_box,
  TEXT_try_button_accessible_name,
  TEXT_try_button_text,
} from "../src/components/constants";
//import "../src/components/data/mockedJson";

test.beforeEach(async ({ page }) => {
  await page.goto("http://localhost:8000/");
  await page.keyboard.press("Control+Alt+.");
});
