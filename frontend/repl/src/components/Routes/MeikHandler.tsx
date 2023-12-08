export function AllMeiks(): Promise<string[]> {
  return fetch("http://localhost:3232/getAllMeiks")
    .then((response) => response.json())
    .then((data) => {
      return data;
    })
    .catch((e) => {
      return "ERROR: " + e;
    });
}
