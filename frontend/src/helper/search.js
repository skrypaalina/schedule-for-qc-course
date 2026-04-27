export default function search(items, term) {
  if (!Array.isArray(items)) return [];

  if (term === undefined || term === null) return [];

  const normalized = term.toString().trim().toLowerCase();

  if (normalized === "") return items;

  return items.filter((item) =>
    Object.values(item).some((value) =>
      String(value).toLowerCase().includes(normalized)
    )
  );
}