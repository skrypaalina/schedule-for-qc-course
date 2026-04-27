import search from "./search";

describe("search helper", () => {
  const items = [
    { name: "Math", teacher: "Ivan", hours: 10 },
    { name: "Physics", teacher: "Petro", hours: 20 },
    { name: "math advanced", teacher: "Oleg", hours: 15 }
  ];

  // --- Edge cases ---
  test("should ignore spaces in term", () => {
    expect(search(items, "  Math  ").length).toBe(2);
  });

  test("should return empty array if items is empty", () => {
    expect(search([], "Math")).toEqual([]);
  });

  test("should return empty array if term is null", () => {
    expect(search(items, null)).toEqual([]);
  });

  test("should return empty array if term is undefined", () => {
    expect(search(items, undefined)).toEqual([]);
  });

  test("should handle empty string term (return all)", () => {
    expect(search(items, "").length).toBe(items.length);
  });

  test("should be case insensitive", () => {
    expect(search(items, "math").length).toBe(2);
  });

  test("should search by teacher field", () => {
    expect(search(items, "Ivan").length).toBe(1);
  });

  test("should work with numeric values", () => {
    expect(search(items, "10").length).toBe(1);
  });

  test("should return empty if no matches", () => {
    expect(search(items, "Biology")).toEqual([]);
  });

  test("should handle whitespace-only term", () => {
    expect(search(items, "   ")).toEqual(items);
  });

  test("should not fail on missing fields", () => {
    const bad = [{ name: null }, { teacher: undefined }];
    expect(search(bad, "test")).toEqual([]);
  });
});