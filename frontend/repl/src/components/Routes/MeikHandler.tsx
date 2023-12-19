import Meik from "./MeikObject";

export function AllMeiks(): Promise<string[]> {
  return fetch("http://localhost:3232/getAllMeiks")
    .then((response) => response.json())
    .then((data) => {
      return data["meiksData"];
    })
    .catch((e) => {
      return "ERROR: " + e;
    });
}

export function algoMeiks(): Promise<string[]> {
  return fetch("http://localhost:3232/getRecMeiks?uid=H6Tkobj4jLpoU6IE1E6S")
    .then((response) => response.json())
    .then((data) => {
      console.log(data);
      return data["meiksData"];
    })
    .catch((e) => {
      return "ERROR: " + e;
    });
}

export function singleMeik(meikId: String) {
  return fetch("http://localhost:3232/getMeikById?id=" + meikId)
    .then((response) => response.json())
    .catch((e) => {
      return "ERROR: " + e;
    });
}

export function changeInfo(
  meikId: String,
  name: String,
  location: String,
  year: String,
  tag: String,
  concentration: String
): Promise<string[]> {
  return fetch(
    "http://localhost:3232/updateMeik?id=" +
      meikId +
      "&name=" +
      name +
      "&location=" +
      location +
      "&year=" +
      year +
      "&tag=" +
      tag +
      "&concentration=" +
      concentration
  )
    .then((response) => response.json())
    .catch((e) => {
      return "ERROR: " + e;
    });
}
