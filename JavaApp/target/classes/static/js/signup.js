function confirmPassword() {
    const pass = document.getElementById('password');
    const conPass = document.getElementById('confirmPassword');
    if (pass != conPass) {
        conPass.setCustomValidity("Password does not match");
        return false;
    }
    return true;
}