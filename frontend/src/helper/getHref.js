export default function getHref(text, link) {
  if (!text || !link) {
    return "";
  }

  const trimmedText = String(text).trim();
  const trimmedLink = String(link).trim();

  if (!trimmedText || !trimmedLink) {
    return "";
  }

  return `<a href="${trimmedLink}">${trimmedText}</a>`;
}