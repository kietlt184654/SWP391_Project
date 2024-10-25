document.addEventListener('DOMContentLoaded', function() {
    const rows = document.querySelectorAll('tbody tr');
    rows.forEach(row => {
        row.addEventListener('click', function() {
            alert(`You clicked on project with ID: ${this.querySelector('td').innerText}`);
        });
    });
});
