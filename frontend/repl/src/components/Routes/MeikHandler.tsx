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

export function updateSearch(uid: String, search: String): Promise<string[]> {
  return fetch(
    "http://localhost:3232/updateSearch?uid=" + uid + "&searched=" + search
  )
    .then((response) => response.json())
    .then((data) => {
      return data["meiksData"];
    })
    .catch((e) => {
      return "ERROR: " + e;
    });
}

export function algoMeiks(uid: String): Promise<AlgoMeiksResponse> {
  return fetch("http://localhost:3232/getRecMeiks?uid=" + uid)
    .then((response) => response.json())
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
interface AlgoMeiksResponse {
  uid: string;
  results: {
    meiks: Meik[];
    // Other properties in the results object
  };
  images: Record<string, string>;
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
