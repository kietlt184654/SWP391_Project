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

    $(document).on('click', '.submit-feedback', function() {
        let feedbackForm = $(this).closest('.collapse');
        let projectID = parseInt(feedbackForm.data('project-id')); // Parse project ID as integer
        let rating = feedbackForm.find('input[name="rating"]').val();
        let feedback = feedbackForm.find('.feedback-text').val().trim();

        console.log("Submitting feedback with project ID:", projectID);
        console.log("Rating:", rating, "Feedback:", feedback);

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
