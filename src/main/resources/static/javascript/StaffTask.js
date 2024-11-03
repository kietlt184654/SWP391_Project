// Reload the page to update the status after form submission
if (window.performance && window.performance.navigation.type === window.performance.navigation.TYPE_BACK_FORWARD) {
    window.location.reload();
}
