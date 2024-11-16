<!DOCTYPE html>
<html lang="en">
  <head>
    <meta charset="UTF-8" />
    <meta http-equiv="X-UA-Compatible" content="IE=edge" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <!-- Bootstrap -->
    <link
rel="stylesheet"
href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css"
        />
    <link rel="stylesheet" href="https://www.w3schools.com/w3css/4/w3.css" />

    <link
rel="stylesheet"
href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css"
        />
    <script
src="/javascript/historyOrder.js"
defer
    ></script>
    <link
rel="stylesheet"
href="/css/historyOrder.css"
        />
<title>History Order</title>
  </head>

  <body>
    <!-------------------- HEADER-------------------->
    <div class="nav">
      <!-- Hamburger button -->
      <div class="nav-button">
        <button id="menu-toggle" class="btn">&#9776;</button>
      </div>

      <!-- Logo -->
      <div class="nav-logo">
        <p>LOGO</p>
      </div>

      <!-- Nav Menu -->
      <div class="nav-menu" id="navMenu">
        <ul>
          <li><a href="Homepage.html" class="link">Home</a></li>
          <li><a href="about.html" class="link">About</a></li>
          <li><a href="blog.html" class="link">Blog</a></li>
          <li><a href="service.html" class="link">Services</a></li>
          <li><a href="Availableproject.html" class="link">Product</a></li>

          <!-- Cart -->
          <li>
            <a href="#" class="icon">
              <i class="fas fa-shopping-cart"></i>
              <span class="notification-badge">0</span>
            </a>
          </li>

          <li>
            <a href="#" class="icon" id="accountIcon">
              <i class="fas fa-user-circle"></i>Username
        <i class="fas fa-caret-down"></i>
            </a>

            <!-- Dropdown menu -->
            <ul class="dropdown-menu">
              <li>
                <a href="profile.html"
        ><i class="fas fa-user-circle"></i> Profile</a
                >
              </li>
              <li>
                <a href="Homepage.html"
        ><i class="fas fa-sign-out-alt"></i> Log Out</a
                >
              </li>
            </ul>
          </li>
        </ul>
      </div>
    </div>

    <!--  content -->
    <!-- Hero Section -->
    <div class="history-container">
<h1>HISTORY ORDER</h1>
      <div class="hero-content">
        <ol class="breadcrumb">
          <li><a href="profile.html">Profile</a></li>
          <li class="active"><a href="historyOrder.html">History Order</a></li>
        </ol>
      </div>
    </div>
    <div class="search-box">
      <input
type="text"
id="searchOrders"
placeholder="Enter order ID or keyword"
onkeyup="filterOrders()"
        />
      <i class="fa-solid fa-magnifying-glass"></i>
    </div>
    <div class="filter-sort-container">
      <!-- Date Range Filter -->
      <div class="date-filter">
        <label for="startDate">From:</label>
        <input type="date" id="startDate" onchange="filterByDate()" />
        <label for="endDate">To:</label>
        <input type="date" id="endDate" onchange="filterByDate()" />
      </div>

      <!-- Sort by Status -->
      <div class="sort-container">
        <label for="sortStatus">Sort by:</label>
        <select id="sortStatus" onchange="sortOrders()">
          <option value="all">All</option>
          <option value="cancelled">Cancelled</option>
          <option value="in-process">In process</option>
          <option value="delivered">Delivered</option>
        </select>
      </div>
    </div>

    <!-- Main content -->
    <div class="order-card">
      <div class="order-status">
        <div class="timeline"></div>
        <ul>
          <li class="status-item completed">
Depart <br /><span>24.05.2024</span>
          </li>
          <li class="status-item completed">
Shipped <br /><span>24.05.2024</span>
          </li>
          <li class="status-item in-progress">
Delivered <br /><span>24.05.2024</span>
          </li>
        </ul>
      </div>

      <div class="order-details">
        <button class="close-btn">×</button>
        <div class="image-container">
          <img
src="/src/main/resources/static/img/a2.jpg"
alt="Product Image"
        />
        </div>
        <div class="order-info">
          <p class="order-id">
        #1<span class="status cancelled">Cancelled</span>
          </p>
          <p class="order-date">24.05.2024</p>
          <p class="location-title">Order location</p>
          <p class="location">123,đường hihi, phường CCC, quận 1</p>
        </div>

        <div class="order-price">
Total: <span class="price">100.000.000VND</span>
        </div>
      </div>
    </div>

    <!-- Order Card 2 with Multiple Images -->

    <div class="order-card">
      <div class="order-status">
        <div class="timeline"></div>
        <ul>
          <li class="status-item completed">
Depart <br /><span>24.05.2024</span>
          </li>
          <li class="status-item completed">
Shipped <br /><span>24.05.2024</span>
          </li>
          <li class="status-item delivered">
Delivered <br /><span>24.05.2024</span>
          </li>
        </ul>
      </div>

      <div class="order-details">
        <button class="close-btn">×</button>
        <div class="image-container">
          <img
src="/src/main/resources/static/img/a2.jpg"
alt="Product Image"
        />
        </div>
        <div class="order-info">
          <p class="order-id">
        #3 <span class="status in-process">In process</span>
          </p>
          <p class="order-date">24.05.2024</p>
          <p class="location-title">Order location</p>
          <p class="location">123, đường ABC, phường DEF, Quận 2</p>
        </div>
        <div class="order-price">
Total: <span class="price">100.000.000VND</span>
        </div>
      </div>
    </div>

    <div class="page-bar">
      <a href="#" class="w3-bar-item w3-button">&laquo;</a>
      <a href="#" class="w3-bar-item w3-button">1</a>
      <a href="#" class="w3-bar-item w3-button">2</a>
      <a href="#" class="w3-bar-item w3-button">3</a>
      <a href="#" class="w3-bar-item w3-button">4</a>
      <a href="#" class="w3-bar-item w3-button">&raquo;</a>
    </div>

    <!-- ---------------------------------------------------------------------------------------------------------- -->
    <!-- Sidebar -->
    <div class="sidebar" id="sidebar">
      <div class="user-info">
        <img src="avatar.png" alt="User Avatar" class="user-avatar" />
        <p class="username">Username</p>
      </div>
      <ul class="sidebar-menu">
        <li>
          <a href="Homepage.html"><i class="fas fa-home"></i> Home</a>
        </li>
        <li>
          <a href="historyOrder.html"
        ><i class="fas fa-history"></i> History Order</a
          >
        </li>
        <li>
          <a href="profile.html"><i class="fas fa-user-circle"></i> Profile</a>
        </li>
        <li>
          <a href="#"><i class="fas fa-cog"></i> Settings</a>
        </li>
        <li>
          <a href="Homepage.html"
        ><i class="fas fa-sign-out-alt"></i> Log Out</a
          >
        </li>
      </ul>
    </div>

    <!-- -------------------------------------- Footer ----------------------------------------------------------------- -->
    <div class="footer">
      <div class="footer-container">
        <div class="footer-info">
          <h4>Information</h4>
          <p>Open: 8 AM - 10 PM</p>
          <p>
PPFH+362, Phú Mỹ Hưng, Khu đô thị Phú Mỹ Hưng, Quận 7, TP. Hồ Chí
Minh
        </p>
        </div>
        <div class="footer-services">
          <h4>Services</h4>
          <ul>
            <li>
              <a href="link-to-construction" class="service-item"
        >Construction Koi pond</a
              >
            </li>
            <li>
              <a href="link-to-cleaning" class="service-item"
        >Cleaning and maintenance</a
              >
            </li>
            <li>
              <a href="link-to-design" class="service-item"
        >Design upon requirements</a
              >
            </li>
          </ul>
        </div>
        <div class="footer-contact">
          <h4>Contact</h4>
          <p><i class="fa-solid fa-phone"></i> 0972423242</p>
          <p><i class="fa-solid fa-envelope"></i> koipond@gmail.com</p>
          <p>
            <i class="fa-brands fa-facebook"></i>
            <a href="https://facebook.com" target="_blank">Visit Us</a>
          </p>
        </div>
      </div>
    </div>
  </body>
</html>