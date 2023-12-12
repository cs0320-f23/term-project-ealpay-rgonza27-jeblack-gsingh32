export function stringToImage(imageDataString: string): HTMLImageElement {
  // Convert the base64 string to a Uint8Array
  const binaryString = window.atob(imageDataString);
  const length = binaryString.length;
  const uint8Array = new Uint8Array(length);

  for (let i = 0; i < length; i++) {
    uint8Array[i] = binaryString.charCodeAt(i);
  }

  // Create a Blob from the Uint8Array
  const blob = new Blob([uint8Array], { type: "image/png" }); // Change the type accordingly

  // Create a data URL from the Blob
  const imageUrl = URL.createObjectURL(blob);

  // Create an image element
  const image = new Image();
  image.src = imageUrl;

  return image;
}
