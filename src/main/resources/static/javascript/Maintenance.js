document.addEventListener('DOMContentLoaded', function() {
    const calendar = document.getElementById('calendar');
    const bookingForm = document.getElementById('bookingForm');
    const selectedDateInput = document.getElementById('selectedDate');
    const messageDiv = document.getElementById('message');
    const currentDateDisplay = document.getElementById('currentDateDisplay');

    let currentDate = new Date();
    const bookedDates = new Set(); // Store booked dates

    function updateDateDisplay() {
        const options = { year: 'numeric', month: 'long', day: 'numeric' };
        currentDateDisplay.textContent = currentDate.toLocaleDateString(undefined, options);
    }

    function generateCalendar() {
        const month = currentDate.getMonth();
        const year = currentDate.getFullYear();
        const firstDay = new Date(year, month, 1).getDay();
        const daysInMonth = new Date(year, month + 1, 0).getDate();

        // Clear previous calendar
        calendar.innerHTML = '';

        // Add empty cells for days before the first day of the month
        for (let i = 0; i < firstDay; i++) {
            const emptyCell = document.createElement('div');
            calendar.appendChild(emptyCell);
        }

        // Add days of the month
        for (let day = 1; day <= daysInMonth; day++) {
            const dayCell = document.createElement('div');
            dayCell.className = 'day';
            dayCell.textContent = day;

            // Check if the date is booked
            const dateKey = `${year}-${month + 1}-${day}`;
            if (bookedDates.has(dateKey)) {
                dayCell.classList.add('booked'); // Add booked class
            }

            dayCell.addEventListener('click', function() {
                if (!bookedDates.has(dateKey)) { // Only allow booking if not booked
                    document.querySelectorAll('.day').forEach(d => d.classList.remove('selected'));
                    dayCell.classList.add('selected');
                    selectedDateInput.value = dateKey;
                    bookingForm.style.display = 'block';
                }
            });

            calendar.appendChild(dayCell);
        }

        updateDateDisplay(); // Update the displayed date
    }

    document.getElementById('prevMonth').addEventListener('click', function() {
        currentDate.setMonth(currentDate.getMonth() - 1);
        generateCalendar();
    });

    document.getElementById('nextMonth').addEventListener('click', function() {
        currentDate.setMonth(currentDate.getMonth() + 1);
        generateCalendar();
    });

    document.getElementById('prevWeek').addEventListener('click', function() {
        currentDate.setDate(currentDate.getDate() - 7);
        generateCalendar();
    });

    document.getElementById('nextWeek').addEventListener('click', function() {
        currentDate.setDate(currentDate.getDate() + 7);
        generateCalendar();
    });

    document.getElementById('prevYear').addEventListener('click', function() {
        currentDate.setFullYear(currentDate.getFullYear() - 1);
        generateCalendar();
    });

    document.getElementById('nextYear').addEventListener('click', function() {
        currentDate.setFullYear(currentDate.getFullYear() + 1);
        generateCalendar();
    });

    bookingForm.addEventListener('submit', function(event) {
        event.preventDefault();
        const customerName = document.getElementById('customerName').value;
        const selectedDate = selectedDateInput.value;

        // Mark the date as booked
        bookedDates.add(selectedDate);
        messageDiv.textContent = `Booking successful for ${customerName} on ${selectedDate}.`;
        bookingForm.reset();
        bookingForm.style.display = 'none';
        generateCalendar(); // Regenerate calendar to show booked date

        // Display the success message
        document.getElementById('displayCustomerName').innerText = customerName;
        document.getElementById('displayPondSize').innerText = document.getElementById('pondSize').value;
        document.getElementById('displayServiceDate').innerText = selectedDate;
        document.getElementById('successMessage').style.display = 'block';
    });

    generateCalendar();

    // Add this to your existing JavaScript file (designService.js or Maintenance.js)
    document.getElementById('pondSize').addEventListener('change', function() {
        const size = this.value;
        let cost = 0;

        switch(size) {
            case 'small':
                cost = 1000;
                break;
            case 'medium':
                cost = 2000;
                break;
            case 'large':
                cost = 3000;
                break;
        }

        document.getElementById('costDisplay').innerText = `Estimated Cost: ${cost} USD`;
        document.getElementById('pondSize').value = size; // Set pond size in the form
    });

    // Assuming you have a way to set the selected date
    // Example: document.getElementById('selectedDate').value = '2023-10-01';
});
// side bar
const menuToggle = document.getElementById("menu-toggle");
const sidebar = document.getElementById("sidebar");

menuToggle.addEventListener("click", () => {
  sidebar.classList.toggle("show");
});