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
