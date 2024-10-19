// side bar
const menuToggle = document.getElementById("menu-toggle");
const sidebar = document.getElementById("sidebar");

menuToggle.addEventListener("click", () => {
  sidebar.classList.toggle("show");
});

// dropdown menu
const accountIcon = document.getElementById("accountIcon");
const dropdownMenu = document.querySelector(".dropdown-menu");

accountIcon.addEventListener("click", (event) => {
  event.preventDefault();
  dropdownMenu.style.display =
    dropdownMenu.style.display === "block" ? "none" : "block";
});

document.addEventListener("click", function (event) {
  if (
    !accountIcon.contains(event.target) &&
    !dropdownMenu.contains(event.target)
  ) {
    dropdownMenu.style.display = "none";
  }
  // icon cart
  let cartCount = 0;

  function updateCartCount(count) {
    cartCount += count;
    document.querySelector(".notification-badge").textContent = cartCount;
  }
  updateCartCount(1);
});

// Control quantity
document.addEventListener("DOMContentLoaded", function () {
  const increaseButtons = document.querySelectorAll(".increase-btn");
  const decreaseButtons = document.querySelectorAll(".decrease-btn");

  increaseButtons.forEach((button) => {
    button.addEventListener("click", function () {
      const id = this.getAttribute("data-id");
      const quantityInput = document.getElementById(`quantity-${id}`);
      let currentValue = parseInt(quantityInput.value);
      quantityInput.value = currentValue + 1;
    });
  });

  decreaseButtons.forEach((button) => {
    button.addEventListener("click", function () {
      const id = this.getAttribute("data-id");
      const quantityInput = document.getElementById(`quantity-${id}`);
      let currentValue = parseInt(quantityInput.value);
      if (currentValue > 1) {
        quantityInput.value = currentValue - 1;
      }
    });
  });
});

//icon-bin:delete row in cart
function deleteRow(element) {
  var row = element.parentNode.parentNode;

  row.parentNode.removeChild(row);
}
