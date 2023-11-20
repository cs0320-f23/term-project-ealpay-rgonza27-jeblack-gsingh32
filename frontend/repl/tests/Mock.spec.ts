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

test("trying to view without loading a file produces an error", async ({
  page,
}) => {
  await page.getByLabel("enter command").click();
  await page.getByLabel("enter command").fill("view");
  await page.getByRole("button").click();
  await page.waitForSelector(".historySpace");
  const output = await page.evaluate(() => {
    return document.querySelector(".historySpace")?.textContent;
  });

  expect(output).toBe("Please load a file");
});

test("after loading a valid file, the response is success", async ({
  page,
}) => {
  await page.getByLabel(TEXT_input_box).click();
  await page
    .getByLabel(TEXT_input_box)
    .fill("load_file census/dol_ri_earnings_disparity.csv");

  await page.getByRole("button").click();
  await page.waitForSelector(".historySpace");
  const resultText = await page.evaluate(() => {
    // gets first element that matches .historySpace
    return document.querySelector(".historySpace")?.textContent;
  });

  expect(resultText).toBe("Success!");
});

test("after loading an invalid file, the response is failure", async ({
  page,
}) => {
  await page.getByLabel(TEXT_input_box).click();
  await page.getByLabel(TEXT_input_box).fill("load_file notFilePath");

  await page.getByRole("button").click();
  await page.waitForSelector(".historySpace");
  const resultText = await page.evaluate(() => {
    // gets first element that matches .historySpace
    return document.querySelector(".historySpace")?.textContent;
  });

  expect(resultText).toBe("Couldn't find the file");
});

test("trying to view without loading a file and then loading one and calling view", async ({
  page,
}) => {
  await page.getByLabel(TEXT_input_box).click();
  await page.getByLabel(TEXT_input_box).fill("view");
  await page.getByRole("button").click();
  await page.waitForSelector(".historySpace");
  var output = await page.evaluate(() => {
    return document.querySelector(".historySpace")?.textContent;
  });

  expect(output).toBe("Please load a file");

  await page.getByLabel(TEXT_input_box).click();
  await page
    .getByLabel(TEXT_input_box)
    .fill("load_file census/dol_ri_earnings_disparity.csv");
  await page.getByRole("button").click();
  await page.getByLabel(TEXT_input_box).click();
  await page.getByLabel(TEXT_input_box).fill("view");
  await page.getByRole("button").click();
  await page.waitForSelector(".historySpace");
  output = await page.evaluate(() => {
    return document.querySelector(".historySpace")?.textContent;
  });

  expect(output).not.toBe("No Files Have Been Parsed");
});

test("verbose: trying to view without loading a file and then loading one and calling brief: view", async ({
  page,
}) => {
  await page.getByLabel(TEXT_input_box).click();
  await page.getByLabel(TEXT_input_box).fill("mode");
  await page.getByRole("button").click();
  await page.getByLabel(TEXT_input_box).click();
  await page.getByLabel(TEXT_input_box).fill("view");
  await page.getByRole("button").click();
  await page.waitForSelector(".historySpace");
  var output = await page.evaluate(() => {
    return document.querySelector(".historySpace")?.textContent;
  });
  expect(output).toContain("Command: view  Output:Please load a file");

  await page.getByLabel(TEXT_input_box).click();
  await page
    .getByLabel(TEXT_input_box)
    .fill("load_file census/dol_ri_earnings_disparity.csv");
  await page.getByLabel(TEXT_input_box).click();
  await page.getByLabel(TEXT_input_box).fill("mode");
  await page.getByRole("button").click();
  await page.getByRole("button").click();
  await page.getByLabel(TEXT_input_box).click();
  await page.getByLabel(TEXT_input_box).fill("view");
  await page.getByRole("button").click();
  await page.waitForSelector(".historySpace");
  output = await page.evaluate(() => {
    return document.querySelector(".historySpace")?.textContent;
  });

  expect(output).not.toContain("Output: No Files Have Been Parsed");
});

test("trying to search without loading a file", async ({ page }) => {
  await page.getByLabel("enter command").click();
  await page.getByLabel("enter command").fill("search white");
  await page.getByRole("button").click();
  await page.waitForSelector(".historySpace");
  var output = await page.evaluate(() => {
    return document.querySelector(".historySpace")?.textContent;
  });

  expect(output).toBe("Please Load File First");
});

test("trying to search without loading a file and then loading one and calling search", async ({
  page,
}) => {
  await page.getByLabel("enter command").click();
  await page.getByLabel(TEXT_input_box).fill("search white");
  await page.getByRole("button").click();
  await page.waitForSelector(".historySpace");
  var output = await page.evaluate(() => {
    return document.querySelector(".historySpace")?.textContent;
  });

  expect(output).toBe("Please Load File First");

  await page.getByLabel("enter command").click();
  await page
    .getByLabel("enter command")
    .fill("load_file census/dol_ri_earnings_disparity.csv");
  await page.getByRole("button").click();
  await page.getByLabel(TEXT_input_box).click();
  await page.getByLabel(TEXT_input_box).fill("search white");
  await page.getByRole("button").click();
  await page.waitForSelector(".historySpace");
  output = await page.evaluate(() => {
    return document.querySelector(".historySpace")?.textContent;
  });
  await expect(page.getByRole("table")).toBeVisible();
  await expect(page.getByRole("cell", { name: "ri" })).toBeVisible();
  await expect(page.getByRole("cell", { name: "white" })).toBeVisible();
  await expect(page.getByRole("cell", { name: '" $1,058.47 "' })).toBeVisible();
  await expect(page.getByRole("cell", { name: "395773.6521" })).toBeVisible();
  await expect(page.getByRole("cell", { name: "$1.00" })).toBeVisible();
  await expect(page.getByRole("cell", { name: "75%" })).toBeVisible();
});

test("verbose, trying to search without loading a file and then loading one and calling search", async ({
  page,
}) => {
  await page.getByLabel("enter command").click();
  await page.getByLabel("enter command").fill("search white");
  await page.getByRole("button").click();
  await page.waitForSelector(".historySpace");
  var output = await page.evaluate(() => {
    return document.querySelector(".historySpace")?.textContent;
  });

  expect(output).toBe("Please Load File First");

  await page.getByLabel("enter command").click();
  await page
    .getByLabel("enter command")
    .fill("load_file census/dol_ri_earnings_disparity.csv");
  await expect(
    page.getByTitle("verbose").getByText("Output:")
  ).not.toBeVisible();

  await page.getByRole("button").click();
  await page.getByLabel(TEXT_input_box).click();
  await page.getByLabel(TEXT_input_box).fill("mode");
  await page.getByRole("button").click();
  await page.getByLabel(TEXT_input_box).click();
  await page.getByLabel(TEXT_input_box).fill("search white");
  await page.getByRole("button").click();
  await page.waitForSelector(".historySpace");
  output = await page.evaluate(() => {
    return document.querySelector(".historySpace")?.textContent;
  });

  expect(output).toContain("Command: mode");

  await expect(page.getByRole("table")).toBeVisible();
  await expect(page.getByRole("cell", { name: "ri" })).toBeVisible();
  await expect(page.getByRole("cell", { name: "white" })).toBeVisible();
  await expect(page.getByRole("cell", { name: '" $1,058.47 "' })).toBeVisible();
  await expect(page.getByRole("cell", { name: "395773.6521" })).toBeVisible();
  await expect(page.getByRole("cell", { name: "$1.00" })).toBeVisible();
  await expect(page.getByRole("cell", { name: "75%" })).toBeVisible();
  await page.getByLabel(TEXT_input_box).click();
  await page.getByLabel(TEXT_input_box).fill("mode");
  await page.getByRole("button").click();
  output = await page.evaluate(() => {
    return document.querySelector(".historySpace")?.textContent;
  });
  expect(output).not.toContain("Command: mode");
});

test("if I search 'white' without column ID, I get the correct result", async ({
  page,
}) => {
  await page.getByLabel(TEXT_input_box).click();
  await page
    .getByLabel(TEXT_input_box)
    .fill("load_file census/dol_ri_earnings_disparity.csv");
  await page.getByRole("button").click();
  await page.getByLabel(TEXT_input_box).click();
  await page.getByLabel(TEXT_input_box).fill("search white");
  await page.getByRole("button").click();

  await expect(page.getByRole("table")).toBeVisible();
  await expect(page.getByRole("cell", { name: "ri" })).toBeVisible();
  await expect(page.getByRole("cell", { name: "white" })).toBeVisible();
  await expect(page.getByRole("cell", { name: '" $1,058.47 "' })).toBeVisible();
  await expect(page.getByRole("cell", { name: "395773.6521" })).toBeVisible();
  await expect(page.getByRole("cell", { name: "$1.00" })).toBeVisible();
  await expect(page.getByRole("cell", { name: "75%" })).toBeVisible();
});

test("if I search 'white' with column index 1, I get the correct result", async ({
  page,
}) => {
  await page.getByLabel(TEXT_input_box).click();
  await page
    .getByLabel(TEXT_input_box)
    .fill("load_file census/dol_ri_earnings_disparity.csv");
  await page.getByRole("button").click();
  await page.getByLabel(TEXT_input_box).click();
  await page.getByLabel(TEXT_input_box).fill("search 1 white");
  await page.getByRole("button").click();

  await expect(page.getByRole("table")).toBeVisible();
  await expect(page.getByRole("cell", { name: "ri" })).toBeVisible();
  await expect(page.getByRole("cell", { name: "white" })).toBeVisible();
  await expect(page.getByRole("cell", { name: '" $1,058.47 "' })).toBeVisible();
  await expect(page.getByRole("cell", { name: "395773.6521" })).toBeVisible();
  await expect(page.getByRole("cell", { name: "$1.00" })).toBeVisible();
  await expect(page.getByRole("cell", { name: "75%" })).toBeVisible();
});

test("if I search 'white' with column header 'data type', I get the correct result", async ({
  page,
}) => {
  await page.getByLabel(TEXT_input_box).click();
  await page
    .getByLabel(TEXT_input_box)
    .fill("load_file census/dol_ri_earnings_disparity.csv");
  await page.getByRole("button").click();
  await page.getByLabel(TEXT_input_box).click();
  await page.getByLabel(TEXT_input_box).fill('search "data type" white');
  await page.getByRole("button").click();

  await expect(page.getByRole("table")).toBeVisible();
  await expect(page.getByRole("cell", { name: "ri" })).toBeVisible();
  await expect(page.getByRole("cell", { name: "white" })).toBeVisible();
  await expect(page.getByRole("cell", { name: '" $1,058.47 "' })).toBeVisible();
  await expect(page.getByRole("cell", { name: "395773.6521" })).toBeVisible();
  await expect(page.getByRole("cell", { name: "$1.00" })).toBeVisible();
  await expect(page.getByRole("cell", { name: "75%" })).toBeVisible();
});

test("if I search 'RI' with column header 'data type', I get an error", async ({
  page,
}) => {
  await page.getByLabel(TEXT_input_box).click();
  await page
    .getByLabel(TEXT_input_box)
    .fill("load_file census/dol_ri_earnings_disparity.csv");
  await page.getByRole("button").click();
  await page.getByLabel(TEXT_input_box).click();
  await page.getByLabel(TEXT_input_box).fill('search "data type" RI');
  await page.getByRole("button").click();

  var output = await page.evaluate(() => {
    return document.querySelector(".historySpace")?.textContent;
  });
  expect(output).toContain("Value was not found");
});

test("if I search an index bigger than the number of header elements", async ({
  page,
}) => {
  await page.getByLabel(TEXT_input_box).click();
  await page
    .getByLabel(TEXT_input_box)
    .fill("load_file census/dol_ri_earnings_disparity.csv");
  await page.getByRole("button").click();
  await page.getByLabel(TEXT_input_box).click();
  await page.getByLabel(TEXT_input_box).fill("search 10 RI");
  await page.getByRole("button").click();

  var output = await page.evaluate(() => {
    return document.querySelector(".historySpace")?.textContent;
  });
  expect(output).toContain("Value was not found");
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

  var output = await page.evaluate(() => {
    return document.querySelector(".historySpace")?.textContent;
  });
  expect(output).toContain("Value was not found");
});

test("if I load a file with a header and then view, I get the correct table", async ({
  page,
}) => {
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
      name: 'RI White " $1,058.47 " 395773.6521  $1.00  75%',
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
      name: 'RI Asian-Pacific Islander " $1,080.09 " 18956.71657 $1.02 4%',
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

test("if I load a file without a header and then view, I get the correct table", async ({
  page,
}) => {
  await page.getByLabel(TEXT_input_box).click();
  await page.getByLabel(TEXT_input_box).fill("load_file stars/ten-star.csv");
  await page.getByRole("button").click();
  await page.getByLabel(TEXT_input_box).click();
  await page.getByLabel(TEXT_input_box).fill("view");
  await page.getByRole("button").click();

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

test("if I load a file without a header and call mode, then view, I get the correct table", async ({
  page,
}) => {
  await page.getByLabel(TEXT_input_box).click();
  await page.getByLabel(TEXT_input_box).fill("load_file stars/ten-star.csv");
  await page.getByRole("button").click();
  await expect(
    page.getByTitle("verbose").getByText("Output:")
  ).not.toBeVisible();
  await page.getByLabel(TEXT_input_box).click();
  await page.getByLabel(TEXT_input_box).fill("mode");
  await page.getByRole("button").click();
  await page.getByLabel(TEXT_input_box).click();
  await page.getByLabel(TEXT_input_box).fill("view");
  await page.getByRole("button").click();

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
  var output = await page.evaluate(() => {
    return document.querySelector(".historySpace")?.textContent;
  });
  expect(output).toContain("Output:");
});

test("I can load + view and then repeat with a different file", async ({
  page,
}) => {
  await page.getByLabel(TEXT_input_box).click();
  await page
    .getByLabel(TEXT_input_box)
    .fill("load_file census/dol_ri_earnings_disparity.csv");
  await page.getByRole("button").click();
  await page.getByLabel(TEXT_input_box).click();
  await page.getByLabel(TEXT_input_box).fill("view");
  await page.getByRole("button").click();

  await expect(
    page.getByRole("row", {
      name: "State Data Type Average Weekly Earnings Number of Workers Earnings Disparity Employed Percent",
    })
  ).toBeVisible();

  await page.getByLabel(TEXT_input_box).click();
  await page.getByLabel(TEXT_input_box).fill("load_file /stars/ten-star.csv");
  await page.getByRole("button").click();
  await page.getByLabel(TEXT_input_box).click();
  await page.getByLabel(TEXT_input_box).fill("view");
  await page.getByRole("button").click();

  await expect(page.getByRole("row", { name: "0 Sol 0 0 0" })).toBeVisible;
});

test("I can load + search and then repeat with a different file", async ({
  page,
}) => {
  await page.getByLabel(TEXT_input_box).click();
  await page
    .getByLabel(TEXT_input_box)
    .fill("load_file census/dol_ri_earnings_disparity.csv");
  await page.getByRole("button").click();
  await page.getByLabel(TEXT_input_box).click();
  await page.getByLabel(TEXT_input_box).fill("search 1 white");
  await page.getByRole("button").click();

  await expect(page.getByRole("cell", { name: "ri" })).toBeVisible();
  await expect(page.getByRole("cell", { name: "white" })).toBeVisible();
  await expect(page.getByRole("cell", { name: '" $1,058.47 "' })).toBeVisible();
  await expect(page.getByRole("cell", { name: "395773.6521" })).toBeVisible();
  await expect(page.getByRole("cell", { name: "$1.00" })).toBeVisible();
  await expect(page.getByRole("cell", { name: "75%" })).toBeVisible();

  await page.getByLabel(TEXT_input_box).click();
  await page.getByLabel(TEXT_input_box).fill("load_file /stars/ten-star.csv");
  await page.getByRole("button").click();
  await page.getByLabel(TEXT_input_box).click();
  await page.getByLabel(TEXT_input_box).fill("search 2 0");
  await page.getByRole("button").click();

  await expect(page.getByRole("row", { name: "0 Sol 0 0 0" })).toBeVisible;
});

test("I cannot search from a different unloaded file (even if it exists in the data)", async ({
  page,
}) => {
  await page.getByLabel(TEXT_input_box).click();
  await page
    .getByLabel(TEXT_input_box)
    .fill("load_file census/dol_ri_earnings_disparity.csv");
  await page.getByRole("button").click();
  await page.getByLabel(TEXT_input_box).click();
  await page.getByLabel(TEXT_input_box).fill("search 2 0");
  await page.getByRole("button").click();

  const resultText = await page.evaluate(() => {
    // gets first element that matches .historySpace
    return document.querySelector(".historySpace")?.textContent;
  });

  expect(resultText).toContain("Value was not found");
});

test("searching for a nonexistent value produces an error", async ({
  page,
}) => {
  await page.getByLabel(TEXT_input_box).click();
  await page
    .getByLabel(TEXT_input_box)
    .fill("load_file census/dol_ri_earnings_disparity.csv");
  await page.getByRole("button").click();
  await page.getByLabel(TEXT_input_box).click();
  await page.getByLabel(TEXT_input_box).fill("search happiness");
  await page.getByRole("button").click();

  const resultText = await page.evaluate(() => {
    // gets first element that matches .historySpace
    return document.querySelector(".historySpace")?.textContent;
  });

  expect(resultText).toContain("Value was not found");
});

test("Searching a single column csv produces the expected result", async ({
  page,
}) => {
  await page.getByLabel(TEXT_input_box).click();
  await page
    .getByLabel(TEXT_input_box)
    .fill(
      "load_file /Users/chloenevas/Documents/mock-cnevas-rgonza27/mock/src/components/data/singleColumn.csv"
    );
  await page.getByRole("button").click();
  await page.getByLabel(TEXT_input_box).click();
  await page.getByLabel(TEXT_input_box).fill("search this");
  await page.getByRole("button").click();

  await expect(page.getByRole("cell", { name: "this" })).toBeVisible();
});

test("I can search for broadband information correctly", async ({ page }) => {
  await page.getByLabel("enter command").click();
  await page
    .getByLabel("enter command")
    .fill('broadband California "San Francisco County"');
  await page.getByRole("button").click();

  await expect(
    page.getByRole("row", {
      name: "return Type successful",
    })
  ).toBeVisible();
  await expect(
    page.getByRole("row", {
      name: "state 06",
    })
  ).toBeVisible();
  await expect(
    page.getByRole("row", {
      name: "Percentage 0.98",
    })
  ).toBeVisible();
});

test("It will give an error for a bad broadband search", async ({ page }) => {
  await page.getByLabel("enter command").click();
  await page.getByLabel("enter command").fill("broadband California bob");
  await page.getByRole("button").click();
  var output = await page.evaluate(() => {
    return document.querySelector(".historySpace")?.textContent;
  });
  expect(output).toContain("Error: please enter a valid State or County");
});
