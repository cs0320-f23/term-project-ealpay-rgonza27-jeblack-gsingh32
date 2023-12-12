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

export function singleMeik(meikId: String): Promise<Meik> {
  return fetch("http://localhost:3232/getMeikById?id=" + meikId)
    .then((response) => response.json())
    .then((data) => {
      console.log(data);
      return data["data"]["user"];
    })
    .then((data) => {
      const a = typeof data === "string" ? JSON.parse(data) : data;

      return a;
    })
    .catch((e) => {
      return "ERROR: " + e;
    });
}
