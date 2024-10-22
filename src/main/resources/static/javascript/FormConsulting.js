document.getElementById('invoiceForm').addEventListener('submit', function(e) {
    e.preventDefault();

    const designName = document.getElementById('designName').value;
    const WaterCapacity = document.getElementById('WaterCapacity').value;
    const designType = document.getElementById('designType').value;
    const description = document.getElementById('description').value;
    const img = document.getElementById('img').value;
    const size = document.getElementById('size').value;
    const price = document.getElementById('price').value;
    const ShapeOfPond = document.getElementById('ShapeOfPond').value;
    const estimatedCompletionTime = document.getElementById('estimatedCompletionTime').value;

    const resultDiv = document.getElementById('result');

    resultDiv.innerHTML = `
        <h2>Invoice Details</h2>
        <div><strong>Design Name:</strong> ${designName}</div>
        <div><strong>Water Capacity:</strong> ${WaterCapacity} Liters</div>
        <div><strong>Design Type:</strong> ${designType}</div>
        <div><strong>Description:</strong> ${description}</div>
        <div><strong>Image URL:</strong> <a href="${img}" target="_blank">View Image</a></div>
        <div><strong>Size:</strong> ${size} m²</div>
        <div><strong>Price:</strong> $${price}</div>
        <div><strong>Shape of Pond:</strong> ${ShapeOfPond}</div>
        <div><strong>Estimated Completion Time:</strong> ${estimatedCompletionTime} days</div>
    `;
});
