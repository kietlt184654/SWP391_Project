$(document).ready(function() {
    // Handle star rating click
    $(document).on('click', '.rating-stars i', function() {
        let selectedStar = $(this).data('value');
        let ratingContainer = $(this).closest('.rating-stars');
        let projectID = parseInt(ratingContainer.closest('.collapse').data('project-id')); // Parse project ID as integer

        ratingContainer.find('i').removeClass('selected');
        $(this).prevAll().addBack().addClass('selected');

        ratingContainer.next('input[name="rating"]').val(selectedStar);
    });

    // Handle feedback submission
    $(document).on('click', '.submit-feedback', function() {
        let feedbackForm = $(this).closest('.collapse');
        let projectID = parseInt(feedbackForm.data('project-id')); // Parse project ID as integer
        let rating = feedbackForm.find('input[name="rating"]').val();
        let feedback = feedbackForm.find('.feedback-text').val().trim();

        if (rating === "0" || feedback === "") {
            alert("Please select a star rating and enter your feedback.");
            return;
        }

        // Disable the button during submission
        $(this).prop('disabled', true);

        $.ajax({
            url: '/submitFeedback',
            method: 'POST',
            data: { projectId: projectID, rating: rating, feedback: feedback },
            success: function(response) {
                alert(response);
                feedbackForm.collapse('hide');

                // Update interface after feedback submission
                feedbackForm.parent().html(
                    `<p><strong>Rating:</strong> ${rating} stars</p>
                     <p><strong>Feedback:</strong> ${feedback}</p>`
                );
            },
            error: function(xhr) {
                alert(xhr.responseText || "An error occurred, please try again.");
                console.error("AJAX Error:", xhr.responseText); // Debugging log
            },
            complete: function() {
                // Enable button after submission completes
                feedbackForm.find('.submit-feedback').prop('disabled', false);
            }
        });
    });
});
$(document).ready(function () {
    // Count projects in each status
    let totalProjects = $('.panel').length;
    let pendingCount = $('.panel:has(.panel-body span:contains("PENDING"))').length;
    let inProgressCount = $('.panel:has(.panel-body span:contains("IN_PROGRESS"))').length;
    let completeCount = $('.panel:has(.panel-body span:contains("COMPLETE"))').length;

    // Update counts in the status bar
    $('#allCount').text(totalProjects);
    $('#pendingCount').text(pendingCount);
    $('#inProgressCount').text(inProgressCount);
    $('#completeCount').text(completeCount);

    // Filter projects based on status
    $('.status-item').on('click', function () {
        $('.status-item').removeClass('active');
        $(this).addClass('active');

        let status = $(this).data('status');

        if (status === 'ALL') {
            $('.panel').show();
        } else {
            $('.panel').hide();
            $(`.panel:has(.panel-body span:contains("${status}"))`).show();
        }
    });
});
// Function to update the badge count dynamically
function updateBadge(count) {
    document.querySelector('.badge').textContent = count;
}
$(document).ready(function () {
    // Function to count projects by status
    function countProjectsByStatus() {
        let needToPendCount = 0;
        let pendingCount = 0;
        let inProgressCount = 0;
        let completeCount = 0;
        let canceledCount = 0; // Add count for Canceled projects
        let totalProjects = 0;

        // Loop through all project panels to count statuses
        $('.panel').each(function () {
            let status = $(this).find('p:contains("Status:") span').text().trim();
            totalProjects++;

            if (status === "Need to Pend") {
                needToPendCount++;
            } else if (status === "Pending") {
                pendingCount++;
            } else if (status === "In Progress") {
                inProgressCount++;
            } else if (status === "COMPLETE") {
                completeCount++;
            } else if (status === "Canceled") {
                canceledCount++; // Count Canceled projects
            }
        });

        // Update the counts in the status bar
        $('#needToPendCount').text(needToPendCount);
        $('#pendingCount').text(pendingCount);
        $('#inProgressCount').text(inProgressCount);
        $('#completeCount').text(completeCount);
        $('#canceledCount').text(canceledCount); // Update Canceled count

        // Update total count for "All"
        $('.status-item[data-status="ALL"] .count').remove();
        $('.status-item[data-status="ALL"]').append(`<span class="count">${totalProjects}</span>`);
    }

    // Function to filter projects by selected status
    function filterProjectsByStatus(selectedStatus) {
        let noProjectsFound = true;

        // Show or hide projects based on the selected status
        $('.panel').each(function () {
            let projectStatus = $(this).find('p:contains("Status:") span').text().trim();

            if (selectedStatus === "ALL") {
                // Show all projects
                $(this).show();
                noProjectsFound = false;
            } else if (
                (selectedStatus === "NEED_TO_PEND" && projectStatus === "Need to Pend") ||
                (selectedStatus === "PENDING" && projectStatus === "Pending") ||
                (selectedStatus === "IN_PROGRESS" && projectStatus === "In Progress") ||
                (selectedStatus === "COMPLETE" && projectStatus === "COMPLETE") ||
                (selectedStatus === "CANCELED" && projectStatus === "Canceled")
            ) {
                $(this).show();
                noProjectsFound = false;
            } else {
                $(this).hide();
            }
        });

        // If no projects are found for the selected status, display "No Project"
        if (noProjectsFound) {
            $('.project-container').append(`<p id="noProjectsMessage">No Project</p>`);
        } else {
            $('#noProjectsMessage').remove();
        }
    }

    // Initial count on page load
    countProjectsByStatus();

    // Add click event to filter projects by status
    $('.status-item').on('click', function () {
        // Get the selected status from the data-status attribute
        let selectedStatus = $(this).data('status');

        // Highlight the active filter
        $('.status-item').removeClass('active');
        $(this).addClass('active');

        // Filter projects by selected status
        filterProjectsByStatus(selectedStatus);
    });


// // Example usage
// $(document).ready(function () {
//     function countProjectsByStatus() {
//         // Initialize counts
//         let needToPendCount = 0;
//         let pendingCount = 0;
//         let inProgressCount = 0;
//         let completeCount = 0;
//
//         // Loop through each project panel
//         $('.panel').each(function () {
//             // Extract the status text
//             let status = $(this).find('p:contains("Status:")').text().replace("Status:", "").trim();
//             console.log("Detected Status:", status); // Debug log
//
//             // Increment counts based on the status
//             if (status === "Need to Pend") {
//                 needToPendCount++;
//             } else if (status === "Pending") {
//                 pendingCount++;
//             } else if (status === "In Progress") {
//                 inProgressCount++;
//             } else if (status === "COMPLETE") {
//                 completeCount++;
//             }
//         });
//
//         // Update the status bar counts
//         $('#needToPendCount').text(needToPendCount);
//         $('#pendingCount').text(pendingCount);
//         $('#inProgressCount').text(inProgressCount);
//         $('#completeCount').text(completeCount);
//
//         console.log("Counts:", {
//             needToPendCount,
//             pendingCount,
//             inProgressCount,
//             completeCount,
//         }); // Debug log
//     }
//
//     // Call the function to update counts
//     countProjectsByStatus();
// });
// $(document).ready(function () {
//     function countProjectsByStatus() {
//         let needToPendCount = 0;
//         let pendingCount = 0;
//         let inProgressCount = 0;
//         let completeCount = 0;
//         let totalProjects = 0;
//
//         // Loop through all project panels to count statuses
//         $('.panel').each(function () {
//             let status = $(this).find('p:contains("Status:") span').text().trim();
//             totalProjects++;
//
//             if (status === "Need to Pend") {
//                 needToPendCount++;
//             } else if (status === "Pending") {
//                 pendingCount++;
//             } else if (status === "In Progress") {
//                 inProgressCount++;
//             } else if (status === "COMPLETE") {
//                 completeCount++;
//             }
//         });
//
//         // Update the counts in the status bar
//         $('#needToPendCount').text(needToPendCount);
//         $('#pendingCount').text(pendingCount);
//         $('#inProgressCount').text(inProgressCount);
//         $('#completeCount').text(completeCount);
//
//         // Update total count for "All"
//         $('.status-item[data-status="ALL"] .count').remove();
//         $('.status-item[data-status="ALL"]').append(`<span class="count">${totalProjects}</span>`);
//     }
//
//     function filterProjectsByStatus(selectedStatus) {
//         let noProjectsFound = true;
//
//         // Show or hide projects based on the selected status
//         $('.panel').each(function () {
//             let projectStatus = $(this).find('p:contains("Status:") span').text().trim();
//
//             if (selectedStatus === "ALL") {
//                 // Show all projects
//                 $(this).show();
//                 noProjectsFound = false;
//             } else if (
//                 (selectedStatus === "NEED_TO_PEND" && projectStatus === "Need to Pend") ||
//                 (selectedStatus === "PENDING" && projectStatus === "Pending") ||
//                 (selectedStatus === "IN_PROGRESS" && projectStatus === "In Progress") ||
//                 (selectedStatus === "COMPLETE" && projectStatus === "COMPLETE")
//             ) {
//                 $(this).show();
//                 noProjectsFound = false;
//             } else {
//                 $(this).hide();
//             }
//         });
//
//         // If no projects are found for the selected status, display "No Project"
//         if (noProjectsFound) {
//             $('.project-container').append(`<p id="noProjectsMessage">No Project</p>`);
//         } else {
//             $('#noProjectsMessage').remove();
//         }
//     }
//
//     // Initial count on page load
//     countProjectsByStatus();
//
//     // Add click event to filter projects by status
//     $('.status-item').on('click', function () {
//         // Get the selected status from the data-status attribute
//         let selectedStatus = $(this).data('status');
//
//         // Highlight the active filter
//         $('.status-item').removeClass('active');
//         $(this).addClass('active');
//
//         // Filter projects by selected status
//         filterProjectsByStatus(selectedStatus);
//     });

    // Handle Cancel Button
    function confirmCancel(form) {
        const confirmed = confirm("Are you sure you want to cancel this project?");
        if (confirmed) {
            return true; // Gửi form nếu người dùng xác nhận
        }
        return false; // Ngăn việc gửi form nếu người dùng từ chối
    }


    // Handle star rating click
    $('.rating-stars i').on('click', function () {
        let selectedStar = $(this).data('value');
        let projectID = $(this).closest('.rating-stars').attr('id').split('-')[1];

        $('#stars-' + projectID + ' i').each(function () {
            $(this).removeClass('selected');
            if ($(this).data('value') <= selectedStar) {
                $(this).addClass('selected');
            }
        });

        $('#ratingInput-' + projectID).val(selectedStar);
    });

    // Function to submit feedback
    window.submitFeedback = function (projectID) {
        let rating = $('#ratingInput-' + projectID).val();
        let feedback = $('#feedbackText-' + projectID).val();

        if (rating === "0" || feedback.trim() === "") {
            alert("Please select a star rating and enter your feedback.");
            return;
        }

        $.ajax({
            url: '/submitFeedback',
            method: 'POST',
            data: { projectId: projectID, rating: rating, feedback: feedback },
            success: function (response) {
                alert(response);
                $('#feedbackForm-' + projectID).collapse('hide');

                // Update interface after successfully submitting feedback
                $('#feedbackForm-' + projectID).parent().html(
                    `<p><strong>Rating:</strong> ${rating} stars</p>
                     <p><strong>Feedback:</strong> ${feedback}</p>`
                );
            },
            error: function (xhr) {
                alert(xhr.responseText || "An error occurred, please try again.");
            }
        });
    };
});

