import { test, expect, defineConfig } from "@playwright/test";
import { TEXT_input_box } from "../src/components/constants";
export default defineConfig({
  timeout: 5 * 100 * 1000,
});

test.beforeEach(async ({ page }) => {
  await page.goto("http://localhost:8000/");
  await page.getByLabel("enter command").click();
  await page
    .getByLabel("enter command")
    .fill("PLEASE_DELETE_MY_PARSED_DATA_I_KNOW_I_WILL_HAVE_TO_LOAD_IT_AGAIN");
  await page.getByRole("button").click();
});

test.afterEach(async ({ page }) => {
  await page.close();
});

test("after I click the button with an invalid input, it loads an error message", async ({
  page,
}) => {
  await page.getByLabel("enter command").click();
  await page.getByLabel("enter command").fill("load");
  await page.getByRole("button").click();
  await page.waitForSelector(".historySpace");
  const resultText = await page.evaluate(() => {
    return document.querySelector(".historySpace")?.textContent;
  });

  expect(resultText).toBe("ERROR: Command not valid");
});

test("mode displays the correct text", async ({ page }) => {
  await page.getByLabel("enter command").click();
  await page.getByLabel("enter command").fill("mode");
  await page.getByRole("button").click();
  await expect(page.getByLabel("history box")).toContainText("Command: mode");
  await expect(page.getByLabel("history box")).toContainText(
    "Output:Mode switched to verbose"
  );
});

test("after loading a valid file, the response is success", async ({
  page,
}) => {
  await page.getByLabel("enter command").click();
  await page
    .getByLabel("enter command")
    .fill("load_file stars/ten-star.csv false");
  await page.getByRole("button").click();
  await expect(page.getByLabel("history box")).toContainText("Success");
});

test("after loading an invalid file, the response is failure", async ({
  page,
}) => {
  await page.getByLabel(TEXT_input_box).click();
  await page.getByLabel(TEXT_input_box).fill("load_file notFilePath");
  await page.getByRole("button").click();
  await page.waitForSelector(".historySpace");

  await expect(page.getByRole("table")).toBeVisible();
  await expect(
    page.getByRole("row", {
      name: "return type reason",
    })
  ).toBeVisible();
  await expect(
    page.getByRole("row", {
      name: "Failure error_data_source(filename does not exist)",
    })
  ).toBeVisible();
});

test("I can load and then view a file successfully", async ({ page }) => {
  // loading file
  await page.getByLabel("enter command").click();
  await page
    .getByLabel("enter command")
    .fill("load_file stars/ten-star.csv true");
  await page.getByRole("button").click();
  await page.waitForSelector(".historySpace");

  // viewing file
  await page.getByLabel("enter command").click();
  await page.getByLabel("enter command").fill("view");
  await page.getByRole("button").click();
  await page.waitForSelector(".historySpace");

  await expect(page.getByRole("table")).toBeVisible();
  await expect(page.getByRole("row", { name: "0 Sol 0 0 0" })).toBeVisible;
  await expect(page.getByRole("row", { name: "1 282.43485 0.00449 5.36884" }))
    .toBeVisible;
  await expect(page.getByRole("row", { name: "2 43.04329 0.00285 -15.24144" }))
    .toBeVisible;
  await expect(page.getByRole("row", { name: "3 277.11358 0.02422 223.27753" }))
    .toBeVisible;
  await expect(
    page.getByRole("row", { name: "3759 96 G. Psc 7.26388 1.55643 0.68697" })
  ).toBeVisible;
  await expect(
    page.getByRole("row", {
      name: "70667 Proxima Centauri -0.47175 -0.36132 -1.15037",
    })
  ).toBeVisible;
  await expect(
    page.getByRole("row", {
      name: "71454 Rigel Kentaurus B -0.50359 -0.42128 -1.1767",
    })
  ).toBeVisible;
  await expect(
    page.getByRole("row", {
      name: "71457 Rigel Kentaurus A -0.50362 -0.42139 -1.17665",
    })
  ).toBeVisible;
  await expect(
    page.getByRole("row", {
      name: "87666 Barnard's Star -0.01729 -1.81533 0.14824",
    })
  ).toBeVisible;
  await expect(
    page.getByRole("row", { name: "118721 -2.28262 0.64697 0.20354" })
  ).toBeVisible;
});

test("I can load and view and then repeat with a different file", async ({
  page,
}) => {
  // loading and viewing first file
  await page.getByLabel(TEXT_input_box).click();
  await page
    .getByLabel(TEXT_input_box)
    .fill("load_file census/dol_ri_earnings_disparity.csv");
  await page.getByRole("button").click();
  await page.getByLabel(TEXT_input_box).click();
  await page.getByLabel(TEXT_input_box).fill("view");
  await page.getByRole("button").click();

  await expect(page.getByRole("table")).toBeVisible();
  await expect(
    page.getByRole("row", {
      name: "State Data Type Average Weekly Earnings Number of Workers Earnings Disparity Employed Percent",
    })
  ).toBeVisible();
  await expect(
    page.getByRole("row", {
      name: "RI White $1,058.47 395773.6521  $1.00  75%",
    })
  ).toBeVisible();
  await expect(
    page.getByRole("row", { name: "RI Black $770.26 30424.80376 $0.73 6%" })
  ).toBeVisible();
  await expect(
    page.getByRole("row", {
      name: "RI Native American/American Indian $471.07 2315.505646 $0.45 0%",
    })
  ).toBeVisible();
  await expect(
    page.getByRole("row", {
      name: "RI Asian-Pacific Islander $1,080.09 18956.71657 $1.02 4%",
    })
  ).toBeVisible();
  await expect(
    page.getByRole("row", {
      name: "RI Hispanic/Latino $673.14 74596.18851 $0.64 14%",
    })
  ).toBeVisible();
  await expect(
    page.getByRole("row", {
      name: "RI Multiracial $971.89 8883.049171 $0.92 2%",
    })
  ).toBeVisible();

  // loading and viewing second file
  await page.getByLabel(TEXT_input_box).click();
  await page.getByLabel(TEXT_input_box).fill("load_file stars/ten-star.csv");
  await page.getByRole("button").click();
  await page.getByLabel(TEXT_input_box).click();
  await page.getByLabel(TEXT_input_box).fill("view");
  await page.getByRole("button").click();

  await expect(page.getByRole("row", { name: "0 Sol 0 0 0" })).toBeVisible;
  await expect(page.getByRole("row", { name: "1 282.43485 0.00449 5.36884" }))
    .toBeVisible;
  await expect(page.getByRole("row", { name: "2 43.04329 0.00285 -15.24144" }))
    .toBeVisible;
  await expect(page.getByRole("row", { name: "3 277.11358 0.02422 223.27753" }))
    .toBeVisible;
  await expect(
    page.getByRole("row", { name: "3759 96 G. Psc 7.26388 1.55643 0.68697" })
  ).toBeVisible;
  await expect(
    page.getByRole("row", {
      name: "70667 Proxima Centauri -0.47175 -0.36132 -1.15037",
    })
  ).toBeVisible;
  await expect(
    page.getByRole("row", {
      name: "71454 Rigel Kentaurus B -0.50359 -0.42128 -1.1767",
    })
  ).toBeVisible;
  await expect(
    page.getByRole("row", {
      name: "71457 Rigel Kentaurus A -0.50362 -0.42139 -1.17665",
    })
  ).toBeVisible;
  await expect(
    page.getByRole("row", {
      name: "87666 Barnard's Star -0.01729 -1.81533 0.14824",
    })
  ).toBeVisible;
  await expect(
    page.getByRole("row", { name: "118721 -2.28262 0.64697 0.20354" })
  ).toBeVisible;
});

test("Error displayed to prevent searching before loading", async ({
  page,
}) => {
  await page.getByLabel("enter command").click();
  await page.getByLabel("enter command").fill("search Sol");
  await page.getByRole("button").click();

  await expect(page.getByRole("table")).toBeVisible();
  await expect(page.getByRole("row", { name: "return type reason" }))
    .toBeVisible;
  await expect(
    page.getByRole("row", { name: "Failure input not in the provided rows" })
  ).toBeVisible;
});

test("I can load and then search a file with a column index successfully", async ({
  page,
}) => {
  // loading file
  await page.getByLabel(TEXT_input_box).click();
  await page
    .getByLabel(TEXT_input_box)
    .fill("load_file /stars/ten-star.csv true");
  await page.getByRole("button").click();
  // searching file
  await page.getByLabel(TEXT_input_box).click();
  await page.getByLabel(TEXT_input_box).fill("search 2 0");
  await page.getByRole("button").click();

  await expect(page.getByRole("table")).toBeVisible();
  await expect(page.getByRole("row", { name: "0 Sol 0 0 0" })).toBeVisible;
});

test("I can load and then search a file with a column name successfully", async ({
  page,
}) => {
  // loading file
  await page.getByLabel(TEXT_input_box).click();
  await page
    .getByLabel(TEXT_input_box)
    .fill("load_file /stars/ten-star.csv true");
  await page.getByRole("button").click();
  // searching file
  await page.getByLabel(TEXT_input_box).click();
  await page.getByLabel(TEXT_input_box).fill("search ProperName Sol");
  await page.getByRole("button").click();

  await expect(page.getByRole("table")).toBeVisible();
  await expect(page.getByRole("row", { name: "0 Sol 0 0 0" })).toBeVisible;
});

test("I can load and then search a file with just target successfully", async ({
  page,
}) => {
  // loading file
  await page.getByLabel(TEXT_input_box).click();
  await page
    .getByLabel(TEXT_input_box)
    .fill("load_file /stars/ten-star.csv true");
  await page.getByRole("button").click();
  // searching file
  await page.getByLabel(TEXT_input_box).click();
  await page.getByLabel(TEXT_input_box).fill("search Sol");
  await page.getByRole("button").click();

  await expect(page.getByRole("table")).toBeVisible();
  await expect(page.getByRole("row", { name: "0 Sol 0 0 0" })).toBeVisible;
});

test("I can load and then search a file with target not in file", async ({
  page,
}) => {
  // loading file
  await page.getByLabel(TEXT_input_box).click();
  await page
    .getByLabel(TEXT_input_box)
    .fill("load_file /stars/ten-star.csv true");
  await page.getByRole("button").click();
  // searching file
  await page.getByLabel(TEXT_input_box).click();
  await page.getByLabel(TEXT_input_box).fill("search Bob");
  await page.getByRole("button").click();

  await expect(page.getByRole("table")).toBeVisible();
  await expect(page.getByRole("row", { name: "return type reason" }))
    .toBeVisible;
  await expect(
    page.getByRole("row", { name: "Failure input not in the rows provided" })
  ).toBeVisible;
});

test("I can load and then search a file with a space in the target successfully", async ({
  page,
}) => {
  // loading file
  await page.getByLabel(TEXT_input_box).click();
  await page
    .getByLabel(TEXT_input_box)
    .fill("load_file census/postsecondary_education.csv false");
  await page.getByRole("button").click();

  // searching file
  await page.getByLabel(TEXT_input_box).click();
  await page.getByLabel(TEXT_input_box).fill('search "Two or More Races"');
  await page.getByRole("button").click();

  await expect(page.getByRole("table")).toBeVisible();
  await expect(
    page.getByRole("row", {
      name: "two or more races 2020 2020 217156 Brown University 58 brown-university 0.018764154 Men 1",
    })
  ).toBeVisible;
  await expect(
    page.getByRole("row", {
      name: "Two or More Races 2020 2020 217156 Brown University 85 brown-university 0.027499191 Women 2",
    })
  ).toBeVisible;
});

test("Searching a file with a column name when no header gives an error message", async ({
  page,
}) => {
  // loading file
  await page.getByLabel(TEXT_input_box).click();
  await page
    .getByLabel(TEXT_input_box)
    .fill("load_file stars/ten-star.csv false");
  await page.getByRole("button").click();
  // searching file
  await page.getByLabel(TEXT_input_box).click();
  await page.getByLabel(TEXT_input_box).fill("search ProperName Sol");
  await page.getByRole("button").click();

  await expect(page.getByRole("table")).toBeVisible();
  await expect(
    page.getByRole("row", {
      name: "return type reason",
    })
  ).toBeVisible();
  await expect(
    page.getByRole("row", {
      name: "Failure error_bad_request: Sol was not found in the file.The available headers are: []",
    })
  ).toBeVisible();
});

test("I can load first file (no header), load second file (header), and search second file", async ({
  page,
}) => {
  // loading first file
  await page.getByLabel(TEXT_input_box).click();
  await page
    .getByLabel(TEXT_input_box)
    .fill("load_file census/dol_ri_earnings_disparity.csv false");
  await page.getByRole("button").click();

  // loading second file
  await page.getByLabel(TEXT_input_box).click();
  await page
    .getByLabel(TEXT_input_box)
    .fill("load_file /stars/ten-star.csv true");
  await page.getByRole("button").click();

  // searching second file
  await page.getByLabel(TEXT_input_box).click();
  await page.getByLabel(TEXT_input_box).fill("search ProperName Sol");
  await page.getByRole("button").click();

  await expect(page.getByRole("table")).toBeVisible();
  await expect(page.getByRole("row", { name: "0 Sol 0 0 0" })).toBeVisible;
});

test("if I search with a negative index I get an error", async ({ page }) => {
  await page.getByLabel(TEXT_input_box).click();
  await page
    .getByLabel(TEXT_input_box)
    .fill("load_file census/dol_ri_earnings_disparity.csv");
  await page.getByRole("button").click();
  await page.getByLabel(TEXT_input_box).click();
  await page.getByLabel(TEXT_input_box).fill("search -1 RI");
  await page.getByRole("button").click();

  await expect(
    page.getByRole("row", {
      name: "return type reason",
    })
  ).toBeVisible();
  await expect(
    page.getByRole("row", {
      name: "Failure error_bad_request: RI was not found in the file.The available headers are: []",
    })
  ).toBeVisible();
});

test("I can change modes without it impacting load or view", async ({
  page,
}) => {
  // change mode
  await page.getByLabel("enter command").click();
  await page.getByLabel("enter command").fill("mode");
  await page.getByRole("button").click();

  // load file
  await page.getByLabel("enter command").click();
  await page
    .getByLabel("enter command")
    .fill("load_file census/dol_ri_earnings_disparity.csv true");
  await page.getByRole("button").click();

  // change mode
  await page.getByLabel("enter command").click();
  await page.getByLabel("enter command").fill("mode");
  await page.getByRole("button").click();

  // view file
  await page.getByLabel("enter command").click();
  await page.getByLabel("enter command").fill("view");
  await page.getByRole("button").click();
  await expect(page.getByRole("table")).toBeVisible();
  await expect(
    page.getByRole("row", {
      name: "State Data Type Average Weekly Earnings Number of Workers Earnings Disparity Employed Percent",
    })
  ).toBeVisible();
  await expect(
    page.getByRole("row", {
      name: "RI White $1,058.47 395773.6521  $1.00  75%",
    })
  ).toBeVisible();
  await expect(
    page.getByRole("row", { name: "RI Black $770.26 30424.80376 $0.73 6%" })
  ).toBeVisible();
  await expect(
    page.getByRole("row", {
      name: "RI Native American/American Indian $471.07 2315.505646 $0.45 0%",
    })
  ).toBeVisible();
  await expect(
    page.getByRole("row", {
      name: "RI Asian-Pacific Islander $1,080.09 18956.71657 $1.02 4%",
    })
  ).toBeVisible();
  await expect(
    page.getByRole("row", {
      name: "RI Hispanic/Latino $673.14 74596.18851 $0.64 14%",
    })
  ).toBeVisible();
  await expect(
    page.getByRole("row", {
      name: "RI Multiracial $971.89 8883.049171 $0.92 2%",
    })
  ).toBeVisible();
});

test("errors are returned if entering no parameters for load, search and broadband", async ({
  page,
}) => {
  // view
  await page.getByLabel("enter command").click();
  await page.getByLabel("enter command").fill("load");
  await page.getByRole("button").click();
  await expect(page.getByLabel("history box")).toContainText(
    "ERROR: Command not valid"
  );

  // search
  await page.getByLabel("enter command").click();
  await page.getByLabel("enter command").fill("search");
  await page.getByRole("button").click();
  await expect(page.getByLabel("history box")).toContainText(
    "ERROR: enter search terms"
  );

  // broadband
  await page.getByLabel("enter command").click();
  await page.getByLabel("enter command").fill("broadband");
  await page.getByRole("button").click();
  await expect(page.getByLabel("history box")).toContainText(
    "ERROR: enter state and county for broadband"
  );
});

test("It will give an error for a bad broadband search", async ({ page }) => {
  await page.getByLabel("enter command").click();
  await page.getByLabel("enter command").fill("broadband California bob");
  await page.getByRole("button").click();

  await expect(
    page.getByRole("row", {
      name: "return type reason",
    })
  ).toBeVisible();
  await expect(
    page.getByRole("row", {
      name: "Failure java.lang.NullPointerException: error_bad_request. county entered not Found: Please enter a valid county for California",
    })
  ).toBeVisible();
});

test("I can search for broadband information correctly", async ({ page }) => {
  await page.getByLabel("enter command").click();
  await page
    .getByLabel("enter command")
    .fill('broadband California "San Francisco County"');
  await page.getByRole("button").click();

  await expect(
    page.getByRole("row", {
      name: "county 075",
    })
  ).toBeVisible();
  await expect(
    page.getByRole("row", {
      name: "return Type success",
    })
  ).toBeVisible();
  await expect(
    page.getByRole("row", {
      name: "state 06",
    })
  ).toBeVisible();
  await expect(
    page.getByRole("row", {
      name: "NAME San Francisco County, California",
    })
  ).toBeVisible();
});

test("I can load a file, get broadband information and then view", async ({
  page,
}) => {
  // load file
  await page.getByLabel("enter command").click();
  await page
    .getByLabel("enter command")
    .fill("load_file census/dol_ri_earnings_disparity.csv true");
  await page.getByRole("button").click();

  // broadband
  await page.getByLabel("enter command").click();
  await page
    .getByLabel("enter command")
    .fill('broadband "New York" "Bronx County"');
  await page.getByRole("button").click();

  await expect(
    page.getByRole("row", {
      name: "county 005",
    })
  ).toBeVisible();
  await expect(
    page.getByRole("row", {
      name: "return Type success",
    })
  ).toBeVisible();
  await expect(
    page.getByRole("row", {
      name: "state 36",
    })
  ).toBeVisible();
  await expect(
    page.getByRole("row", {
      name: "NAME Bronx County, New York",
    })
  ).toBeVisible();

  // view file
  await page.getByLabel("enter command").click();
  await page.getByLabel("enter command").fill("view");
  await page.getByRole("button").click();

  await expect(
    page.getByRole("row", {
      name: "State Data Type Average Weekly Earnings Number of Workers Earnings Disparity Employed Percent",
    })
  ).toBeVisible();
  await expect(
    page.getByRole("row", {
      name: "RI White $1,058.47 395773.6521  $1.00  75%",
    })
  ).toBeVisible();
  await expect(
    page.getByRole("row", { name: "RI Black $770.26 30424.80376 $0.73 6%" })
  ).toBeVisible();
  await expect(
    page.getByRole("row", {
      name: "RI Native American/American Indian $471.07 2315.505646 $0.45 0%",
    })
  ).toBeVisible();
  await expect(
    page.getByRole("row", {
      name: "RI Asian-Pacific Islander $1,080.09 18956.71657 $1.02 4%",
    })
  ).toBeVisible();
  await expect(
    page.getByRole("row", {
      name: "RI Hispanic/Latino $673.14 74596.18851 $0.64 14%",
    })
  ).toBeVisible();
  await expect(
    page.getByRole("row", {
      name: "RI Multiracial $971.89 8883.049171 $0.92 2%",
    })
  ).toBeVisible();
});

test("without loading a file, view does not work", async ({ page }) => {
  await page.getByLabel("enter command").click();
  await page.getByLabel("enter command").fill("view");
  await page.getByRole("button").click();

  await expect(page.getByRole("table")).toBeVisible();
  await expect(
    page.getByRole("cell", {
      name: "CSV Not Loaded, please load a CSV First or provide a CSV with information in it",
    })
  ).toBeVisible();
});
