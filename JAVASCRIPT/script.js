document.addEventListener("DOMContentLoaded", () => {
  const form = document.getElementById("loginForm");

  if (form) {
    form.addEventListener("submit", e => {
      e.preventDefault();
      const user = document.getElementById("username").value;

      if (user === "student") {
        localStorage.setItem("score", "2 / 2");
        window.location.href = "dashboard.html";
      } else if (user === "admin") {
        alert("Admin dashboard simulation");
      } else if (user === "examiner") {
        alert("Examiner dashboard simulation");
      } else {
        alert("Invalid credentials");
      }
    });
  }
});
