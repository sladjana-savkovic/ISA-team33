$(document).ready(function () {
    $('body').prepend($(
        '<nav class="navbar navbar-expand-lg navbar-dark bg-dark fixed-top">'
        + '<div class="collapse navbar-collapse" id="navbarNav">'
        + ' <ul class="navbar-nav">'
        + '  <li class="nav-item">'
        + '  <a class="nav-link" href="/html/doctor/calendar.html">Working calendar</a>'
        + '  </li>'
		+ '  <li class="nav-item">'
        + '  <a class="nav-link" href="/html/doctor/examined_patients.html">Examined patients</a>'
        + '  </li>'
		+ ' <li class="nav-item">'
        + '  <a class="nav-link" href="/html/doctor/vacation_request.html">Vacation request</a>'
        + '  </li>'
		+ ' <li class="nav-item">'
        + '  <a class="nav-link" href="/html/doctor/profile.html">Profile</a>'
        + '  </li>'
        + ' </ul>'
        + ' </div>'
        + ' </nav>'
    ));
});