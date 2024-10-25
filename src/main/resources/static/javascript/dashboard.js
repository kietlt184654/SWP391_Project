// project chart
const ctx = document.getElementById("projectChart").getContext("2d");
const projectChart = new Chart(ctx, {
  type: "bar", // Loại biểu đồ: bar (biểu đồ cột)
  data: {
    labels: [
      "Dự án đang thi công",
      "Dự án đã hoàn thành",
      "Dự án mới trong tháng",
    ], // Nhãn cho trục X
    datasets: [
      {
        label: "Số lượng dự án", // Chú thích của biểu đồ
        data: [15, 25, 30], // Dữ liệu cho trục Y (số dự án)
        backgroundColor: [
          "rgba(54, 162, 235, 0.5)", // Màu sắc của các cột
          "rgba(75, 192, 192, 0.5)",
        ],
        borderColor: ["rgba(54, 162, 235, 1)", "rgba(75, 192, 192, 1)"],
        borderWidth: 1,
      },
    ],
  },
  options: {
    scales: {
      y: {
        beginAtZero: true, // Trục Y bắt đầu từ 0
      },
    },
  },
});

// customer chart
const btx = document.getElementById("customerChart").getContext("2d");
const customerChart = new Chart(btx, {
  type: "pie",
  data: {
    labels: ["Khách hàng mới", "Khách hàng tiềm năng", "Dự án mới trong tháng"], // Nhãn cho trục X
    datasets: [
      {
        label: "Số lượng dự án", // Chú thích của biểu đồ
        data: [10, 25, 30], // Dữ liệu cho trục Y (số dự án)
        backgroundColor: [
          "rgba(54, 162, 235, 0.5)", // Màu sắc của các cột
          "rgba(75, 192, 192, 0.5)",
        ],
        borderColor: ["rgba(54, 162, 235, 1)", "rgba(75, 192, 192, 1)"],
        borderWidth: 1,
      },
    ],
  },
  options: {
    scales: {
      y: {
        beginAtZero: true, // Trục Y bắt đầu từ 0
      },
    },
  },
});
