document.addEventListener('DOMContentLoaded', function () {
    const passwordInput = document.getElementById('password');
    const toggleIcon = document.getElementById('input-icon');

    toggleIcon.addEventListener('click', function () {
        // Toggle the input type between password and text
        const type = passwordInput.getAttribute('type') === 'password' ? 'text' : 'password';
        passwordInput.setAttribute('type', type);
        
        // Toggle the icon class for the eye icon
        this.classList.toggle('ri-eye-off-line');
        this.classList.toggle('ri-eye-line');
    });
});
