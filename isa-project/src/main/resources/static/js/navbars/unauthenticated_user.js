$(document).ready(function () {
    $('body').prepend($(
        '<nav class="navbar navbar-expand-lg navbar-dark bg-dark fixed-top">'
        + '<div class="collapse navbar-collapse" id="navbarNav">'
        + ' <ul class="navbar-nav">'
        + '  <li class="nav-item">'
        + '  <a class="nav-link" href="/html/user/login.html">Log in</a>'
        + '  </li>'
        + '  <li class="nav-item">'
        + '  <a class="nav-link" href="/html/user/drug_search.html">Drug search</a>'
        + '  </li>'
        + '  <li class="nav-item">'
        + '  <a class="nav-link" href="/html/user/pharmacy_search.html">Pharmacy search</a>'
        + '  </li>'
        + '  <li class="nav-item">'
        + '  <a class="nav-link" href="/html/user/patient_registration.html">Registration</a>'
        + '  </li>'        
        + ' </ul>'
        + ' </div>'
        + ' </nav>'
    ));
});