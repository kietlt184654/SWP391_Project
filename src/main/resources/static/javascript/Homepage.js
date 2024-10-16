window.onload = function () {
  showSlides();
};

var slideIndex = 0;
function showSlides() {
  var i;
  var slides = document.getElementsByClassName("mySlides");
  var dots = document.getElementsByClassName("dot");

  // Hide all slides
  for (i = 0; i < slides.length; i++) {
    slides[i].style.display = "none";
  }

  slideIndex++;
  if (slideIndex > slides.length) {
    slideIndex = 1;
  }

  // Remove the "active" class from all dots
  for (i = 0; i < dots.length; i++) {
    dots[i].className = dots[i].className.replace(" active", "");
  }

  // Display the current slide and make the corresponding dot active
  slides[slideIndex - 1].style.display = "block";
  dots[slideIndex - 1].className += " active";

  // Automatically change slide every 2 seconds
  setTimeout(showSlides, 2000);
}
