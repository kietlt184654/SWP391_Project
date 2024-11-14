$('.rating-stars i').on('click', function() {
    let selectedStar = $(this).data('value');
    let projectID = $(this).closest('.rating-stars').attr('id').split('-')[1];

    $('#stars-' + projectID + ' i').each(function() {
        $(this).removeClass('selected');
        if ($(this).data('value') <= selectedStar) {
            $(this).addClass('selected');
        }
    });

    $('#ratingInput-' + projectID).val(selectedStar);
});

function submitFeedback(projectID) {
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
        success: function(response) {
            alert(response);
            $('#feedbackForm-' + projectID).collapse('hide');

            // Update interface after successfully submitting feedback
            $('#feedbackForm-' + projectID).parent().html(
                `<p><strong>Rating:</strong> ${rating} stars</p>
                 <p><strong>Feedback:</strong> ${feedback}</p>`
            );
        },
        error: function(xhr) {
            alert(xhr.responseText || "An error occurred, please try again.");
        }
    });
}