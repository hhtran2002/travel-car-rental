/** @type {import('tailwindcss').Config} */
export default {
  content: ["./index.html", "./src/**/*.{js,ts,jsx,tsx}"],
  theme: {
    extend: {
      colors: {
        primary: "#00FF00", // Neon Green
        dark: "#121212", // Nền đen
      },
    },
  },
  plugins: [],
};
